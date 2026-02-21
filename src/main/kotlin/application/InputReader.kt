package application

import domain.*
import application.exceptions.WrongArgumentException
import java.time.LocalDate
import org.apache.logging.log4j.kotlin.logger

class InputReader(val app: Handler) {

    fun readLine() = app.io.readLine()
    fun printLine(message: String) = app.io.printLine(message)
    fun handleError(e: Exception) = app.handleError(e)

    fun readOrganization(collectionManager: CollectionManager): Organization {
        val id = collectionManager.generateNewID()
        val name = readName("Введите название организации:", false)
        val x = readFloatMax("X (max 547)",false, 547f)!!
        val y = readFloat("Y", false)!!
        val turnover: Float = readFloatMin("Оборот (>0)", false, 1f)!!
        var fullName: String

        while (true) {
            fullName = readString("Полное имя (уникальное):", false)!!
            if (!collectionManager.checkFullNameUnique(fullName) && fullName.isNotBlank()) break
            logger.warn("Имя $fullName уже занято")
            handleError(WrongArgumentException("Это имя уже занято."))
        }

        val empCount = readLongMin("Сотрудники",true, 1L)
        val type = readEnum("Тип (commercial, public, government, private limited company, open joint stock company)", false)
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

    fun readString(p: String, nullable: Boolean): String? {

        if (!nullable) {
            while (true) {
                printLine("$p: ")
                app.io.printBefore("> ")
                val s: String = readLine()?.trim() ?: ""
                return s
            }
        }
        else {
            while (true) {
                printLine("$p или нажмите Enter, чтобы оставить поле пустым: ")
                app.io.printBefore("> ")
                val s: String? = readLine()
                logger.info(s ?: "")
                return if (!s.isNullOrBlank()) s.trim()
                else null
            }
        }
    }
    private fun readName(p: String, nullable: Boolean): String {


        val s = readString(p, nullable)

        logger.info(s ?: "")
        return if (s != null && s != "") s.trim()
        else {
            logger.warn("Название не может быть пустым")
            handleError(WrongArgumentException("Название не может быть пустым"))
            readName(p, nullable)
        }
    }

    private fun readLong(p: String, nullable: Boolean): Long? {

        val s = readString(p, nullable)

        logger.info(s ?: "")
        if (s != null) {
            val parsedLong: Long? = s.toLongOrNull()
            return if (parsedLong != null) parsedLong
            else {
                logger.warn("$parsedLong - не число.")
                handleError(WrongArgumentException("$parsedLong - не число."))
                readLong(p, nullable)
            }
        }
        return null
    }

    //Нижняя граница
    private fun readLongMin(p: String, nullable: Boolean, min: Long): Long? {

        val parsedLong = readLong(p, nullable)
        return if (parsedLong != null && parsedLong >= min) parsedLong
        else if (parsedLong != null) {
            logger.warn("Число $parsedLong больше возможного")
            handleError(WrongArgumentException("Число $parsedLong больше возможного."))
            readLongMin(p, nullable, min)
        }
        else null
    }

    private fun readFloat(p: String, nullable: Boolean): Float? {

        val s = readString(p, nullable)

        logger.info(s ?: "")

        if (s != null) {
            val parsedFloat: Float? = s.toFloatOrNull()
             return if (parsedFloat != null) parsedFloat
            else {
                 logger.warn("Это не число")
                 handleError(WrongArgumentException("Это не число! Попробуйте ещё раз:"))
                 readFloat(p, nullable)
            }
        }
        return null
    }
    //Только верхняя граница
    private fun readFloatMax(p: String , nullable: Boolean, max: Float): Float? {

        val parsedFloat = readFloat(p, nullable)
            return if (parsedFloat != null && parsedFloat <= max) parsedFloat
            else if (parsedFloat != null) {
                logger.warn("Число $parsedFloat больше возможного")
                handleError(WrongArgumentException("Число $parsedFloat больше возможного."))
                readFloatMax(p, nullable, max)
            }
            else null
    }
    //Только нижняя граница
    private fun readFloatMin(p: String, nullable: Boolean, min: Float): Float? {
        val parsedFloat = readFloat(p, nullable)
        return if (parsedFloat != null && parsedFloat >= min) parsedFloat
        else if (parsedFloat != null) {
            logger.warn("Число $parsedFloat меньше возможного")
            handleError(WrongArgumentException("Число $parsedFloat меньше возможного."))
            readFloatMin(p, nullable, min)
        }
        else null
    }

    private fun readEnum(p: String, nullable: Boolean): OrganizationType {

        val s: String = readString(p, nullable)!!

        logger.info(s)

        return when (s.lowercase()) {
            "commercial" -> OrganizationType.COMMERCIAL
            "public" -> OrganizationType.PUBLIC
            "government" -> OrganizationType.GOVERNMENT
            "private limited company" -> OrganizationType.PRIVATE_LIMITED_COMPANY
            "open joint stock company" -> OrganizationType.OPEN_JOINT_STOCK_COMPANY
            else -> {
                logger.warn("Введён неккоректный формат типа организации")
                handleError(WrongArgumentException("Введён неккоректный формат типа организации"))
                readEnum(p, nullable)
            }
        }
    }

}