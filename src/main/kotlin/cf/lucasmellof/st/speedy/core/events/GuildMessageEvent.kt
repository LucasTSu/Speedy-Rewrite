package cf.lucasmellof.st.speedy.core.events

import cf.lucasmellof.st.speedy.SpeedyLauncher.config
import cf.lucasmellof.st.speedy.core.commands.CommandEvent
import cf.lucasmellof.st.speedy.core.commands.Registry
import cf.lucasmellof.st.speedy.utils.CrashReader
import cf.lucasmellof.st.speedy.utils.basicEmbedBuilder
import cf.lucasmellof.st.speedy.utils.ready
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 17/04/2020
 */
object GuildMessageEvent {
    fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if (ready.not()) return
        if (event.author.idLong != 213680997546459136L)
            return
        val content = event.message.contentRaw
        val selfId = event.jda.selfUser.id
        if (CrashReader(event).checkIfIsCrash()) {
            return
        }
        if (event.isWebhookMessage || event.author.isFake || event.author.isBot || event.author.id == selfId) return
        var prefix = config.prefix
        if (prefix == "%mention%") prefix = event.jda.selfUser.asMention
        if (content.matches("<@!?$selfId>\$".toRegex())) {
            basicEmbedBuilder(
                event.jda,
                "Meu prefixo é ``$prefix``",
                "Para ver o que eu posso fazer,use ``${prefix}ajuda``",
                false,
                event.author,
                event.channel
            )
            return
        }
        val isMentionPrefix = content.matches("^<@!?$selfId>\\s.*".toRegex())
        prefix = if (isMentionPrefix) content.substring(0, content.indexOf('>') + 1) else prefix
        val index = if (isMentionPrefix) prefix.length + 1 else prefix.length

        val allArgs = content.substring(index).split("\\s+".toRegex())
        val command = Registry.getCommandByName(allArgs[0])
        val args = allArgs.drop(1)
        if (command != null) {
            if (command.isDeveloperOnly && event.author.id != config.developer) {
                event.channel.sendMessage("Esse comando só pode ser usado por " + event.jda.getUserById(config.developer)!!.name)
                return
            }
            if((command.isStaffOnly && !event.member!!.roles.contains(event.jda.getRoleById(config.staffRoleID)))|| event.author.id != config.developer){
                event.channel.sendMessage("Esse comando só pode ser usado pela equipe!")
                return
            }
            GlobalScope.async {
                command.execute(args, CommandEvent(event, prefix, command.name))
            }
        }
    }
}