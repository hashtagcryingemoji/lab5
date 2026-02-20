package application.commands

import application.Handler

class RemoveByID  (
    override val app: Handler
): Command {
    override val name = "remove_greater"
    override val description = "Удаляет из коллекции все элементы, превышающие заданный"

    override fun execute(argument: String) {
        val collectionManager = app.collectionManager

        collectionManager.removeById(argument.toInt())
    }
}