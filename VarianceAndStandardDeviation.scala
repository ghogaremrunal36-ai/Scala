import scala.util.Random
import scala.math.sqrt
object VarianceAndStandardDeviation {
  def main(args: Array[String]): Unit = {

    val data = Array.fill(10)(Random.nextInt(100) + 1)
    println("Dataset: " + data.mkString(", "))

    val mean = data.sum.toDouble / data.length
    val variance = data.map(x => math.pow(x - mean, 2)).sum / data.length
    val standardDeviation = sqrt(variance)

    println(f"Mean = $mean%.2f")
    println(f"Variance = $variance%.2f")
    println(f"Standard Deviation = $standardDeviation%.2f")
  }

}
