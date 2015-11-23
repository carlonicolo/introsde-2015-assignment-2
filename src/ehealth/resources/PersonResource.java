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

//    // Application integration
//    // Request #2
//    @GET
//    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//    public Person getPerson() {
//        Person person = this.getPersonById(id);
//        if (person == null)
//            throw new RuntimeException("Get: Person with " + id + " not found");
//        return person;
//    }
    
    
    // Application integration
    // Request #2
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getPerson() {
        Person person = this.getPersonById(id);
        if (person == null)
           return Response.status(Response.Status.NOT_FOUND).entity("Get: Person with " + id + " not found").build();
        else
           return Response.ok(person).build();
    }
    
    
   
    //Request #7:
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
    
    // Request #3
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
    
    //Request #6
//    @GET
//    @Path("{measureType}")
//    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//    public List<HealthMeasureHistory> getPersonHistory(@PathParam("measureType") String measureName) {
//    	System.out.println("///////////"+measureName);
//    	System.out.println("///////////"+id);
//    	List<MeasureDefinition> list_md = MeasureDefinition.getAll();
//    	MeasureDefinition md = new MeasureDefinition();
//    	for (MeasureDefinition temp : list_md) {
//    		if (temp.getMeasureName().equals(measureName)){
//    			md=temp;
//    		}
//    	}
//    	Person person = this.getPersonById(id);
//    	List<HealthMeasureHistory> list_MH = HealthMeasureHistory.getByPersonMeasure(person, md);
//    	//List<HealthMeasureHistory> list_MH = HealthMeasureHistory.getAll();
//        /*if (** == null)
//            throw new RuntimeException("Get: History for person " + id + " not found");*/
//        return list_MH;
//    }
    
    
    //#Request #8
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
     * Extra #2 (Request #10):
     * PUT /person/{id}/{measureType}/{mid} should update the value for the {measureType} (e.g., weight) 
     * identified by {mid}, related to the person identified by {id}
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
     * Request #6 and Extra #3
     * 
     * GET /person/{id}/{measureType}?before={beforeDate}&after={afterDate} should return the history of {measureType} 
     * (e.g., weight) for person {id} in the specified range of date
     * 
     * @param measureName
     * @return
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