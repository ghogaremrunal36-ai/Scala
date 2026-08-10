import breeze.linalg._
import scala.io.Source

object M2_Mrunal_practical8 {

  def main(args: Array[String]): Unit = {
    val file =
      "E:/mrunal/Scala/Mrunal/src/main/scala/ionosphere_data.csv"

    val lines =
      Source.fromFile(file).getLines().drop(1).toSeq

    val rows = lines.length
    val cols = lines.head.split(",", -1).length - 1
    val k = 2

    println(s"Dataset: $rows rows, $cols features")
    val data = DenseMatrix.zeros[Double](rows, cols)

    for (i <- lines.indices) {

      val v = lines(i).split(",", -1)

      for (j <- 0 until cols) {

        val value = v(j).trim

        data(i, j) =
          if (value.equalsIgnoreCase("TRUE"))
            1.0
          else if (value.equalsIgnoreCase("FALSE"))
            0.0
          else
            value.toDouble
      }
    }
    var centroids =
      DenseMatrix.zeros[Double](k, cols)
    val random =
      scala.util.Random.shuffle(
        (0 until rows).toList
      ).take(k)
    for (i <- 0 until k) {
      centroids(i, ::) := data(random(i), ::)
    }
    var assignments =
      DenseVector.zeros[Int](rows)
    var oldAssignments =
      DenseVector.fill[Int](rows)(-1)
    var iteration = 0
    var converged = false
    while (iteration < 100 && !converged) {

      for (i <- 0 until rows) {
        var minDistance = Double.MaxValue
        var cluster = 0
        for (j <- 0 until k) {
          val distance =
            euclideanDistance(
              data(i, ::).t,
              centroids(j, ::).t
            )
          if (distance < minDistance) {
            minDistance = distance
            cluster = j
          }
        }

        assignments(i) = cluster
      }
      if (assignments == oldAssignments) {
        converged = true
      }
      oldAssignments = assignments.copy

      centroids =
        DenseMatrix.zeros[Double](k, cols)
      val count =
        Array.fill(k)(0)
      for (i <- 0 until rows) {
        val cluster =
          assignments(i)

        centroids(cluster, ::) +=
          data(i, ::)

        count(cluster) += 1
      }
      for (j <- 0 until k) {

        if (count(j) > 0) {

          centroids(j, ::) /=
            count(j).toDouble
        }
      }

      iteration += 1
    }
    println("\n==============================")
    println("K-MEANS RESULTS")
    println("==============================")

    println(s"Iterations: $iteration")

    println("\nFinal Centroids:")
    println(centroids)

    println("\nCluster Counts:")
    for (j <- 0 until k) {

      val total =
        (0 until rows)
          .count(i => assignments(i) == j)

      println(
        s"Cluster $j = $total points"
      )
    }
  }
}