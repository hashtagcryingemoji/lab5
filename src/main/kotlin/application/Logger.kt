package application

interface Logger {
    fun add(info: String)
    fun getLogs(): List<String>
}