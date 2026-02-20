package application.commands

import application.Handler
import domain.Organization

class RemoveGreater (
    override val app: Handler
): Command {
    override val name = "remove_greater"
    override val description = "Удаляет из коллекции все элементы, превышающие заданный"

    override fun execute(argument: String) {
        val collectionManager = app.collectionManager
        val inputReader = app.inputReader
        val io = app.io

        val org: Organization = inputReader.readOrganization(collectionManager, false)
        val count = collectionManager.countGreater(org)

        collectionManager.removeGreater(org)
        io.printLine("Из коллекции удалено $count элементов")
    }
}