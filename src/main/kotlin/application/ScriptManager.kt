package application

import java.io.File
import java.util.*

class ScriptManager(
    private val userOutput: IOPort,
    private val fileName: String
): IOPort by userOutput {

    override fun readLine(): String? {
        val file = File(fileName)
        val sc = Scanner(file)

        return if (sc.hasNextLine()) sc.nextLine() else null
    }
}