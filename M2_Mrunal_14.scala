import scala.io.Source
import scala.math.Ordering
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object M2_Mrunal_14 {
  def main(args: Array[String]): Unit = {


    val filePath = "E:\\mrunal\\Scala\\MKG\\src\\main\\scala\\worlds2022_champs.csv"


    val source = Source.fromFile(filePath)

    val lines = source.getLines().toList

    source.close()


    val data = lines.tail

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val dates = data.map { line =>

      val columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")

      val dateString = columns(2).trim

      LocalDateTime.parse(dateString, formatter).toLocalDate
    }

    val gamesPerDay = dates
      .groupBy(identity)
      .map {
        case (date, games) => (date, games.size)
      }
      .toSeq
      .sortBy(_._1.toString)


    println("WORLD 2022 CHAMPIONS - TIME SERIES ANALYSIS")
    println("============================================")
    println()

    println("Date\t\tGames Played")
    println("--------------------------------")

    gamesPerDay.foreach {
      case (date, count) =>
        println(s"$date\t$count")
    }
    val totalGames = dates.size

    val totalDays = gamesPerDay.size

    val averageGames =
      totalGames.toDouble / totalDays

    val maximum = gamesPerDay.maxBy(_._2)

    val minimum = gamesPerDay.minBy(_._2)

    println()
    println("TIME SERIES SUMMARY")
    println("===================")

    println(s"Total Games        : $totalGames")
    println(s"Total Days         : $totalDays")
    println(f"Average Games/Day  : $averageGames%.2f")

    println(
      s"Maximum Games/Day  : ${maximum._2} on ${maximum._1}"
    )

    println(
      s"Minimum Games/Day  : ${minimum._2} on ${minimum._1}"
    )

    val firstDate = gamesPerDay.head
    val lastDate = gamesPerDay.last

    println()
    println("TIME PERIOD")
    println("===========")

    println(s"First Date : ${firstDate._1}")
    println(s"Last Date  : ${lastDate._1}")

    println()
    println("TREND ANALYSIS")
    println("==============")

    if (lastDate._2 > firstDate._2) {
      println("Games per day increased between the first and last recorded day.")
    }
    else if (lastDate._2 < firstDate._2) {
      println("Games per day decreased between the first and last recorded day.")
    }
    else {
      println("Games per day remained the same between the first and last recorded day.")
    }
  }
}
