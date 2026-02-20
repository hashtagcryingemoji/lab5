package application.commands

import application.Handler

class Show(
   override val app: Handler
): Command {
    private val io = app.io
    private val collectionManager = app.collectionManager
    override val description: String = "Выводит список всех организаций"
    override val name: String = "show"

    override fun execute(argument: String) {
        io.printLine(collectionManager.getCollection().toString())
    }
}
