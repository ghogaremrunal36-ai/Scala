import scala.io.Source

object M2_Mrunal_15 {
  def main(args: Array[String]): Unit = {

    val filePath = "E:\\mrunal\\Scala\\MKG\\src\\main\\scala\\apple_products.csv"

    val source = Source.fromFile(filePath)

    val lines = source.getLines().toList

    source.close()

    val data = lines.tail
    val salePrices = data.map { line =>

      val columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")

      columns(3).trim.toDouble
    }
    val degree = 3

    println("APPLE PRODUCTS - POLYNOMIAL FEATURES")
    println("=====================================")
    println()

    println("Sale Price\tX\t\tX^2\t\tX^3")
    println("-----------------------------------------------")
    salePrices.foreach { x =>

      val x1 = x
      val x2 = Math.pow(x, 2)
      val x3 = Math.pow(x, 3)

      println(
        f"$x%.0f\t\t$x1%.0f\t\t$x2%.0f\t\t$x3%.0f"
      )
    }
    val polynomialFeatures = salePrices.map { x =>

      Array(
        x,
        Math.pow(x, 2),
        Math.pow(x, 3)
      )
    }
    println()
    println("Polynomial features generated successfully.")

    println()
    println(s"Number of records : ${polynomialFeatures.length}")
    println(s"Polynomial degree  : $degree")
  }
}
