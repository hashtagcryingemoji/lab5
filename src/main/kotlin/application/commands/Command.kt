package application.commands

interface Command {
    val name: String
    val description: String
    fun execute(argument: String)
}