package application

interface IOPort {
    fun printLine(message: Any?)
    fun readLine(): String
}