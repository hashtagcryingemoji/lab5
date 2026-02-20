package data

import application.StorageGateway
import domain.Organization
import nl.adaptivity.xmlutil.serialization.XML
import java.io.*
import java.util.*
import kotlin.collections.ArrayDeque

class StorageManager: StorageGateway {
    override fun downloadCollection(fileName: String): ArrayDeque<Organization> {
        if (fileName.isEmpty()) return ArrayDeque()
        val file = File(fileName)
        if (!file.exists()) return ArrayDeque()
        var res = ""
        val sc = Scanner(file)

        while(sc.hasNextLine()){
            res += sc.nextLine()
        }

        val decode = XML.decodeFromString(OrganizationsContainer.serializer(), res)
        val collection = decode
            .organizations
            .map(OrganizationDTO::toDomain)
            .toList()

        return ArrayDeque(collection)
    }

    override fun uploadCollection(collection: ArrayDeque<Organization>, fileName: String) {
        val file = File(fileName)
        val list = collection
            .map(OrganizationDTO::toDto)
            .toList()
        val container = OrganizationsContainer(list)
        val xml = XML { indentString = "    " }
        val content = xml.encodeToString(OrganizationsContainer.serializer(), container)
        val fileWriter = FileWriter(file)

        fileWriter.write(content)
        fileWriter.flush()
    }
}