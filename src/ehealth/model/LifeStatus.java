package ehealth.model;

import ehealth.dao.LifeCoachDao;
import ehealth.model.MeasureDefinition;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.OneToOne;

/**
 * The persistent class for the "LifeStatus" database table.
 * 
 */
@Entity
@Table(name = "LifeStatus")


/**
 * These queries are used to:
 * - find and select the lifestatus
 * - find and get the lifestatus for a person with a measure definition. The person and the measure defintion are passed as parameter 
 */
@NamedQueries({
    @NamedQuery(name = "LifeStatus.findAll", query = "SELECT l FROM LifeStatus l"),
    @NamedQuery(name="LifeStatus.findByMeasureDefPerson", query="SELECT l FROM LifeStatus l WHERE l.person = ?1 AND l.measureDefinition = ?2")
})
@XmlRootElement(name="lifestatus")
public class LifeStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_lifestatus")
	@TableGenerator(name="sqlite_lifestatus", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="LifeStatus")
	@Column(name = "idMeasure")
	private int idMeasure;

	@Column(name = "value")
	private String value;
	
	@OneToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef", insertable = true, updatable = true)
	private MeasureDefinition measureDefinition;
	
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;

	public LifeStatus() {
	}
	/**
	 * This is the costructor of the class LifeStatus and is used 
	 * to create a complete lifestatus with all elements
	 * 
	 * @param person the person for which we want create the lifestatus 
	 * @param md the measure definition associated to this lifestatus
	 * @param value the value of this lifestatus
	 */
	public LifeStatus(Person person, MeasureDefinition md, String value) {
		this.person = person;
		this.measureDefinition = md;
		this.value = value; 
		
	}

	@XmlTransient
	public int getIdMeasure() {
		return this.idMeasure;
	}

	public void setIdMeasure(int idMeasure) {
		this.idMeasure = idMeasure;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@XmlElement(name = "measure")
	public MeasureDefinition getMeasureDefinition() {
		return measureDefinition;
	}
	
	public void setMeasureDefinition(MeasureDefinition param) {
		this.measureDefinition = param;
	}

	// we make this transient for JAXB to avoid and infinite loop on serialization
	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	 
	/**
	 *This method is use to get the lifestatus 
	 *using the lifestatus id passed as parameter
	 * 
	 * @param lifestatusId the lifestatus id that we want find
	 * @return p the lifestatus
	 */
	public static LifeStatus getLifeStatusById(int lifestatusId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		LifeStatus p = em.find(LifeStatus.class, lifestatusId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	/**
	 *This method is used to find and get all the lifestatus 
	 *saved in the database
	 * 
	 * @return list the list containing all the lifestatus found in the database
	 */
	public static List<LifeStatus> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<LifeStatus> list = em.createNamedQuery("LifeStatus.findAll", LifeStatus.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	
	/**
	 * This method is used to save the lifestatus passed as parameter
	 * 
	 * @param p the lifestatus to save
	 * @return p the lifestatus saved
	 */
	public static LifeStatus saveLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	/**
	 * This method is used to update the lifestatus passed as parameter
	 * 
	 * @param p the lifestatus to update
	 * @return p the lifestatus updated
	 */
	public static LifeStatus updateLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	
	/**
	 * This method is used to remove the lifestatus passed as parameter
	 * 
	 * @param p the lifestatus to remove
	 */
	public static void removeLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
	
	
	//POST Extra
	/**
	 * This method is used to find and get lifestatus 
	 * related to a person with a measure definition
	 * 
	 * @param md the measure definition 
	 * @param p the person that we want check to see if there is a lifestatus with a determinated measuredefinition
	 * @return ls the lifestatus for this person
	 * @return null in the case there are not present lifestatus for this person with this measure definition
	 */
	public static LifeStatus getLifeStatusByMeasureDefPerson(MeasureDefinition md, Person p){
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        try{
        LifeStatus ls = em.createNamedQuery("LifeStatus.findByMeasureDefPerson", LifeStatus.class).setParameter(1, p).setParameter(2, md).getSingleResult();
        LifeCoachDao.instance.closeConnections(em);
        return ls;
        }catch(Exception e){
        	return null;
        }
    }
	
	
	
	
}
