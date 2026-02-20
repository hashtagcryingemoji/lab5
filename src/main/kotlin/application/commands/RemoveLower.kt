package application.commands

import application.Handler
import domain.Organization

class RemoveLower (
    override val app: Handler
): Command {
    override val name = "remove_lower"
    override val description = "Удаляет из коллекции все элементы, меньше чем"

    override fun execute(argument: String) {
        val collectionManager = app.collectionManager
        val inputReader = app.inputReader
        val io = app.io

        val org: Organization = inputReader.readOrganization(collectionManager, false)
        val count = collectionManager.countLower(org)

        collectionManager.removeLower(org)
        io.printLine("Из коллекции удалено $count элементов")
    }
}