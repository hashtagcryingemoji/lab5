package application.commands

import application.Handler
import application.ScriptExecutor

class ExecuteScript(
    val app: Handler
): Command {
    override val name = "execute_script"
    override val description = "Исполняет скрипты"
    override fun execute(argument: String) {
        val scriptExecutor = ScriptExecutor(app, argument)
        scriptExecutor.run()
    }
}