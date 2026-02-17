package data

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("Organizations", "", "")
data class OrganizationsContainer (
    val organizations: List<OrganizationDTO>
)