object MeanMedianMode {
  def main(args: Array[String]): Unit ={
    val numbers = List(10, 20, 30, 40, 20, 50, 20)
    val mean = numbers.sum.toDouble / numbers.length
    val sorted = numbers.sorted
    val median =
      if (sorted.length % 2 == 0)
        (sorted(sorted.length / 2 - 1) + sorted(sorted.length / 2)).toDouble / 2
      else
        sorted(sorted.length / 2)
    val mode = numbers.groupBy(identity).maxBy(_._2.size)._1
    println("Numbers: " + numbers)
    println("Mean = " + mean)
    println("Median = " + median)
    println("Mode = " + mode)
  }
}