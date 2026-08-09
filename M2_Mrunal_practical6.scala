import breeze.linalg._
import breeze.numerics.sigmoid
import breeze.optimize.{LBFGS, DiffFunction}
import scala.io.Source
import javax.swing.{JFrame, JPanel, WindowConstants}
import java.awt.Graphics

object M2_Mrunal_practical6 {

  def main(args: Array[String]): Unit = {

    // Read CSV file
    val data = Source.fromFile("src/main/scala/Social_Network_Ads.csv")
      .getLines()
      .drop(1)
      .map(_.split(",").map(_.trim.toDouble))
      .toArray

    val n = data.length

    // Age and Salary
    val X = DenseMatrix(data.map(r =>
      Array(r(0), r(1) / 1000.0)): _*)

    val y = DenseVector(data.map(r => r(2)): _*)

    // Add intercept
    val X1 = DenseMatrix.horzcat(
      DenseMatrix.ones[Double](n, 1), X
    )

    // Logistic regression loss and gradient
    val f = new DiffFunction[DenseVector[Double]] {

      def calculate(w: DenseVector[Double]) = {

        val p = sigmoid(X1 * w)
        var loss = 0.0

        for (i <- 0 until n) {
          val pi = math.max(1e-10, math.min(1 - 1e-10, p(i)))
          loss -= y(i) * math.log(pi) +
            (1 - y(i)) * math.log(1 - pi)
        }

        loss /= n

        val gradient = (X1.t * (p - y)) * (1.0 / n)

        (loss, gradient)
      }
    }

    // Train model
    val optimizer = new LBFGS[DenseVector[Double]](
      maxIter = 100,
      m = 5
    )

    val weights =
      optimizer.minimize(f, DenseVector.zeros[Double](3))

    println("===== Logistic Regression =====")
    println("Intercept: " + weights(0))
    println("Age: " + weights(1))
    println("Salary: " + weights(2))

    // Accuracy
    var correct = 0

    for (i <- 0 until n) {
      val p = sigmoid(
        weights dot DenseVector(1.0, X(i, 0), X(i, 1))
      )

      val predicted =
        if (p >= 0.5) 1.0 else 0.0

      if (predicted == y(i))
        correct += 1
    }

    println(f"Accuracy: ${correct * 100.0 / n}%.2f%%")

    // Prediction
    val age = 30.0
    val salary = 50000.0

    val probability = sigmoid(
      weights dot DenseVector(
        1.0,
        age,
        salary / 1000.0
      )
    )

    println(f"\nPrediction probability: $probability%.2f")

    if (probability >= 0.5)
      println("Purchased: Yes")
    else
      println("Purchased: No")

    // Graph
    // Graph
    val frame = new JFrame("Logistic Regression Graph")
    frame.setSize(1000, 700)
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    frame.add(new JPanel {

      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)

        val g2 = g.asInstanceOf[java.awt.Graphics2D]

        val left = 100
        val bottom = 600
        val width = 800
        val height = 450

        // Title
        g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30))
        g2.drawString("Logistic Regression Graph", 330, 60)

        // Axes
        g2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 16))
        g2.drawLine(left, bottom, left + width, bottom)
        g2.drawLine(left, bottom, left, bottom - height)

        // X-axis label
        g2.drawString("Age", 480, 650)

        // Y-axis label
        g2.drawString("Estimated Salary", 10, 350)

        // X-axis numbers: Age
        for (age <- 0 to 100 by 10) {
          val x = left + (age * 8)

          g2.drawLine(x, bottom, x, bottom + 5)
          g2.drawString(age.toString, x - 5, bottom + 25)
        }

        // Y-axis numbers: Salary
        for (salary <- 0 to 150000 by 25000) {

          val y =
            bottom - (salary / 150000.0 * height).toInt

          g2.drawLine(left - 5, y, left, y)

          g2.drawString(
            salary.toString,
            15,
            y + 5
          )
        }

        // Plot data points
        for (r <- data) {

          val age = r(0)
          val salary = r(1)

          val x =
            left + (age * 8).toInt

          val y =
            bottom -
              (salary / 150000.0 * height).toInt

          if (r(2) == 0) {
            // Class 0
            g2.fillOval(x - 5, y - 5, 10, 10)
          } else {
            // Class 1
            g2.drawOval(x - 5, y - 5, 10, 10)
          }
        }

        // Decision boundary
        if (weights(2) != 0) {

          val age1 = 0.0
          val age2 = 100.0

          // Salary is calculated in thousands
          val salary1 =
            -(weights(0) + weights(1) * age1) /
              weights(2)

          val salary2 =
            -(weights(0) + weights(1) * age2) /
              weights(2)

          // Convert thousands to actual salary
          val actualSalary1 = salary1 * 1000
          val actualSalary2 = salary2 * 1000

          val x1 =
            left + (age1 * 8).toInt

          val x2 =
            left + (age2 * 8).toInt

          val y1 =
            bottom -
              (actualSalary1 / 150000.0 * height).toInt

          val y2 =
            bottom -
              (actualSalary2 / 150000.0 * height).toInt

          g2.setColor(java.awt.Color.RED)
          g2.drawLine(x1, y1, x2, y2)
          g2.setColor(java.awt.Color.BLACK)
        }

        // Legend
        g2.setFont(
          new java.awt.Font(
            "Arial",
            java.awt.Font.BOLD,
            16
          )
        )

        g2.fillOval(700, 90, 12, 12)
        g2.drawString(
          "Class 0 - Not Purchased",
          720,
          102
        )

        g2.drawOval(700, 120, 12, 12)
        g2.drawString(
          "Class 1 - Purchased",
          720,
          132
        )

        g2.drawLine(700, 155, 720, 155)
        g2.drawString(
          "Decision Boundary",
          730,
          160
        )
      }
    })

    frame.setVisible(true)
  }
}