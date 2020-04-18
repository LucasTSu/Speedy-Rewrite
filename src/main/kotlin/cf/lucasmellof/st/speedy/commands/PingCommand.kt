package cf.lucasmellof.st.speedy.commands

import cf.lucasmellof.st.speedy.core.commands.Category
import cf.lucasmellof.st.speedy.core.commands.Command
import cf.lucasmellof.st.speedy.core.commands.CommandEvent
import cf.lucasmellof.st.speedy.utils.currentTimeMilis
import cf.lucasmellof.st.speedy.utils.emptyEmbedBuilder
import java.lang.System.currentTimeMillis

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 18/04/2020
 */
class PingCommand : Command("ping", Category.INFO,"Retorna um ping estimado para os servidores do Discord") {
    override suspend fun execute(args: List<String>, e: CommandEvent) {
        val time = currentTimeMilis()
        e.sendTypying().queue{
            val embed = emptyEmbedBuilder(e.getJDA(),e.getAuthor())
                .setTitle("\uD83C\uDFD3 Pong!")
                .addField("Latency", "__**${currentTimeMillis() - time}ms**__", false)
                .addField("Websocket", "__**${e.getJDA().gatewayPing}ms**__", false)
            e.reply(embed.build())
        }
    }
}