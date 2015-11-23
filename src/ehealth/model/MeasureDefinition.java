package ehealth.model;

import ehealth.dao.LifeCoachDao;
import ehealth.model.MeasureDefaultRange;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import com.fasterxml.jackson.annotation.JsonValue;


/**
 * The persistent class for the "MeasureDefinition" database table.
 * 
 */
@Entity
@Table(name="MeasureDefinition")


@NamedQueries({
	@NamedQuery(name="MeasureDefinition.findAll", query="SELECT m FROM MeasureDefinition m"),
	@NamedQuery(name="MeasureDefinition.getMeasureDefinitionByName", query="SELECT d FROM MeasureDefinition d WHERE d.measureName = ?1 ")
})

@XmlRootElement(name="measure")
public class MeasureDefinition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_measuredef")
	@TableGenerator(name="sqlite_measuredef", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="MeasureDefinition")
	@Column(name="idMeasureDef")
	private int idMeasureDef;

	@Column(name="measureName")
	private String measureName;

	@Column(name="measureType")
	private String measureType;

	@OneToMany(mappedBy="measureDefinition")
	private List<MeasureDefaultRange> measureDefaultRange;

	public MeasureDefinition() {
	}
	@XmlTransient
	public int getIdMeasureDef() {
		return this.idMeasureDef;
	}

	public void setIdMeasureDef(int idMeasureDef) {
		this.idMeasureDef = idMeasureDef;
	}
	
	@XmlValue
	@JsonValue
	public String getMeasureName() {
		return this.measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}
	@XmlTransient
	public String getMeasureType() {
		return this.measureType;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}
	@XmlTransient
	public List<MeasureDefaultRange> getMeasureDefaultRange() {
	    return measureDefaultRange;
	}

	public void setMeasureDefaultRange(List<MeasureDefaultRange> param) {
	    this.measureDefaultRange = param;
	}

	// database operations
	
	
	/**
	 * This method is used to get the measure definition by the id passed
	 * through the parameter
	 * 
	 * @param personId	is the personId for which we want retrive the measure id
	 * @return p 		is the measure definition associated to the person id
	 */
	public static MeasureDefinition getMeasureDefinitionById(int personId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		MeasureDefinition p = em.find(MeasureDefinition.class, personId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	
	//Query to retrive the name of the definition
	/**
	 * This method is use to get the measure definition name using a parameter
	 * to search in the database and use it in the query.
	 * E.i. getMeasureDefinitionByName(weight)
	 * If the definition is not present it doesn't get anything
	 * 
	 * @param measureName is the name of the measure definition to search in the database
	 * @return p the measure definition found
	 */
	public static MeasureDefinition getMeasureDefinitionByName(String measureName) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		try{
		MeasureDefinition p = em.createNamedQuery("MeasureDefinition.getMeasureDefinitionByName", MeasureDefinition.class).setParameter(1, measureName).getSingleResult();
		LifeCoachDao.instance.closeConnections(em);
		return p;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * This method is used to get all measure definition.
	 * 
	 * @return list is the list of all measure definition found in the database table
	 */
	public static List<MeasureDefinition> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<MeasureDefinition> list = em.createNamedQuery("MeasureDefinition.findAll", MeasureDefinition.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	/**
	 * 
	 * 
	 * @param p
	 * @return
	 */
	public static MeasureDefinition saveMeasureDefinition(MeasureDefinition p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public static MeasureDefinition updateMeasureDefinition(MeasureDefinition p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	
	/**
	 * 
	 * @param p
	 */
	public static void removeMeasureDefinition(MeasureDefinition p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
