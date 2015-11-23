package ehealth.model;

import ehealth.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
//import javax.xml.bind.annotation.XmlAccessType;
//import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The persistent class for the "Person" database table.
 * 
 */
@Entity  // indicates that this class is an entity to persist in DB
@Table(name="Person") // to whole table must be persisted 


/**
 * Queries that will be used:
 * to find all person in the table Person
 * to find all person that have a measure definition between 30 and 100 where 30 is the min and 100 the max  (e.i: weight,30,100) 
 */
@NamedQueries({
    @NamedQuery(name="Person.findAll", query="SELECT p FROM Person p"),
    @NamedQuery(name="Person.findByMeasureDefRange", query="SELECT p FROM Person p INNER JOIN p.lifeStatus l WHERE l.measureDefinition = ?1 AND " + "CAST(l.value NUMERIC(10,2)) BETWEEN ?2 AND ?3")
})

@XmlRootElement
@XmlType(propOrder={"idPerson", "name", "lastname" , "birthdate", "lifeStatus"})
@JsonPropertyOrder({ "idPerson", "firstname", "lastname" , "birthdate", "lifeStatus"})



/**
 * This class is represents the model for a Person
 * Here we have the queries and all methods needed 
 * to retrive information on Person.
 * In particular the two queries Person.findAll and Person.findByMeasureDefRange
 * used in the methods getAll() and getPersonMeasureRange(MeasureDefinition md, double min, double max)
 * 
 * @author Carlo Nicolo'
 *
 */


public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_person")
    @TableGenerator(name="sqlite_person", table="sqlite_sequence",
        pkColumnName="name", valueColumnName="seq",
        pkColumnValue="Person")
    @Column(name="idPerson")
    private int idPerson;
    @Column(name="lastname")
    private String lastname;
    @Column(name="name")
    private String name;
    
    
    @Temporal(TemporalType.DATE) // defines the precision of the date attribute
    @Column(name="birthdate")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date birthdate;
    
    
    // mappedBy must be equal to the name of the attribute in LifeStatus that maps this relation
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<LifeStatus> lifeStatus;
    
    @XmlElementWrapper(name = "healthprofile")
    @XmlElement(name="measureType")
    public List<LifeStatus> getLifeStatus() {
        return lifeStatus;
    }
    
    /**
     * Method to set the value of lifeStatus
     * 
     * @param lifeStatus this represents the lifeStatus of a determinate person
     */
    public void setLifeStatus(List<LifeStatus> lifeStatus){
    	this.lifeStatus = lifeStatus ;
    }
    
    /**
     * Method that get the value of idPerson
     * that represents the id value associated to 
     * a determinated person
     * 
     * @return idPerson	this is the id of the person
     */
    public int getIdPerson(){
        return idPerson;
    }

    /**
     * Method to get the value of person lastname
     * 
     * @return lastname this is the lastname of the Person
     */
    public String getLastname(){
        return lastname;
    }
    
    
    /**
     * Method to get the value of Person name
     * 
     * @return name this is the name, the firstname of the Person
     */
    @XmlElement(name="firstname")
    public String getName(){
        return name;
    }
    
    /**
     * Method to get the value of Person birthdate
     * 
     * @return birthdate this is the birthdate of the Person
     */
    public Date getBirthdate(){
        return birthdate;
    }
    
    
    /**
     * Method to set id of a Person
     * 
     * @param idPerson this is the id of a Person
     */
    public void setIdPerson(int idPerson){
        this.idPerson = idPerson;
    }
    
    
    /**
     * Method to set lastname
     * 
     * @param lastname the value of lastname
     */
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    
    /**
     * Method to set name of the Person
     * 
     * @param name the value of lastname
     */
    public void setName(String name){
        this.name = name;
    }
    
    
    /**
     * Method to set birtdate of a Person
     * 
     * @param birthdate the value of birthdate
     */
    public void setBirthdate(Date birthdate){
        this.birthdate = birthdate;
    }
    
    /**
     * This static method allow to get a Person with a 
     * defined id passed as parameter.
     * In the method have been used the method getEntityManagerFactory().getCache().evictAll()
     * applied to the EntityManager to deal with the problems of cache. 
     * This method allows to release cache and read the actual contents of the database.
     * 
     * @param personId is the int id of the person that we want take
     * @return p this returns the Person with specified personId as param
     */
    public static Person getPersonById(int personId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        
        //Release cache and read the actual contents of the databese
        em.getEntityManagerFactory().getCache().evictAll();
        Person p = em.find(Person.class, personId);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    /**
     * This method is used to get a list of Person.
     * To retrive data to the database is used the query Person.findAll
     * specified in the NamedQueries that select all Person inf the table Person 
     *  
     * 
     * In the method have been used the method getEntityManagerFactory().getCache().evictAll()
     * applied to the EntityManager to deal with the problems of cache. 
     * This method allows to release cache and read the actual contents of the database.
     * 
     * @return list where list represents a list of Person
     */
    public static List<Person> getAll() {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        
        //Release cache and read the actual contents of the databese
        em.getEntityManagerFactory().getCache().evictAll();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
            .getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }
    
    /**
     * This method is used to create a list of Person that have a
     * measure defination value between a min and a max.
     * The query used is Person.findbyMeasureDefRange 
     * 
     * @param md 	this is the measure definition (e.g: weight or height)
     * @param min 	is the lower bound of the measure definition
     * @param max 	is the upper bound of the measure definition 
     * @return list this is a list with all person that have a measure definition that is the the range {(min),(max)}
     */
    public static List<Person> getPersonMeasureRange(MeasureDefinition md, double min, double max) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        
        //Release cache and read the actual contents of the databese
        em.getEntityManagerFactory().getCache().evictAll();
        TypedQuery<Person> query = em.createNamedQuery("Person.findByMeasureDefRange", Person.class);
		query.setParameter(1, md);
		query.setParameter(2, min);
		query.setParameter(3, max);
		
        List<Person> list = query.getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }
    
    

    /**
     * This method is used to save a Person passed as param
     * 
     * @param p 	this is the person that we want save
     * @return p	this is the person that we have saved		 
     */
    public static Person savePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    } 

    /**
     * This method is used to update a Person passed as param
     * 
     * @param p		this is the person that we want update
     * @return p	this is the person that with the updated values
     */
    public static Person updatePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    /**
     * This method remove the specified person passed as param
     * 
     * @param p	this is the person that we want remove
     */
    public static void removePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }
    
}