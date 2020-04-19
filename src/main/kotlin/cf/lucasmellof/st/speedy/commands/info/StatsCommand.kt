package cf.lucasmellof.st.speedy.commands.info

import cf.lucasmellof.st.speedy.core.commands.Category
import cf.lucasmellof.st.speedy.core.commands.Command
import cf.lucasmellof.st.speedy.core.commands.CommandEvent
import cf.lucasmellof.st.speedy.utils.convertToStringRepresentation
import cf.lucasmellof.st.speedy.utils.emptyEmbedBuilder
import cf.lucasmellof.st.speedy.utils.getGithubAsset
import cf.lucasmellof.st.speedy.utils.getUptime
import net.dv8tion.jda.api.JDAInfo
import java.lang.management.ManagementFactory

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 18/04/2020
 */
class StatsCommand : Command("stats", Category.INFO, "Mostra algumas informações do Speedy") {
    override suspend fun execute(args: List<String>, e: CommandEvent) {
        val os = ManagementFactory.getOperatingSystemMXBean() as com.sun.management.OperatingSystemMXBean
        val embed = emptyEmbedBuilder(e.getJDA(), e.getAuthor())
                .setTitle("Status do Speedy")
                .setThumbnail(getGithubAsset("stats"))
                .addField(":information_desk_person: Usuários:", e.getJDA().users.size.toString(), true)
                .addField(":page_with_curl: Canais de texto:", e.getJDA().textChannels.size.toString(), true)
                .addField(":musical_note: Canais de voz:", e.getJDA().voiceChannels.size.toString(), true)
                .addField(":wave: Threads:", Thread.activeCount().toString(), true)
                .addField("<:discord:682685017566216281> Versão do JDA:", JDAInfo.VERSION, true)
                .addField(":vertical_traffic_light: Uso de RAM (LIVRE/TOTAL):", convertToStringRepresentation(Runtime.getRuntime().freeMemory()) + " / " +convertToStringRepresentation(Runtime.getRuntime().totalMemory()) , true)
                .addField(":watch: Tempo conectado:", getUptime(), true)
        e.getChannel().sendMessage(embed.build()).queue()
    }
}