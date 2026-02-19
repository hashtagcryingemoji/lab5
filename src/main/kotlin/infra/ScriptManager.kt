package infra

import java.io.EOFException
import kotlin.collections.ArrayDeque

class ScriptManager {
    private val st = ArrayDeque<ScriptParser>()

    fun addToStack(scriptParser: ScriptParser) = st.addLast(scriptParser)

    fun getCommand(): String{
        if (st.isEmpty()) throw EOFException()

        val lastScript = st.last()
        val res: String

        if (lastScript.isDone()){
            st.last().closeScanner()
            st.removeLast()
            res = getCommand()
        }
        else {
            res = lastScript.getLine()!!
        }
        return res
    }

    fun clearStack() = st.clear()
}