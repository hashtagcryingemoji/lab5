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
        if (collectionManager.getCollection().isEmpty()) io.printLine("Вы еще не успели насоздавать шедевров...")
        collectionManager.getCollection().forEach { println(it) }
    }
}
