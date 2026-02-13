package application

import domain.Address
import domain.Organization
import domain.OrganizationRepository
import domain.OrganizationType
import java.util.Deque

class CollectionManager(
    private val organizationCollection : ArrayDeque<Organization>,

) : OrganizationRepository {

    fun clear() = organizationCollection.clear()

    fun getCollection() : ArrayDeque<Organization> = organizationCollection

    fun countType(type: OrganizationType) : Int = organizationCollection.count {it.type == type}

    fun sumEmployees(): Long = organizationCollection.sumOf { it.employeesCount ?: 0L }

    fun countLessAddress(address: Address): Int = organizationCollection.count { it.officialAddress < address }

    fun removeGreater(organization: Organization) {
        for (org in organizationCollection){
            if (org.id > organization.id) {
                organizationCollection.remove(org)
            }
        }
    }

    fun removeLower(organization: Organization) {
        for (org in organizationCollection){
            if (org.id < organization.id) {
                organizationCollection.remove(org)
            }
        }
    }


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