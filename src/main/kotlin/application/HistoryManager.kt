package application

class HistoryManager: Logger {

    private var stack = ArrayDeque<String>()

    override fun getLogs(): List<String> {
        return stack.toList()
    }

    override fun add(info: String) {
        if (stack.size == 10) stack.removeFirstOrNull()
        stack.addLast(info)
    }
}