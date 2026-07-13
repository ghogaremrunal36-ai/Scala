import com.github.tototoshi.csv._
import java.io.File
import scala.util.Try


object mrunalPractical9 {
  def main(args: Array[String]): Unit = {

    val inputFile = new File("src/main/scala/BP.csv")
    val reader = CSVReader.open(inputFile)
    val allRows = reader.allWithHeaders()
    reader.close()

    // Numeric columns in BP.csv
    val numericColumns = Seq(
      "age",
      "SBP",
      "DBP",
      "BS",
      "BodyTem",
      "HeartRate",
      "MinExercise"
    )


    val stats: Map[String, (Double, Int)] = numericColumns.map { col =>

      val values = allRows.map(row => row.getOrElse(col, "").trim)

      val validNumbers = values.flatMap(v => Try(v.toDouble).toOption)

      val missingCount = values.count(v => v.isEmpty || Try(v.toDouble).isFailure)

      val mean =
        if (validNumbers.nonEmpty)
          validNumbers.sum / validNumbers.size
        else
          0.0

      (col, (mean, missingCount))

    }.toMap

    println("\n------ Missing Data Report ------")

    stats.foreach {
      case (col, (mean, missing)) =>
        println(f"$col%-12s Missing = $missing%-3d Mean = $mean%.2f")
    }


    val cleanedRows = allRows.map { row =>

      numericColumns.foldLeft(row) { (updatedRow, col) =>

        val value = updatedRow.getOrElse(col, "").trim

        val newValue =
          Try(value.toDouble).toOption match {
            case Some(_) => value
            case None    => f"${stats(col)._1}%.2f"
          }

        updatedRow.updated(col, newValue)
      }
    }

    val outputFile = new File("BP_cleaned.csv")

    val writer = CSVWriter.open(outputFile)

    val headers = cleanedRows.head.keys.toSeq

    writer.writeRow(headers)

    cleanedRows.foreach(row => writer.writeRow(headers.map(row)))

    writer.close()

    println("\nMissing values replaced successfully.")
    println("Cleaned file saved as BP_cleaned.csv")
  }
}
