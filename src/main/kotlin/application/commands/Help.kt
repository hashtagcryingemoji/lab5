package application.commands

import application.Handler

class Help (
    override val app: Handler
): Command {
    override val name = "help"
    override val description = "Выводит информацию о всех доступных командах"

    override fun execute(argument: String) {
        val commandCollection = app.invoker.getCommands()
        for (command in commandCollection){
            println("${command.name} - ${command.description}")
        }
    }
}
