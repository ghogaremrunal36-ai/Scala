import com.github.tototoshi.csv._
import java.io.File

object mrubalpractical10 {
  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(new File("src/main/scala/customer.csv"))
    val data = reader.allWithHeaders()
    reader.close()

    val threshold = 50

    // Filter rows where age > 50
    val filteredRows = data.filter { row =>
      row.get("age").exists(value => value.toIntOption.exists(_ > threshold))
    }

    println(s"\nTotal Rows with age > $threshold: ${filteredRows.length}\n")

    // Print each filtered row
    filteredRows.foreach { row =>
      println(row.values.mkString(", "))
    }
  }
}
