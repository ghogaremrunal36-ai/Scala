import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File

object CombinedPlot {
  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(new File("src/main/scala/maintenance.csv"))
    val data = reader.allWithHeaders()
    reader.close()
    val resolutionTimes =
      data.map(_("Resolution_Time_Hours").toDouble)
    val x = DenseVector(
      (0 until resolutionTimes.length).map(_.toDouble).toArray
    )
    val y = DenseVector(
      resolutionTimes.toArray
    )
    val fig = Figure("Maintenance - Line + Scatter Plot")
    val plt = fig.subplot(0)

    plt += plot(
      x,
      y,
      name = "Resolution Time",
      colorcode = "blue"
    )
    plt += plot(
      x,
      y,
      '.',
      name = "Resolution Time Points",
      colorcode = "red"
    )
    plt.xlabel = "Time (Days)"
    plt.ylabel = "Resolution Time (Hours)"
    plt.title = "Maintenance Resolution Time - Line + Scatter"

    plt.legend = true

    fig.refresh()
  }
}