import scala.io.Source

object M2_Mrunal_practical2 extends App {

  val file = Source.fromFile("src/main/scala/car data.csv")

  val prices = file.getLines().drop(1).map { line =>
    line.split(",")(1).toDouble
  }.toList

  file.close()

  val window = 3

  val sma = prices.sliding(window).map(_.sum / window).toList

  val weights = List(1.0, 2.0, 3.0)
  val weightSum = weights.sum

  val wma = prices.sliding(window).map { values =>
    values.zip(weights).map {
      case (price, weight) => price * weight
    }.sum / weightSum
  }.toList

  val alpha = 2.0 / (window + 1)

  var ema = List(prices.head)

  for (i <- 1 until prices.length) {
    val value = alpha * prices(i) + (1 - alpha) * ema.last
    ema = ema :+ value
  }

  println("Original Prices")
  println(prices)

  println("\nSimple Moving Average (SMA)")
  println(sma)

  println("\nWeighted Moving Average (WMA)")
  println(wma)

  println("\nExponential Moving Average (EMA)")
  println(ema)
}
