package application

import application.commands.Add
import application.commands.ExecuteScript

class ScriptExecutor(
    val app: Handler,
    val pathName: String
): Handler {
    init {
        println("Executing script $pathName")
    }
    override val io = ScriptReader(pathName)
    override val collectionManager = app.collectionManager
    override val invoker: CommandInvoker = CommandInvoker(this)
    override val inputReader: InputReader = InputReader(this)

    override fun handleError(e: Exception) {
        throw e
    }

    override fun run() {
        val add = Add(this)
        val executeScript = ExecuteScript(this)
        invoker.registerCommand(executeScript)
        invoker.registerCommand(add)
        while (true) {
            val line = io.readLine()
            if (line == null) break
            if (line.isBlank()) continue

            invoker.handleInput(line)

            if (line.trim().lowercase() == "!exit") break


        }
    }
}