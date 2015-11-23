package ehealth.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;


import ehealth.model.Person;

/**
 * This class is a wrapper for diplaying the Person 
 * as requested for the json format
 * 
 * @author Carlo Nicolo'
 *
 */
@XmlRootElement(name="people")
//@XmlAccessorType(XmlAccessType.FIELD)
public class PeopleWrapper {

	/**
	 * Create a list of person
	 */
	@XmlElement(name="person")
	@JsonProperty("people")
	public List<Person> people = new ArrayList<Person>();
	
	/**
	 * This method set the people List to the parameter passed 
	 * @param people List of Person
	 */
	public void setPeople(List<Person> people){
	    	this.people = people ;
	}
	
}
