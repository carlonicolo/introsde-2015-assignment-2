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
* **client-server-*.log**: four files that contain the result of the execution performed over my sever and the partner server, using both format JSON and XML;

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
* **client-server-json.log**;
* **client-server-xml.log**;

that contain the output requests/responses information as requsted for the assignment.

Is also possible to execute the client over my own server in this way:
```
ant execute.client.myserver
```
and create in this way the corresponding log files:
* **client-server-json-myserver.log**;
* **client-server-xml-myserver.log**;

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

![StandaloneServerExecution](http://www.carlonicolo.com/IntroSDE/Assignment2/standaloneExecution.png)



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

### Real execution flow to explain the API 
As mentioned before the command performed to execute the client on my server is
```
ant execute.client.myserver
```
Well then we are ready to start explaining in pratice how the request/response work:

myserver address: **https://enigmatic-sierra-2066.herokuapp.com/sdelab**
### **Step 3.1.**
**Send R#1 (GET BASE_URL/person)**. Calculate how many people are in the response. If more than 2, result is OK, else is ERROR (less than 3 persons). Save into a variable id of the first person (first_person_id) and of the last person (last_person_id)

**R#1** GET /person list all the people in the database


**JSON**
```json
      Request #1: GET person/ Accept: application/json Content-Type: application/json
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      {"people": [
          {
              "firstname": "Joe",
              "idPerson": 1,
              "birthdate": "1977-08-31",
              "lastname": "Brigante",
              "measureType": [
                  {
                      "measure": "height",
                      "value": "1.83"
                  },
                  {
                      "measure": "weight",
                      "value": "72"
                  }
              ]
          },
          {
              "firstname": "Andrew",
              "idPerson": 2,
              "birthdate": "1998-08-31",
              "lastname": "Law",
              "measureType": [
                  {
                      "measure": "height",
                      "value": "1.87"
                  },
                  {
                      "measure": "weight",
                      "value": "89"
                  }
              ]
          },
          {
              "firstname": "Bob",
              "idPerson": 3,
              "birthdate": "1968-08-31",
              "lastname": "Marley",
              "measureType": [
                  {
                      "measure": "height",
                      "value": "1.70"
                  },
                  {
                      "measure": "weight",
                      "value": "68"
                  }
              ]
          },
          {
              "firstname": "Joe",
              "idPerson": 51,
              "birthdate": null,
              "lastname": null,
              "measureType": []
          },
          {
              "firstname": "Joe",
              "idPerson": 53,
              "birthdate": null,
              "lastname": null,
              "measureType": []
          }
      ]}
      First person id json: 1
      Last person id json: 53

```
**XML**
```
Request #1: GET person/ Accept: application/xml Content-Type: application/xml
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      <people>
        <person>
          <idPerson>1</idPerson>
          <firstname>Joe</firstname>
          <lastname>Brigante</lastname>
          <birthdate>1977-08-31T09:00:00Z</birthdate>
          <healthprofile>
            <measureType>
              <measure>height</measure>
              <value>1.83</value>
            </measureType>
            <measureType>
              <measure>weight</measure>
              <value>72</value>
            </measureType>
          </healthprofile>
        </person>
        <person>
          <idPerson>2</idPerson>
          <firstname>Andrew</firstname>
          <lastname>Law</lastname>
          <birthdate>1998-08-31T08:00:00Z</birthdate>
          <healthprofile>
            <measureType>
              <measure>height</measure>
              <value>1.87</value>
            </measureType>
            <measureType>
              <measure>weight</measure>
              <value>89</value>
            </measureType>
          </healthprofile>
        </person>
        <person>
          <idPerson>3</idPerson>
          <firstname>Bob</firstname>
          <lastname>Marley</lastname>
          <birthdate>1968-08-31T10:00:00Z</birthdate>
          <healthprofile>
            <measureType>
              <measure>height</measure>
              <value>1.70</value>
            </measureType>
            <measureType>
              <measure>weight</measure>
              <value>68</value>
            </measureType>
          </healthprofile>
        </person>
      </people>
```
As we expected all worded fine because there are more than two people and the GET request have been served and executed.

---

### **Step 3.2.** 
Send **R#2** for first_person_id. If the responses for this is 200 or 202, the result is OK.

**R#2**  GET /person/{id} should give all the personal information plus current measures of person identified by {id} (e.g., current measures means current health profile)

**JSON**

```json
Request #2: GET person/1 Accept: application/json Content-Type: application/json
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      {
          "firstname": "Joe",
          "idPerson": 1,
          "birthdate": "1977-08-31",
          "lastname": "Brigante",
          "measureType": [
              {
                  "measure": "height",
                  "value": "1.83"
              },
              {
                  "measure": "weight",
                  "value": "72"
              }
          ]
      }

```

**XML**

```xml
Request #2: GET person/1 Accept: application/xml Content-Type: application/xml
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      <person>
        <idPerson>1</idPerson>
        <firstname>Joe</firstname>
        <lastname>Brigante</lastname>
        <birthdate>1977-08-31T09:00:00Z</birthdate>
        <healthprofile>
          <measureType>
            <measure>height</measure>
            <value>1.83</value>
          </measureType>
          <measureType>
            <measure>weight</measure>
            <value>72</value>
          </measureType>
        </healthprofile>
      </person>
```
Also in this case all is as expected the person with the first printed as requested.

---

### **Step 3.3.** 
Send **R#3** for first_person_id changing the firstname. If the responses has the name changed, the result is OK.

**R#3** PUT /person/{id} should update the personal information of the person identified by {id} (e.g., only the person's information, not the measures of the health profile)

**JSON**

```json
Request #3: PUT person/1 Accept: application/json Content-Type: application/json
      ==> Result: [OK]
      ==> HTTP Status: [201]
      [BODY]
      
      {
          "firstname": "Joe",
          "idPerson": 1,
          "birthdate": "1977-08-31",
          "lastname": "Brigante",
          "measureType": [
              {
                  "measure": "height",
                  "value": "1.83"
              },
              {
                  "measure": "weight",
                  "value": "110"
              }
          ]
      }
```
**XML**

```xml
Request #3: PUT person/1 Accept: application/xml Content-Type: application/xml
      ==> Result: [ERROR]
      ==> HTTP Status: [201]
      [BODY]
      
      <person>
        <idPerson>1</idPerson>
        <firstname>Smith</firstname>
        <lastname>Brigante</lastname>
        <birthdate>1977-08-31T09:00:00Z</birthdate>
        <healthprofile>
          <measureType>
            <measure>height</measure>
            <value>1.83</value>
          </measureType>
          <measureType>
            <measure>weight</measure>
            <value>72</value>
          </measureType>
        </healthprofile>
      </person>
```
As expected in the xml output we have the result [ERROR] because json is executed before than xml than the name have been changed and when is executed the xml media.type we change the firstname with the same name then as result is not changed. 


---

### **Step 3.4.**
Send **R#4** to create the following person. Store the id of the new person. If the answer is 201 (200 or 202 are also applicable) with a person in the body who has an ID, the result is OK.

```json
{
          "firstname"     : "Chuck",
          "lastname"      : "Norris",
          "birthdate"     : "1945-01-01"
          "healthProfile" : {
                    "weight"  : 78.9,
                    "height"  : 172
          }
    }
```

**R#4** POST /person should create a new person and return the newly created person with its assigned id (if a health profile is included, create also those measurements for the new person).

**JSON**
```json
Request #4: POST person/ Accept: application/json Content-Type: application/json
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      {
          "firstname": "Chuck",
          "idPerson": 52,
          "birthdate": "1945-01-01",
          "lastname": "Norris",
          "measureType": [
              {
                  "measure": "weight",
                  "value": "78.9"
              },
              {
                  "measure": "height",
                  "value": "172"
              }
          ]
      }
```


**XML**
```xml
Request #4: POST person/ Accept: application/xml Content-Type: application/xml
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      <person>
        <idPerson>54</idPerson>
        <firstname>Chuck</firstname>
        <lastname>Norris</lastname>
        <birthdate>1945-01-01T00:00:00Z</birthdate>
        <healthprofile>
          <measureType>
            <measure>weight</measure>
            <value>78.9</value>
          </measureType>
          <measureType>
            <measure>height</measure>
            <value>172</value>
          </measureType>
        </healthprofile>
      </person>
```
We can see that the new person have been created in the right way.

---

### **Step 3.5. 
Send **R#5** for the person you have just created. Then send R#1 with the id of that person. If the answer is 404, your result must be OK.

**R#5** DELETE /person/{id} should delete the person identified by {id} from the system

**JSON**

```json
Request #5: DELETE person/52 Accept: application/json Content-Type: application/json
      ==> Result: [OK]
      ==> HTTP Status: [404]
      [BODY]
```

**XML**

```xml
Request #5: DELETE person/54 Accept: application/xml Content-Type: application/xml
      ==> Result: [OK]
      ==> HTTP Status: [404]
      [BODY]
```

---

### **Step 3.6. 
Follow now with the **R#9 (GET BASE_URL/measureTypes)**. 
If response contains more than 2 measureTypes - result is OK, else is ERROR (less than 3 measureTypes). Save all measureTypes into array (measure_types)

**R#9** GET /measureTypes should return the list of measures your model supports in the following formats:
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

```xml
<measureTypes>
    <measureType>weight</measureType>
    <measureType>height</measureType>
    <measureType>steps</measureType>
    <measureType>bloodpressure</measureType>
</measureTypes>
```

**output**

**JSON**

```json
Request #6: GET /measureTypes Accept: application/json Content-Type: application/json
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      {"measureTypes": [
          "weight",
          "height",
          "steps",
          "blood pressure",
          "heart rate",
          "bmi"
      ]}
```

**XML**

```xml
Request #6: GET /measureTypes Accept: application/xml Content-Type: application/xml
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      <measureTypes>
        <measureType>weight</measureType>
        <measureType>height</measureType>
        <measureType>steps</measureType>
        <measureType>blood pressure</measureType>
        <measureType>heart rate</measureType>
        <measureType>bmi</measureType>
      </measureTypes>
```

---

### **Step 3.7. 
Send **R#6 (GET BASE_URL/person/{id}/{measureType})** 
for the first person you obtained at the beginning and the last person, and for each measure types from measure_types. If no response has at least one measure - result is ERROR (no data at all) else result is OK. Store one measure_id and one measureType.

**R#6**  GET /person/{id}/{measureType} should return the list of values (the history) of {measureType} (e.g. weight) for person identified by {id}

**JSON**

```json
Request #7: GET /measureTypes/1/weight Accept: application/json Content-Type: application/json
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      {
          "created": "2015-11-18",
          "mid": 2,
          "value": "110"
      }
```

**XML**

```xml
Request #7: GET /measureTypes/1/weight Accept: application/xml Content-Type: application/xml
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      <measureHistory>
        <mesure>
          <mid>2</mid>
          <value>110</value>
          <created>2015-11-18T09:00:00Z</created>
        </mesure>
        <mesure>
          <mid>53</mid>
          <value>90</value>
          <created>2011-12-09T00:00:00Z</created>
        </mesure>
      </measureHistory>
```


---

### **Step 3.8. 
Send **R#7 (GET BASE_URL/person/{id}/{measureType}/{mid})** for the stored measure_id and measureType. If the response is 200, result is OK, else is ERROR.

**R#7** GET /person/{id}/{measureType}/{mid} should return the value of {measureType} (e.g. weight) identified by {mid} for person identified by {id}

**JSON**

```json
Request #8: GET person/1/weight/2 Accept: application/json Content-Type: application/json
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      Mid value: 110
```

**XML**

```xml
Request #8: GET person/1/weight/2 Accept: application/xml Content-Type: application/xml
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      Mid value: 110
```

Is easy to see that the mid value is taken in the correct way in effect in the step3.7 we saw this value associated to the mid 2


---

### **Step 3.9.** 
Choose a measureType from measure_types and send the request R#6 (GET BASE_URL/person/{first_person_id}/{measureType}) and save count value (e.g. 5 measurements). Then send **R#8 (POST BASE_URL/person/{first_person_id}/{measureTypes})** with the measurement specified below. Follow up with another R#6 as the first to check the new count value. If it is 1 measure more - print OK, else print ERROR. Remember, first with JSON and then with XML as content-types
```xml
 <measure>
    <value>72</value>
    <created>2011-12-09</created>
</measure>
```


**R#8** POST /person/{id}/{measureType} should save a new value for the {measureType} (e.g. weight) of person identified by {id} and archive the old value in the history


**JSON**

```json
Request #9: POST person/1/weight Accept: application/json Content-Type: application/json
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      {
          "measure": "weight",
          "value": "72"
      }
```

**XML**

```xml
Request #9: POST person/1/weight Accept: application/xml Content-Type: application/xml
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      <lifestatus>
        <measure>weight</measure>
        <value>72</value>
      </lifestatus>
```

As we can see the value is saved in the right way.


---

### **Step 3.10.** 
Send **R#10 using the {mid}** or the measure created in the previous step and updating the value at will. Follow up with at R#6 to check that the value was updated. If it was, result is OK, else is ERROR.

```xml
<measure>
    <value>90</value>
    <created>2011-12-09</created>
</measure>
```

**R#10** PUT /person/{id}/{measureType}/{mid} should update the value for the {measureType} (e.g., weight) identified by {mid}, related to the person identified by {id}

**JSON**

```json
Request #10: PUT person/1/weight/53 Accept: application/json Content-Type: application/json
      ==> Result: [OK]
      ==> HTTP Status: [201]
      [BODY]
      
      {
          "created": "2011-12-09",
          "mid": 53,
          "value": "90"
      }
```

**XML**

```xml
Request #10: PUT person/1/weight/56 Accept: application/xml Content-Type: application/xml
      ==> Result: [OK]
      ==> HTTP Status: [201]
      [BODY]
      
      <measureHistory>
        <measure>
          <mid>2</mid>
          <value>110</value>
          <created>2015-11-18T09:00:00Z</created>
        </measure>
        <measure>
          <mid>53</mid>
          <value>90</value>
          <created>2011-12-09T00:00:00Z</created>
        </measure>
        <measure>
          <mid>56</mid>
          <value>90</value>
          <created>2011-12-09T00:00:00Z</created>
        </measure>
      </measureHistory>
```

Because in the assignment is not specified if show only the updated measure or all measureHistory i decided to show only the updated value in JSON and all the measureHistory in XML. As we can see the value has been updated


---

### **Step 3.11.** 
Send **R#11** for a measureType, before and after dates given by your fellow student (who implemnted the server). If status is 200 and there is at least one measure in the body, result is OK, else is ERROR

**R#11** GET /person/{id}/{measureType}?before={beforeDate}&after={afterDate} should return the history of {measureType} (e.g., weight) for person {id} in the specified range of date


**JSON**

```json
Request #11: GET person/1/weight?before=2015-12-09&after=2010-07-10 Accept: application/json Content-Type: application/json
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      [
          {
              "created": "2015-11-18",
              "mid": 2,
              "value": "110"
          },
          {
              "created": "2011-12-09",
              "mid": 53,
              "value": "90"
          }
      ]
```

**XML**

```xml
Request #11: GET person/1/weight?before=2015-12-09&after=2010-07-10 Accept: application/xml Content-Type: application/xml
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      <measureHistory>
        <measure>
          <mid>2</mid>
          <value>110</value>
          <created>2015-11-18T09:00:00Z</created>
        </measure>
        <measure>
          <mid>53</mid>
          <value>90</value>
          <created>2011-12-09T00:00:00Z</created>
        </measure>
        <measure>
          <mid>56</mid>
          <value>90</value>
          <created>2011-12-09T00:00:00Z</created>
        </measure>
      </measureHistory>
```

---

### **Step 3.12.** 
Send **R#12** using the same parameters as the preivious steps. If status is 200 and there is at least one person in the body, result is OK, else is ERROR

**R#12** GET /person?measureType={measureType}&max={max}&min={min} retrieves people whose {measureType} (e.g., weight) value is in the [{min},{max}] range (if only one for the query params is provided, use only that)

**JSON**

```json
Request #12: GET person?measureType=weight&max=100&min=20 Accept: application/json Content-Type: application/json
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      {"people": [
          {
              "firstname": "Andrew",
              "idPerson": 2,
              "birthdate": "1998-08-31",
              "lastname": "Law",
              "measureType": [
                  {
                      "measure": "height",
                      "value": "1.87"
                  },
                  {
                      "measure": "weight",
                      "value": "89"
                  }
              ]
          },
          {
              "firstname": "Bob",
              "idPerson": 3,
              "birthdate": "1968-08-31",
              "lastname": "Marley",
              "measureType": [
                  {
                      "measure": "height",
                      "value": "1.70"
                  },
                  {
                      "measure": "weight",
                      "value": "68"
                  }
              ]
          },
          {
              "firstname": "Joe",
              "idPerson": 1,
              "birthdate": "1977-08-31",
              "lastname": "Brigante",
              "measureType": [
                  {
                      "measure": "height",
                      "value": "1.83"
                  },
                  {
                      "measure": "weight",
                      "value": "72"
                  }
              ]
          }
      ]}
```

**XML**

```xml
Request #12: GET person/1?weight?max=100&min=20 Accept: application/xml Content-Type: application/xml
      ==> Result: [OK]
      ==> HTTP Status: [200]
      [BODY]
      
      <people>
        <person>
          <idPerson>2</idPerson>
          <firstname>Andrew</firstname>
          <lastname>Law</lastname>
          <birthdate>1998-08-31T08:00:00Z</birthdate>
          <healthprofile>
            <measureType>
              <measure>height</measure>
              <value>1.87</value>
            </measureType>
            <measureType>
              <measure>weight</measure>
              <value>89</value>
            </measureType>
          </healthprofile>
        </person>
        <person>
          <idPerson>3</idPerson>
          <firstname>Bob</firstname>
          <lastname>Marley</lastname>
          <birthdate>1968-08-31T10:00:00Z</birthdate>
          <healthprofile>
            <measureType>
              <measure>height</measure>
              <value>1.70</value>
            </measureType>
            <measureType>
              <measure>weight</measure>
              <value>68</value>
            </measureType>
          </healthprofile>
        </person>
        <person>
          <idPerson>1</idPerson>
          <firstname>Smith</firstname>
          <lastname>Brigante</lastname>
          <birthdate>1977-08-31T09:00:00Z</birthdate>
          <healthprofile>
            <measureType>
              <measure>height</measure>
              <value>1.83</value>
            </measureType>
            <measureType>
              <measure>weight</measure>
              <value>72</value>
            </measureType>
          </healthprofile>
        </person>
      </people>
```



## Appendix

**JSON**

```json

```

**XML**

```xml

```
