package cf.lucasmellof.st.speedy.core.events

import cf.lucasmellof.st.speedy.SpeedyLauncher.config
import cf.lucasmellof.st.speedy.core.commands.Registry
import cf.lucasmellof.st.speedy.utils.ready
import net.dv8tion.jda.api.events.ReadyEvent

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 18/04/2020
 */
class ReadyEvent {
    fun onReady(e: ReadyEvent) {
        println(
            """
            ||-=========================================================
            || Account info: ${e.jda.selfUser.name}#${e.jda.selfUser.discriminator} (ID: ${e.jda.selfUser.id})
            || Connected to ${e.jda.guilds.size} guilds, ${e.jda.textChannels.size + e.jda.voiceChannels.size} channels
            || Default prefix: ${config.prefix}
            || Registered Commands: ${Registry.commands.size}
            || Status: ${e.jda.presence.status}
            || Presence: ${e.jda.presence.activity?.name}
            ||-=========================================================
            """.trimMargin("|")
        )
        ready = true
    }
}