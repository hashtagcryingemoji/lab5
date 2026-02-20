package application.commands

import application.Handler
import domain.Organization

class Add(
    private val app: Handler
): Command {

    override val description: String = "Добавляет организацию в коллекцию"
    override val name: String = "add"
    override fun execute(argument: String) {
        val org: Organization = app.inputReader.readOrganization(app.collectionManager, false)
        app.collectionManager.add(org)
        app.io.printLine("Организация '${org.name}' успешно добавлена.")
    }

}
// app.getContext():