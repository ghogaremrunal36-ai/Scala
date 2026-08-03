import scala.io.Source

object M2_Mrunal_Practical4 {
  def main(args: Array[String]): Unit = {


    val file = "E:/mrunal/Scala/Mrunal/src/main/scala/_MAADEN.csv"

    // Read CSV
    val data = Source.fromFile(file).getLines()
      .drop(1)
      .map(_.split(","))
      .filter(_.length >= 8)
      .toList

    val sortedData = data.sortBy(row => -row(2).trim.toDouble)

    val top5 = sortedData.take(5)

    println("Top 5 Rows Sorted by Price")
    println("-------------------------------------------------------------")
    println("Date\t\t\tPrice\tOpen\tHigh\tLow\tVolume\tChange %")

    top5.foreach { row =>
      val date = row(0).replace("\"", "") + "," + row(1)
      println(s"$date\t${row(2)}\t${row(3)}\t${row(4)}\t${row(5)}\t${row(6)}\t${row(7)}")
    }
  }
}
