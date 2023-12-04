# About

This project implements simple proxy for ElasticSearch which allows to:
* Create ElasticSearch indexes
* Create document in the created index (index provided as URL path variable)
* Get document from the created index (index provided as URL path variable)

The project is done as an interview task.
It is implemented in Java using Spring framework and spring-data-elasticsearch 
component features.

# Prerequisites

* JDK 21
* Maven 3.9.x
* Project Lombok support in your IDE
* ElasticSearch (see section [ElasticSearch installation](#ElasticSearch installation))

## Tests

The project contains Postman tests in /tests subfolder, in order to use them:
1. Import tests from file *esapi.postman_collection*
2. Import Environment from file *esapi.postman_environment*
3. Replace value of *serverUri* environment variable to the service API URL
4. Select the corresponding environment and tests collection
5. Run the tests in the order

# Configuration

All the configuration is done using standard Spring Boot mechanisms.
If you are using packaged JAR to run the application it would be convenient
to use application.properties file outside a packaged jar.
See: https://docs.spring.io/spring-boot/docs/3.2.0/reference/html/howto.html#howto.properties-and-configuration.external-properties-location

Update following application properties before executing the service 
(either as IDE project or as a packaged JAR):

# Build

To build the package use command
```
mvn clean package
```
In this case you can use externalized configuration to have the environmnet 

# ElasticSearch installation

To accomplish part 1 of the task I used VirtualBox virtual machine with Ubuntu 
Linux. The following software was installed on the virtual server:

  * **OS**: Ubuntu 22.04.3 LTS (Jammy Jellyfish), server version
      https://releases.ubuntu.com/jammy/ubuntu-22.04.3-live-server-amd64.iso
  * **Java**: OpenJDK JDK 21, OpenJDK JRE 21, from packages 
  * **ElasticSearch**: Elasticsearch 8.11.1, using Debian package

The server is configured from scratch using following steps:

1. **Update package info**
   ```
   sudo apt update && sudo apt upgrade
   ```
2. **Install OpenJDK (JRE included)**
    ```
    sudo apt-get install openjdk-21-jdk
    ```
3. **Install ElasticSearch from Debian package**
   ```  
   # The ElasticSearch is installed using Debian package, as described here:
   # https://www.elastic.co/guide/en/elasticsearch/reference/current/deb.html
   # Following procedure is a short version of the one described at 
   # ElasticSearch website:
   
   wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo gpg \
   --dearmor -o /usr/share/keyrings/elasticsearch-keyring.gpg
   sudo apt-get install apt-transport-https
   echo "deb [signed-by=/usr/share/keyrings/elasticsearch-keyring.gpg] \
   https://artifacts.elastic.co/packages/8.x/apt stable main" | sudo tee \
   /etc/apt/sources.list.d/elastic-8.x.list
   sudo apt-get update && sudo apt-get install elasticsearch
   sudo /bin/systemctl daemon-reload
   sudo /bin/systemctl enable elasticsearch.service
   
   # Copy and save the generated superuser password from the command output
   # to access the server later
   ```
4. **Run ElasticSearch**
   ```
   sudo systemctl start elasticsearch.service
   ```
5. **Check the ability to access the ElasticSearch**

    Try to access the service via browser using URL (use username "elastic" and 
    previously saved password): *https://{SERVER_IP}:9200/*

    The response should be similar to the following:
    ```
    {
      "name" : "ubuntu2204",
      "cluster_name" : "elasticsearch",
      "cluster_uuid" : "WeYtfXoiTIqAAP4WbHe-ZA",
      "version" : {
        "number" : "8.11.1",
        "build_flavor" : "default",
        "build_type" : "deb",
        "build_hash" : "6f9ff581fbcde658e6f69d6ce03050f060d1fd0c",
        "build_date" : "2023-11-11T10:05:59.421038163Z",
        "build_snapshot" : false,
        "lucene_version" : "9.8.0",
        "minimum_wire_compatibility_version" : "7.17.0",
        "minimum_index_compatibility_version" : "7.0.0"
      },
      "tagline" : "You Know, for Search"
    }
    ```  
    Ignore the message about the certificate check in your browser and proceed, 
    it appears since the given certificate is self signed. In case if you're 
    installing ElasticSearch on the server, which has dedicated domain name 
    (DNS record) pointing to the server you can generate the free certificate 
    using "Let's Encrypt" for the given domain name and install it for 
    ElasticSearch.
  
    If you cannot access the ElasticSearch by IP - please check your 
    Network settings, VM Network Settings, and Firewall configuration on your PC 
    and on the server.
    To open the port on the server add following rule to the firewall:
    ```  
    sudo iptables -I INPUT -p tcp --dport 9200 -j ACCEPT
    ```
6. **Switch ElasticSearch from the bundled OpenJDK to the one installed at step 2
   (optional)**
   
    It is not fully clear why the item 3 of the task description mentions the 
    requirement to check ElasticSearch for compatibility with JVM, since 
    ElasticSearch is distributed with bundled OpenJDK (at least for Linux 
    distributions).
    I guess it might be because author assumes that ElasticSearch should use the 
    installed JDK version.
    In this case we can switch ElasticSearch to use the installed JDK by 
    setting corresponding environment variable (in dedicated file since we're 
    running ElasticSearch as a service) and reloading the service:

    ```
    echo ES_JAVA_HOME="/usr/lib/jvm/java-21-openjdk-amd64" | sudo tee -a \
    /etc/default/elasticsearch
    sudo systemctl restart elasticsearch.service
    ```

7. **Fix for self-signed certificate (optional)**
    If you are using self-signed certificate for your Elasticsearch instance, you 
    need have to import it into your Java trust store, which is located at 
    *{JAVA_HOME}/lib/security/cacerts*
    Following actions are needed: 
    - Export the certificate using browser to crt file by opening the ElasticSearch 
      API URL in your browser (e.g. Chrome) and clicking "Not secure" in address bar,
      then "Certificate is not valid" &rarr; "Details" &rarr; "Export..."
    - Import the certificate to the Java trust store using 