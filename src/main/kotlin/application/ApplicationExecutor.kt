package application

import application.commands.*
import application.exceptions.EmptyArgumentException
import application.exceptions.EndlessRecursionException
import application.exceptions.ScriptInterruptedWhileReadingException
import application.exceptions.WrongArgumentException
import data.StorageManager
import java.io.FileNotFoundException
import kotlin.system.exitProcess
import org.apache.logging.log4j.kotlin.logger

class ApplicationExecutor(
    override val io: IOPort,
    override val initialPath: String
): Handler {

    override val invoker = CommandInvoker(this)
    override val inputReader = InputReader(this)
    override val storageGateway: StorageManager = StorageManager(this)
    private val collection = storageGateway.downloadCollection(initialPath)

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
                io.printBefore("> ")
                setOfPaths.clear()
                val line = io.readLine() ?: break
                if (line.isBlank()) continue

                if (line.isNotBlank()) logger.info(line)
                invoker.handleInput(line)
                logsManager.add(line)
            }
            catch (e: SecurityException){
                logger.warn(e.message!!)
                io.printLine("Нет прав для чтения файла =(")
                continue
            }
            catch (e: FileNotFoundException)
            {
                logger.warn(e.message!!)
                io.printLine("Похоже, что такого файла ('${e.message}') вовсе не существовало...")
            }
            catch (e: WrongArgumentException){
                logger.warn(e.message)
                io.printLine(e.message)
                continue
            }
            catch (e: EmptyArgumentException){
                logger.warn(e.message!!)
                io.printLine(e.message)
                continue
            }
            catch (e: EndlessRecursionException) {
                logger.warn(e.message!!)
                io.printLine(e.message)
                setOfPaths.clear()
                continue
            }
            catch (e: NumberFormatException){
                logger.warn(e.message!!)
                io.printLine(e.message)
                continue
            }
            catch (e: NoSuchElementException) {
                logger.warn(e.message!!)
                io.printLine(e.message)
                continue
            }
            catch (e: ScriptInterruptedWhileReadingException){
                logger.warn(e.message!!)
                io.printLine("Кажется скрипт заполнен не до конца...")
                setOfPaths.clear()
                continue
            }
            catch (e: Throwable) {
                logger.fatal(e.message!!)
                storageGateway.uploadCollection(collection, ".ErrorSaveFile.xml")
                io.printLine("Непредвиденная ошибка. Список организаций был сохранен в .ErrorSaveFile")
                exitProcess(1)
            }
        }
    }
}
