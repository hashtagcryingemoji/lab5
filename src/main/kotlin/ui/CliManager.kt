package ui

import application.CliPort
import java.util.Scanner

class CliManager: CliPort {
    private val scanner = Scanner(System.`in`)

    override fun userOutput(message: String) {
        print(message)
    }

    override fun userInput(): String {
        val text = scanner.next()
        return text
    }
}