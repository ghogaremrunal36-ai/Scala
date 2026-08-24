from pyspark.sql import SparkSession

spark = SparkSession.builder \
    .appName("JoinCSV") \
    .master("local[*]") \
    .getOrCreate()

df1 = spark.read.csv("sales_target.csv", header=True, inferSchema=True)
df2 = spark.read.csv("category_details.csv", header=True, inferSchema=True)

joined_df = df1.join(df2, on="Category", how="inner")

joined_df.show()

joined_df.write.csv("joined_output", header=True, mode="overwrite")

spark.stop()