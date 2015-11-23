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
* ** *.log**: four files that contain the result of the execution performed over my sever and the partner server, using both format JSON and XML;

Configuration files:
* **ivy.xml**: to deal with the dependencies;
* **Procfile**: necessary to deploy the server on heroku;
* **WebContent/META-INF/persistence.xml**: used to configure the EntityManager;
* **build.xml**: used to execute the project.

All classes are documented and commented and for generating the javadoc is possible to execute the command:
```
generate.doc
```

## Task of code



## How to run




## Appendix
