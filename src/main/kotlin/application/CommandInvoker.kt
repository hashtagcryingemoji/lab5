package application

import application.commands.*

class CommandInvoker(val app: Handler) {
    private val commands = mutableMapOf<String, Command>()

    private val add = Add(app)
    private val executeScript = ExecuteScript(app)
    private val show = Show(app)
    private val clear = Clear(app)
    private val countByType = CountByType(app)
    private val help = Help(app)
    private val info = Info(app)
    private val save = Save(app)
    private val sumOfEmployeesCount = SumOfEmployeesCount(app)
    private val countLessThanOfficialAddress = CountLessThanOfficialAddress(app)
    private val removeLower = RemoveLower(app)
    private val removeGreater = RemoveGreater(app)
    private val removeByID = RemoveByID(app)
    private val update = Update(app)
    private val exit = Exit(app)
    private val history = History(app)
    
    fun registerCommand(command: Command) {
        commands[command.name] = command
    }

    init {
        registerCommand(show)
        registerCommand(executeScript)
        registerCommand(add)
        registerCommand(clear)
        registerCommand(countByType)
        registerCommand(help)
        registerCommand(info)
        registerCommand(save)
        registerCommand(sumOfEmployeesCount)
        registerCommand(countLessThanOfficialAddress)
        registerCommand(removeLower)
        registerCommand(removeGreater)
        registerCommand(removeByID)
        registerCommand(update)
        registerCommand(exit)
        registerCommand(history)
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