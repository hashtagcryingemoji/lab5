import application.ApplicationExecutor
import ui.CliManager

fun main(args: Array<String>?) {
    val cliManager = CliManager()
    val app = ApplicationExecutor(cliManager, args?.getOrNull(0) ?: "")
    app.run()


}