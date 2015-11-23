package ehealth.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import ehealth.model.MeasureDefinition;


/**
 * This class is a wrapper for diplaying the MeasureDefinition 
 * as requested for the json format
 * 
 * @author Carlo Nicolo'
 *
 */
@XmlRootElement(name="measureTypes")
public class MeasureDefinitionWrapper {
	
	@XmlElement(name="measureType")
	@JsonProperty("measureTypes")
	public List<MeasureDefinition> measureDefinition = new ArrayList<MeasureDefinition>();
	
	//set method
	public void setMeasureDefinition(List<MeasureDefinition> definitions) {
		this.measureDefinition = definitions;
	}
	
	
}
