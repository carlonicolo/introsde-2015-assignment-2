package ehealth.client;



import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;



public class ClientCarlo{
	
	private WebTarget service;
	
	//Response var
	private Response response1;
	private Response response1_1;
	private Response response2;
	private Response response3;
	private Response response3_get;
	private Response response4;
	private Response response4_json;
	
	private Response response5;
	private Response response5_json;
	
	private Response response6;
	private Response response6_json;
	
	
	private Response response7;
	private Response response7_first_id;
	private Response response7_last_id;
	private Response response7_first_id_json;
	private Response response7_last_id_json;
	
	private Response response8;
	private Response response8_json;
	
	private Response response9;
	private Response response9_post;
	private Response response9_get;
	private Response response9_json;
	private Response response9_post_json;
	private Response response9_get_json;
	
	
	private Response response10;
	private Response response10_put;
	private Response response10_get;
	private Response response10_json;
	private Response response10_put_json;
	private Response response10_get_json;
	
	
	private Response response11;
	private Response response11_json;
	private Response response12;
	private Response response12_json;
	
	
	// Query string
	private String xmlGetAll;
	private String xmlGetId_request1_1;
	private String xmlGetId;
	private String xmlPutPersonId;
	private String xmlGetId_put;
	private String xmlPost;
	private String xmlDeleteId;
	private String xmlGetMeasureTypes;
	private String xmlGetRequest7_first_id;
	private String xmlGetRequest7_last_id;
	private String xmlMeasureTypeMid;
	private String xmlGetMeasureType_step9;
	private String xmlPostMeasureType_step9;
	private String xmlGetMeasureType_step9_1;
	private String xmlGetRequest10;
	private String xmlGetRequest10_HealthHistory;
	private String xmlGetRequest11;
	private String xmlGetRequest12;
	private String json_put;
	private String JsonPost;
	private String JsonDeleteId;
	private String jsonGetMeasureTypes;
	private String jsonGetRequest7_first_id;
	private String jsonGetRequest7_last_id;
	private String jsonMeasureTypeMid;
	private String jsonGetMeasureType_step9;
	private String jsonPostMeasureType_step9;
	private String jsonGetMeasureType_step9_1;
	private String jsonGetRequest10;
	private String jsonGetRequest12;
	
	
	//Path string
	private String base_url = "person/";
	
	private String path_request2 = "";
	private String path_request2_json = "";
	
	
	private String path_request6 = "/measureTypes";
	private String path_request7_first_id = "";
	private String path_request7_last_id = "";
	
	private String path_request7_first_id_json = "";
	private String path_request7_last_id_json = "";
	
	
	private String path_request8 = "";
	private String path_request9 = "";
	private String path_request10 = "";
	private String path_request11 = "";
	private String path_request12 = "";
	private String path_request12_format;
	private String path_request11_format;
	private String path_request12_format_json;
	
	// Error string xml
	private String codeErrorR1 = "";
	private String codeErrorR2 = "";
	private String codeErrorR3 = "";
	private String codeErrorR4 = "";
	private String codeErrorR5 = "";
	private String codeErrorR6 = "";
	private String codeErrorR7 = "";
	private String codeErrorR8 = "";
	private String codeErrorR9 = "";
	private String codeErrorR10 = "";
	private String codeErrorR11 = "";
	private String codeErrorR12 = "";
	
	//Error var JSON
	private String codeErrorR1_json = "";
	private String codeErrorR2_json = "";
	private String codeErrorR3_json = "";
	private String codeErrorR4_json = "";
	private String codeErrorR5_json = "";
	private String codeErrorR6_json = "";
	private String codeErrorR7_json = "";
	private String codeErrorR8_json = "";
	private String codeErrorR9_json = "";
	private String codeErrorR10_json = "";
	private String codeErrorR11_json = "";
	private String codeErrorR12_json = "";
	
	
	
	//Name var
	private String first_person_id;
	private String last_person_id;
	
	private String first_person_id_json;
	private String last_person_id_json;
	
	private String input_put;
	
	private String firstname_request3;
	private String firstname_request3_json;
	
	private String id_new_person_request4;
	private String id_new_person_request4_json;
	
	private String measure_id;
	private String measuretype;
	
	//accept request var
	private String xml = "application/xml";
	private String json = "application/json";
	
	//MeasureType lenght
	private int measureTypeLenght;
	private int measureTypeLenght_json;
	
	//Control flag measureType
	boolean first_id_person_measure = false;
	boolean last_id_person_measure = false;
	boolean first_person_history_exists = false;
	boolean last_person_history_exists = false;
	
	//ArrayList containing measure type
	private ArrayList<String> measureTypeList;
	private ArrayList<String> measureTypeList_json;
	
	//ArrayList storing the values of existing mid and measure type for first_di and last_id
	// the values inside the arraylist are fomatted in this way {mid,measureType,mid,measureType}
	private ArrayList<String> listMeasureIdMeasureType_first_id;
	private ArrayList<String> listMeasureIdMeasureType_last_id;
	private ArrayList<String> listMeasureIdMeasureType_first_id_json;
	private ArrayList<String> listMeasureIdMeasureType_last_id_json;
	
	//NumberChildren
	int number_children_request9 = 0;
	int number_children_request9_after = 0;
	int number_children_request9_json = 0;
	int number_children_request9_after_json = 0;
	
	
	//Var used in the request R9
	private String mid_r9 = ""; //mid taken from the healthHistory created in R9
	private String val_r9 = ""; // value taken from the healthHistory created in R9
	private String mid_r9_json = "";
	private String val_r9_json = "";
	
	//Mid values used in request10
	private String mid_r10 = "";
	private String val_r10 = "";
	private String mid_r10_json = "";
	private String val_r10_json = "";
	
	
	private String xmlGetRequest_last_id_filled;
	
	// Var request11
	private String before = "";
	private String after = "";

	// Var request 12
	private String min = "";
	private String max = "";

	/**
	 * This is method allow to create an object for this class
	 * with the param connection string that represents the address of the
	 * server that we want interact with
	 * 
	 * @param conn is the connection string representing the server that we want contact
	 */
	public ClientCarlo(String conn){
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getBaseURI(conn));
		this.service = service;
	}
	
	
	
	// STEP 1:
	/**
	 * This method sends the R#1 request GET /person
	 * Calculate how many people are in the response. 
	 * If more than 2, result is OK, else is ERROR (less than 3 persons). 
	 * Save into a variable id of the first person (first_person_id) and of the last person (last_person_id)
	 * 
	 * 
	 * Assignment client Step
	 * Step 3.1
	 * MediaType.Application_XML
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request1() throws ParserConfigurationException, SAXException, IOException{
		response1 = service.path(base_url).request().accept(MediaType.APPLICATION_XML).get(Response.class);
		xmlGetAll = response1.readEntity(String.class);
		Element rootElement= ClientCarlo.createXMLelement(xmlGetAll);

		//boolean x = false;
		if (rootElement.getElementsByTagName("person").getLength() >2){
			codeErrorR1 = "OK";
		}else{
			codeErrorR1 = "ERROR";
		}


		first_person_id = rootElement.getFirstChild().getFirstChild().getTextContent();
		last_person_id = rootElement.getLastChild().getFirstChild().getTextContent();


		displayClientResponse(1, response1, "GET", base_url,  xml, codeErrorR1);
		System.out.println(prettyFormat(xmlGetAll));
		System.out.println("=============================================================================================================");

	}
	
	
	
	/**
	 * This method sends the R#1 request GET /person
	 * Calculate how many people are in the response. 
	 * If more than 2, result is OK, else is ERROR (less than 3 persons). 
	 * Save into a variable id of the first person (first_person_id) and of the last person (last_person_id)
	 * 
	 * 
	 * Assignment client Step
	 * Step 3.1
	 * MediaType.Application_JSON
	 * 
	 */
	public void request1_json(){
		Response response1_json = service.path(base_url).request().accept(MediaType.APPLICATION_JSON).get(Response.class);
		String JsonGetAll = response1_json.readEntity(String.class);
		JSONObject getAll_json = new JSONObject(JsonGetAll);
		JSONArray json_array_request1 = getAll_json.getJSONArray("people");
		
		if(json_array_request1.length() > 2){
			codeErrorR1_json = "OK";
			displayClientResponse(1, response1_json, "GET", base_url, json, codeErrorR1_json);
			System.out.println(prettyFormatJson(JsonGetAll));
		}else{
			codeErrorR1_json = "ERROR";
			displayClientResponse(1, response1_json, "GET", base_url, json, codeErrorR1_json);
			System.out.println(prettyFormatJson(JsonGetAll));
		}
		
		int arraylenght = (json_array_request1.length())-1;
		
		//System.out.println("ArrayLenght: " + arraylenght);
		
		
		
		first_person_id_json = Integer.toString(json_array_request1.getJSONObject(0).getInt("idPerson"));
		last_person_id_json = Integer.toString(json_array_request1.getJSONObject(arraylenght).getInt("idPerson"));
		
		System.out.println("First person id json: " + first_person_id_json);
		System.out.println("Last person id json: " + last_person_id_json);
		
	}
	
	
	
	
	
	
	// STEP 2:
	/**
	 * Step 3.2
	 * Send R#2 for first_person_id. If the responses for this is 200 or 202, the result is OK.
	 * 
	 * MediaType.APPLICATION_XML
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request2() throws ParserConfigurationException, SAXException, IOException{
		path_request2 = base_url+first_person_id;
		response2 = service.path(path_request2).request().accept(MediaType.APPLICATION_XML).get(Response.class);
		xmlGetId = response2.readEntity(String.class);
		Element rootElementR2 = createXMLelement(xmlGetId);
		
		if(response2.getStatus() == 200 || response2.getStatus() == 202){
			codeErrorR2 = "OK";
			displayClientResponse(2, response2, "GET", path_request2,  xml, codeErrorR2);
			System.out.println(prettyFormat(xmlGetId));
			System.out.println("=============================================================================================================");
		}else{
			codeErrorR2 = "ERROR";
			displayClientResponse(2, response2, "GET", path_request2,  xml, codeErrorR2);
			System.out.println(prettyFormat(xmlGetId));
			System.out.println("=============================================================================================================");
		}
	}
	
	/**
	 * Step 3.2
	 * Send R#2 for first_person_id. If the responses for this is 200 or 202, the result is OK.
	 * 
	 * MediaType.APPLICATION_JSON
	 */
	public void request2_json(){
		
		path_request2_json = base_url+first_person_id_json;
		
		Response response2_json = service.path(path_request2_json).request().accept(MediaType.APPLICATION_JSON).get(Response.class);
		String JsonGetFirst_id = response2_json.readEntity(String.class);
		//JSONObject get_first_id_json = new JSONObject(JsonGetFirst_id);
		//JSONArray json_array_request = get_first_id_json.getJSONArray("people");
		
		
		
		if(response2_json.getStatus() == 200 || response2_json.getStatus() == 202){
			codeErrorR2_json = "OK";
			displayClientResponse(2, response2_json, "GET", path_request2_json, json, codeErrorR2_json);
			System.out.println(prettyFormatJson(JsonGetFirst_id));
		}else{
			codeErrorR2_json = "ERROR";
			displayClientResponse(2, response2_json, "GET", path_request2_json, json, codeErrorR2_json);
			System.out.println(prettyFormatJson(JsonGetFirst_id));
		}
		
		
	}
	
	
	
	
	
	
	/**
	 * Step 3.3
	 * Send R#3 for first_person_id changing the firstname. If the responses has the name changed, the result is OK
	 * 
	 * MediaType.APPLICATION_XML
	 * 
	 * @param firstname the name to change
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request3(String firstname) throws ParserConfigurationException, SAXException, IOException{
		firstname_request3 = firstname;
		input_put = "<person><firstname>"+firstname_request3+"</firstname></person>";
		
		
		response3 = service.path(path_request2).request().accept(MediaType.APPLICATION_XML).put(Entity.entity(input_put, MediaType.APPLICATION_XML));
		
		
		
		response3_get = service.path(path_request2).request().accept(MediaType.APPLICATION_XML).get(Response.class);
		xmlGetId_put = response3_get.readEntity(String.class);
		Element rootElementR3 = createXMLelement(xmlGetId_put);
		
		String firstname_after_put = rootElementR3.getFirstChild().getFirstChild().getTextContent();
		if(firstname_after_put == firstname_request3){
			codeErrorR3 = "OK";
			displayClientResponse(3, response3, "PUT", path_request2, xml, codeErrorR3);
			System.out.println(prettyFormat(xmlGetId_put));
			System.out.println("=============================================================================================================");
		}else
		{
			codeErrorR3 = "ERROR";
			displayClientResponse(3, response3, "PUT", path_request2, xml, codeErrorR3);
			System.out.println(prettyFormat(xmlGetId_put));
			System.out.println("=============================================================================================================");
		}
	}
	
	
	
	/**
	 * Step 3.3
	 * Send R#3 for first_person_id changing the firstname. If the responses has the name changed, the result is OK
	 * MediaType.APPLICATION_JSON
	 * 
	 * @param firstname the name to change
	 */
	public void request3_json(String firstname){
		firstname_request3_json = firstname;

		JSONObject obj = new JSONObject();
		obj.put("firstname", firstname_request3_json);
		String input_put = obj.toString();

		Response response3_json = service.path(path_request2_json).request().accept(MediaType.APPLICATION_JSON).put(Entity.entity(input_put, MediaType.APPLICATION_JSON));

		Response response3_get_json = service.path(path_request2_json).request().accept(MediaType.APPLICATION_JSON).get(Response.class);
		json_put = response3_get_json.readEntity(String.class);

		//System.out.println("RESPONSE JSON 3 " + response3_get_json);
		//System.out.println("JSON_PUT " + json_put);

		JSONObject getPut_json = new JSONObject(json_put);
		//System.out.println("firstname_request_json: " + firstname_request3_json);


		String firstname_after_put_json = getPut_json.getString("firstname").toString();
		//System.out.println("firstname_after_put_json: " + firstname_after_put_json);

		//boolean x = false;
		//x = (firstname_after_put_json.equals(firstname_request3_json));
		//System.out.println(x);

		if(firstname_after_put_json.equals(firstname_request3_json)){
			codeErrorR3_json = "OK";
			displayClientResponse(3, response3_json, "PUT", path_request2_json, json, codeErrorR3_json);
			System.out.println(prettyFormatJson(json_put));
		}else{
			codeErrorR3_json = "ERROR";
			displayClientResponse(3, response3_json, "PUT", path_request2_json, json, codeErrorR3_json);
			System.out.println(prettyFormatJson(json_put));
		}


	}
	
	
	
	
	
	// STEP 4:
	/**
	 * Step 3.4. Send R#4 to create the following person. Store the id of the new person. 
	 * If the answer is 201 (200 or 202 are also applicable) with a person in the body who has an ID, the result is OK
	 * 
	 * MediaType.APPLICATION_XML
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request4() throws ParserConfigurationException, SAXException, IOException{
		
		String input_post = "<person>"
				+ "<firstname>Chuck</firstname>"
				+ "<lastname>Norris</lastname>"
				+ "<birthdate>1945-01-01</birthdate>"
				+ "<healthprofile>"
				+ "    <measureType>"
				+ "        <measure>weight</measure>"
				+ "        <value>78.9</value>"
				+ "   </measureType>"
				+ "   <measureType>"
				+ "        <measure>height</measure> "
				+ "        <value>172</value>"
				+ "   </measureType>"
				+ "</healthprofile>"
				+ "</person>";
		
		response4 = service.path(base_url).request().accept(MediaType.APPLICATION_XML).post(Entity.entity(input_post, MediaType.APPLICATION_XML));
		xmlPost = response4.readEntity(String.class);
		Element rootElementR4 = createXMLelement(xmlPost);
		this.id_new_person_request4 = rootElementR4.getFirstChild().getTextContent();
		int res_stat = response4.getStatus();
		if( res_stat == 200 || res_stat == 201 || res_stat == 202 ){
			codeErrorR4 = "OK";
			displayClientResponse(4, response4, "POST", base_url, xml, codeErrorR4);
			System.out.println(prettyFormat(xmlPost));
			System.out.println("=============================================================================================================");
		}else{
			codeErrorR4 = "ERROR";
			displayClientResponse(4, response4, "POST", base_url, xml, codeErrorR4);
			System.out.println("=============================================================================================================");
		}
		
		
	}
	
	
	/**
	 * Step 3.4. Send R#4 to create the following person. Store the id of the new person. 
	 * If the answer is 201 (200 or 202 are also applicable) with a person in the body who has an ID, the result is OK
	 * 
	 * MediaType.APPLICATION_JSON
	 * 
	 */
	public void request4_json() throws ParserConfigurationException, SAXException, IOException{
		
		String input_post = "<person>"
				+ "<firstname>Chuck</firstname>"
				+ "<lastname>Norris</lastname>"
				+ "<birthdate>1945-01-01</birthdate>"
				+ "<healthprofile>"
				+ "    <measureType>"
				+ "        <measure>weight</measure>"
				+ "        <value>78.9</value>"
				+ "   </measureType>"
				+ "   <measureType>"
				+ "        <measure>height</measure> "
				+ "        <value>172</value>"
				+ "   </measureType>"
				+ "</healthprofile>"
				+ "</person>";
		
		response4_json = service.path(base_url).request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(input_post, MediaType.APPLICATION_XML));
		JsonPost = response4_json.readEntity(String.class);
		
		JSONObject post_json = new JSONObject(JsonPost);
		
		id_new_person_request4_json = Integer.toString(post_json.getInt("idPerson"));
		//System.out.println("id_new_person_request4: " + id_new_person_request4_json);
		
		//System.out.println("JSONObject getPost_json: " + getPost_json);
		
		//Element rootElementR4 = createXMLelement(xmlPost);
		//this.id_new_person_request4 = rootElementR4.getFirstChild().getTextContent();
		int res_stat = response4_json.getStatus();
		if( res_stat == 200 || res_stat == 201 || res_stat == 202 ){
			codeErrorR4 = "OK";
			displayClientResponse(4, response4_json, "POST", base_url, json, codeErrorR4);
			System.out.println(prettyFormatJson(JsonPost));
			System.out.println("=============================================================================================================");
		}else{
			codeErrorR4 = "ERROR";
			displayClientResponse(4, response4_json, "POST", base_url, json, codeErrorR4);
			System.out.println(prettyFormatJson(JsonPost));
			System.out.println("=============================================================================================================");
		}
		
		
	}
	
	
	
	/**
	 * Step 3.5. Send R#5 for the person you have just created.
	 * Then send R#1 with the id of that person. If the answer is 404, your result must be OK.
	 * 
	 * MediaType.APPLICATION_XML
	 * 
	 */
	public void request5(){
		//System.out.println(this.id_new_person_request4);
		String path_request5 = base_url+this.id_new_person_request4;
		response5 = service.path(path_request5).request().accept(MediaType.APPLICATION_XML).delete(Response.class);
		xmlDeleteId = response5.readEntity(String.class);
		Response status = request1_id(this.id_new_person_request4);
		if(status.getStatus() == 404){
			codeErrorR5 = "OK";
			displayClientResponse(5, status, "DELETE", path_request5, xml, codeErrorR5);
			System.out.println("=============================================================================================================");
		}else{
			codeErrorR5 = "ERROR";
			displayClientResponse(5, status, "DELETE", path_request5, xml, codeErrorR5);
			System.out.println("=============================================================================================================");
		}
	}
	
	
	public Response request1_id(String id){
		String path_request1_1 = base_url+id;
		response1_1 = service.path(path_request1_1).request().accept(MediaType.APPLICATION_XML).get(Response.class);
		xmlGetId_request1_1 = response1_1.readEntity(String.class);
		//int status = (response1_1.getStatus());
		//return status;
		return response1_1;
	}
	
	/**
	 * Step 3.5. Send R#5 for the person you have just created.
	 * Then send R#1 with the id of that person. If the answer is 404, your result must be OK.
	 * 
	 * MediaType.APPLICATION_JSON
	 * 
	 */
	public void request5_json(){
		//System.out.println(this.id_new_person_request4_json);
		String path_request5 = base_url+id_new_person_request4_json;
		//System.out.println(path_request5);
		
		response5_json = service.path(path_request5).request().accept(MediaType.APPLICATION_JSON).delete(Response.class);
		JsonDeleteId = response5_json.readEntity(String.class);
		//int status = request1_id(id_new_person_request4_json);
		Response deleteResponse = request1_id("id_new_person_request4_json");
		
		if(deleteResponse.getStatus() == 404){
			codeErrorR5 = "OK";
			displayClientResponse(5, deleteResponse, "DELETE", path_request5, json, codeErrorR5);
			System.out.println("=============================================================================================================");
		}else{
			codeErrorR5 = "ERROR";
			displayClientResponse(5, deleteResponse, "DELETE", path_request5, json, codeErrorR5);
			System.out.println("=============================================================================================================");
		}
	}
	
	
	/**
	 * Step 3.6. Follow now with the R#9 (GET BASE_URL/measureTypes). 
	 * If response contains more than 2 measureTypes - result is OK, else is ERROR 
	 * (less than 3 measureTypes). Save all measureTypes into array (measure_types)
	 * 
	 * MediaType.APPLICATION_XML
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request6() throws ParserConfigurationException, SAXException, IOException{
		response6 = service.path(path_request6).request().accept(MediaType.APPLICATION_XML).get(Response.class);
		xmlGetMeasureTypes = response6.readEntity(String.class);
		Element rootElementR6 = createXMLelement(xmlGetMeasureTypes);
		//System.out.println("Numero elementi measureTypes: " + rootElementR6.getElementsByTagName("measure").getLength() );
		NodeList m6 = rootElementR6.getChildNodes();
		
		//if(rootElementR6.getElementsByTagName("measure").getLength() > 2){
		 if(m6.getLength() > 2){
			codeErrorR6 = "OK";
			displayClientResponse(6, response6, "GET", path_request6, xml, codeErrorR6);
			System.out.println(prettyFormat(xmlGetMeasureTypes));
			System.out.println("=============================================================================================================");
		}else{
			codeErrorR6 = "ERROR";
			displayClientResponse(6, response6, "GET", path_request6, xml, codeErrorR6);
			System.out.println(prettyFormat(xmlGetMeasureTypes));
			System.out.println("=============================================================================================================");
		}
		
		measureTypeList = new ArrayList<String>();
		measureTypeLenght = rootElementR6.getElementsByTagName("measureType").getLength();
		
		//System.out.println("Root element of the document: " + rootElementR6.getNodeName());
        NodeList measureType = rootElementR6.getChildNodes();
		
        for(int i = 0; i < measureTypeLenght; i++){
        	measureTypeList.add(measureType.item(i).getTextContent());
        	//System.out.println(measureTypeList.get(i).toString());
        }
        
        //System.out.println("Valore measureTypeList.size() ---> "+ measureTypeList.size());
        
	}
	
	
	
	/**
	 * Step 3.6. Follow now with the R#9 (GET BASE_URL/measureTypes). 
	 * If response contains more than 2 measureTypes - result is OK, else is ERROR 
	 * (less than 3 measureTypes). Save all measureTypes into array (measure_types)
	 * 
	 * MediaType.APPLICATION_JSON
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request6_json() throws ParserConfigurationException, SAXException, IOException{
		response6_json = service.path(path_request6).request().accept(MediaType.APPLICATION_JSON).get(Response.class);
		jsonGetMeasureTypes = response6_json.readEntity(String.class);
		
		JSONObject getAll_json = new JSONObject(jsonGetMeasureTypes);
		//System.out.println("jsonGetMeasureTypes: " + jsonGetMeasureTypes);
		
		JSONArray json_array_request1 = getAll_json.getJSONArray("measureTypes");
		//System.out.println("json_array_request1: " + json_array_request1);		
		
		if(json_array_request1.length() > 2){
			codeErrorR6_json = "OK";
			displayClientResponse(6, response6_json, "GET", path_request6, json, codeErrorR6_json);
			System.out.println(prettyFormatJson(jsonGetMeasureTypes));
			System.out.println("=============================================================================================================");
		}else{
			codeErrorR6 = "ERROR";
			displayClientResponse(6, response6_json, "GET", path_request6, json, codeErrorR6_json);
			System.out.println(prettyFormatJson(jsonGetMeasureTypes));
			System.out.println("=============================================================================================================");
		}
//
		measureTypeList_json = new ArrayList<String>();
		
		for(int i = 0; i < json_array_request1.length(); i++){
			//System.out.println("Element: " + i + " contains: " + json_array_request1.getString(i));
			measureTypeList_json.add(json_array_request1.getString(i));
		}
		

	}
	
	
	/**
	 * Step 3.7. Send R#6 (GET BASE_URL/person/{id}/{measureType}) for the first person you obtained at the beginning and the last person,
	 * and for each measure types from measure_types. 
	 * If no response has at least one measure - result is ERROR (no data at all) else result is OK. Store one measure_id and one measureType.
	 * 
	 * MediaType.APPLICATION_XML
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request7() throws ParserConfigurationException, SAXException, IOException{
		path_request7_first_id = "person/"+first_person_id+"/";
		path_request7_last_id = "person/"+last_person_id+"/";
		
		//System.out.println(path_request7_first_id);
		//System.out.println(path_request7_last_id);
		

		listMeasureIdMeasureType_first_id = new ArrayList<>();
		listMeasureIdMeasureType_last_id = new ArrayList<>();
		
	
		int countValid_measure_first_id = 0;
		int countValid_measure_last_id = 0;
		
		//loop first_id
		for(int i = 0; i < measureTypeList.size(); i++){
			response7_first_id = service.path(path_request7_first_id+measureTypeList.get(i).toString()).request().accept(MediaType.APPLICATION_XML).get(Response.class);
			xmlGetRequest7_first_id = response7_first_id.readEntity(String.class);
			Element rootElementR7_first_id = createXMLelement(xmlGetRequest7_first_id);
			NodeList measureList_first_id = rootElementR7_first_id.getChildNodes();
			
			if(measureList_first_id.getLength() > 0){
				
				//add the values of firstchild content measure_id, measureType and the content
				listMeasureIdMeasureType_first_id.add(rootElementR7_first_id.getFirstChild().getFirstChild().getTextContent()); //mid
				listMeasureIdMeasureType_first_id.add(measureTypeList.get(i).toString()); //measureType
				listMeasureIdMeasureType_first_id.add(xmlGetRequest7_first_id);
				
				first_id_person_measure = true;
				countValid_measure_first_id++;
				
			}
		}
		
		
		if(countValid_measure_first_id < 0){
		
		//Loop for last_id
		for(int i = 0; i < measureTypeList.size(); i++){
			response7_last_id = service.path(path_request7_last_id+measureTypeList.get(i).toString()).request().accept(MediaType.APPLICATION_XML).get(Response.class);
			xmlGetRequest7_last_id = response7_last_id.readEntity(String.class);
			System.out.println("Valore xmlGetRequest7_last_id: " + xmlGetRequest7_last_id);
			Element rootElementR7_last_id = createXMLelement(xmlGetRequest7_last_id);
			NodeList measureList_last_id = rootElementR7_last_id.getChildNodes();
			
			if(measureList_last_id.getLength() > 0){
				
				//add the values of firstchild content measure_id, measureType and the content
				listMeasureIdMeasureType_last_id.add(rootElementR7_last_id.getFirstChild().getFirstChild().getTextContent()); //mid
				listMeasureIdMeasureType_last_id.add(measureTypeList.get(i).toString()); //measureType
				listMeasureIdMeasureType_last_id.add(xmlGetRequest7_last_id);
				
				last_id_person_measure = true;
				
				countValid_measure_last_id++;
			}
			
			
		}//end for respose7_last_id
		
	}
		if((countValid_measure_first_id >0) || (countValid_measure_first_id>0)){
			codeErrorR7 = "OK";
			displayClientResponse(7, Response.ok().build(), "GET", path_request6, xml, codeErrorR7);
			if(countValid_measure_first_id > 0){
				System.out.println(prettyFormat(listMeasureIdMeasureType_first_id.get(2)));
				}else{
					System.out.println(prettyFormat(listMeasureIdMeasureType_last_id.get(2)));
				}
		}else{
			codeErrorR7 = "ERROR";
			displayClientResponse(7, Response.ok().build(), "GET", path_request6, xml, codeErrorR7);
			System.out.println(prettyFormat(xmlGetMeasureTypes));
		}
		
		
		
		if(first_id_person_measure == true){
		measure_id = listMeasureIdMeasureType_first_id.get(0);
		measuretype = listMeasureIdMeasureType_first_id.get(1);
		first_person_history_exists = true;
		}else if(last_id_person_measure == true){
			measure_id = listMeasureIdMeasureType_last_id.get(0);
			measuretype = listMeasureIdMeasureType_last_id.get(1);
			last_person_history_exists = true;
		}else{
			first_person_history_exists = false;
			last_person_history_exists = false;
		}
		
		//System.out.println("measure_id: " + measure_id); 
		//System.out.println("measureType: " + measuretype);
		System.out.println("=============================================================================================================");
	}
	
	
	
	/**
	 * Step 3.7. Send R#6 (GET BASE_URL/person/{id}/{measureType}) for the first person you obtained at the beginning and the last person,
	 * and for each measure types from measure_types. 
	 * If no response has at least one measure - result is ERROR (no data at all) else result is OK. Store one measure_id and one measureType.
	 * 
	 * MediaType.APPLICATION_JSON
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request7_json() throws ParserConfigurationException, SAXException, IOException{
			path_request7_first_id = "person/"+first_person_id_json+"/";
			path_request7_last_id = "person/"+last_person_id_json+"/";
			
			//System.out.println(path_request7_first_id);
			//System.out.println(path_request7_last_id);
			
			//System.out.println("path_request7_first_id " + path_request7_first_id);
			

			listMeasureIdMeasureType_first_id_json = new ArrayList<>();
			listMeasureIdMeasureType_last_id_json = new ArrayList<>();
			
		
			int countValid_measure_first_id = 0;
			int countValid_measure_last_id = 0;
			
			//loop first_id
			for(int i = 0; i < measureTypeList_json.size(); i++){
				response7_first_id_json = service.path(path_request7_first_id+measureTypeList_json.get(i).toString()).request().accept(MediaType.APPLICATION_JSON).get(Response.class);
				jsonGetRequest7_first_id = response7_first_id_json.readEntity(String.class);
				//System.out.println("jsonGetRequest7_first_id: " + jsonGetRequest7_first_id);
				JSONArray jsonR7 = new JSONArray(jsonGetRequest7_first_id);
				
				
				if(jsonR7.length() > 0){
					
					JSONObject first_id_json = jsonR7.getJSONObject(0);
					
					//System.out.println("first_id_json: " + first_id_json );
					//System.out.println("First element of the object first_id_json: " + first_id_json.getInt("mid"));
					//System.out.println("Measure linked to this mid: " + measureTypeList_json.get(i).toString());
					
					//add the values of firstchild content measure_id, measureType and the content
					listMeasureIdMeasureType_first_id_json.add(Integer.toString(first_id_json.getInt("mid"))); //mid
					listMeasureIdMeasureType_first_id_json.add(measureTypeList_json.get(i).toString()); //measureType
					listMeasureIdMeasureType_first_id_json.add(first_id_json.toString());
					
					
					first_id_person_measure = true;
					countValid_measure_first_id++;
					
				}
				
			}
			
			
			if(countValid_measure_first_id < 0){
			
			//Loop for last_id
			for(int i = 0; i < measureTypeList.size(); i++){
				response7_last_id_json = service.path(path_request7_last_id+measureTypeList_json.get(i).toString()).request().accept(MediaType.APPLICATION_JSON).get(Response.class);
				jsonGetRequest7_last_id = response7_last_id_json.readEntity(String.class);
				System.out.println("Valore xmlGetRequest7_last_id: " + xmlGetRequest7_last_id);
				JSONArray jsonR7 = new JSONArray(jsonGetRequest7_last_id);
				
				if(jsonR7.length() > 0){
					
					JSONObject last_id_json = jsonR7.getJSONObject(0);
					
					//add the values of firstchild content measure_id, measureType and the content
					listMeasureIdMeasureType_first_id_json.add(Integer.toString(last_id_json.getInt("mid"))); //mid
					listMeasureIdMeasureType_first_id_json.add(measureTypeList_json.get(i).toString()); //measureType
					listMeasureIdMeasureType_first_id_json.add(last_id_json.toString());
					
					last_id_person_measure = true;
					
					countValid_measure_last_id++;
				}
				
				
			}//end for respose7_last_id
			
		}
			if((countValid_measure_first_id >0) || (countValid_measure_first_id>0)){
				codeErrorR7_json = "OK";
				displayClientResponse(7, Response.ok().build(), "GET", path_request6, json, codeErrorR7_json);
				if(countValid_measure_first_id > 0){
					System.out.println(prettyFormatJson(listMeasureIdMeasureType_first_id_json.get(2)));
					}else{
						System.out.println(prettyFormatJson(listMeasureIdMeasureType_last_id_json.get(2)));
					}
			}else{
				codeErrorR7 = "ERROR";
				displayClientResponse(7, Response.ok().build(), "GET", path_request6, json, codeErrorR7_json);
				//System.out.println(prettyFormat(xmlGetMeasureTypes));
			}
			
			
			
			if(first_id_person_measure == true){
			measure_id = listMeasureIdMeasureType_first_id_json.get(0);
			measuretype = listMeasureIdMeasureType_first_id_json.get(1);
			first_person_history_exists = true;
			}else if(last_id_person_measure == true){
				measure_id = listMeasureIdMeasureType_last_id_json.get(0);
				measuretype = listMeasureIdMeasureType_last_id_json.get(1);
				last_person_history_exists = true;
			}else{
				first_person_history_exists = false;
				last_person_history_exists = false;
			
				
			}
			
			//System.out.println("measure_id: " + measure_id); 
			//System.out.println("measureType: " + measuretype);
			System.out.println("=============================================================================================================");
			
			
		}
	
	

	/**
	 * Step 3.8. Send R#7 (GET BASE_URL/person/{id}/{measureType}/{mid}) 
	 * for the stored measure_id and measureType. If the response is 200, result is OK, else is ERROR.
	 * 
	 * MediaType.APPLICATION_XML
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request8() throws ParserConfigurationException, SAXException, IOException{
		
		//control if there is a valid id and measuretype for the first_person_id or last_person_id
		if(first_person_history_exists){
		path_request8 = base_url+first_person_id+"/"+measuretype+"/"+measure_id;
		//System.out.println("path_request8 ---> " + path_request8);
		response8 = service.path(path_request8).request().accept(MediaType.APPLICATION_XML).get(Response.class);
		xmlMeasureTypeMid = response8.readEntity(String.class);
		
		//Element rootElement= ClientCarlo.createXMLelement(xmlMeasureTypeMid);
		
		//System.out.println("xmlMeasureTypeMid ---> "+xmlMeasureTypeMid);
		
		
		if(response8.getStatus() == 200){
			codeErrorR8 = "OK";
			displayClientResponse(8, response8, "GET", path_request8, xml, codeErrorR8);
			System.out.println("Mid value: " + xmlMeasureTypeMid);
			System.out.println("=============================================================================================================");
		}else{
			codeErrorR8 = "ERROR";
			displayClientResponse(8, response8, "GET", path_request8, xml, codeErrorR8);
			System.out.println("=============================================================================================================");
		}
		
		}else if(last_person_history_exists){
			path_request8 = base_url+first_person_id+"/"+measuretype+"/"+measure_id;
			//System.out.println("path_request8 ---> " + path_request8);
			response8 = service.path(path_request8).request().accept(MediaType.APPLICATION_XML).get(Response.class);
			xmlMeasureTypeMid = response8.readEntity(String.class);
			
			//Element rootElement= ClientCarlo.createXMLelement(xmlMeasureTypeMid);
			
			//System.out.println("xmlMeasureTypeMid ---> "+xmlMeasureTypeMid);
			
			
			if(response8.getStatus() == 200){
				codeErrorR8 = "OK";
				displayClientResponse(8, response8, "GET", path_request8, xml, codeErrorR8);
				System.out.println("Mid value: " + xmlMeasureTypeMid);
				System.out.println("===========================================================");
			}else{
				codeErrorR8 = "ERROR";
				displayClientResponse(8, response8, "GET", path_request8, xml, codeErrorR8);
				System.out.println("===========================================================");
			}
			
		}else{
			System.out.println("First and last person id don't have a MeasureHistory");
			System.out.println("===========================================================");
		}
		
	}
	
	
	
	/**
	 * Step 3.8. Send R#7 (GET BASE_URL/person/{id}/{measureType}/{mid}) 
	 * for the stored measure_id and measureType. If the response is 200, result is OK, else is ERROR.
	 * 
	 * MediaType.APPLICATION_JSON
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request8_json() throws ParserConfigurationException, SAXException, IOException{

		//control if there is a valid id and measuretype for the first_person_id or last_person_id
		if(first_person_history_exists){
			path_request8 = base_url+first_person_id_json+"/"+measuretype+"/"+measure_id;
			//System.out.println("path_request8 ---> " + path_request8);
			response8_json = service.path(path_request8).request().accept(MediaType.APPLICATION_JSON).get(Response.class);
			jsonMeasureTypeMid = response8_json.readEntity(String.class);
			
			//System.out.println("Value of jsonMeasureTypeMid: " + jsonMeasureTypeMid);



			if(response8_json.getStatus() == 200){
				codeErrorR8_json = "OK";
				displayClientResponse(8, response8_json, "GET", path_request8, json, codeErrorR8_json);
				System.out.println("Mid value: " + jsonMeasureTypeMid);
				System.out.println("=============================================================================================================");
			}else{
				codeErrorR8_json = "ERROR";
				displayClientResponse(8, response8, "GET", path_request8, json, codeErrorR8_json);
				System.out.println("=============================================================================================================");
			}

		}else if(last_person_history_exists){
			path_request8 = base_url+first_person_id+"/"+measuretype+"/"+measure_id;
			//System.out.println("path_request8 ---> " + path_request8);
			response8_json = service.path(path_request8).request().accept(MediaType.APPLICATION_XML).get(Response.class);
			jsonMeasureTypeMid = response8_json.readEntity(String.class);

			//Element rootElement= ClientCarlo.createXMLelement(xmlMeasureTypeMid);

			//System.out.println("xmlMeasureTypeMid ---> "+xmlMeasureTypeMid);


			if(response8_json.getStatus() == 200){
				codeErrorR8_json = "OK";
				displayClientResponse(8, response8_json, "GET", path_request8, json, codeErrorR8_json);
				System.out.println("Mid value: " + jsonMeasureTypeMid);
				System.out.println("===========================================================");
			}else{
				codeErrorR8_json = "ERROR";
				displayClientResponse(8, response8_json, "GET", path_request8, json, codeErrorR8_json);
				System.out.println("===========================================================");
			}

		}else{
			System.out.println("First and last person id don't have a MeasureHistory");
			System.out.println("===========================================================");
		}

	}
		
	
	/**
	 * Step 3.9. Choose a measureType from measure_types and send the request 
	 * R#6 (GET BASE_URL/person/{first_person_id}/{measureType}) 
	 * and save count value (e.g. 5 measurements). 
	 * Then send R#8 (POST BASE_URL/person/{first_person_id}/{measureTypes}) 
	 * with the measurement specified below. 
	 * Follow up with another R#6 as the first to check the new count value. If it is 1 measure more - print OK, else print ERROR
	 * 
	 * MediaType.APPLICATION_XML
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request9() throws ParserConfigurationException, SAXException, IOException{
		
		//System.out.println("Valore measureTypeList.get(0)" + measureTypeList.get(0));
		
		path_request9 = base_url+first_person_id+"/"+measureTypeList.get(0);
		//System.out.println("path_request9: " + path_request9);
		
		response9 = service.path(path_request9).request().accept(MediaType.APPLICATION_XML).get(Response.class);
		xmlGetMeasureType_step9 = response9.readEntity(String.class);
		Element rootElementR9= ClientCarlo.createXMLelement(xmlGetMeasureType_step9);
		number_children_request9 = rootElementR9.getChildNodes().getLength();
		//System.out.println("number_children_request9: " + number_children_request9);
		
		
		String input_post = "<measure>"
				+ "<value>72</value>"
		        + "<created>2011-12-09</created>"
				+ "</measure>";
		
		
		
		
		
		response9_post = service.path(path_request9).request().accept(MediaType.APPLICATION_XML).post(Entity.entity(input_post, MediaType.APPLICATION_XML));
		xmlPostMeasureType_step9 = response9_post.readEntity(String.class);
		//System.out.println("response9_post.getStatus(): " + response9_post.getStatus());
		Element rootElementR10 = createXMLelement(xmlPostMeasureType_step9);
		
		
		
		response9_get = service.path(path_request9).request().accept(MediaType.APPLICATION_XML).get(Response.class);
		xmlGetMeasureType_step9_1 = response9_get.readEntity(String.class);
		//System.out.println(xmlGetMeasureType_step9_1);
		Element rootElementR9_1= ClientCarlo.createXMLelement(xmlGetMeasureType_step9_1);
		number_children_request9_after = rootElementR9_1.getChildNodes().getLength();
		//System.out.println("number_children_request9_after: " + number_children_request9_after);
		
		if(number_children_request9_after == (number_children_request9 + 1)){
			codeErrorR9 = "OK";
			displayClientResponse(9, response9_post, "POST", path_request9, xml, codeErrorR9);
			System.out.println(prettyFormat(xmlPostMeasureType_step9));
			System.out.println("=============================================================================================================");
		}else{
			codeErrorR9 = "ERROR";
			displayClientResponse(9, response9_post, "POST", path_request9, xml, codeErrorR9);
			System.out.println("=============================================================================================================");
		}
		
		
		
		// Store the GET BASE_URL/person/{id}/{measureType} to take the mid of the created healthistory
		mid_r9 = rootElementR9_1.getLastChild().getFirstChild().getTextContent();
		val_r9 = rootElementR9_1.getLastChild().getFirstChild().getNextSibling().getTextContent();
		
		
	}
	
	
	
	/**
	 * Step 3.9. Choose a measureType from measure_types and send the request 
	 * R#6 (GET BASE_URL/person/{first_person_id}/{measureType}) 
	 * and save count value (e.g. 5 measurements). 
	 * Then send R#8 (POST BASE_URL/person/{first_person_id}/{measureTypes}) 
	 * with the measurement specified below. 
	 * Follow up with another R#6 as the first to check the new count value. If it is 1 measure more - print OK, else print ERROR
	 * 
	 * MediaType.APPLICATION_JSON
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request9_json() throws ParserConfigurationException, SAXException, IOException{

		//System.out.println("Valore measureTypeList.get(0)" + measureTypeList.get(0));

		path_request9 = base_url+first_person_id_json+"/"+measureTypeList_json.get(0);
		//System.out.println("path_request9: " + path_request9);

		response9_json = service.path(path_request9).request().accept(MediaType.APPLICATION_JSON).get(Response.class);
		jsonGetMeasureType_step9 = response9_json.readEntity(String.class);
		//Element rootElementR9= ClientCarlo.createXMLelement(jsonGetMeasureType_step9);
		
		//System.out.println("QUI !!! jsonGetMeasureType_step9: " + jsonGetMeasureType_step9);
		JSONArray jsonR9 = new JSONArray(jsonGetMeasureType_step9);
		//System.out.println("jsonR9 come array: " + jsonR9);
		//System.out.println("Lunghezza array R9: " + jsonR9.length());
		
		
		number_children_request9_json = jsonR9.length();
		//System.out.println("number_children_request9: " + number_children_request9);


//		String input_post = "<measure>"
//				+ "<value>72</value>"
//				+ "<created>2011-12-09</created>"
//				+ "</measure>";
		
		
		JSONObject obj = new JSONObject();
		obj.put("value", "72");
		obj.put("created", "2011-12-09");
		String input_post = obj.toString();
		
		

		response9_post_json = service.path(path_request9).request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(input_post, MediaType.APPLICATION_JSON));
		jsonPostMeasureType_step9 = response9_post_json.readEntity(String.class);
		//System.out.println("response9_post.getStatus(): " + response9_post.getStatus());
		//Element rootElementR10 = createXMLelement(jsonPostMeasureType_step9);
		//System.out.println("jsonPostMeasureType_step9: " + jsonPostMeasureType_step9);



		response9_get_json = service.path(path_request9).request().accept(MediaType.APPLICATION_JSON).get(Response.class);
		jsonGetMeasureType_step9_1 = response9_get_json.readEntity(String.class);
		//System.out.println(jsonGetMeasureType_step9_1);
		
		JSONArray jsonR9_1 = new JSONArray(jsonGetMeasureType_step9_1);
		 
		//System.out.println("jsonR9_1 come array: " + jsonR9_1);
		//System.out.println("Lunghezza array R9_1: " + jsonR9_1.length());
		
		
		number_children_request9_after_json = jsonR9_1.length();
		
		JSONObject jsonGetAll = jsonR9_1.getJSONObject(number_children_request9_after_json - 1);
		//System.out.println("Ultimo elemento dell'array: " + jsonGetAll);
		
		//System.out.println("Mid: " + jsonGetAll.get("mid").toString());
		//System.out.println("value: " + jsonGetAll.get("value").toString());
		
		mid_r9_json = jsonGetAll.get("mid").toString(); 
		val_r9_json = jsonGetAll.get("value").toString();
		
		
		

		if(number_children_request9_after_json == (number_children_request9_json + 1)){
			codeErrorR9_json = "OK";
			displayClientResponse(9, response9_post_json, "POST", path_request9, json, codeErrorR9_json);
			System.out.println(prettyFormatJson(jsonPostMeasureType_step9));
			System.out.println("=============================================================================================================");
		}else{
			codeErrorR9_json = "ERROR";
			displayClientResponse(9, response9_post_json, "POST", path_request9, json, codeErrorR9_json);
			System.out.println("=============================================================================================================");
		}



	}

	
	
	
	/**
	 * Step 3.10. Send R#10 using the {mid} or the measure created in the previous step and updating the value at will. 
	 * Follow up with at R#6 to check that the value was updated. If it was, result is OK, else is ERROR.
	 * 
	 * MediaType.APPLICATION_XML
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request10() throws ParserConfigurationException, SAXException, IOException{
		
		path_request10 = base_url+first_person_id+"/"+measureTypeList.get(0)+"/"+mid_r9;
		
		
		String input_put = "<measure>"
				+ "<value>90</value>"
		        + "<created>2011-12-09</created>"
				+ "</measure>";
		
		response10_put = service.path(path_request10).request().accept(MediaType.APPLICATION_XML).put(Entity.entity(input_put, MediaType.APPLICATION_XML));
		
		response10_get = service.path(path_request9).request().accept(MediaType.APPLICATION_XML).get(Response.class);
		xmlGetRequest10 = response10_get.readEntity(String.class);
		//System.out.println("Response10_get: " + response10_get);
		Element rootElementR10_get= ClientCarlo.createXMLelement(xmlGetRequest10);
		
		
		
		mid_r10 = rootElementR10_get.getLastChild().getFirstChild().getTextContent();
		val_r10 = rootElementR10_get.getLastChild().getFirstChild().getNextSibling().getTextContent();
	
		
		if( val_r10 != val_r9 ){
			codeErrorR10 = "OK";
			displayClientResponse(10, response10_put, "PUT", path_request10, xml, codeErrorR10);
			//Print the measureHistory and then the last childnode is the one that is updated
			System.out.println(prettyFormat(xmlGetRequest10));
			System.out.println("=============================================================================================================");
		}else{
			codeErrorR9 = "ERROR";
			displayClientResponse(10, response10_put, "PUT", path_request10, xml, codeErrorR10);
			System.out.println("=============================================================================================================");
		}
		
	}
	
	
	
	
	/**
	 * Step 3.10. Send R#10 using the {mid} or the measure created in the previous step and updating the value at will. 
	 * Follow up with at R#6 to check that the value was updated. If it was, result is OK, else is ERROR.
	 * 
	 * MediaType.APPLICATION_JSON
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request10_json() throws ParserConfigurationException, SAXException, IOException{
		
		path_request10 = base_url+first_person_id_json+"/"+measureTypeList_json.get(0)+"/"+mid_r9_json;
		
	
		JSONObject obj = new JSONObject();
		obj.put("value", "90");
		obj.put("created", "2011-12-09");
		String input_put = obj.toString();
		
//		String input_put = "<measure>"
//				+ "<value>90</value>"
//		        + "<created>2011-12-09</created>"
//				+ "</measure>";
		
		response10_put_json = service.path(path_request10).request().accept(MediaType.APPLICATION_JSON).put(Entity.entity(input_put, MediaType.APPLICATION_JSON));
		
		response10_get_json = service.path(path_request9).request().accept(MediaType.APPLICATION_JSON).get(Response.class);
		jsonGetRequest10 = response10_get_json.readEntity(String.class);
		
		//System.out.println("jsonGetRequest10: " + jsonGetRequest10);
		
		//System.out.println("Response10_get_json: " + jsonGetRequest10);
		
		JSONArray jsonR10 = new JSONArray(jsonGetRequest10);
		 
		
		
		number_children_request9_after_json = jsonR10.length();
		
		JSONObject jsonGetAll = jsonR10.getJSONObject(number_children_request9_after_json - 1);
		
		//String jsonGetAll = jsonR10.get(number_children_request9_after_json - 1).toString();
		
		
		mid_r10_json = jsonGetAll.get("mid").toString(); 
		val_r10_json = jsonGetAll.get("value").toString();
		
		
		if( val_r10_json != val_r9_json ){
			codeErrorR10_json = "OK";
			displayClientResponse(10, response10_put_json, "PUT", path_request10, json, codeErrorR10_json);
			//Print the measureHistory and then the last childnode is the one that is updated
			//System.out.println(jsonGetAll);
			System.out.println(prettyFormatJson(jsonGetAll.toString()));
			System.out.println("=============================================================================================================");
		}else{
			codeErrorR9_json = "ERROR";
			displayClientResponse(10, response10_put_json, "PUT", path_request10, json, codeErrorR10_json);
			System.out.println("=============================================================================================================");
		}
		
	}
	
	/**
	 * Step 3.11. Send R#11 for a measureType, before and after dates given by your fellow student (who implemnted the server). 
	 * If status is 200 and there is at least one measure in the body, result is OK, else is ERROR
	 * 
	 * MediaType.APPLICATION_XML
	 * 
	 * @param befor is the upper bound limit date
	 * @param aft is the lower bound limit date
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request11(String befor, String aft) throws ParserConfigurationException, SAXException, IOException{
		
		//path=person/1/weight?before=2015-12-09&after=2015-07-10
		this.before = befor;
		this.after = aft;
		
		path_request11_format = base_url+first_person_id+"/"+measureTypeList.get(0)+"?"+"before="+before+"&after="+after;
		
		path_request11 = base_url+first_person_id+"/"+measureTypeList.get(0);
		
		response11 = service.path(path_request11).queryParam("before", before).queryParam("after", after).request().accept(MediaType.APPLICATION_XML).get(Response.class);
		xmlGetRequest11 = response11.readEntity(String.class);
		Element rootElement_R11 = ClientCarlo.createXMLelement(xmlGetRequest11);
		
		//System.out.println("Response request11: " + response11.toString());
		NodeList r11 = rootElement_R11.getChildNodes();
		//System.out.println("xmlGetRequest11: " + xmlGetRequest11);
		
		//System.out.println(xmlGetRequest11);
		
		//System.out.println("rootElement_R11.getChildNodes().getLength(): " + rootElement_R11.getChildNodes().getLength());
		
		if( (response11.getStatus()) == 200 && (rootElement_R11.getChildNodes().getLength()>0) ){
			codeErrorR11 = "OK";
			displayClientResponse(11, response11, "GET", path_request11_format, xml, codeErrorR11);
			System.out.println(prettyFormat(xmlGetRequest11));
		}else{
			codeErrorR11 = "ERROR";
			displayClientResponse(11, response11, "GET", path_request11_format, xml, codeErrorR11);
			System.out.println(prettyFormat(xmlGetRequest11));
		}
	}
	
	
	
	/**
	 * Step 3.11. Send R#11 for a measureType, before and after dates given by your fellow student (who implemnted the server). 
	 * If status is 200 and there is at least one measure in the body, result is OK, else is ERROR
	 * 
	 * MediaType.APPLICATION_JSON
	 * 
	 * @param befor is the upper bound limit date
	 * @param aft is the lower bound limit date
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request11_json(String befor, String aft) throws ParserConfigurationException, SAXException, IOException{

		//path=person/1/weight?before=2015-12-09&after=2015-07-10
		this.before = befor;
		this.after = aft;

		String path_request11_format_json = base_url+first_person_id_json+"/"+measureTypeList_json.get(0)+"?"+"before="+before+"&after="+after;

		String path_request11_json = base_url+first_person_id_json+"/"+measureTypeList_json.get(0);

		Response r11_json = service.path(path_request11_json).queryParam("before", before).queryParam("after", after).request().accept(MediaType.APPLICATION_JSON).get(Response.class);
		String jsonGetRequest11 = r11_json.readEntity(String.class);
//		Element rootElement_R11 = ClientCarlo.createXMLelement(xmlGetRequest11);
//		NodeList r11 = rootElement_R11.getChildNodes();
		
		//System.out.println("Output jsonGetRequest11" + jsonGetRequest11);
		
		JSONArray arrayR11 = new JSONArray(jsonGetRequest11);
		//System.out.println("arrayR11: " + arrayR11 );
		int arrayR11Lenght = arrayR11.length(); 
		String ar = arrayR11.toString(4);
		
		if( ( r11_json.getStatus() ) == 200 && ( arrayR11Lenght > 0 ) ){
			codeErrorR11_json = "OK";
			displayClientResponse(11, r11_json, "GET", path_request11_format_json, json, codeErrorR11_json);
			System.out.println(ar);
		}else{
			codeErrorR11_json = "ERROR";
			displayClientResponse(11, r11_json, "GET", path_request11_format_json, json, codeErrorR11_json);
			System.out.println(ar);
		}
	}
	
	
	
	
	// STEP 12
	//
	// Send R#12 GET /person?measureType={measureType}&max={max}&min={min} retrieves people whose {measureType} (e.g., weight) value is in the [{min},{max}] range 
	// (if only one for the query params is provided, use only that)
	//
	// using the same parameters as the preivious steps.
	// If status is 200 and there is at least one person in the body, result is OK, else is ERROR
	// 
	
	/**
	 * Step 3.12. Send R#12 using the same parameters as the preivious steps. 
	 * If status is 200 and there is at least one person in the body, result is OK, else is ERROR
	 * 
	 * MediaType.APPLICATION_XML
	 * 
	 * @param mx is the upper bound limit of the measure value
	 * @param mi is the lower bound limit of the measure value
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request12(String mx, String mi) throws ParserConfigurationException, SAXException, IOException{
		
		//path=person/1/weight?max=100&min=40 SBAGLIATO
		
		// /person?measureType={measureType}&max={max}&min={min} CORRETTO ! 
		this.min = mi;
		this.max = mx;
		
		String path_request12 = "person";
		String measureTypeRequest12 = measureTypeList.get(0);
		path_request12_format = base_url+first_person_id+"?"+measureTypeList.get(0)+"?"+"max="+max+"&min="+min;
		
		response12 = service.path(path_request12).queryParam("measureType",measureTypeRequest12).queryParam("max", max).queryParam("min", min).request().accept(MediaType.APPLICATION_XML).get(Response.class);
		//System.out.println(response12);
		
		xmlGetRequest12 = response12.readEntity(String.class);
		Element rootElement_R12 = ClientCarlo.createXMLelement(xmlGetRequest12);
		NodeList r12 = rootElement_R12.getChildNodes();


		if( (response12.getStatus()) == 200 && (rootElement_R12.getChildNodes().getLength()>0) ){
			codeErrorR12 = "OK";
			displayClientResponse(12, response12, "GET", path_request12_format, xml, codeErrorR12);
			System.out.println(prettyFormat(xmlGetRequest12));
		}else{
			codeErrorR12 = "ERROR";
			displayClientResponse(12, response12, "GET", path_request12_format, xml, codeErrorR12);
			System.out.println(prettyFormat(xmlGetRequest12));
		}

		
		
	}
	
	
	// STEP 12
	//
	// Send R#12 GET /person?measureType={measureType}&max={max}&min={min} retrieves people whose {measureType} (e.g., weight) value is in the [{min},{max}] range 
	// (if only one for the query params is provided, use only that)
	//
	// using the same parameters as the preivious steps.
	// If status is 200 and there is at least one person in the body, result is OK, else is ERROR
	// 
	
	/**
	 * Step 3.12. Send R#12 using the same parameters as the preivious steps. 
	 * If status is 200 and there is at least one person in the body, result is OK, else is ERROR
	 * 
	 * MediaType.APPLICATION_JSON
	 * 
	 * @param mx is the upper bound limit of the measure value
	 * @param mi is the lower bound limit of the measure value
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void request12_json(String mx, String mi) throws ParserConfigurationException, SAXException, IOException{

		//path=person/1/weight?max=100&min=40 SBAGLIATO
		this.min = mi;
		this.max = mx;


	//	/person?measureType={measureType}&max={max}&min={min} 
		
		
		String path_request12_json = "person";
		String measureTypeRequest12 = measureTypeList_json.get(0);
		
		path_request12_format_json = path_request12_json+"?measureType="+measureTypeList_json.get(0)+"&"+"max="+max+"&min="+min;

	

		response12_json = service.path(path_request12_json).queryParam("measureType",measureTypeRequest12).queryParam("max", max).queryParam("min", min).request().accept(MediaType.APPLICATION_JSON).get(Response.class);
		//System.out.println("Response12_json: " + response12_json);
		
		jsonGetRequest12 = response12_json.readEntity(String.class);
		

		
		JSONObject r12 = new JSONObject(jsonGetRequest12);
		//System.out.println("r12 lenght: " + r12.length());


		if( (response12_json.getStatus()) == 200 && (r12.length() > 0) ){
			codeErrorR12_json = "OK";
			displayClientResponse(12, response12_json, "GET", path_request12_format_json, json, codeErrorR12_json);
			System.out.println(prettyFormatJson(jsonGetRequest12));
		}else{
			codeErrorR12_json = "ERROR";
			displayClientResponse(12, response12_json, "GET", path_request12_format_json, json, codeErrorR12_json);
			System.out.println(prettyFormatJson(jsonGetRequest12));
		}



	}

	/**
	 * This method is used to call the help in my server
	 */
	public void help(){
		String help_path = "/help";
		
		Response response_help = service.path(help_path).request().accept(MediaType.TEXT_PLAIN).get(Response.class);
		String help = response_help.readEntity(String.class);
		
		System.out.println(help);
		
	}
	
	
	public String getId_new_person_request4() {
		return id_new_person_request4;
	}




	public void setId_new_person_request4(String id_new_person_request4) {
		this.id_new_person_request4 = id_new_person_request4;
	}

	
	/**
	 * This method is used to create a root element
	 * 
	 * @param query the query string 
	 * @return rootElement
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Element createXMLelement(String query) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(query)));
		Element rootElement = document.getDocumentElement();
		return rootElement;
	}
	
	
	
	/**
	 * This method is used to format in pretty way the xml
	 * 
	 * @param input the string to format
	 * @param indent the value of indentation code
	 * @return a string pretty well formatted in xml style
	 */
	public static String prettyFormat(String input, int indent) {
	    try {
	        Source xmlInput = new StreamSource(new StringReader(input));
	        StringWriter stringWriter = new StringWriter();
	        StreamResult xmlOutput = new StreamResult(stringWriter);
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setAttribute("indent-number", indent);
	        Transformer transformer = transformerFactory.newTransformer(); 
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        transformer.transform(xmlInput, xmlOutput);
	        return xmlOutput.getWriter().toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e); // simple exception handling, please review it
	    }
	}

	/**
	 * This method is used to format in pretty way the xml
	 * @param input
	 * @return
	 */
	public static String prettyFormat(String input) {
	    return prettyFormat(input, 2);
	}
	
	/**
	 * This method is used to display the JSON in pretty format
	 * @param jsonString the string to indent in json style
	 * @return string formatted in right way for json
	 */
	private static String prettyFormatJson(String jsonString) {
		JSONObject json = new JSONObject(jsonString); // Convert text to object
		return json.toString(4); // Print it with specified indentation	
	}
	
	
	/**
	 * This method is a template used to display the 
	 * request in the format requested by the assignment 
	 * 
	 * Example:
	 * Display the output of request
	 * Request #7: GET /person/5/weight/899 Accept: APPLICATION/XML Content-Type: APPLICATION/XML  
	 * => Result: OK
	 * => HTTP Status: 200
	 * 
	 * 
	 * @param request the number of the request
	 * @param res is the response variable from which is taken the status
	 * @param typerequest the type of request e.i. GET
	 * @param path the path of the request
	 * @param accept the content type accepted
	 * @param code_result the result of the performed request
	 */
	private static void displayClientResponse(int request, Response res, String typerequest ,String path ,String accept, String code_result){
		int status = res.getStatus();
		
		System.out.println("=============================================================================================================");
		System.out.println("Request #"+request+": "+typerequest+" "+ path + " Accept: " + accept + " Content-Type: " + accept );
		System.out.println("==> Result: [" + code_result + "]");
		System.out.println("==> HTTP Status: [" + status + "]");
		System.out.println("[BODY]");
		//System.out.println("==========================================================");
		
		
		System.out.println();
	} 
	
	
	/**
	 * This method is used to create URI used to connect the cliet to the server
	 * passed as String in the param
	 * 
	 * @param conn the connection string of the server
	 * @return 
	 */
	private static URI getBaseURI(String conn) {
		return UriBuilder.fromUri(conn).build(); //my server
		//return UriBuilder.fromUri("https://peaceful-hamlet-5616.herokuapp.com/sdelab").build(); //Andrea
	}
	
	
	
	
	// Get and set of variables
	
	public WebTarget getService() {
		return service;
	}




	public void setService(WebTarget service) {
		this.service = service;
	}




	public Response getResponse1() {
		return response1;
	}




	public void setResponse1(Response response1) {
		this.response1 = response1;
	}




	public Response getResponse2() {
		return response2;
	}




	public void setResponse2(Response response2) {
		this.response2 = response2;
	}




	public Response getResponse3() {
		return response3;
	}




	public void setResponse3(Response response3) {
		this.response3 = response3;
	}




	public Response getResponse4() {
		return response4;
	}




	public void setResponse4(Response response4) {
		this.response4 = response4;
	}




	public Response getResponse5() {
		return response5;
	}




	public void setResponse5(Response response5) {
		this.response5 = response5;
	}




	public Response getResponse6() {
		return response6;
	}




	public void setResponse6(Response response6) {
		this.response6 = response6;
	}




	public Response getResponse7() {
		return response7;
	}




	public void setResponse7(Response response7) {
		this.response7 = response7;
	}




	public Response getResponse8() {
		return response8;
	}




	public void setResponse8(Response response8) {
		this.response8 = response8;
	}




	public Response getResponse9() {
		return response9;
	}




	public void setResponse9(Response response9) {
		this.response9 = response9;
	}




	public String getXmlGetAll() {
		return xmlGetAll;
	}




	public void setXmlGetAll(String xmlGetAll) {
		this.xmlGetAll = xmlGetAll;
	}




	public String getCodeErrorR1() {
		return codeErrorR1;
	}




	public void setCodeErrorR1(String codeErrorR1) {
		this.codeErrorR1 = codeErrorR1;
	}




	public String getCodeErrorR2() {
		return codeErrorR2;
	}




	public void setCodeErrorR2(String codeErrorR2) {
		this.codeErrorR2 = codeErrorR2;
	}




	public String getCodeErrorR3() {
		return codeErrorR3;
	}




	public void setCodeErrorR3(String codeErrorR3) {
		this.codeErrorR3 = codeErrorR3;
	}




	public String getFirst_person_id() {
		return first_person_id;
	}




	public void setFirst_person_id(String first_person_id) {
		this.first_person_id = first_person_id;
	}




	public String getLast_person_id() {
		return last_person_id;
	}




	public void setLast_person_id(String last_person_id) {
		this.last_person_id = last_person_id;
	}


	public ArrayList<String> getMeasureTypeList() {
		return measureTypeList;
	}




	public void setMeasureTypeList(ArrayList<String> measureTypeList) {
		this.measureTypeList = measureTypeList;
	}
	
	
}



	

