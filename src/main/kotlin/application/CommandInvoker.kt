package application

import application.commands.Command

class CommandInvoker(val app: Handler) {
    private val commands = mutableMapOf<String, Command>()

    fun registerCommand(command: Command) {
        commands[command.name] = command
    }

    fun handleInput(input: String) {
        val parts = input.trim().split(' ', limit = 2)
        val commandName = parts[0].lowercase()
        val argument = if (parts.size > 1) parts[1] else ""
        val command = commands[commandName]

        if (command != null) {
            command.execute(argument)

        } else {
            app.io.printLine("Команда '$commandName' не найдена. Введите 'help', чтобы ознакомиться со списком доступных команд.")
        }
    }

    fun getCommands() = commands.values

}