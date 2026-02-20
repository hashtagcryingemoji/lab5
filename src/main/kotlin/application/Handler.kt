package application


interface Handler {
    val io: IOPort
    val invoker: CommandInvoker
    val inputReader: InputReader
    val collectionManager: CollectionManager
    val storageGateway: StorageGateway
    val logsManager: Logger

    fun handleError(e: Exception)
    fun run()

}