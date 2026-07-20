import com.github.tototoshi.csv._
import java.io.File

object mrunalpractical12 {

  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(new File("src/main/scala/census.csv"))
    val data = reader.allWithHeaders()
    reader.close()
    val categories = data.map(_("income")).distinct.sorted
    val newData = data.map { row =>
      val income = row("income")

      val oneHot = categories.map(cat =>
        cat -> (if (cat == income) "1" else "0")
      ).toMap

      (row - "income") ++ oneHot
    }
    val headers = newData.head.keys.toList
    println(headers.mkString(", "))

    newData.foreach { row =>
      println(headers.map(row).mkString(", "))
    }
    val writer = CSVWriter.open(new File("census_encoded.csv"))
    writer.writeRow(headers)
    newData.foreach(row => writer.writeRow(headers.map(row)))
    writer.close()
    println("One-hot encoded file written to census_encoded.csv")
  }
}