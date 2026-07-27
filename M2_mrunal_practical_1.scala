import scala.io.Source

object M2_mrunal_practical_1 extends App {

  try {
    val file = Source.fromFile("src/main/scala/car data.csv")
    val data = file.getLines().drop(1).flatMap { line =>
      val cols = line.split(",")

      if (cols.length >= 4) {
        for {
          x <- cols(2).trim.toDoubleOption
          y <- cols(3).trim.toDoubleOption
        } yield (x, y)
      } else {
        None
      }
    }.toList

    file.close()
    if (data.isEmpty) {
      println("No valid numeric data found.")
      System.exit(0)
    }
    val (x, y) = data.unzip
    val n = x.length.toDouble
    val meanX = x.sum / n
    val meanY = y.sum / n
    val numerator = x.zip(y).map {
      case (xi, yi) =>
        (xi - meanX) * (yi - meanY)
    }.sum

    val denominator = math.sqrt(
      x.map(xi => math.pow(xi - meanX, 2)).sum *
        y.map(yi => math.pow(yi - meanY, 2)).sum
    )
    val r =
      if (denominator == 0) 0.0
      else numerator / denominator
    val relation =
      if (r > 0.7)
        "Strong Positive"
      else if (r > 0)
        "Weak Positive"
      else
        "Negative"
    val df = n - 2
    val tStat =
      if (math.abs(r) == 1.0)
        Double.PositiveInfinity
      else
        r * math.sqrt(df / (1 - r * r))
    val isSignificant = math.abs(tStat) > 1.96

    println("--------------------------------------")
    println(s"Dataset Size : ${n.toInt} records")
    println(f"Pearson Correlation (r) : $r%.4f")
    println(s"Relationship : $relation")
    println(f"t-Statistic : $tStat%.4f")
    println(s"Significant at 5% level : $isSignificant")
    println("--------------------------------------")

  } catch {

    case _: java.io.FileNotFoundException =>
      println("Error: File not found!")

    case e: Exception =>
      println("Error: " + e.getMessage)

  }

}