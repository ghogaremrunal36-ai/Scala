import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

object M2_Mrunal_Practical13 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Shopping Classification")
      .master("local[*]")
      .getOrCreate()

    val df = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("E:\\mrunal\\Scala\\MKG\\src\\main\\scala\\shopping_behavior.csv")

    println("Original Dataset:")
    df.show(10, false)

    val data = df.select(
      "Age",
      "Review_Influence_Score",
      "Social_Ads_Influence_Score",
      "Price_Comparison_Frequency",
      "Discount_Importance_Score",
      "Shop_Most_Where"
    )

    val numericData = data
      .withColumn("Age", data("Age").cast("double"))
      .withColumn(
        "Review_Influence_Score",
        data("Review_Influence_Score").cast("double")
      )
      .withColumn(
        "Social_Ads_Influence_Score",
        data("Social_Ads_Influence_Score").cast("double")
      )
      .withColumn(
        "Price_Comparison_Frequency",
        data("Price_Comparison_Frequency").cast("double")
      )
      .withColumn(
        "Discount_Importance_Score",
        data("Discount_Importance_Score").cast("double")
      )

    val cleanData = numericData.na.drop()

    println("Clean Dataset:")
    cleanData.show(10, false)

    val labelIndexer = new StringIndexer()
      .setInputCol("Shop_Most_Where")
      .setOutputCol("label")

    val assembler = new VectorAssembler()
      .setInputCols(Array(
        "Age",
        "Review_Influence_Score",
        "Social_Ads_Influence_Score",
        "Price_Comparison_Frequency",
        "Discount_Importance_Score"
      ))
      .setOutputCol("features")


    val logisticRegression = new LogisticRegression()
      .setFeaturesCol("features")
      .setLabelCol("label")
      .setMaxIter(20)


    val pipeline = new Pipeline()
      .setStages(Array(
        labelIndexer,
        assembler,
        logisticRegression
      ))


    val Array(trainingData, testData) =
      cleanData.randomSplit(Array(0.8, 0.2), seed = 42)

    println("Training Data:")
    trainingData.show(5, false)

    println("Test Data:")
    testData.show(5, false)


    val model = pipeline.fit(trainingData)
    val predictions = model.transform(testData)

    println("Predictions:")
    predictions
      .select(
        "Shop_Most_Where",
        "label",
        "prediction",
        "probability"
      )
      .show(20, false)


    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("label")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")

    val accuracy = evaluator.evaluate(predictions)

    println("----------------------------------")
    println("Classification Accuracy = " + accuracy)
    println("----------------------------------")

    spark.stop()
  }
}