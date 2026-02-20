package application.commands

import application.Handler

class History (
    override val app: Handler
): Command{
    override val name = "history"
    override val description = "Выводит на экран последние 10 команд"

    override fun execute(argument: String) {
        val historyManager = app.logsManager
        val history = historyManager.getLogs()


        for (command in history){
            println(command)
        }
    }
}