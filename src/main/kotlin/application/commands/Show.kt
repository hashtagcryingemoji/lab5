package application.commands

import application.CollectionManager
import application.IOPort

class Show(
    private val collectionManager: CollectionManager,
    private val io: IOPort
): Command {
    override val description: String = "Выводит список всех организаций"
    override val name: String = "show"
    override fun execute(argument: String) {
        io.printLine(collectionManager.getCollection().toString())
    }
}