import java.nio.file.FileAlreadyExistsException
import java.nio.file.Files
import java.nio.file.Paths
import java.time.MonthDay
import java.time.Year

fun main() {
  initializeNewDay()
}

fun initializeNewDay(year: Int = Year.now().value, day: Int = MonthDay.now().dayOfMonth) {
  val strYear = year.toString()
  val strDay = day.toString().padStart(2, '0')

  Files.createDirectories(Paths.get("src/_$strYear/Day$strDay"))
  createFile("src/_$strYear/Day$strDay/Day$strDay.kt")
  createFile("src/_$strYear/Day$strDay/Day$strDay.txt")
  createFile("src/_$strYear/Day$strDay/Day${strDay}_test.txt")
}

fun createFile(fileName: String) {
  try {
    Files.createFile(Paths.get(fileName))
  } catch (e: FileAlreadyExistsException) {
    println(e.message)
  }
}