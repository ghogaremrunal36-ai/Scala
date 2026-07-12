import breeze.linalg._
object prac7elementwise {
  def main(args: Array[String]): Unit = {

    val mat1 = DenseMatrix((1.0, 2.0), (3.0, 4.0))
    val mat2 = DenseMatrix((5.0, 6.0), (7.0, 8.0))

    val addition = mat1 + mat2
    val subtraction = mat1 - mat2
    val multiplication = mat1 * mat2
     val division = mat1 / mat2

    println("Matrix 1:")
    println(mat1)
    println("\nMatrix 2:")
    println(mat2)
    println("\nAddition:")
    println(addition)
    println("\nSubtraction:")
    println(subtraction)
    println("\nElement-wise Multiplication:")
    println(multiplication)
    println("\nElement-wise Division:")
    println(division)
  }
}

