package application.commands

import application.Handler

class Clear (
    override val app: Handler
): Command{
    override val name = "clear"
    override val description = "Очищает коллекцию"

    override fun execute(argument: String) {
        val collectionManager = app.collectionManager

        if (collectionManager.getCollection().isNotEmpty()) {
            collectionManager.clear()
            app.io.printLine("Коллекция была успешно очищена")
        }
        else{
            app.io.printLine("Коллекция уже пуста")
        }
    }
}
