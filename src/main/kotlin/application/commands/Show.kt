package application.commands

import application.Handler

class Show(
   override val app: Handler
): Command {
    private val io = app.io
    override val description: String = "Выводит список всех организаций"
    override val name: String = "show"

    override fun execute(argument: String) {
        val collectionManager = app.collectionManager

        io.printLine(collectionManager.getCollection().toString())
    }
}
