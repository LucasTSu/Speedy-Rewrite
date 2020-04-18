package cf.lucasmellof.st.speedy.utils

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.awt.Color
import java.util.function.Consumer

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
fun emptyEmbedBuilder(jda: JDA, user: User):EmbedBuilder{
    val embed = EmbedBuilder()
        .setColor(Color.MAGENTA)
        .setAuthor("Speedy - Informações", null, jda.selfUser.avatarUrl)
        .setFooter("Pedido por ${user.name}", user.effectiveAvatarUrl)
    return embed
}
fun basicEmbedBuilder(jda: JDA, name: String, value: String, inline: Boolean, user: User): EmbedBuilder {
    val embed = EmbedBuilder()
        .setColor(Color.MAGENTA)
        .setAuthor("Speedy - Informações", null, jda.selfUser.avatarUrl)
        .addField(name, value, inline)
        .setFooter("Pedido por ${user.name}", user.effectiveAvatarUrl)
    return embed
}

fun basicEmbedBuilder(jda: JDA, name: String, value: String, inline: Boolean, user: User, channel: MessageChannel) {
    return channel.sendMessage(basicEmbedBuilder(jda, name, value, inline, user).build()).queue()
}
var ready = false
fun currentTimeMilis() = System.currentTimeMillis()
