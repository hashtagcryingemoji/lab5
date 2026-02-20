package application

import application.commands.Add
import application.commands.ExecuteScript
import application.commands.Show
import application.exceptions.WrongArgumentException
import java.io.EOFException

class ApplicationExecutor(
    override val io: IOPort,
    override val collectionManager: CollectionManager,
): Handler {
    override val invoker = CommandInvoker(io)
    override val inputReader = InputReader(this)





    override fun handleError(e: Exception) {
        io.printLine(e.message)
    }

    override fun run() {
        val add = Add(this)
        val executeScript = ExecuteScript(this)
        val show = Show(this)
        invoker.registerCommand(show)
        invoker.registerCommand(executeScript)
        invoker.registerCommand(add)
        while (true) {
            try {
                val line = io.readLine() ?: break
                if (line.trim().lowercase() == "!exit") break
                if (line.isBlank()) continue

                try {
                    invoker.handleInput(line)
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