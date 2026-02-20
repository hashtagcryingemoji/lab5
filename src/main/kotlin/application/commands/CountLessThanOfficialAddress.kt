package application.commands

import application.Handler
import domain.Address

class CountLessThanOfficialAddress (
    override val app: Handler
): Command {
    override val name = "count_less_than_official_address"
    override val description = "Подсчитывает количество организаций чей адрес меньше заданного"

    override fun execute(argument: String) {
        val collectionManager = app.collectionManager
        val inputReader = app.inputReader

        val street = inputReader.readString("Улица", true)
        val zip = inputReader.readString("Индекс", true)
        val address = Address(street, zip)

        val count = collectionManager.countLessAddress(address)

        app.io.printLine("Количество организаций с меньшим адресом - $count")
    }
}