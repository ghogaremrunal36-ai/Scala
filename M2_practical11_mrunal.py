from pyspark.sql import SparkSession
from pyspark.sql.functions import avg

spark = SparkSession.builder \
    .appName("GroupByAverage") \
    .master("local[*]") \
    .getOrCreate()


df = spark.read.csv(
    "products.csv",
    header=True,
    inferSchema=True
)

print("Original Data:")
df.show()


result = df.groupBy("CategoryID") \
    .agg(avg("Price").alias("Average_Price"))

print("Average Price for Each Category:")
result.show()

spark.stop()