import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File

object mrunalPractical15 {
  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(new File("src/main/scala/maintenance.csv"))
    val data = reader.allWithHeaders()
    reader.close()

    val resolutionTimes =
      data.map(_("Resolution_Time_Hours").toDouble)
    val x = DenseVector(
      (1 to resolutionTimes.length).map(_.toDouble).toArray
    )
    val y = DenseVector(
      resolutionTimes.toArray
    )

    val fig = Figure("Maintenance Resolution Time Trend")
    val plt = fig.subplot(0)
    plt += plot(
      x,
      y,
      name = "Resolution Time",
      colorcode = "blue"
    )

    plt.xlabel = "Maintenance Log Number"
    plt.ylabel = "Resolution Time (Hours)"
    plt.title = "Resolution Time for Maintenance Logs"

    fig.refresh()
  }
}
