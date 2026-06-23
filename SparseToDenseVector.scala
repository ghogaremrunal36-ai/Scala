import breeze.linalg._
object SparseToDenseVector {
  def main(args: Array[String]): Unit = {

    val sparseVec = SparseVector(5)(1 -> 2.0, 3 -> 4.0, 4 -> 5.0)
    println("Sparse Vector:")
    println(sparseVec)

    val denseVec = sparseVec.toDenseVector
    println("\nDense Vector:")
    println(denseVec)

    val sum = breeze.linalg.sum(denseVec)
    val mean = sum / denseVec.length.toDouble
    val v2 = DenseVector(1.0, 2.0, 3.0, 4.0, 5.0)
    val dotProduct = denseVec dot v2

    println("\nSum = " + sum)
    println("Mean = " + mean)
    println("Dot Product = " + dotProduct)
  }
}