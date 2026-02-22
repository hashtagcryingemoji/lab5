package application

import application.commands.*
import application.exceptions.EndlessRecursionException
import application.exceptions.WrongArgumentException
import org.apache.logging.log4j.kotlin.logger

class ScriptExecutor(
    val app: Handler,
    val pathName: String,
): Handler {
    init {
        println("Executing script $pathName")
    }

    override val initialPath = app.initialPath
    override val io = ScriptReader(pathName)
    override val collectionManager = app.collectionManager
    override val invoker = CommandInvoker(this)
    override val inputReader = InputReader(this)
    override val storageGateway = app.storageGateway
    override val logsManager = app.logsManager
    override val setOfPaths: MutableSet<String> = app.setOfPaths
    override fun handleError(e: Exception) {
        throw e
    }

    override fun run() {
        if (!setOfPaths.add(io.getCurrentPath())) throw EndlessRecursionException("Обнаружена бесконечная рекурсия скриптов: $pathName")

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
        try {
            while (true) {
                val line = io.readLine() ?: break
                if (line.isBlank()) continue
                logsManager.add("script: $line")
                try {
                    invoker.handleInput(line)
                } catch (e: WrongArgumentException) {
                    app.handleError(e)
                    logger.error("Ошибка чтения скрипта $pathName")
                    throw WrongArgumentException("Ошибка чтения скрипта $pathName")
                }
            }
        } finally {
            io.printLine("Скрипт $pathName выполнился успешно.")
            setOfPaths.remove(io.getCurrentPath())
        }
    }
}