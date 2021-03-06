package ehealth.resources;

import ehealth.dao.LifeCoachDao;
import ehealth.model.*;
import ehealth.wrapper.MeasureDefinitionWrapper;
import ehealth.wrapper.MeasureHistoryWrapper;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

/**
 * This is class is represents the MeasureResource
 * and exposes a GET request method getMeasureType 
 * 
 * @author Carlo Nicolo'
 *
 */
@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/measureTypes")
public class MeasureResource {

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
    
    
    //Request #9
    // Return the list of measureTypes
    /**
     * This method is used to retrieve a list of MeasureDefinition
     * 
     * Assignment Request
     * Request #9: GET /measureTypes should return the list of measures your model supports
     * 
     * @return w contains a list of MeasureDefinition
     */
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public MeasureDefinitionWrapper getMeasureType() {
    	MeasureDefinitionWrapper w = new MeasureDefinitionWrapper();
    	w.setMeasureDefinition(MeasureDefinition.getAll());
        return w;
    }
    
}