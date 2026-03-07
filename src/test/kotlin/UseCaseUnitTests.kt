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
    fun `script endless recursion test case`(){
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

    @Test
    fun `script not found test case`(){
        val io = mockk<IOPort>(relaxed = true)
        val pathName = "invalidPath.txt"
        every { io.readLine() } returns "execute_script $pathName" andThen "exit"
        val app = ApplicationExecutor(io, "")
        app.run()
        verify(exactly = 1) { io.printLine("Похоже, что такого файла ($pathName) вовсе не существовало...") }
    }

    @Test
    fun `remove greater organizations use case`(){
        val io = mockk<IOPort>(relaxed = true)
        every { io.readLine() } returnsMany listOf(
            "add",
            "Test4",
            "5",
            "5",
            "5",
            "Unique4" ,
            "",
            "public",
            "",
            "index",
            "add",
            "Test3",
            "5",
            "5",
            "5",
            "Unique3" ,
            "",
            "public",
            "",
            "index",
            "add",
            "Test2",
            "5",
            "5",
            "5",
            "Unique2" ,
            "",
            "public",
            "",
            "index",
            "remove_greater",
            "Test1",
            "5",
            "5",
            "5",
            "Unique1" ,
            "",
            "public",
            "",
            "index",
            "exit")
        val app = ApplicationExecutor(io, "")
        app.run()
        verify(exactly = 1) { io.printLine("Из коллекции удалено 3 элементов") }
    }
    @Test
    fun `remove lower organizations use case`(){
        val io = mockk<IOPort>(relaxed = true)
        every { io.readLine() } returnsMany listOf(
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
            "add",
            "Test2",
            "5",
            "5",
            "5",
            "Unique2" ,
            "",
            "public",
            "",
            "index",
            "add",
            "Test3",
            "5",
            "5",
            "5",
            "Unique3" ,
            "",
            "public",
            "",
            "index",
            "remove_lower",
            "Test4",
            "5",
            "5",
            "5",
            "Unique4" ,
            "",
            "public",
            "",
            "index",
            "exit")
        val app = ApplicationExecutor(io, "")
        app.run()
        verify(exactly = 1) { io.printLine("Из коллекции удалено 3 элементов") }
    }
    @Test
    fun `remove by id test case`(){
        val io = mockk<IOPort>(relaxed = true)
        every { io.readLine() } returnsMany listOf(
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
            "remove_by_id 1",
            "exit"
        )
        val app = ApplicationExecutor(io, "")
        app.run()
        verify(exactly = 1) { io.printLine("Элемент с ID 1 удален.") }
    }

    @Test
    fun `sum of employees use case`(){
        val io = mockk<IOPort>(relaxed = true)
        every { io.readLine() } returnsMany listOf(
            "add",
            "Test1",
            "5",
            "5",
            "5",
            "Unique1" ,
            "5",
            "public",
            "",
            "index",
            "add",
            "Test2",
            "5",
            "5",
            "5",
            "Unique2" ,
            "6",
            "public",
            "",
            "index",
            "add",
            "Test3",
            "5",
            "5",
            "5",
            "Unique3" ,
            "7",
            "public",
            "",
            "index",
            "sum_of_employees_count",
            "exit"
        )
        val app = ApplicationExecutor(io, "")
        app.run()
        verify(exactly = 1) { io.printLine("Общее количество работяг в коллекции: 18") }
    }

}