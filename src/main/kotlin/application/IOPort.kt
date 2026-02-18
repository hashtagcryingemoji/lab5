package application

interface IOPort {
    fun printLine(message: Any?)
    fun printError(message: Any?)
    fun readLine(): String
}