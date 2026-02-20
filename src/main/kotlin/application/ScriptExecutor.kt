package application

import application.commands.*

class ScriptExecutor(
    val app: Handler,
    pathName: String
): Handler {
    init {
        println("Executing script $pathName")
    }
    override val io = ScriptReader(pathName)
    override val collectionManager = app.collectionManager
    override val invoker = CommandInvoker(this)
    override val inputReader = InputReader(this)
    override val storageGateway = app.storageGateway
    override val logsManager = app.logsManager

    override fun handleError(e: Exception) {
        throw e
    }

    override fun run() {
        val add = Add(this)
        val executeScript = ExecuteScript(this)
        val show = Show(this)
        val clear = Clear(this)
        val countByType = CountByType(this)
        val help = Help(this)
        val info = Info(this)
        val save = Save(this)
        val sumOfEmployeesCount = SumOfEmployeesCount(this)
        val countLessThanOfficialAddress = CountLessThanOfficialAddress(this)
        val removeLower = RemoveLower(this)
        val removeGreater = RemoveGreater(this)
        val removeByID = RemoveByID(this)
        val update = Update(this)
        val exit = Exit(this)
        val history = History(this)

        with(invoker) {
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

        while (true) {
            val line = io.readLine() ?: break
            if (line.isBlank()) continue
            logsManager.add("script: $line")
            invoker.handleInput(line)
        }
    }
}