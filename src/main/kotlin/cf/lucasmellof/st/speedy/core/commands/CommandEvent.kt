package cf.lucasmellof.st.speedy.core.commands

import cf.lucasmellof.st.speedy.utils.basicEmbedBuilder
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
class CommandEvent(val event: GuildMessageReceivedEvent, val prefix: String, val cmd: String) {
    fun reply(message: Message) {
        sendTypying().queue {
            event.channel.sendMessage(message).queue()
        }
    }

    fun reply(message: MessageEmbed) {
        sendTypying().queue {
            event.channel.sendMessage(message).queue()
        }
    }

    fun reply(message: CharSequence) {
        sendTypying().queue {
            event.channel.sendMessage(message).queue()
        }
    }

    fun getAuthor() = event.author
    fun getGuild() = event.guild
    fun getMember() = event.member
    fun sendTypying(): RestAction<Void> = event.channel.sendTyping()
    fun getJDA() = event.jda
    fun getChannel() = event.channel
    fun getMessage() = event.message
    fun missingArgs() = reply(basicEmbedBuilder(getJDA(), "Faltando Argumentos", "Você não inseriu os argumentos, use ${prefix}help $cmd para ver os exemplos.", false, getAuthor()).build())

}