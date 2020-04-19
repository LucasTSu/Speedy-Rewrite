package cf.lucasmellof.st.speedy.commands.owner

import cf.lucasmellof.st.speedy.Speedy
import cf.lucasmellof.st.speedy.core.commands.Category
import cf.lucasmellof.st.speedy.core.commands.Command
import cf.lucasmellof.st.speedy.core.commands.CommandEvent
import cf.lucasmellof.st.speedy.enums.ExitStatus
import cf.lucasmellof.st.speedy.utils.emptyEmbedBuilder
import cf.lucasmellof.st.speedy.utils.getGithubAsset
import kotlin.system.exitProcess

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 18/04/2020
 */
class ShutdownCommand : Command("shutdown", Category.OWNER, "Finaliza o Speedy", aliases = listOf("desligar"), isDeveloperOnly = true) {
    override suspend fun execute(args: List<String>, e: CommandEvent) {
        val embed = emptyEmbedBuilder(e.getJDA(), e.getAuthor())
                .setTitle("Desligando")
               .setThumbnail(getGithubAsset("shutdown"))
                .setDescription("Encerrando todas as atividades!")
        e.reply(embed.build())
        Thread.sleep(1000)
        Speedy.jda.shutdownNow()
        exitProcess(ExitStatus.SHUTDOWN.code)
    }
}