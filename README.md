# Introsde 2015 Assignment 2
This is the second assignment for the "Introduction to Service Design and Engineering" course.
In this assignment is requested to implement a server and a client calling this server. </br>
The server should be deployed on Heroku.</br>
This assignment covers:
* [LAB05](https://github.com/IntroSDE/lab05): The REST architectural style & RESTful web services
* [LAB06](https://github.com/IntroSDE/lab06): CRUD RESTful Services
* [LAB07](https://github.com/IntroSDE/lab07): Reading and writing from Databases & JPA (Java Persistence API)


The name of the student with whom you worked in pair is: *Andrea Bontempelli*</br>
The URL of my server is: [https://enigmatic-sierra-2066.herokuapp.com/sdelab/](https://enigmatic-sierra-2066.herokuapp.com/sdelab/)</br>
The URL of server made by Andrea Bontempelli is: [https://peaceful-hamlet-5616.herokuapp.com/sdelab](https://peaceful-hamlet-5616.herokuapp.com/sdelab)</br>
The Github repository of Andrea Bontempelli is: [https://github.com/abonte/introsde-2015-assignment-2](https://github.com/abonte/introsde-2015-assignment-2) 

## The code
In the picture below is showed the source tree of the project

![Tree project](http://www.carlonicolo.com/IntroSDE/Assignment2/TreeProjectA2.png)

* **src/ehealth/model**: model classes for the project;
* **src/ehealth/resources**: resource classes;
* **src/ehealth/dao**: data access object;
* **src/ehealth/wrapper**: classes used to format the output of XML and JSON;
* **src/ehealth/client**: the client for connecting to my server or my partner server;
* **src/ehealth/App.java**: a stand-alone server used to test locally the server;
* **lifecoach.sqlite**: the database;
* ***.log**: four files that contain the result of the execution performed over my sever and the partner server, using both format JSON and XML;

Configuration files:
* **ivy.xml**: to deal with the dependencies;
* **Procfile**: necessary to deploy the server on heroku;
* **WebContent/META-INF/persistence.xml**: used to configure the EntityManager;
* **build.xml**: used to execute the project.

All classes are documented and commented and for generating the javadoc is possible to execute the command:
```
ant generate.doc
```

## How to run
In this project is used ant, then for execute the project and in this way connect to the partner server or my own server is necessary to perform this ant command:
```
ant execute.client
```
to execute the client on the server partner. As result will be created two log files:
* client-server-json.log;
* client-server-xml.log;
that contain the output requests/responses information as requsted for the assignment.

Is also possible to execute the client over my own server in this way:
```
ant execute.client.myserver
```
and create in this way the corresponding log files:
* client-server-json-myserver.log;
* client-server-xml-myserver.log;

This is the part of build.xml file that perform that commands:
```xml
<!-- Start client for the assignment calling the friend server -->
    	<target name="execute.client" depends="install">
    		
    		<record name="client-server-json.log" action="start"/>
    		<antcall target="execute.client.friend.json"></antcall>
    		<record name="client-server-json.log" action="stop"/>
    		
    		<record name="client-server-xml.log" action="start"/>
    		<antcall target="execute.client.friend.xml"></antcall>
    		<record name="client-server-xml.log" action="stop"/>
    		
    	</target>
<!-- end client for the assignment calling the friend server -->

```
Is possible also to execute this project using the stand-alone server, where the project have been copied.
To execute using the stand-alone server the command to execute is:
```
ant start
```
This is a screenshot that shows the execution of this command in the eclipse IDE

![Tree project](http://www.carlonicolo.com/IntroSDE/Assignment2/standaloneExecution.png)



## Exposed services through a RESTful API
For the [assignment](https://sites.google.com/a/unitn.it/introsde_2015-16/lab-sessions/assignments/assignment-2) is requested to implement CRUD operations for person, plus some services to access and update health profile measurements.

**Request #1**: GET /person 

**Request #2**: GET /person/{id} 

**Request #3**: PUT /person/{id}

**Request #4**: POST /person 

**Request #5**: DELETE /person/{id} 

**Request #6**: GET /person/{id}/{measureType} 

**Request #7**: GET /person/{id}/{measureType}/{mid} 

**Request #8**: POST /person/{id}/{measureType} 

**Request #9**: GET /measureTypes should return the list of measures your model supports in the following formats:
```xml
 <measureTypes>
    <measureType>weight</measureType>
    <measureType>height</measureType>
    <measureType>steps</measureType>
    <measureType>bloodpressure</measureType>
</measureTypes>
```
```json
{
   "measureType": [
        "weight",
        "height",
        "steps",
        "bloodpressure"
    ]
}
```
**Extra #2 (Request #10)**: PUT /person/{id}/{measureType}/{mid} 

**Extra #3 (Request #11)**: GET /person/{id}/{measureType}?before={beforeDate}&after={afterDate} 

**Extra #4 (Request #12)**: GET /person?measureType={measureType}&max={max}&min={min}

To better understand and see in pratice how the server works and how these services work i will execute the client on my server and explain all requests looking at the response. In this way not only will be explained the exposed service but also check the correct execution of the project.
I will use the content of **client-server-xml-myserver.log** and **client-server-json-myserver.log**, the real data stored and collected after the execution of:
```
ant execute.client.myserver
```





## Appendix
