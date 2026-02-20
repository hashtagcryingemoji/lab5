package application

import domain.Address
import domain.Organization
import domain.OrganizationRepository
import domain.OrganizationType

class CollectionManager(
    private val organizationCollection : ArrayDeque<Organization>,
) : OrganizationRepository {

    private var currentID: Int = 0

    fun generateNewID() : Int{
        return ++currentID
    }

    fun checkFullNameUnique(fullName: String) : Boolean = organizationCollection.any { it.fullName == fullName }

    fun clear() {
        organizationCollection.clear()
        currentID = 0
    }
    fun getCollection() : List<Organization> = organizationCollection.toList()

    fun countType(type: OrganizationType) : Int = organizationCollection.count {it.type == type}

    fun sumEmployees(): Long = organizationCollection.sumOf { it.employeesCount ?: 0L }

    fun countLessAddress(address: Address): Int = organizationCollection.count { it.officialAddress < address }

    fun removeGreater(organization: Organization) = organizationCollection.removeIf { it > organization}

    fun removeLower(organization: Organization) = organizationCollection.removeIf { it < organization}


    override fun add(organization: Organization) {
        organizationCollection.addLast(organization)
    }

    override fun updateById(id: Int, organization: Organization) {
        organizationCollection.removeIf { it.id == id }
        organizationCollection.addLast(organization)
    }

    override fun removeById(id: Int) {
        organizationCollection.removeIf{ organization -> organization.id == id }
    }

}