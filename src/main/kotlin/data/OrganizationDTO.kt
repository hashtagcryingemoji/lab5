package data

import domain.Address
import domain.Coordinates
import domain.Organization
import domain.OrganizationType
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDate

@Serializable
@XmlSerialName("organization", "", "")
data class OrganizationDTO(
    @XmlElement val id: Int,
    @XmlElement val name: String,
    @XmlElement val coordinatesX: Float,
    @XmlElement val coordinatesY: Float,
    @XmlElement val creationDate: String,
    @XmlElement val annualTurnover: Float,
    @XmlElement val fullName: String,
    @XmlElement val employeesCount: Long?,
    @XmlElement val organizationType: String,
    @XmlElement val street: String?,
    @XmlElement val zipCode: String?
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