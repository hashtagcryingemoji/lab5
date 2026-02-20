package application


interface Handler {
    val io: IOPort
    val invoker: CommandInvoker
    val collectionManager: CollectionManager
    val inputReader: InputReader

    fun handleError(e: Exception)
    fun run()

}