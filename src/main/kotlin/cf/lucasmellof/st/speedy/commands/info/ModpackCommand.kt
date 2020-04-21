package cf.lucasmellof.st.speedy.commands.info

import cf.lucasmellof.st.speedy.core.commands.Category
import cf.lucasmellof.st.speedy.core.commands.Command
import cf.lucasmellof.st.speedy.core.commands.CommandEvent
import cf.lucasmellof.st.speedy.utils.emptyEmbedBuilder
import cf.lucasmellof.st.speedy.utils.getGithubAsset

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 21/04/2020
 */
class ModpackCommand : Command("modpack", Category.INFO, "Informa o modpack.") {
    override suspend fun execute(args: List<String>, e: CommandEvent) {
        val embed = emptyEmbedBuilder(e.getJDA(), e.getAuthor())
            .setThumbnail(getGithubAsset("modpack"))
            .setTitle("Modpacks")
            .addField(
                "**Industrial**",
                "ModPack: [Clique aqui](https://www.technicpack.net/modpack/speedtech.1557664) ou digite `Speedtech - Industrial` no Technic Launcher",
                true
            )
        e.reply(embed.build())
    }
}