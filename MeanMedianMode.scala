object MeanMedianMode {
  def main(args: Array[String]): Unit = {
      val numbers = List(18, 67, 56, 22, 56, 80, 20)

      val mean = numbers.sum.toDouble / numbers.length

      val sorted = numbers.sorted
      val median =
        if (sorted.length % 2 == 0)
          (sorted(sorted.length / 2 - 1) + sorted(sorted.length / 2)).toDouble / 2
        else
          sorted(sorted.length / 2)

      val mode = numbers.groupBy(identity)
        .mapValues(_.size)
        .maxBy(_._2)
        ._1

      println("Mean = " + mean)
      println("Median = " + median)
      println("Mode = " + mode)
    }


}
