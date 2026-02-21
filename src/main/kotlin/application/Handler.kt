package application


interface Handler {
    val io: IOPort
    val invoker: CommandInvoker
    val inputReader: InputReader
    val collectionManager: CollectionManager
    val storageGateway: StorageGateway
    val logsManager: Logger
    val setOfPaths: MutableSet<String>
    val initialPath: String
    fun handleError(e: Exception)
    fun run()

}