package application.commands

import application.Handler

class SumOfEmployeesCount(
    override val app: Handler
): Command {
    override val name = "sum_of_employees_count"
    override val description = "Возвращает количество работяг во всей коллекции"

    override fun execute(argument: String) {
        val count = app.collectionManager.sumEmployees()

        app.io.printLine("Общее количество работяг в коллекции: $count")
    }
}