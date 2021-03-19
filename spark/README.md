

# Introduction

- With the python analytics project's success, London Gift Shop (LGS) wants our team to apply the same data analysis but using a large dataset. The python analytics works in one node and runs locally. To provide a distributed computing solution, our team decided to use Spark. 

  - Write the Zeppelin Notebook running on Hadoop

  - - Hadoop clusters are created in the Google Cloud Platform
    - Reimplement queries using PySpark

  - Use Azure Databrick

  - - create the Azure Databrick service
    - create an execution cluster in Databrick
    - Reimplement questions and queries using PySpark



# Databricks Implementation

- Developers create Notebook and Cluster in the azureDatabricks. When developers run the Notebook, they need to choose a specific Cluster which the Notebook runs with
- upload CSV file to Databrick file system
- The cluster manager in azureDatabricks monitors the resources in worker nodes and assign the tasks
- User access SparkDriver through the SparkSession, and SaprkDriver will get data from Azure storage

![spark](./spark.png)



![azure-databrick](/Users/haotianzhu/Desktop/jarvis_data_eng_HaotianZhu/spark/azure-databrick.png)



 [Retail Data Analytics with PySpark.ipynb](Retail Data Analytics with PySpark.ipynb) 

# Zeppelin Implementation

- Developer access Zeppelin server via web UI. 
  - Zeppelin server is located in the master node in GCP 
- The Hive Server uses Spark Session and YARN resource manager will assign the tasks to different worker nodes 
  - The Spark executor is where spark running the queries 

![hadoop](./hadoop.png)![wdi_data_analytics](/Users/haotianzhu/Desktop/jarvis_data_eng_HaotianZhu/spark/wdi_data_analytics.png)

[WDI Data Analytics.json](Spark Dataframe - WDI Data Analytics.json) 

