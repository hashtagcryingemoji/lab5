package application.commands

import application.Handler
import application.ScriptExecutor
import application.exceptions.EmptyArgumentException

class ExecuteScript(
    override val app: Handler
): Command {
    override val name = "execute_script"
    override val description = "Исполняет скрипты"
    override fun execute(argument: String) {

        if (argument.isBlank()) app.handleError(EmptyArgumentException("Путь к файлу не указан"))
        val scriptExecutor = ScriptExecutor(app, argument)
        scriptExecutor.run()
    }
}