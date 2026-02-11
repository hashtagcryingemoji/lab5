package domain

interface OrganizationRepository {
    fun add(organization: Organization)
    fun updateById(id: Int)
    fun removeById(id: Int)
}