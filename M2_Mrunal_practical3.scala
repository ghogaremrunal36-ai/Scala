import scala.io.Source

object M2_Mrunal_practical3 {
  def main(args: Array[String]): Unit = {

    val file = "E:\\mrunal\\Scala\\Mrunal\\src\\main\\scala\\_ARAB-SEA.csv"

    val prices = Source.fromFile(file).getLines()
      .drop(1)
      .map(_.split(",")(1))
      .toList

    val frequency = prices.groupBy(identity).map {
      case (price, list) => (price, list.size)
    }

    println("Price\tFrequency\tCumulative Frequency")

    var cumulative = 0

    for ((price, freq) <- frequency.toSeq.sortBy(_._1)) {
      cumulative += freq
      println(s"$price\t$freq\t\t$cumulative")
    }
  }

}
