package application.commands

import application.Handler

class Show(
   private val app: Handler
): Command {
    val io = app.io
    val collectionManager = app.collectionManager
    override val description: String = "Выводит список всех организаций"
    override val name: String = "show"
    override fun execute(argument: String) {
        io.printLine(collectionManager.getCollection().toString())
    }
}