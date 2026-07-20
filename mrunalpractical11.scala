import scala.io.Source

object mrunalpractical11 {
  def main(args: Array[String]): Unit = {
    val lines = Source.fromFile("src/main/scala/mrunal.txt").getLines().toList

    val words = lines
      .flatMap(_.toLowerCase.split("\\W+"))
      .filter(_.nonEmpty)


    val wordCounts = words.groupBy(identity).view.mapValues(_.size).toMap


    println("Word Frequencies:")
    wordCounts.toSeq.sortBy(-_._2).foreach {
      case (word, count) =>
        println(f"$word%-15s -> $count")
    }
  }
}

