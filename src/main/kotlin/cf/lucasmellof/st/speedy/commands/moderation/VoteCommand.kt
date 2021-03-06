package cf.lucasmellof.st.speedy.commands.moderation

import cf.lucasmellof.st.speedy.SpeedyLauncher.config
import cf.lucasmellof.st.speedy.core.commands.Category
import cf.lucasmellof.st.speedy.core.commands.Command
import cf.lucasmellof.st.speedy.core.commands.CommandEvent
import cf.lucasmellof.st.speedy.utils.emptyEmbedBuilder
import cf.lucasmellof.st.speedy.utils.getGithubAsset

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 19/04/2020
 */
class VoteCommand : Command("votação", Category.MODERATION, description = "Cria uma votação.", example = " [votação]", isStaffOnly = true, aliases = listOf("votar")) {
    override suspend fun execute(args: List<String>, e: CommandEvent) {
        if (args.isNullOrEmpty()) {
            e.missingArgs()
            return
        }
        e.getMessage().delete().queue()
        val embed = emptyEmbedBuilder(e.getJDA(), e.getAuthor())
                .setTitle("Votação")
                .setThumbnail(getGithubAsset("vote"))
                .setDescription("Votação iniciada por ${e.getAuthor().name}")
                .addField(args.joinToString(" "), "", true)
        val channel = e.getJDA().getTextChannelById(config.voteChannel)!!
        channel.sendMessage(embed.build())
                .queue {
                    it.addReaction("asim:701247371518148608").queue()
                    it.addReaction("ano:701247371891310622").queue()
                }
        channel.sendMessage(e.getJDA().getRoleById(config.everyoneRole)!!.asMention).queue {
            it.delete().queue()
        }
    }
}