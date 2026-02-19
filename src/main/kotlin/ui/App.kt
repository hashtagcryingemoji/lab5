package ui

import application.CommandInvoker
import application.IOPort

class App (private val invoker: CommandInvoker,
           private val io: IOPort
) {
    fun run() {
        while (true) {
            val line = io.readLine()
            if (line.isBlank()) continue


            invoker.handleInput(line)

            if (line.trim().lowercase() == "exit") break
        }
    }
}