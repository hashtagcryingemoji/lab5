import application.ApplicationExecutor
import ui.CliManager
import pashalko.EasterEggGenerator.Companion.mostValuableFunc
import pashalko.EasterEggGenerator.Companion.idk

fun main(args: Array<String>) {
    val cliManager = CliManager()
    val app = ApplicationExecutor(cliManager, args.getOrNull(0) ?: "")
    idk()

    app.run()

    mostValuableFunc()
}
