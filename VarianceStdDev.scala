import breeze.linalg._
import breeze.stats._
import scala.util.Random

object VarianceStdDev {
  def main(args: Array[String]): Unit = {

    // Create a DenseVector instead of a standard Scala Vector for Breeze stats
    val data = DenseVector.fill(10)(Random.nextInt(100).toDouble)

    val meanValue = mean(data)
    val varianceValue = variance(data)
    val stdDevValue = stddev(data)

    println("Random Dataset: " + data)
    println("Mean: " + meanValue)
    println("Variance: " + varianceValue)
    println("Standard Deviation: " + stdDevValue)
  }
}

