package application.commands

import application.Handler

interface Command {
    val app: Handler
    val name: String
    val description: String
    fun execute(argument: String)
}
