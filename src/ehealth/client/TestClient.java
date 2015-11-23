package ehealth.client;



import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;

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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mongodb.util.JSON;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;



public class TestClient {

	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		
		String connAndrea = "https://peaceful-hamlet-5616.herokuapp.com/sdelab";
		String connCarlo = "https://enigmatic-sierra-2066.herokuapp.com/sdelab";
        
		ClientCarlo carlo = new ClientCarlo(connCarlo);
		ClientCarlo andrea = new ClientCarlo(connAndrea);
		System.out.println(args[0]);
		System.out.println(args[1]);
		
		int argCount = args.length;
		if (argCount > 0){
			String method = args[0];
			//Execution of the method PrintAllPeople
			if (method.equals("xml")) {
				String connxml = args[1];
				if(connxml.equals("https://enigmatic-sierra-2066.herokuapp.com/sdelab")){
					carlo.request1();
			        carlo.request2();
			        carlo.request3("Smith");
			        carlo.request4();
			        carlo.request5();
			        carlo.request6();
			        carlo.request7();
			        carlo.request8();
			        carlo.request9();
			        carlo.request10();
			        
			        //path=person/1/weight?before=2015-12-09&after=2015-07-10
			        carlo.request11("2015-12-09", "2010-07-10");
			        carlo.request12("100", "20");
			        
				}else if(connxml.equals("https://peaceful-hamlet-5616.herokuapp.com/sdelab")){
					andrea.request1();
			        andrea.request2();
			        andrea.request3("Jack");
			        andrea.request4();
			        andrea.request5();
			        andrea.request6();
			        andrea.request7();
			        andrea.request8();
			        andrea.request9();
			        andrea.request10();
			        
			        //path=person/1/weight?before=2015-12-09&after=2015-07-10
			        andrea.request11("2015-12-09", "2010-07-10");
			        andrea.request12("100", "20"); 
			        
			        
				}

			}else if (method.equals("json")){
				String connjson = args[1];
				if(connjson.equals("https://enigmatic-sierra-2066.herokuapp.com/sdelab")){
					carlo.request1_json();
			        carlo.request2_json();
			        carlo.request3_json("Joe");
			        carlo.request4_json();
			        carlo.request5_json();
			        carlo.request6_json();
			        carlo.request7_json();
			        carlo.request8_json();
			        carlo.request9_json();
			        carlo.request10_json();
			        carlo.request11_json("2015-12-09", "2010-07-10");
			        carlo.request12_json("100", "20");
				}else if(connjson.equals("https://peaceful-hamlet-5616.herokuapp.com/sdelab")){
					andrea.request1_json();
			        andrea.request2_json();
			        andrea.request3_json("Joe");
			        andrea.request4_json();
			        andrea.request5_json();
			        andrea.request6_json();
			        andrea.request7_json();
			        andrea.request8_json();
			        andrea.request9_json();
			        andrea.request10_json();
			        andrea.request11_json("2015-12-09", "2010-07-10");
			        andrea.request12_json("100", "20");
				}
			}
		}
	}
	
	
}
	
	