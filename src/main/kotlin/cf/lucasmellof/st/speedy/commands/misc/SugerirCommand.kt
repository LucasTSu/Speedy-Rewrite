package cf.lucasmellof.st.speedy.commands.misc

import cf.lucasmellof.st.speedy.SpeedyLauncher.config
import cf.lucasmellof.st.speedy.core.commands.Category
import cf.lucasmellof.st.speedy.core.commands.Command
import cf.lucasmellof.st.speedy.core.commands.CommandEvent
import cf.lucasmellof.st.speedy.utils.basicEmbedBuilder
import cf.lucasmellof.st.speedy.utils.emptyEmbedBuilder
import cf.lucasmellof.st.speedy.utils.getGithubAsset
import java.util.concurrent.TimeUnit

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 18/04/2020
 */
class SugerirCommand : Command("sugerir",Category.MISC,"Cria uma sugestão.", example = " [sugestão]") {
    override suspend fun execute(args: List<String>, e: CommandEvent) {
        if(args.isNullOrEmpty()) {
            e.missingArgs()
            return
        }
        e.getMessage().delete().queue()
        val embed = emptyEmbedBuilder(e.getJDA(),e.getAuthor())
            .setTitle("Sugestão")
            .setThumbnail(getGithubAsset("suggestion"))
            .setDescription("Sugerido por ${e.getAuthor().name}")
            .addField(args.joinToString(" "),"",true)
        e.getJDA().getTextChannelById(config.sugestionChannel)!!.sendMessage(embed.build()).queue(){
            it.addReaction("asim:701247371518148608").queue()
            it.addReaction("ano:701247371891310622").queue()
        }
    }
}