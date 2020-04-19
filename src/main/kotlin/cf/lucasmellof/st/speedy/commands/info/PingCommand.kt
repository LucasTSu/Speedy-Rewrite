package cf.lucasmellof.st.speedy.commands.info

import cf.lucasmellof.st.speedy.core.commands.Category
import cf.lucasmellof.st.speedy.core.commands.Command
import cf.lucasmellof.st.speedy.core.commands.CommandEvent
import cf.lucasmellof.st.speedy.utils.currentTimeMilis
import cf.lucasmellof.st.speedy.utils.emptyEmbedBuilder
import cf.lucasmellof.st.speedy.utils.getGithubAsset
import java.lang.System.currentTimeMillis

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 18/04/2020
 */
class PingCommand : Command("ping", Category.INFO, "Retorna um ping estimado para os servidores do Discord") {
    override suspend fun execute(args: List<String>, e: CommandEvent) {
        val time = currentTimeMilis()
        e.sendTypying().queue {
            val embed = emptyEmbedBuilder(e.getJDA(), e.getAuthor())
                .setTitle("Pong!")
                .setThumbnail(getGithubAsset("ping_pong"))
                .addField(":zap: API", "__**${currentTimeMillis() - time}ms**__", false)
                .addField(":stopwatch: Gateway", "__**${e.getJDA().gatewayPing}ms**__", false)
            e.reply(embed.build())
        }
    }
}