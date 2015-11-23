package ehealth.resources;

//import ehealth.model.MeasureDefinition;
import ehealth.model.*;
import ehealth.wrapper.MeasureHistoryWrapper;
import ehealth.wrapper.PeopleWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;



/**
 * This class is a resource for the Person and provides
 * some methods for GET, PUT, POST,DELETE requests
 * 
 * GET:
 * getPerson()
 * getMeasureHistoryId(@PathParam("measureType") String measureName,@PathParam("mid") int mid)
 * getPersonHTML()
 * getPersonById(int personId)
 * getPersonHistory(@PathParam("measureType") String measureName, QueryParam("before") String before,QueryParam("after") String after)
 * 
 * PUT:
 * putPerson(Person person)
 * putHealthHistory(HealthMeasureHistory heathhostory,@PathParam("mid") int mid)
 * 
 * 
 * POST
 * newMeasureValue(HealthMeasureHistory hmh, @PathParam("measureType") String measureName)
 * 
 * DELETE:
 * deletePerson()
 * 
 * @author Carlo Nicolo'
 *
 */
@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class PersonResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public PersonResource(UriInfo uriInfo, Request request,int id, EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.entityManager = em;
    }

    public PersonResource(UriInfo uriInfo, Request request,int id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    
    /**
     * This method is used to get the all the personal information 
     * and produce a response for XML and JSON and
     * 
     * Assignment request
     * Request #2 of the assignment: GET /person/{id} should give all the 
     * personal information plus current measures of person identified by {id}
     * 
     * @return null if the person is not found
     * @return person is the person related to the id
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getPerson() {
        Person person = this.getPersonById(id);
        if (person == null)
           return Response.status(Response.Status.NOT_FOUND).entity("Get: Person with " + id + " not found").build();
        else
           return Response.ok(person).build();
    }
    
    
    /**
     * This method is used to get the value of {measureType} identified by the {mid} 
     * for the person identified by {id}.
     * This is done calling the method getHealthMeasureHistoryById(mid)
     * 
     * Assignment request
     * Request #7 : GET /person/{id}/{measureType}/{mid} should return the value of {measureType} 
     * (e.g. weight) identified by {mid} for person identified by {id}
     * 
     * @param measureName is the name of the measure
     * @param mid is an int value representing the mid
     * @return value of measureType
     */
    @GET
    @Path("{measureType}/{mid}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getMeasureHistoryId(@PathParam("measureType") String measureName,@PathParam("mid") int mid) {
    	return HealthMeasureHistory.getHealthMeasureHistoryById(mid).getValue();
    }
    
    
    
    // for the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public Person getPersonHTML() {
        Person person = this.getPersonById(id);
        if (person == null)
            throw new RuntimeException("Get: Person with " + id + " not found");
        System.out.println("Returning person... " + person.getIdPerson());
        return person;
    }
    
    
    /**
     * This method is used to update the personal information of a specified person using the method
     * updatePerson.
     * 
     * Assignment request
     * Request #3: PUT /person/{id} should update the personal information of the person identified by {id}
     * 
     * @param person the person that we want update
     * @return res the Response of this method
     */
    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response putPerson(Person person) {
        System.out.println("--> Updating Person... " +this.id);
        System.out.println("--> "+person.toString());
        Person.updatePerson(person);
        Response res;
        Person existing = getPersonById(this.id);

        if (existing == null) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
            person.setIdPerson(this.id);
            if (person.getName() == null){
            	person.setName(existing.getName());
            }
            if (person.getLastname() == null){
            	person.setLastname(existing.getLastname());
            }
            if (person.getBirthdate() == null){
            	person.setBirthdate(existing.getBirthdate());
            }
            person.setLifeStatus(existing.getLifeStatus());
            Person.updatePerson(person);
        }
        return res;
    } 
    
    /**
     * This method is used to delete a person identified by the id.
     * 
     * Assignment Request
     * Request #5: DELETE /person/{id} should delete the person identified by {id} from the system
     */
    @DELETE
    public void deletePerson() {
        Person c = getPersonById(id);
        if (c == null)
            throw new RuntimeException("Delete: Person with " + id
                    + " not found");
        Person.removePerson(c);
    }
    
    

    public Person getPersonById(int personId) {
        System.out.println("Reading person from DB with id: "+personId);

        // this will work within a Java EE container, where not DAO will be needed
        //Person person = entityManager.find(Person.class, personId); 

        Person person = Person.getPersonById(personId);
        //System.out.println("Person: "+person.toString());
        return person;
    }
    
    
    /**
     * This method is used to save a new value for the measureType
     * 
     * Assignment request
     * Request #8: POST /person/{id}/{measureType} should save a new value for the {measureType} (e.g. weight) 
     * of person identified by {id} and archive the old value in the history
     * 
     * @param hmh is the HealthMeasureHistory
     * @param measureName is the measure name
     * @return newlf that is the new lifestatus 
     */
    @POST
    @Path("{measureType}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public LifeStatus newMeasureValue(HealthMeasureHistory hmh, @PathParam("measureType") String measureName){
        Person person = this.getPersonById(id);
        
        //searches the measure definition associated with the name of the measure
        MeasureDefinition md = new MeasureDefinition();
        
        
        md = MeasureDefinition.getMeasureDefinitionByName(measureName);
        
        
        
        //remove actual 'lifestatus' for measureName
        LifeStatus lf = LifeStatus.getLifeStatusByMeasureDefPerson(md,person);
       
        if(lf != null){
        LifeStatus.removeLifeStatus(lf);
        }
        
        
        //save new 'lifestatus' for measureName
        LifeStatus newlf = new LifeStatus(person, md, hmh.getValue());
        newlf = LifeStatus.saveLifeStatus(newlf);
        
        //insert the new measure value in the history
        hmh.setPerson(person);
        hmh.setMeasureDefinition(md);
        HealthMeasureHistory.saveHealthMeasureHistory(hmh);
        
        return LifeStatus.getLifeStatusById(newlf.getIdMeasure());
    }
    
    
    
    /**
     * This method is used to update the value of measureType identified by {mid} and related to
     * the person 
     * 
     * Assignment request
     * Extra #2(Request #10): PUT /person/{id}/{measureType}/{mid} should update the value for the {measureType} 
     * (e.g., weight) identified by {mid}, related to the person identified by {id}
     * 
     * @param heathhostory is the healthHistory 
     * @param mid is the id of the HealthMeasureHistory that must be modified
     * @return res contains the object Response 
     */
    @PUT
    @Path("{measureType}/{mid}")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response putHealthHistory(HealthMeasureHistory heathhostory,@PathParam("mid") int mid){
    	Response res;
    	HealthMeasureHistory existing = HealthMeasureHistory.getHealthMeasureHistoryById(mid);

    	if (existing == null) {
    		res = Response.noContent().build();
    	} else {
    		res = Response.created(uriInfo.getAbsolutePath()).build();
    		existing.setValue(heathhostory.getValue());
    		HealthMeasureHistory.updateHealthMeasureHistory(existing);
    	}
    	return res;
    }
    
    
    /**
     * This method is used for two GET requests:
     * 
     * Assignment request
     * - Request #6: GET /person/{id}/{measureType} should return the list of values 
     * (the history) of {measureType} (e.g. weight) for person identified by {id}
     * 
     * - Request #11: GET /person/{id}/{measureType}?before={beforeDate}&after={afterDate} 
     * should return the history of {measureType} (e.g., weight) for person {id} in the specified range of date
     * 
     * To deal with the problem of the path, there is a condition that evaluates the params.
     * In this way if the params "before" and "after" are not passed means that is requested R#6
     * Otherwise if the params "before" and "after" are passed is executed R#11
     * 
     * 
     * @param measureName measure name
     * @param before the upper bound value
     * @param after the lower boud value
     * @return w contains the MeasureHistory for the request #6
     * @return y contains the MeasureHistory for the request #11
     * @throws ParseException
     */
    @GET
    @Path("{measureType}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MeasureHistoryWrapper getPersonHistory(@PathParam("measureType") String measureName, @QueryParam("before") String before,
    		@QueryParam("after") String after) throws ParseException {
    	
    	MeasureDefinition md = new MeasureDefinition();
    	md = MeasureDefinition.getMeasureDefinitionByName(measureName);
    	
    	Person person = this.getPersonById(id);
    	
    	List<HealthMeasureHistory> list_MH = new ArrayList<HealthMeasureHistory>();
    	
    	if( (before == null) || (after == null) ){
    	list_MH = HealthMeasureHistory.getByPersonMeasure(person, md);
    	//return list_MH;
    	MeasureHistoryWrapper w = new MeasureHistoryWrapper();
        w.setHealthMeasureHistory(list_MH);
        return w;
    	}
    	//altrimenti vogliamo eseguire la query ?before={beforeDate}&after={afterDate}
    	else{
    		Calendar before1 = Calendar.getInstance();
        	SimpleDateFormat sdf_before = new SimpleDateFormat("yyyy-MM-dd");
        	before1.setTime(sdf_before.parse(before));
        	
        	
        	Calendar after1 = Calendar.getInstance();
        	SimpleDateFormat sdf_after = new SimpleDateFormat("yyyy-MM-dd");
        	after1.setTime(sdf_after.parse(after));
    		
    		
    		
        	list_MH = HealthMeasureHistory.getByPersonMeasureRangeDate(person, md, before1, after1);
        	
        	System.out.println("First element: " + list_MH.get(0).getValue().toString());
        	MeasureHistoryWrapper y = new MeasureHistoryWrapper();
            y.setHealthMeasureHistory(list_MH);
            return y;
    	}
        
    }
    
    
    
    
}