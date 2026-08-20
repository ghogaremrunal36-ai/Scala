from pyspark.sql import SparkSession

spark = SparkSession.builder \
    .appName("FilterRows") \
    .master("local[*]") \
    .getOrCreate()

df = spark.read.csv(
    "Data Numerical.csv",
    header=True,
    inferSchema=True,
    sep=","
)
print("Original Data:")
df.show()

threshold = 0.75

filtered_df = df.filter(df["IPK"] > threshold)
print("Students with IPK greater than", threshold)
filtered_df.show()

spark.stop()