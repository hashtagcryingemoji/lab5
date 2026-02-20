package application
import domain.*
import application.exceptions.WrongArgumentException
import java.time.LocalDate

class InputReader(val app: Handler) {

    fun readLine() = app.io.readLine()
    fun printLine(message: String) = app.io.printLine(message)
    fun handleError(e: Exception) = app.handleError(e)

    fun readOrganization(collectionManager: CollectionManager, isFromScript: Boolean): Organization {
        val id = collectionManager.generateNewID()
        val name = readName("Введите название организации:", false, isFromScript)
        val x = readFloatMax("X (max 547)", 547f, false, isFromScript)!!
        val y = readFloat("Y", false, isFromScript)!!
        val turnover: Float = readFloatMin("Оборот (>0)",0f, false, isFromScript)!!
        var fullName: String

        while (true) {
            fullName = readString("Полное имя (уникальное)", false)!!
            if (!collectionManager.checkFullNameUnique(fullName)) break
            handleError(WrongArgumentException("Это имя уже занято."))

        }

        val empCount = readLongMin("Сотрудники",0L, true, isFromScript)!!
        val type = readEnum("Тип (commercial, public, government, private limited company, open joint stock company", false, isFromScript)
        val street = readString("Улица", true)!!
        val zip = readString("Индекс", true)!!

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
                printLine("$p: ")

                val s: String = readLine()?.trim() ?: ""

                return s
            }
        }
        else {
            while (true) {
                printLine("$p или нажмите Enter, чтобы оставить поле пустым: ")

                val s: String = readLine()?.trim() ?: ""
                if (s.isEmpty()) return null
                return s
            }

        }
    }
    private fun readName(p: String, nullable: Boolean, isFromScript: Boolean): String {
        var s = readString(p, nullable)
        return if (s != "" && s != null) s.trim()
        else {
            handleError(WrongArgumentException("Название не может быть пустым. Попробуйте ещё раз:"))
            readName(p, nullable, isFromScript)
            }

    }
    private fun readLong(p: String, nullable: Boolean, isFromScript: Boolean): Long? {
        var s = readString(p, nullable)
        if (s != null) {
            val parsedLong: Long? = s.toLongOrNull()
            return if (parsedLong != null) parsedLong
            else {
                handleError(WrongArgumentException("Это не число, попробуйте ещё раз:"))
                readLong(p, nullable, isFromScript)
            }
        }
        return null
    }

    //Нижняя граница
    private fun readLongMin(p: String, min: Long, nullable: Boolean, isFromScript: Boolean): Long? {
        var s = readString(p, nullable)
        if (s != null) {
            val parsedLong: Long? = s.toLongOrNull()
            return if (parsedLong != null)
                if (parsedLong > min) parsedLong
                else {
                    handleError(WrongArgumentException("Количество сотрудников должно быть больше 0. Попробуйте еще раз"))
                    readLongMin(p, min, nullable, isFromScript)
                }
            else {
                handleError(WrongArgumentException("Это не число! Попробуйте ещё раз:"))
                readLong(p, nullable,  isFromScript)
            }
        }
        return null
    }



private fun readFloat(p: String, nullable: Boolean, isFromScript: Boolean): Float? {
        var s = readString(p, nullable)
        if (s != null) {
            val parsedFloat: Float? = s.toFloatOrNull()
             return if (parsedFloat != null) parsedFloat
            else {
                handleError(WrongArgumentException("Это не число! Попробуйте ещё раз:"))
                 readFloat(p, nullable, isFromScript)
            }
        }
        return null
    }
    //Только верхняя граница
    private fun readFloatMax(p: String, max: Float, nullable: Boolean, isFromScript: Boolean): Float? {
        var s = readString(p, nullable)
        if (s != null) {
            val parsedFloat: Float? = s.toFloatOrNull()
            return if (parsedFloat != null) {
                if (parsedFloat > max) {
                    handleError(WrongArgumentException("Число больше $max! Попробуйте другое:"))
                    readFloatMax(p, max, nullable, isFromScript)
                }
                parsedFloat
            }
            else {
                handleError(WrongArgumentException("Это не число! Попробуйте ещё раз:"))
                readFloatMax(p, max, nullable, isFromScript)

            }
        }
        return null
    }
    //Только нижняя граница
    private fun readFloatMin(p: String, min: Float, nullable: Boolean, isFromScript: Boolean): Float? {
        var s = readString(p, nullable)
        if (s != null) {
            val parsedFloat: Float? = s.toFloatOrNull()
            return if (parsedFloat != null) {
                if (parsedFloat < min) {
                    handleError(WrongArgumentException("Число меньше $min! Попробуйте другое:"))
                    readFloatMin(p, min, nullable, isFromScript)
                }
                parsedFloat

            }
            else {


                handleError(WrongArgumentException("Это не число! Попробуйте ещё раз:"))
                readFloatMin(p, min,  nullable, isFromScript)
            }
        }
        return null
    }
    private fun readEnum(p: String, nullable: Boolean, isFromScript: Boolean): OrganizationType {

        val s: String = readString(p, nullable)!!

        return when (s.lowercase()) {
            "commercial" -> OrganizationType.COMMERCIAL
            "public" -> OrganizationType.PUBLIC
            "government" -> OrganizationType.GOVERNMENT
            "private limited company" -> OrganizationType.PRIVATE_LIMITED_COMPANY
            "open joint stock company" -> OrganizationType.OPEN_JOINT_STOCK_COMPANY
            else -> {
                handleError(WrongArgumentException("Попробуйте еще раз:"))
                readEnum(p, nullable, isFromScript)
            }
        }
    }

}