import breeze.linalg._
import scala.io.Source
import java.awt._
import javax.swing._

object M2_Mrunal_Practical5 {

  class Graph(
               x: Array[Double],
               y: Array[Double],
               b: Double,
               m: Double
             ) extends JPanel {

    override def paintComponent(g: Graphics): Unit = {
      super.paintComponent(g)

      val g2 = g.asInstanceOf[Graphics2D]

      val w = getWidth
      val h = getHeight

      val left = 70
      val bottom = 60

      val maxX = x.max
      val maxY = y.max

      def sx(v: Double): Int =
        left + (v / maxX * (w - left - 30)).toInt

      def sy(v: Double): Int =
        h - bottom - (v / maxY * (h - 100)).toInt


      g2.setColor(Color.BLACK)
      g2.setFont(new Font("Arial", Font.BOLD, 28))
      g2.drawString(
        "Linear Regression Graph",
        w / 2 - 180,
        40
      )
      g2.setColor(Color.BLACK)

      g2.drawLine(
        left,
        60,
        left,
        h - bottom
      )
      g2.drawLine(
        left,
        h - bottom,
        w - 30,
        h - bottom
      )
      g2.setColor(Color.BLUE)

      for (i <- x.indices) {
        g2.fillOval(
          sx(x(i)) - 5,
          sy(y(i)) - 5,
          10,
          10
        )
      }
      g2.setColor(Color.RED)

      g2.drawLine(
        sx(0),
        sy(b),
        sx(maxX),
        sy(b + m * maxX)
      )
      g2.setColor(Color.BLACK)

      g2.drawString(
        "X",
        w / 2,
        h - 20
      )
      g2.drawString(
        "Y",
        25,
        75
      )
      g2.setColor(Color.BLUE)

      g2.fillOval(
        w - 250,
        80,
        12,
        12
      )

      g2.setColor(Color.BLACK)

      g2.drawString(
        "Original Data",
        w - 225,
        92
      )
      g2.setColor(Color.RED)

      g2.drawLine(
        w - 250,
        115,
        w - 230,
        115
      )
      g2.setColor(Color.BLACK)

      g2.drawString(
        "Regression Line",
        w - 225,
        120
      )
    }
  }
  def main(args: Array[String]): Unit = {
    val lines = Source
      .fromFile("src/main/scala/linear regression dataset.csv")
      .getLines()
      .toList
    val data = lines.flatMap { line =>

      val p = line.split(",").map(_.trim)

      if (p.length == 2) {
        try {
          Some(
            (
              p(0).toDouble,
              p(1).toDouble
            )
          )
        } catch {
          case _: NumberFormatException =>
            None
        }
      } else {
        None
      }
    }
    val X = DenseVector(
      data.map(_._1).toArray
    )
    val Y = DenseVector(
      data.map(_._2).toArray
    )
    println("Original Data")
    println("X = " + X)
    println("Y = " + Y)

    val xm = sum(X) / X.length
    val ym = sum(Y) / Y.length

    val m =
      (X - xm).dot(Y - ym) /
        (X - xm).dot(X - xm)

    val b = ym - m * xm
    println("\nLinear Regression Results")

    println("Intercept = " + b)
    println("Slope = " + m)

    println("\nRegression Equation:")

    println(
      "y = " + b + " + " + m + "x"
    )
    val inputX = 80.0

    val prediction =
      b + m * inputX

    println("\nPrediction")
    println(
      "For x = " + inputX
    )
    println(
      "Predicted y = " + prediction
    )
    SwingUtilities.invokeLater(() => {

      val frame =
        new JFrame("Linear Regression Graph")

      frame.setDefaultCloseOperation(
        WindowConstants.EXIT_ON_CLOSE
      )
      frame.setSize(
        1200,
        800
      )
      frame.add(
        new Graph(
          X.toArray,
          Y.toArray,
          b,
          m
        )
      )
      frame.setLocationRelativeTo(null)
      frame.setVisible(true)
    })
  }
}