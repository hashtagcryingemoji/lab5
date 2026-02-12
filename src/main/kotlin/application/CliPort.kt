package application

interface CliPort {
    fun userOutput(message: String)
    fun userInput(): String
}