package SpringBootDemo.RESTTemplateDemo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import SpringBootDemo.Beans.Student;

@Service
public class RESTTemplateService {
	
	private  String uri = "http://localhost:8080/students";
	//Call external web services and abstract the implementation from caller
	@Autowired RestTemplate restTemplate;
	
	public  ResponseEntity<List> getStudents()
	{		     
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		     
		    ResponseEntity<List> result = restTemplate.exchange(uri, HttpMethod.GET, entity, List.class);
		     
		    System.out.println(result.getBody());
		    
		    return result;
		
	}
	
	public  void updateStudent(Student student, int id)
	{
	    uri = uri + "/{id}";	    
	    System.out.println("\nURI: " + uri);	    
	    Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("id", id); 	     
	    restTemplate.put(uri, student, params);	    
	}
	
	public  void createStudent(Student student)
	
	{	 
	    //Student student = new Student(80, "GamaRation", "601-222-1111", "gamma@abaqus.com" ); 	    
	    List<Student> students = restTemplate.postForObject( uri, student, List.class);		    
	    System.out.println(students.toString());
	}	
	
	public  void deleteStudent(int id)
	{
	    uri = uri + "/{id}";
	     
	    Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("id", id);
	    restTemplate.delete(uri, params);
	}
	
	public  ResponseEntity<List> getStudentByName(String param)
	{
	    Map<String, String> params = new HashMap<String, String>();	    
	    
	    if(param.equalsIgnoreCase("name"))
	    {
	    	params.put("name", "GamaRadiation");
	    	uri = uri +"?name={name}&";	    	
	    }
	    else if(param.equalsIgnoreCase("age"))
	    {
	    	params.put("age", "100");
	    	uri = uri +"?age={age}&";
	    }
	    else if(param.equalsIgnoreCase("id"))
	    {
	    	params.put("id", "104");
	    	uri = uri +"?id={id}&";
	    }
	    else
	    {
	    	params.put("id", "");  
	    	params.put("name", "Uppumara");
	    	params.put("age", "80");
	    	uri = uri +"?name={name}&age={age}&";	    	
	    }
	    ResponseEntity<List> result = restTemplate.getForEntity(uri, List.class, params);
	    System.out.println(result.getBody());
	    
	    return result;
	}
}
