package application
import domain.*
import java.time.LocalDate
import java.util.Locale.getDefault

class CommandParser(private val io: IOPort) {
    fun readOrganization(collectionManager: CollectionManager): Organization {
        val id = collectionManager.generateNewID()
        val name = readName("Введите название организации:", false)
        val x = readFloatMax("X (max 547)", 547f, false)!!
        val y = readFloat("Y", false)!!
        val turnover: Float = readFloatMin("Оборот (>0)",0f, false)!!
        var fullName: String

        while (true) {
            fullName = readString("Полное имя (уникальное)", false)!!
            if (!collectionManager.checkFullNameUnique(fullName)) break
            io.printLine("Это имя уже занято.")

        }

        val empCount = readLongMin("Сотрудники",0L, nullable = true)
        val type = readEnum("Тип (commercial, public, government, private limited company, open joint stock company", false)
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
                return s
            }

        }
    }
    private fun readName(p: String, nullable: Boolean): String {
        var s = readString(p, nullable)
        return if (s != "" && s != null) s.trim()
        else {
            io.printLine("Название не может быть пустым. Попробуйте ещё раз:")
            readName(p, nullable)
        }
    }
    private fun readLong(p: String, nullable: Boolean): Long? {
        var s = readString(p, nullable)
        if (s != null) {
            val parsed_Long: Long? = s.toLongOrNull()
            return if (parsed_Long != null) parsed_Long
            else {
                io.printLine("Это не число! Попробуйте ещё раз:")
                readLong(p, nullable)
            }
        }
        return null
    }

    //Нижняя граница
    private fun readLongMin(p: String, min: Long, nullable: Boolean): Long? {
        var s = readString(p, nullable)
        if (s != null) {
            val parsed_Long: Long? = s.toLongOrNull()
            return if (parsed_Long != null)
                if (parsed_Long > min) parsed_Long
                else {
                    io.printLine("Количество сотрудников должно быть больше 0. Попробуйте еще раз")
                    readLongMin(p, min, nullable)
                }
            else {
                io.printLine("Это не число! Попробуйте ещё раз:")
                readLong(p, nullable)
            }
        }
        return null
    }


    private fun readFloat(p: String, nullable: Boolean): Float? {
        var s = readString(p, nullable)
        if (s != null) {
            val parsed_Float: Float? = s.toFloatOrNull()
             return if (parsed_Float != null) parsed_Float
            else {
                io.printLine("Это не число! Попробуйте ещё раз:")
                return readFloat(p, nullable)
            }
        }
        return null
    }
    //Только верхняя граница
    private fun readFloatMax(p: String, max: Float, nullable: Boolean): Float? {
        var s = readString(p, nullable)
        if (s != null) {
            val parsed_Float: Float? = s.toFloatOrNull()
            return if (parsed_Float != null) {
                if (parsed_Float > max) {
                    io.printLine("Число больше $max! Попробуйте другое:")
                    readFloatMax(p, max, nullable)
                }
                parsed_Float
            }
            else {
                io.printLine("Это не число! Попробуйте ещё раз:")
                return readFloatMax(p, max, nullable)
            }
        }
        return null
    }
    //Только нижняя граница
    private fun readFloatMin(p: String, min: Float, nullable: Boolean): Float? {
        var s = readString(p, nullable)
        if (s != null) {
            val parsed_Float: Float? = s.toFloatOrNull()
            return if (parsed_Float != null) {
                if (parsed_Float < min) {
                    io.printLine("Число меньше $min! Попробуйте другое:")
                    readFloatMin(p, min, nullable)
                }
                parsed_Float

            }
            else {
                io.printLine("Это не число! Попробуйте ещё раз:")
                return readFloatMin(p, min,  nullable)
            }
        }
        return null
    }
    private fun readEnum(p: String, nullable: Boolean): OrganizationType {

        val s: String = readString(p, nullable)!!
        return when (s.lowercase()) {
            "commercial" -> OrganizationType.COMMERCIAL
            "public" -> OrganizationType.PUBLIC
            "government" -> OrganizationType.GOVERNMENT
            "private limited company" -> OrganizationType.PRIVATE_LIMITED_COMPANY
            "open joint stock company" -> OrganizationType.OPEN_JOINT_STOCK_COMPANY
            else -> {
                io.printLine("Попробуйте еще раз:")
                readEnum(p, nullable)
            }
        }
    }

}