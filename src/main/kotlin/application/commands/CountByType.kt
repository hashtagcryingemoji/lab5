package application.commands

import application.Handler
import domain.OrganizationType

class CountByType (
    override val app: Handler
): Command {
    override val name = "count_by_type"
    override val description = "Подсчитывает количество организаций заданного типа"

    override fun execute(argument: String) {
        val waitIsItTrue = OrganizationType.entries.any{ it.toString() == argument }
        val collectionManager = app.collectionManager

        if (waitIsItTrue){
            val count = collectionManager.countType(OrganizationType.valueOf(argument))

            app.io.printLine("Количество организаций такого типа: $count")
        }
        else{
            val neatOrganizationTypes = OrganizationType
                .entries
                .toString()
                .replace("_", " ")
            val n = neatOrganizationTypes.length

            app.handleError(IllegalArgumentException("Неправильный тип организации"))
            app.io.printLine("Доступные типы для ввода: ${neatOrganizationTypes.substring(1, n - 1)}")
        }

    }
}