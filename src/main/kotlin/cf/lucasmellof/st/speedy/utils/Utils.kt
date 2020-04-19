package cf.lucasmellof.st.speedy.utils

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 19/04/2020
 */
object Utils {
    private val executors = Executors.newSingleThreadScheduledExecutor()
    fun runAsync(runnable: () -> Unit, delay: Long = 0, unit: TimeUnit = TimeUnit.MILLISECONDS) {
        executors.schedule(runnable, delay, unit)
    }
}