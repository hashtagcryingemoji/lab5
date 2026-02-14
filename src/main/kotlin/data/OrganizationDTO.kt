package data

import domain.Address
import domain.Coordinates
import domain.Organization
import domain.OrganizationType
import java.time.LocalDate

data class OrganizationDTO(
    val id: Int,
    val name: String,
    val coordinatesX: Float,
    val coordinatesY: Float,
    val creationDate: String,
    val annualTurnover: Float,
    val fullName: String,
    val employeesCount: Long?,
    val organizationType: String,
    val street: String?,
    val zipCode: String?
){
    companion object {
        fun toDto(org: Organization): OrganizationDTO{
            val organizationDTO = OrganizationDTO(
                org.id,
                org.name,
                org.coordinates.x,
                org.coordinates.y,
                org.creationDate.toString(),
                org.annualTurnover,
                org.fullName,
                org.employeesCount,
                org.type.toString(),
                org.officialAddress.street,
                org.officialAddress.zipCode
            )

            return organizationDTO
        }

        fun toDomain(organizationDTO: OrganizationDTO): Organization{
            val coordinates = Coordinates(
                organizationDTO.coordinatesX,
                organizationDTO.coordinatesY
            )

            val address = Address(
                organizationDTO.street,
                organizationDTO.zipCode
            )

            val organization = Organization(
                organizationDTO.id,
                organizationDTO.name,
                coordinates,
                LocalDate.parse(organizationDTO.creationDate),
                organizationDTO.annualTurnover,
                organizationDTO.fullName,
                organizationDTO.employeesCount,
                OrganizationType.valueOf(organizationDTO.organizationType),
                address
            )

            return organization
        }
    }
}