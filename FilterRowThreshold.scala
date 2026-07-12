import com.github.tototoshi.csv._
import java.io.File

object FilterRowThreshold {
  def main(args: Array[String]): Unit = {
    val reader = CSVReader.open(new File("src/main/scala/heart.csv"))
    val data = reader.allWithHeaders()
    reader.close()
    val threshold = 300
    // Filter rows where "Cholesterol" > 200
    val filteredRows = data.filter { row =>
      row.get("Cholesterol").exists(value => value.toIntOption.exists(_ >
        threshold))
    }
    println(s"\nTotal Rows with chol > $threshold: ${filteredRows.length}\n")
    // Print each filtered row
    filteredRows.foreach { row =>
      println(row.values.mkString(", "))
    }
  }
}
