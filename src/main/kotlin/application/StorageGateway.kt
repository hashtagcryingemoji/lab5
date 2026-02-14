package application

import domain.Organization

interface StorageGateway {
    fun downloadCollection(fileName: String): ArrayDeque<Organization>
    fun uploadCollection(collection: ArrayDeque<Organization>, fileName: String)
}