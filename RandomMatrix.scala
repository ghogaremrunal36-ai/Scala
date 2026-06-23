import breeze.linalg._
import breeze.stats.distributions.Rand
object RandomMatrix {
  def main(args: Array[String]): Unit = {
    val matrix = DenseMatrix.rand[Double](3, 3, Rand.uniform)
    println("Original Matrix:")
    println(matrix)

    val transposeMatrix = matrix.t
    println("\nTranspose Matrix:")
    println(transposeMatrix)

    val determinantValue = det(matrix)
    println("\nDeterminant: " + determinantValue)
  }
}
