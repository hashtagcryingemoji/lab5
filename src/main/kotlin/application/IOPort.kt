package application

interface IOPort {
    fun printLine(message: Any?)
    fun printBefore(message: Any?)
    fun readLine(): String?
}