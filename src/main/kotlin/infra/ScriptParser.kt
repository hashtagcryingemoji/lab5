package infra

import java.io.File
import java.util.Scanner

class ScriptParser(path: String) {
    private val file = File(path)
    private val sc = Scanner(file)

    fun getLine(): String? {
        return if (sc.hasNextLine()) sc.nextLine()

        else return null
    }

    fun isDone(): Boolean{
        return !sc.hasNextLine()
    }

    fun closeScanner() = sc.close()
}