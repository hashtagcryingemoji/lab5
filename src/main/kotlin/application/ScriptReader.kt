package application

import java.io.File
import java.util.Scanner

class ScriptReader(pathname: String) : IOPort {
    val file = File(pathname)
    val scanner = Scanner(file)
    override fun readLine(): String? {
        return if (scanner.hasNextLine()) scanner.nextLine()
        else {
            scanner.close()
            null
        }
    }

    override fun printLine(message: Any?) {
    }
}