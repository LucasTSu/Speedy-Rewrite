package cf.lucasmellof.st.speedy.core.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.requests.RestAction
import java.awt.Color
import java.util.function.Consumer

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 17/04/2020
 */
class CommandEvent(val event: GuildMessageReceivedEvent) {
    fun reply(message: Message) {
        sendTypying().queue()
        event.channel.sendMessage(message)
    }

    fun reply(message: MessageEmbed) {
        sendTypying().queue()
        event.channel.sendMessage(message)
    }

    fun reply(message: CharSequence) {
        sendTypying().queue()
        event.channel.sendMessage(message)
    }

    fun getAuthor() = event.author
    fun getGuild() = event.guild
    fun getMember() = event.member
    fun sendTypying(): RestAction<Void> = event.channel.sendTyping()
    fun getJDA() = event.jda
}