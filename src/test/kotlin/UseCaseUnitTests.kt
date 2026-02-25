import application.ApplicationExecutor
import application.IOPort
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter


class UseCaseUnitTests {

    @Test
    fun `exit command use case`() {
        val cliManager = mockk<IOPort>(relaxed = true)

        every {cliManager.readLine() } returns "exit"

        val app = ApplicationExecutor(cliManager, "")

        app.run()

        verify(exactly = 1) { cliManager.readLine() }
        verify(exactly = 2) { cliManager.printLine(any()) }
    }

    @Test
    fun `unusual user output use case`() {
        val cliManager = mockk<IOPort>(relaxed = true)

        every { cliManager.readLine() } returnsMany listOf(null, "", "exit")

        val app = ApplicationExecutor(cliManager, "")

        app.run()

        verify(exactly = 1) { cliManager.readLine() }
        verify(exactly = 1) { cliManager.printLine(any()) }
        verify(exactly = 1) { cliManager.printBefore(any()) }
    }

    @Test
    fun `organization adding use case`(){
        val cliManager = mockk<IOPort>(relaxed = true)

        every { cliManager.readLine() } returnsMany listOf(
            "add",
            "Test1",
            "5",
            "5",
            "5",
            "Unique1" ,
            "",
            "public",
            "",
            "index",
            "exit"
        )

        val app = ApplicationExecutor(cliManager, "")

        app.run()

        verify(exactly = 11) { cliManager.readLine() }
    }

    @Test
    fun `script not found test case`(){
        val cliManager = mockk<IOPort>(relaxed = true)

        val file = File("w.txt")
        val writer = FileWriter(file)
        writer.write("execute_script w.txt")
        writer.close()
        every { cliManager.readLine() } returnsMany listOf("execute_script w.txt", "exit")

        val app = ApplicationExecutor(cliManager, "")

        app.run()

        verify { cliManager.printLine("Обнаружена бесконечная рекурсия скриптов: w.txt")}
    }

    @Test
    fun `download collection use case`(){
        val cliManager = mockk<IOPort>(relaxed = true)

        val writer = PrintWriter("w.xml")
        writer.print("")
        writer.print("<Organizations/>")
        writer.close()

        every { cliManager.readLine() } returnsMany listOf(
            "add",
            "Test1",
            "5",
            "5",
            "5",
            "Unique1" ,
            "",
            "public",
            "",
            "index",
            "save w.xml",
            "exit"
        )

        val app = ApplicationExecutor(cliManager, "w.xml")
        val storageGateway = app.storageGateway

        app.run()

        val res = storageGateway.downloadCollection("w.xml")
        assertEquals(1, res.size)
    }
}