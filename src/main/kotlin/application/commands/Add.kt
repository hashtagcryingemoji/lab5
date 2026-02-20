package application.commands

import application.Handler
import domain.Organization

class Add(
    override val app: Handler
): Command {
    override val description: String = "Добавляет организацию в коллекцию"
    override val name: String = "add"

    override fun execute(argument: String) {
        val collectionManager = app.collectionManager
        val io = app.io
        val inputReader = app.inputReader
        val org: Organization = inputReader.readOrganization(collectionManager, false)

        collectionManager.add(org)
        io.printLine("Организация '${org.name}' успешно добавлена.")
    }

}
