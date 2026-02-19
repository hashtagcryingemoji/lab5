package application

import application.commands.Command

class CommandInvoker(private val ioPort: IOPort) {
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
            try {
                command.execute(argument)
            } catch (e: Exception) {
                ioPort.printLine(e.message)
            }
        } else {
            ioPort.printLine("Команда '$commandName' не найдена. Введите 'help, чтобы ознакомиться со списком доступных команд.")

        }
    }

    fun getCommands() = commands.values

}