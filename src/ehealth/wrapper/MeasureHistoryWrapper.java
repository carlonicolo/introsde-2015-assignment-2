package ehealth.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonValue;

import ehealth.model.HealthMeasureHistory;;

/**
 * This class is a wrapper for diplaying the MeasureHistory 
 * as requested for the json format
 *  
 * @author Carlo Nicolo'
 *
 */
@XmlRootElement(name="measureHistory")
public class MeasureHistoryWrapper {

	/**
	 * List of HealthMeasureHistory
	 */
	private List<HealthMeasureHistory> healthMeasureHistory = new ArrayList<HealthMeasureHistory>();
	
	//get method
	@XmlElement(name = "measure")
	@JsonValue
	public List<HealthMeasureHistory> getHealthMeasureHistory(){
		return this.healthMeasureHistory;
	}
	
	//set method
	public void setHealthMeasureHistory(List<HealthMeasureHistory> hmh){
	    	this.healthMeasureHistory = hmh ;
	}
	
}
