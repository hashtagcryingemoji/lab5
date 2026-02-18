package application
import domain.*
import java.time.LocalDate
import java.util.Locale.getDefault

class CommandParser(private val io: IOPort) {
    fun readOrganization(collectionManager: CollectionManager): Organization {
        val id = collectionManager.generateNewID()
        val name = readString("Введите имя", false)!!
        val x = readFloat("X (max 547)", true)!!
        val y = readFloat("Y", true)!!
        val turnover: Float = readFloat("Оборот (>0)", false)!!
        var fullName: String

        while (true) {
            fullName = readString("Полное имя (уникальное)", false)!!
            if (collectionManager.checkFullNameUnique(fullName)) break
            io.printError("Это имя уже занято.")
        }

        val empCount = readLong("Сотрудники", nullable = true)
        val type = readEnum("Тип", false)
        val street = readString("Улица", true)
        val zip = readString("Индекс", true)

        return Organization(
            id, name, Coordinates(x, y),
            annualTurnover = turnover,
            fullName = fullName, employeesCount = empCount, type = type,
            officialAddress = Address(street, zip),
            creationDate = LocalDate.now()
        )
    }

    private fun readString(p: String, nullable: Boolean): String? {
        if (!nullable) {
            while (true) {
                io.printLine("$p: ")

                val s: String = io.readLine().trim()

                return s
            }
        }
        else {
            while (true) {
                io.printLine("$p или нажмите Enter, чтобы оставить поле пустым: ")

                val s: String = io.readLine().trim()
                if (s.isEmpty()) return null
            }

        }
    }

    private fun readLong(p: String, nullable: Boolean): Long? {
        var s = readString(p, nullable)
        if (s != null) {
            val numL: Long? = s.toLongOrNull()
            return if (numL != null) numL
            else {
                io.printError("Это не число! Попробуйте ещё раз:")
                return readLong(p, nullable)
            }
        }
        return null
    }

    private fun readFloat(p: String, nullable: Boolean): Float? {
        var s = readString(p, nullable)
        if (s != null) {
            val numF: Float? = s.toFloatOrNull()
            return if (numF != null) {
                if (numF > 547F) {
                    io.printError("Число больше 547! Попробуйте другое:")
                    return readFloat(p, nullable)
                }
                numF

            }
            else {
                io.printError("Это не число! Попробуйте ещё раз:")
                return readFloat(p, nullable)
            }
        }
        return null
    }

    private fun readEnum(p: String, nullable: Boolean): OrganizationType {

        val s: String = readString(p, nullable)!!
        when (s.lowercase(getDefault())) {
            "commercial" -> OrganizationType.COMMERCIAL
            "public" -> OrganizationType.PUBLIC
            "government" -> OrganizationType.GOVERNMENT
            "private limited company" -> OrganizationType.PRIVATE_LIMITED_COMPANY
            "open joint stock company" -> OrganizationType.OPEN_JOINT_STOCK_COMPANY
            else -> {
                io.printLine("Попробуйте еще раз:")
                return readEnum(p, nullable)
            }
        }
        io.printLine("Попробуйте еще раз:")
        return readEnum(p, nullable)
    }

}