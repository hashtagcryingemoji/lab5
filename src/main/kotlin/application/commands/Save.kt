package application.commands

import application.Handler

class Save(
    override val app: Handler
): Command {
    override val name = "save"
    override val description = "Сохраняет коллекцию в файл"

    override fun execute(argument: String) {
        val storageManager = app.storageGateway
        val collection = app.collectionManager.getCollection()

        storageManager.uploadCollection(ArrayDeque(collection), argument)
    }
}