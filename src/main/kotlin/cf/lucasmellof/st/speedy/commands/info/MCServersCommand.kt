package cf.lucasmellof.st.speedy.commands.info

import cf.lucasmellof.st.speedy.core.commands.Category
import cf.lucasmellof.st.speedy.core.commands.Command
import cf.lucasmellof.st.speedy.core.commands.CommandEvent
import cf.lucasmellof.st.speedy.utils.emptyEmbedBuilder
import cf.lucasmellof.st.speedy.utils.getGithubAsset
import com.github.kevinsawicki.http.HttpRequest
import com.google.gson.JsonParser

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 18/04/2020
 */
class MCServersCommand : Command("mcservers", Category.INFO, "Informa se o status dos servidor do Minecraft") {
    override suspend fun execute(args: List<String>, e: CommandEvent) {
        val embed = emptyEmbedBuilder(e.getJDA(), e.getAuthor())
        JsonParser.parseString(HttpRequest.get("https://status.mojang.com/check").body()).asJsonArray.forEach {
            val service = it.asJsonObject.entrySet().first()
            val status = service.value.toString()
            val prefix = if (service.key.contains("minecraft")) "<:minecraft:665333984049365005>" else "<:mojang:665334564792696842>"
            val emoji = when (status) {
                "\"green\"" -> "<:green:701240310453502082>"
                "\"yellow\"" -> "<:yellow:701240310558228482>"
                "\"red\"" -> "<:red:701240310575136788>"
                else -> "<:gray:701240310184804384>"
            }
            embed.addField(prefix + service.key, emoji, true)
        }
        embed.setTitle("\uD83D\uDCE1 Status da Mojang")
                .setThumbnail(getGithubAsset("minecraftexp","gif"))
        e.reply(embed.build())
    }
}