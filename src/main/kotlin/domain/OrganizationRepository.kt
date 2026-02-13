package domain

interface OrganizationRepository {
    fun add(organization: Organization)
    fun updateById(id: Int, organization: Organization)
    fun removeById(id: Int)
}