import breeze.linalg._
object submatrix {
  def main(args: Array[String]): Unit = {

    val matrix = DenseMatrix(
      (11, 22, 33, 44),
      (55, 66, 77, 88),
      (99, 10, 20, 30),
      (40, 50, 60, 70)
    )
    println(s"Original Matrix:\n$matrix")

    val subMatrix = matrix(1 to 2, 1 to 3)
    println(s"\nSub-Matrix (rows 1-2, cols 1-3):\n$subMatrix")

    val rowSums = sum(subMatrix(*, ::))
    println(s"\nRow Sums: $rowSums")

    val colSums = sum(subMatrix(::, *))
    println(s"Column Sums: $colSums")
  }
}
