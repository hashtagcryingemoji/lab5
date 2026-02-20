package application.commands

import application.Handler
import kotlin.system.exitProcess

class Exit (
    override val app: Handler
): Command{
    override val name = "exit"
    override val description = "Завершает программу (без записи файла в коллекцию)"

    override fun execute(argument: String) {
        val io = app.io
        io.printLine("Программа завершена")
        exitProcess(0)
    }
}