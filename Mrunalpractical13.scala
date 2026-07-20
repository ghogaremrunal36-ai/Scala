import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File

object Mrunalpractical13 {

  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(new File("src/main/scala/maintenance.csv"))
    val data = reader.allWithHeaders()
    reader.close()

    val software = data.filter(_("Issue_Type") == "Software")
    val hardware = data.filter(_("Issue_Type") == "Hardware")
    val connectivity = data.filter(_("Issue_Type") == "Connectivity")

    def extractXY(rows: List[Map[String, String]]) = {

      val x = DenseVector((1 to rows.length).map(_.toDouble).toArray)
      val y = DenseVector(
        rows.map(_("Resolution_Time_Hours").toDouble).toArray
      )
      (x, y)
    }
    val (xSoftware, ySoftware) = extractXY(software)
    val (xHardware, yHardware) = extractXY(hardware)
    val (xConnectivity, yConnectivity) = extractXY(connectivity)

    val fig = Figure()
    val plt = fig.subplot(0)

    plt.title = "Resolution Time by Issue Type"
    plt.xlabel = "Issue Number"
    plt.ylabel = "Resolution Time (Hours)"

    plt += plot(
      xSoftware,
      ySoftware,
      '.',
      name = "Software",
      colorcode = "blue"
    )
    plt += plot(
      xHardware,
      yHardware,
      '.',
      name = "Hardware",
      colorcode = "red"
    )

    plt += plot(
      xConnectivity,
      yConnectivity,
      '.',
      name = "Connectivity",
      colorcode = "green"
    )

    plt.legend = true

    fig.refresh()
  }
}