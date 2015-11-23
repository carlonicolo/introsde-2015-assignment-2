package ehealth.model;

import ehealth.dao.LifeCoachDao;
import ehealth.model.Person;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * The persistent class for the "HealthMeasureHistory" database table.
 * 
 */
@Entity
@Table(name="HealthMeasureHistory")

/**
 * These queries are used to:
 * 
 * 1 - find all HealthMeasureHistory in the database 
 * 2 - find HealthMeasureHistory by measure it means find HealthMeasureHistory related to a person ( passed as parameter ) with a measureDefinition (passed as parameter)
 * 3 - find HealthMeasureHistory by measure date it means find a person that has an HealthMeasureHistory with a date between two parameters
 */
@NamedQueries({
	@NamedQuery(name="HealthMeasureHistory.findAll", query="SELECT h FROM HealthMeasureHistory h"),
	@NamedQuery(name="HealthMeasureHistory.findByMeasure", query="SELECT h FROM HealthMeasureHistory h WHERE h.person = ?1 AND h.measureDefinition = ?2"),
	@NamedQuery(name="HealthMeasureHistory.findByMeasureDate", query="SELECT h FROM HealthMeasureHistory h WHERE h.person = ?1 AND h.measureDefinition = ?2 AND h.timestamp BETWEEN ?4 AND ?3")
})
@XmlType(propOrder={"idMeasureHistory", "value" , "timestamp"})
@JsonPropertyOrder({ "mid", "value", "created"})
@XmlRootElement(name="measure")

/**
 * 
 * 
 * @author Carlo Nicolo'
 *
 */
public class HealthMeasureHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_mhistory")
	@TableGenerator(name="sqlite_mhistory", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="HealthMeasureHistory")
	@Column(name="idMeasureHistory")
	private int idMeasureHistory;

	//@Temporal(TemporalType.DATE)
	
	//@Column(name="timestamp")
	//private String timestamp;
	
	@Temporal(TemporalType.DATE) // defines the precision of the date attribute
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd") // to set in this way otherwise doesn't work 
	@Column(name="timestamp")
    private Date timestamp;
	
	
	@Column(name="value")
	private String value;

	@ManyToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef")
	private MeasureDefinition measureDefinition;

	// notice that we haven't included a reference to the history in Person
	// this means that we don't have to make this attribute XmlTransient
	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;

	public HealthMeasureHistory() {
	}
	
	@XmlElement(name="mid")
	public int getIdMeasureHistory() {
		return this.idMeasureHistory;
	}

	public void setIdMeasureHistory(int idMeasureHistory) {
		this.idMeasureHistory = idMeasureHistory;
	}
	
	@XmlElement(name="created")
	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	@XmlTransient
	public MeasureDefinition getMeasureDefinition() {
	    return measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition param) {
	    this.measureDefinition = param;
	}
	
	@XmlTransient
	public Person getPerson() {
	    return person;
	}

	public void setPerson(Person param) {
	    this.person = param;
	}

	// database operations
	
	/**
	 * This method is used to get the HealthMeasureHistory by id passed as parameter
	 * 
	 * @param id the identification to find 
	 * @return health_measure_history the HealthMeasureHistory found for that specified id
	 */
	public static HealthMeasureHistory getHealthMeasureHistoryById(int id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthMeasureHistory health_measure_history = em.find(HealthMeasureHistory.class, id);
		LifeCoachDao.instance.closeConnections(em);
		return health_measure_history;
	}
	
	/**
	 * This method is used to get all HealthMeasureHistory stored in the database.
	 * This method use the NamedQuery HealthMeasureHistory.findAll
	 * 
	 * @return health_measure_history_list this contains all the HealthMeasureHistory found in the database
	 */
	public static List<HealthMeasureHistory> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<HealthMeasureHistory> health_measure_history_list = em.createNamedQuery("HealthMeasureHistory.findAll", HealthMeasureHistory.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return health_measure_history_list;
	}
	
	
	/**
	 * This method is used to get all HealthMeasureHistory related to a 
	 * Person for a specified MeasureDefinition
	 * 
	 * 
	 * @param objP the person for which we want find the HealthMeasureHistory
	 * @param idMeasureDef the MeasureDefinition saved for the Person
	 * @return health_measure_history_list contains all HealthMeasureHistory related to the Person that has the specified MeasureDefinition
	 */
	public static List<HealthMeasureHistory> getByPersonMeasure(Person objP, MeasureDefinition idMeasureDef) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		TypedQuery<HealthMeasureHistory> query = em.createNamedQuery("HealthMeasureHistory.findByMeasure", HealthMeasureHistory.class);
		query.setParameter(1, objP);
		query.setParameter(2, idMeasureDef);
		List<HealthMeasureHistory> health_measure_history_list = query.getResultList();
	    LifeCoachDao.instance.closeConnections(em);
		return health_measure_history_list;
	}
	
	/**
	 * This method is used to save the HealthMeasureHistory passed as parameter
	 * 
	 * @param health_measure_history the HealthMeasureHistory that we want save
	 * @return health_measure_history is the HealthMeasureHistory saved
	 */
	public static HealthMeasureHistory saveHealthMeasureHistory(HealthMeasureHistory health_measure_history) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(health_measure_history);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return health_measure_history;
	}
	
	
	/**
	 * This method is used to udpate the HealthMeasureHistory passed as parameter
	 * 
	 * @param health_measure_history the HealthMeasureHistory that we want update
	 * @return health_measure_history the HealthMeasureHistory updated
	 */
	public static HealthMeasureHistory updateHealthMeasureHistory(HealthMeasureHistory health_measure_history) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		health_measure_history=em.merge(health_measure_history);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return health_measure_history;
	}
	
	
	/**
	 * This method is used to remove the HealthMeasureHistory passed as parameter
	 * 
	 * @param health_measure_history the HealthMeasureHistory to remove
	 */
	public static void removeHealthMeasureHistory(HealthMeasureHistory health_measure_history) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    health_measure_history=em.merge(health_measure_history);
	    em.remove(health_measure_history);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}

	/**
	 * This method is used to get the person that have a HealthMeasureHistory with a MeasureDefinition saved between two dates
	 * 
	 * @param person the person that we want check
	 * @param md the MeasureDefinition that we want check
	 * @param before the upper bound of the search. Example look before than 2015-11-10
	 * @param after the lower bound of the search. Example look after than 2011-10-11
	 * @return health_measure_history_list containing all HealthMeasureHistory found 
	 */
	public static List<HealthMeasureHistory> getByPersonMeasureRangeDate(Person person, MeasureDefinition md, Calendar before, Calendar after) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		TypedQuery<HealthMeasureHistory> query = em.createNamedQuery("HealthMeasureHistory.findByMeasureDate", HealthMeasureHistory.class);
		query.setParameter(1, person);
		query.setParameter(2, md);
		query.setParameter(3, before.getTime());
		query.setParameter(4, after.getTime());
		
		List<HealthMeasureHistory> health_measure_history_list = query.getResultList();
	    LifeCoachDao.instance.closeConnections(em);
		return health_measure_history_list;
	}
}
