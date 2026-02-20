package application

import application.commands.*
import application.exceptions.WrongArgumentException
import data.StorageManager
import domain.Organization
import java.io.EOFException

class ApplicationExecutor(
    override val io: IOPort,
    pathToFile: String
): Handler {
    override val invoker = CommandInvoker(this)
    override val inputReader = InputReader(this)
    override val storageGateway: StorageManager = StorageManager()

    private val collection = ArrayDeque<Organization>()
    override val collectionManager = CollectionManager(collection)

    override val logsManager = HistoryManager()
    override fun handleError(e: Exception) {
        io.printLine(e.message)
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
            try {
                val line = io.readLine() ?: break

                if (line.isBlank()) continue

                try {
                    invoker.handleInput(line)
                    logsManager.add(line)
                } catch (e: WrongArgumentException){
                    io.printLine(e.message)
                    continue
                }

            }
            catch (e: EOFException) {
                io.printLine(e.message)
                io.printLine("Скрипт завершееен")
                continue
            }
        }
    }
}
