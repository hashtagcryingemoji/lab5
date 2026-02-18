package application

import domain.Address
import domain.Organization
import domain.OrganizationRepository
import domain.OrganizationType

class CollectionManager(
    private val organizationCollection : ArrayDeque<Organization>,
) : OrganizationRepository {

    private var currentID: Int = -1

    fun generateNewID() : Int{
        if (currentID == -1) {
            if (organizationCollection.isEmpty()) {
                currentID = 1
                return 1
            }
            val maxID = organizationCollection.maxOf { it.id }
            currentID = maxID + 1

            return maxID + 1
        }
        return currentID+1
    }

    fun checkFullNameUnique(fullName: String) : Boolean = organizationCollection.find { it.fullName == fullName } != null

    fun clear() {
        organizationCollection.clear()
        currentID = -1
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
        organizationCollection.removeIf({ organization -> organization.id == id })
    }

}