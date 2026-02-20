package application.commands

import application.Handler
import domain.Organization

class Update (
    override val app: Handler
): Command {
    override val name = "update"
    override val description = "Обновляет элемент в коллекции по заданному id"

    override fun execute(argument: String) {
        val collectionManager = app.collectionManager
        val inputReader = app.inputReader
        val org: Organization = inputReader.readOrganization(collectionManager, false)
        val id = argument.toInt()

        collectionManager.updateById(id, org)
    }
}