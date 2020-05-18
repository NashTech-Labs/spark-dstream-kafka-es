package com.knoldus

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark.streaming._
import play.api.libs.json.Json

object DataFromKafka extends App {

  val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark-Demo")
  val inputTopic = "inputTopic"
  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(sc, Seconds(1))
  val checkpointPath = "/home/knoldus/Downloads/hadoopFile"
  val intervalSec = 15
  ssc.checkpoint(checkpointPath)
  val kafkaParams: Map[String, Object] = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "spark-demo",
    "kafka.consumer.id" -> "kafka-consumer-01"
  )


  val inputStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(ssc,
    PreferConsistent, Subscribe[String, String](Array(inputTopic), kafkaParams))

  val processedStream = inputStream.map(record => Json.parse(record.value()).as[Person]).checkpoint(Seconds(intervalSec.toLong))
  processedStream.saveToEs("demo-index")

  ssc.start()
  ssc.awaitTermination()

}
