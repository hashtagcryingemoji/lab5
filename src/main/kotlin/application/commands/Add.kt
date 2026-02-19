package application.commands

import application.CollectionManager
import application.CommandParser
import application.IOPort
import domain.Organization

class Add(
    private val collectionManager: CollectionManager,
    private val commandParser: CommandParser,
    private val io: IOPort
): Command {
    override val description: String = "Добавляет организацию в коллекцию"
    override val name: String = "add"
    override fun execute(argument: String) {
        val org: Organization = commandParser.readOrganization(collectionManager)
        collectionManager.add(org)
    }

}