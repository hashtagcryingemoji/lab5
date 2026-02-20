package application.commands

import application.Handler
import application.exceptions.WrongArgumentException
import domain.Organization

class Update (
    override val app: Handler
): Command {
    override val name = "update"
    override val description = "Обновляет элемент в коллекции по заданному id"

    override fun execute(argument: String) {
        val collectionManager = app.collectionManager
        val inputReader = app.inputReader
        val id: Int
        try {
            id = argument.toInt()
        }
        catch (e: Throwable) {
            throw WrongArgumentException("Неверный формат аргумента.")
        }
        val org: Organization = inputReader.readOrganization(collectionManager)

        collectionManager.updateById(id, org)
    }
}