package cf.lucasmellof.st.speedy.core.commands

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.hooks.EventListener

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 17/04/2020
 */
abstract class Command(
    val name: String,
    val category: Category,
    val description: String,
    val example: String? = null,
    val usage: String? = null,
    val aliases: List<String> = listOf(),
    val subCommands: List<String> = listOf(),
    val allowPrivate: Boolean = true,
    val isDeveloperOnly: Boolean = false,
    val isHidden: Boolean = false
) {
    abstract suspend fun execute(args: List<String>, e: CommandEvent)
    fun <E> List<E>.random(random: java.util.Random): E = get(random.nextInt(size))

}