package application.commands

import application.Handler
import java.io.FileNotFoundException

class Save(
    override val app: Handler
): Command {
    override val name = "save"
    override val description = "Сохраняет коллекцию в файл"

    override fun execute(argument: String) {
        val storageManager = app.storageManager
        val collection = app.collectionManager.getCollection()

        try {
            storageManager.uploadCollection(ArrayDeque(collection), argument)
        } catch (e: FileNotFoundException) {
            app.handleError(FileNotFoundException("Нет прав записи в файл $argument"))
        }
    }
}