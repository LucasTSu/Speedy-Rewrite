package cf.lucasmellof.st.speedy

import cf.lucasmellof.st.speedy.core.commands.Registry
import cf.lucasmellof.st.speedy.core.config.instances.Config
import cf.lucasmellof.st.speedy.core.manager.EventManager
import mu.KotlinLogging
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.ChunkingFilter
import net.dv8tion.jda.api.utils.MemberCachePolicy
import net.dv8tion.jda.api.utils.cache.CacheFlag
import java.util.*

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 17/04/2020
 */
class Speedy(var config: Config) {
    companion object {
        lateinit var jda: JDA
        val logger = KotlinLogging.logger { Speedy::class }
    }

    fun load() {
        logger.info { "> Loading Speedy" }
        logger.info { "Initializing JDA" }
        Registry().registerCommandsByReflections()
        jda = JDABuilder.createDefault(
                        config.token,
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MESSAGE_TYPING,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.GUILD_EMOJIS
                )
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setAutoReconnect(true)
                .addEventListeners(EventManager())
                .build()
    }
}