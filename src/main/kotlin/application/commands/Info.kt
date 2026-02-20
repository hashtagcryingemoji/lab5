package application.commands

import application.Handler

class Info (
    override val app: Handler
): Command {
    override val name = "info"
    override val description = "Выводит информацию о коллекции"

    override fun execute(argument: String) {
        val collectionManager = app.collectionManager
        val collection = collectionManager.getCollection()

        if(collection.isEmpty()) println("Коллекция пуста :(") else println("Количество элементов в коллекции: ${collection.size}")
        println(app.collectionManager.getInitializationDate())
    }
}