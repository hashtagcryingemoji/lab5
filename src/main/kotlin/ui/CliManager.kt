package ui

import application.IOPort
import java.util.Scanner

class CliManager: IOPort {
    private val scanner = Scanner(System.`in`)

    override fun printLine(message: Any?) {
        println(message)
    }

    override fun readLine(): String? {
        return if (scanner.hasNextLine()) scanner.nextLine()
        else {
            scanner.close()
            null
        }

    }
}