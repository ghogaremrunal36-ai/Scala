import breeze.linalg._

object DenseVectorBreeze {
  def main(args: Array[String]): Unit = {

    // Create dense vectors
    val v1 = DenseVector(1.0, 2.0, 3.0, 4.0, 5.0)
    val v2 = DenseVector(5.0, 4.0, 3.0, 2.0, 1.0)

    println(s"Vector v1: $v1")
    println(s"Vector v2: $v2")

    // Sum of elements in v1
    val sum = breeze.linalg.sum(v1)
    println(f"Sum of v1: $sum%.2f")

    // Mean of elements in v1
    val mean = breeze.stats.mean(v1)
    println(f"Mean of v1: $mean%.2f")

    // Dot product of v1 and v2
    val dotProduct = v1 dot v2
    println(f"Dot Product of v1 and v2: $dotProduct%.2f")
  }
}

