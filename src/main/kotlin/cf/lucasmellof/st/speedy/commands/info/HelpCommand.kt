package cf.lucasmellof.st.speedy.commands.info

import cf.lucasmellof.st.speedy.core.commands.Category
import cf.lucasmellof.st.speedy.core.commands.Command
import cf.lucasmellof.st.speedy.core.commands.CommandEvent
import cf.lucasmellof.st.speedy.core.commands.Registry
import cf.lucasmellof.st.speedy.utils.emptyEmbedBuilder
import cf.lucasmellof.st.speedy.utils.getGithubAsset
import java.util.stream.Collectors

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 18/04/2020
 */
class HelpCommand : Command("help", Category.INFO, "Mostra a lista de comandos.", aliases = listOf("ajuda", "comandos")) {
    override suspend fun execute(args: List<String>, e: CommandEvent) {
        when (args.size) {
            0 -> helpEmbed(e)
            1 -> {
                when {
                    Registry.commands.contains(Registry.getCommandByName(args[0])) -> {
                        val cmd = Registry.getCommandByName(args[0])
                        val embed = emptyEmbedBuilder(e.getJDA(), e.getAuthor())
                            .setThumbnail(getGithubAsset("help"))
                            .setTitle(":bookmark_tabs: Ajuda para o comando: **${cmd.name}**")
                            .addField("Descrição:", "``${cmd.description}``", false)
                        if (!cmd.example.isNullOrBlank()) {
                            embed.addField("Exemplos:", "``${e.prefix}${cmd.name}${cmd.example}``", false)
                        }
                        embed.addField("Apenas desenvolvedor:", "``${if (cmd.isDeveloperOnly) "Sim" else "Não"}``", false)
                            .addField("Apenas equipe:", "``${if (cmd.isStaffOnly) "Sim" else "Não"}``", false)
                        e.reply(embed.build())
                    }
                    Registry.aliases.containsKey(args[0]) -> {
                        val cmd = Registry.aliases[args[0]]
                        val embed = emptyEmbedBuilder(e.getJDA(), e.getAuthor())
                            .setThumbnail(getGithubAsset("help"))
                            .setTitle(":bookmark_tabs: Ajuda para o comando: **${cmd!!.name}**")
                            .addField("Descrição:", "``${cmd.description}``", false)
                        if (!cmd.example.isNullOrBlank()) {
                            embed.addField("Exemplos:", "``${e.prefix}${cmd.name}${cmd.example}``", false)
                        }
                        embed.addField("Apenas desenvolvedor:", "``${if (cmd.isDeveloperOnly) "Sim" else "Não"}``", false)
                            .addField("Apenas equipe:", "``${if (cmd.isStaffOnly) "Sim" else "Não"}``", false)
                        e.reply(embed.build())
                    }
                    else -> helpEmbed(e)
                }
            }
            else -> helpEmbed(e)
        }
    }

    fun helpEmbed(event: CommandEvent) {
        val embed = emptyEmbedBuilder(event.getJDA(), event.getAuthor())
            .setTitle("Listando todos os comandos:")
            .setThumbnail(getGithubAsset("help"))
            .setDescription("Informações de cada comando em ``${event.prefix}help {comando}``")
        for (category in Category.values()) {
            val commands = getCommandByCategory(category)
            embed.addField("${category.emoji} | ${category.cName}", commands, false)
        }
        event.reply(embed.build())
    }

    private fun getCommandByCategory(category: Category): String? {
        return Registry.commands.filter { it.category == category }.map { "``${it.name}``" }.stream()
            .collect(Collectors.joining(", "))
    }
}