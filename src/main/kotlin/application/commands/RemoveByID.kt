package application.commands

import application.Handler
import application.exceptions.WrongArgumentException

class RemoveByID  (
    override val app: Handler
): Command {
    override val name = "remove_by_id"
    override val description = "Удаляет из коллекции элемент по ID"

    override fun execute(argument: String) {
        val collectionManager = app.collectionManager
        try {
            if (collectionManager.checkID(argument.toInt())) {
                app.io.printLine("Элемента с таким ID не существует.")
            } else {
                collectionManager.removeById(argument.toInt())
                app.io.printLine("Элемент с ID $argument удален.")
            }
        } catch (ex: NumberFormatException) {
            throw WrongArgumentException("Введенный аргумент не является числом.")
        }
    }
}