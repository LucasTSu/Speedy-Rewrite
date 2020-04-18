package cf.lucasmellof.st.speedy.core.manager

import cf.lucasmellof.st.speedy.core.events.DiscordReadyEvent
import cf.lucasmellof.st.speedy.core.events.GuildMessageEvent
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.EventListener

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 17/04/2020
 */
class EventManager : EventListener {
    override fun onEvent(event: GenericEvent) {
        if (event is GuildMessageReceivedEvent) {
            GuildMessageEvent.onGuildMessageReceived(event)
        } else if (event is ReadyEvent) {
            DiscordReadyEvent.onReady(event)
        }
    }
}