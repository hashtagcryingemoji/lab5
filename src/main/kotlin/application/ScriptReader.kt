package application

import application.exceptions.ScriptInterruptedWhileReadingException
import java.io.File
import java.io.FileNotFoundException
import java.util.Scanner

class ScriptReader(pathname: String) : IOPort {
    val file = File(pathname)
    val scanner = Scanner(file)
    init {
        if (!file.exists()) {
            throw FileNotFoundException(pathname)
        }
    }

    override fun printBefore(message: Any?) {
    }
    override fun readLine(): String? {
        try {
            return if (scanner.hasNextLine()) scanner.nextLine()
            else {
                scanner.close()
                null
            }
        } catch (e: IllegalStateException){
            throw ScriptInterruptedWhileReadingException("Кажется скрипт ${file.absolutePath} заполнен не до конца...")
        }
    }

    override fun printLine(message: Any?) {
    }

    fun getCurrentPath(): String = this.file.absolutePath
}