package cf.lucasmellof.st.speedy.utils

import cf.lucasmellof.st.speedy.Speedy.Companion.logger
import cf.lucasmellof.st.speedy.core.commands.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import org.json.JSONObject
import java.awt.Color
import java.io.IOException
import java.lang.management.ManagementFactory
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter
import java.util.*


/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 17/04/2020
 */
fun printCoolLogo() = println(
    """
    _____                     __     
  / ___/____  ___  ___  ____/ /_  __
  \__ \/ __ \/ _ \/ _ \/ __  / / / /
 ___/ / /_/ /  __/  __/ /_/ / /_/ / 
/____/ .___/\___/\___/\__,_/\__, /  
    /_/                    /____/ 
""".trimIndent()
)

val random = Random()
var ready = false
fun stripFormatting(text: String): String = text.replace("@", "\\@")
    .replace("~~", "\\~\\~")
    .replace("*", "\\*")
    .replace("`", "\\`")
    .replace("_", "\\_")

fun parseTime(milliseconds: Long): String {
    val seconds = milliseconds / 1000 % 60
    val minutes = milliseconds / (1000 * 60) % 60
    val hours = milliseconds / (1000 * 60 * 60) % 24
    val days = milliseconds / (1000 * 60 * 60 * 24)

    return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds)
}

fun emptyEmbedBuilder(jda: JDA, user: User): EmbedBuilder {
    return EmbedBuilder()
            .setColor(Color.MAGENTA)
            .setAuthor("Speedy - Informações", null, jda.selfUser.avatarUrl)
            .setFooter("Pedido por ${user.name}", user.effectiveAvatarUrl)
}

fun basicEmbedBuilder(jda: JDA, name: String, value: String, inline: Boolean, user: User): EmbedBuilder {
    return EmbedBuilder()
            .setColor(Color.MAGENTA)
            .setAuthor("Speedy - Informações", null, jda.selfUser.avatarUrl)
            .addField(name, value, inline)
            .setFooter("Pedido por ${user.name}", user.effectiveAvatarUrl)
}

fun basicEmbedBuilder(jda: JDA, name: String, value: String, inline: Boolean, user: User, channel: MessageChannel) {
    return channel.sendMessage(basicEmbedBuilder(jda, name, value, inline, user).build()).queue()
}

fun currentTimeMilis() = System.currentTimeMillis()
fun getGithubAsset(image: String) = "https://github.com/LucasTSu/Speedy-Rewrite/blob/master/assets/$image.png?raw=true"
fun getGithubAsset(image: String, extension: String) = "https://github.com/LucasTSu/Speedy-Rewrite/blob/master/assets/$image.$extension?raw=true"
fun getUptime(): String? {
    val duration = ManagementFactory.getRuntimeMXBean().uptime
    val years = duration / 31104000000L
    val months = duration / 2592000000L % 12
    val days = duration / 86400000L % 30
    val hours = duration / 3600000L % 24
    val minutes = duration / 60000L % 60
    val seconds = duration / 1000L % 60
    var uptime =
            ((if (years == 0L) "" else "$years Anos, ") + (if (months == 0L) "" else "$months Meses, ")
                    + (if (days == 0L) "" else "$days Dias, ") + (if (hours == 0L) "" else "$hours Horas, ")
                    + (if (minutes == 0L) "" else "$minutes Minutos, ") + if (seconds == 0L) "" else "$seconds Segundos")
    uptime = replaceLast(uptime, ", ", "")
    return replaceLast(uptime, ",", " e")
}

fun replaceLast(text: String, regex: String, replacement: String): String {
    return text.replaceFirst("(?s)(.*)" + regex.toRegex(), "$1$replacement")
}

private const val K: Long = 1024
private const val M = K * K
private const val G = M * K
private const val T = G * K
fun convertToStringRepresentation(value: Long): String? {
    val dividers = longArrayOf(T, G, M, K, 1)
    val units = arrayOf("TB", "GB", "MB", "KB", "B")
    if (value < 1)
        throw IllegalArgumentException("Invalid file size: $value")
    var result: String? = null
    for (i in dividers.indices) {
        val divider = dividers[i]
        if (value >= divider) {
            result = format(value, divider, units[i])
            break
        }
    }
    return result
}

private fun format(value: Long, divider: Long, unit: String): String {
    val result = if (divider > 1) value.toDouble() / divider.toDouble() else value.toDouble()
    return DecimalFormat("#,##0.#").format(result) + " " + unit
}

fun convertStringToMember(str: String, e: CommandEvent): Member? {
    if (str.isEmpty())
        return null

    val id = Regex("<@!?(\\d{17,20})>").find(str)?.groups?.get(1)?.value ?: str
    val member = try {
        e.getGuild().getMemberById(id)
    } catch (e: NumberFormatException) {
        null
    }
    if (member != null)
        return member

    val (username, discrim) = convertUsernameDiscrim(str)
    return if (discrim != null)
        e.getGuild().members.find { it.user.name == username && it.user.discriminator == discrim }
    else
        e.getGuild().members.find { it.user.name == str }
}

fun convertUsernameDiscrim(str: String): Pair<String?, String?> {
    return Regex("(.{1,32})#(\\d{4})").find(str).let {
        if (it == null)
            null to null
        else
            it.groups[1]?.value to it.groups[2]?.value
    }
}

fun getDateFormatter() = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS")

fun sendToHastebin(data: String?): String? {
    val client: CloseableHttpClient? = HttpClientBuilder.create().build()
    val post = HttpPost("https://hasteb.in/documents")
    try {
        post.entity = StringEntity(data)
        val response: CloseableHttpResponse? = client?.execute(post)
        val result = EntityUtils.toString(response?.entity)
        return "https://hasteb.in/" + JSONObject(result).getString("key")
    } catch (e: IOException) {
        logger.warn(data)
    }
    return data
}