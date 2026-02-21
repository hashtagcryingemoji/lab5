package application

import application.commands.*
import application.exceptions.EmptyArgumentException
import application.exceptions.EndlessRecursionException
import application.exceptions.WrongArgumentException
import data.StorageManager
import java.io.FileNotFoundException
import kotlin.system.exitProcess

class ApplicationExecutor(
    override val io: IOPort,
    pathToFile: String
): Handler {
    override val invoker = CommandInvoker(this)
    override val inputReader = InputReader(this)
    override val storageGateway: StorageManager = StorageManager(this)
    private val collection = storageGateway.downloadCollection(pathToFile)

    override val collectionManager = CollectionManager(collection)
    override val setOfPaths: MutableSet<String> = mutableSetOf<String>()
    override val logsManager = HistoryManager()
    override fun handleError(e: Exception) {
        if (e is EmptyArgumentException) {
            throw e
        }
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
        io.printLine("Добро пожаловать в Imop 1.0.\nВведите 'help', чтобы ознакомиться со списком доступных команд.")
        while (true) {
            try {
                setOfPaths.clear()
                val line = io.readLine() ?: break
                if (line.isBlank()) continue

                invoker.handleInput(line)
                logsManager.add(line)
            }
            catch (e: SecurityException){
                io.printLine("Нет прав для чтения файла =(")
                continue
            }
            catch (e: FileNotFoundException)
            {
                io.printLine("Похоже, что такого файла ('${e.message}') вовсе не существовало...")
            }
            catch (e: WrongArgumentException){
                io.printLine(e.message)
                continue
            }
            catch (e: EmptyArgumentException){
                io.printLine(e.message)
                continue
            }
            catch (e: EndlessRecursionException) {
                io.printLine(e.message)
                setOfPaths.clear()
                continue
            }
            catch (e: NumberFormatException){
                io.printLine(e.message)
                continue
            }
            catch (e: Throwable) {
                storageGateway.uploadCollection(collection, ".ErrorSaveFile.xml")
                io.printLine("Непредвиденная ошибка. Список организаций был сохранен в .ErrorSaveFile")
                exitProcess(1)
            }
        }
    }
}
