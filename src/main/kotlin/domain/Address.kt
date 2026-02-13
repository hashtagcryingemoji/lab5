package domain

data class Address (
    val street: String?,
    val zipCode: String?
) : Comparable<Address> {
    override fun compareTo(other: Address): Int {
        val zipComparison = (this.zipCode ?: "").compareTo(other.zipCode ?: "")
        if (zipComparison != 0) return zipComparison
        return (this.street ?: "").compareTo(other.street ?: "")
    }
}