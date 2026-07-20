import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File
object MrunalPractical14 {
  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(new File("src/main/scala/maintenance.csv"))
    val data = reader.allWithHeaders()
    reader.close()
    val resolutionTimes =
      DenseVector(
        data.map(_("Resolution_Time_Hours").toDouble).toArray
      )
    val fig = Figure("Histogram of Resolution Time")
    val binSizes = List(5, 10, 20)
    for ((bins, idx) <- binSizes.zipWithIndex) {
      val plt = fig.subplot(1, binSizes.length, idx)
      plt += hist(resolutionTimes, bins)
      plt.title = s"Histogram with $bins bins"
      plt.xlabel = "Resolution Time (Hours)"
      plt.ylabel = "Frequency"
    }
    fig.refresh()
  }
}
