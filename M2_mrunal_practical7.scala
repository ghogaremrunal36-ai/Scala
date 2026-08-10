import breeze.linalg.{DenseVector, euclideanDistance}
import scala.io.Source

object M2_mrunal_practical7 {
  case class DataPoint(
                        features: DenseVector[Double],
                        label: String
                      )

  def main(args: Array[String]): Unit = {

    val fileName = "E:/mrunal/Scala/Mrunal/src/main/scala/whisky.csv"
    val source = Source.fromFile(fileName)
    val dataset = source
      .getLines()
      .drop(1)
      .map { line =>

        val values = line.split(",", -1)
        val features = DenseVector(
          values(2).toDouble,
          values(3).toDouble,
          values(4).toDouble,
          values(5).toDouble,
          values(6).toDouble,
          values(7).toDouble,
          values(8).toDouble,
          values(9).toDouble,
          values(10).toDouble,
          values(11).toDouble,
          values(12).toDouble,
          values(13).toDouble
        )
        val label = values(1)

        DataPoint(features, label)
      }
      .toSeq

    source.close()
    println("WHISKY DATASET")
    println("--------------------------------")

    dataset.foreach { point =>
      println(
        s"Features: ${point.features} | Distillery: ${point.label}"
      )
    }
    val newPoint = DenseVector(
      2.0,
      3.0,
      1.0,
      0.0,
      0.0,
      2.0,
      2.0,
      1.0,
      2.0,
      2.0,
      2.0,
      2.0
    )
    println("\nNew Whisky:")
    println(newPoint)

    var minDistance = Double.MaxValue
    var predictedLabel = ""

    for (point <- dataset) {

      val distance =
        euclideanDistance(newPoint, point.features)

      println(
        s"Distance to ${point.label}: $distance"
      )

      if (distance < minDistance) {
        minDistance = distance
        predictedLabel = point.label
      }
    }
    println("\n--------------------------------")
    println("KNN CLASSIFICATION RESULT")
    println("--------------------------------")

    println(s"Nearest Distance: $minDistance")
    println(s"Predicted Distillery: $predictedLabel")
  }
}
