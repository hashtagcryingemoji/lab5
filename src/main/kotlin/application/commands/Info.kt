package application.commands

import application.Handler

class Info (
    override val app: Handler
): Command {
    override val name = "info"
    override val description = "Выводит информацию о коллекции"

    override fun execute(argument: String) {
        val collectionManager = app.collectionManager
        val io = app.io
        val collection = collectionManager.getCollection()

        if(collection.isEmpty()) {
            io.printLine("Коллекция пуста :(")
        }
        else {
            io.printLine("Количество элементов в коллекции: ${collection.size}")
            io.printLine("дата создания шедевра - ${collectionManager.getInitializationDate()}")

            val collect = collectionManager.getCollection()

            io.printLine("Организации в коллекции:")

            for (organization in collect){
                io.printLine("${organization.fullName} с id номер ${organization.id}")
            }
        }

    }
}