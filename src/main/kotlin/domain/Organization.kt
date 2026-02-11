package domain

import java.time.LocalDate

class Organization (
    val id: Int,
    val name: String,
    val coordinates: Coordinates,
    val creationDate: LocalDate,
    val annualTurnover: Float,
    val fullName: String,
    val employeesCount: Long?,
    val type: OrganizationType,
    val officialAddress: Address
) : Comparable<Organization> {
    init {
        require(id > 0) { "Значение поля должно быть больше 0" }
        require(name != "") { "Строка не может быть пустой" }
        require(annualTurnover > 0) { "Значение поля должно быть больше 0" }
        if (employeesCount != null ) require(employeesCount > 0) { "Значение поля должно быть больше 0" }
    }
    override fun compareTo(other: Organization): Int {
        return id.compareTo(other.id)
    }
}