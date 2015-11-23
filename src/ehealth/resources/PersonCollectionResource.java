package ehealth.resources;
import ehealth.model.*;
import ehealth.wrapper.PeopleWrapper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

/**
 * This class is a collection Resource for the Person
 * and provides some methods to perform GET and POST requests.
 * 
 * GET
 * getCount()
 * getPersonsBrowser(@QueryParam("measureType") String measureName,@QueryParam("min") Double min,@QueryParam("max") Double max)
 * 
 * POST
 * newPerson(Person person)
 * 
 * 
 * 
 * 
 * @author Carlo Nicolo'
 *
 */
@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/person")
public class PersonCollectionResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    // will work only inside a Java EE application
    @PersistenceUnit(unitName="introsde-jpa")
    EntityManager entityManager;

    // will work only inside a Java EE application
    @PersistenceContext(unitName = "introsde-jpa",type=PersistenceContextType.TRANSACTION)
    private EntityManagerFactory entityManagerFactory;
    
    

    /**
     * This method is used to know the number of people
     * 
     * @return String.valueOf(count) that is a String representing the number of the people
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        System.out.println("Getting count...");
        List<Person> people = Person.getAll();
        int count = people.size();
        return String.valueOf(count);
    }

    /**
     * This method is used to create a new Person.
     * First checks if the person has an healthProfile then create a person with
     * a healthProfile
     * 
     * Assignment request #4: POST /person should create a new person and return the newly created person with its 
     * assigned id (if a health profile is included, create also those measurements for the new person).
     * 
     * @param person
     * @return person the person created and saved in the database
     * @throws IOException
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
    public Person newPerson(Person person) throws IOException {
        System.out.println("Creating new person...");
        // checks if person includes life statuses, in other words a 'healthprofile'
        if(person.getLifeStatus() == null){
            return Person.savePerson(person);
        }else{
            //removes the life statuses in the persons and puts them in another variable
            ArrayList<LifeStatus> list_lifeStatus = new ArrayList<>();
            list_lifeStatus.addAll(person.getLifeStatus());
            person.setLifeStatus(null);
            
            //saves the person in the database and retrieve the id
            Person p = Person.savePerson(person);
            int id_person = p.getIdPerson();
            
            //creates the today date
            Calendar today = Calendar.getInstance();
            
            //the use of this list avoid the insertion of the same measure multiple time
            //the list stores progressively the measure already inserted 
            ArrayList<Integer> control = new ArrayList<>();
            
            //iterates on all 'lifestatus' the client wants to insert
            for(int i=0; i<list_lifeStatus.size(); i++){
                //associates the 'lifestatus' with the person
                list_lifeStatus.get(i).setPerson(p);
                HealthMeasureHistory history_element = new HealthMeasureHistory();
                
                //retrieves the name of the measures inserted by the client (e.g. weight)
                String measureName = list_lifeStatus.get(i).getMeasureDefinition().getMeasureName();
                
                //searches the measure definition associated with the name of the measure
                MeasureDefinition temp = new MeasureDefinition();
                temp = MeasureDefinition.getMeasureDefinitionByName(measureName);
                
                System.out.println("Before if " + temp.getMeasureName());
                
                if (temp != null && !control.contains(temp.getIdMeasureDef())){
                    control.add(temp.getIdMeasureDef());
                    //associates the lifestatus with the corresponding measureDefinition
                    list_lifeStatus.get(i).setMeasureDefinition(temp); 
                    history_element.setMeasureDefinition(temp);
                    history_element.setPerson(p);
                    history_element.setValue(list_lifeStatus.get(i).getValue());
                    history_element.setTimestamp(today.getTime());
                    LifeStatus.saveLifeStatus(list_lifeStatus.get(i));  //saves lifestatus in the db
                    HealthMeasureHistory.saveHealthMeasureHistory(history_element);
                }
            }
            return Person.getPersonById(id_person);
        }
    }   
    
    
    
    
    
    /**
     * Defines that the next path parameter after the base url is
     * treated as a parameter and passed to the PersonResources
     * Allows to type http://localhost:599/base_url/1
     * 1 will be treaded as parameter todo and passed to PersonResource
     * 
     * @param id 
     * @return PersonResouce
     */
    @Path("{personId}")
    public PersonResource getPerson(@PathParam("personId") int id) {
        return new PersonResource(uriInfo, request, id);
    }
    
   
    
    
    /**
     * This method is used a GET request and perform the request #1 and request #12
     * In this case to deal with the ambiguity due to the fact that that both requests have
     * the same path in the first line of this method is checked the value of min and max double.
     * If both min and max are null then is executed the request 1# otherwise the request #12 is performed
     * 
     * Assignment request
     * Request #1: GET /person should list all the people (see above Person model to know what data to return here) 
     * in your database (wrapped under the root element "people")
     * 
     * Request #12: GET /person?measureType={measureType}&max={max}&min={min} retrieves people whose {measureType} 
     * (e.g., weight) value is in the [{min},{max}] range (if only one for the query params is provided, use only that)
     * 
     * @param measureName
     * @param min is the lower bound value
     * @param max is the upper bound value
     * @return list of people
     */
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public PeopleWrapper getPersonsBrowser(@QueryParam("measureType") String measureName, @QueryParam("min") Double min,
    		@QueryParam("max") Double max) {
    	
    	List<Person> people = new ArrayList<Person>();
    	
    	
    	if( (min == null) && (max == null) ){
    		System.out.println("Getting list of people...");
            people = Person.getAll();
            PeopleWrapper w = new PeopleWrapper();
            w.setPeople(people);
            return w;
    	}
    	//There is only the value of min
    	else if(max == null)
    	{
    		MeasureDefinition md = new MeasureDefinition();
        	md = MeasureDefinition.getMeasureDefinitionByName(measureName);
        	
    		max = 300.0;
    		System.out.println("Sono in max null");
    		people = Person.getPersonMeasureRange(md, min, max);
    		//return people;
    		PeopleWrapper z = new PeopleWrapper();
            z.setPeople(people);
            return z;
    	}
    	
    	//There is only the value of max
    	else if(min == null){
    		MeasureDefinition md = new MeasureDefinition();
        	md = MeasureDefinition.getMeasureDefinitionByName(measureName);
    		
    		min = 0.0;
    		System.out.println("Sono in min null");
    		people = Person.getPersonMeasureRange(md, min, max);
    		//return people;
    		PeopleWrapper y = new PeopleWrapper();
            y.setPeople(people);
            return y;
    		
    	}
    	
    	//Are present both values min and max
    	else
    	{
    		MeasureDefinition md = new MeasureDefinition();
        	md = MeasureDefinition.getMeasureDefinitionByName(measureName);
    		
    		System.out.println("I due valori esistono !");
    		people = Person.getPersonMeasureRange(md, min, max);
    		PeopleWrapper f = new PeopleWrapper();
            f.setPeople(people);
            return f;
    	}
    	
        
    }
       
}