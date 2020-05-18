#spark-dstream-kafka-es
In this template I have demonstrated how to use Spark-DStream to connect Kafka to ES.

To run this, follow these steps:
 - Your Zookeper and Kafka should be up.
 - Create a topic named "inputTopic".
 - Start your elaticsearch.
 - Clone the repostory :
      ``git clone https://github.com/knoldus/spark-dstream-kafka-es.git``
      
 - Use command ``sbt run`` on terminal after going to the main directory of cloned repository.
 - Create a producer for inputTopic and produce data like : {"id":"10101","name":"Muskan","city":"Delhi"}