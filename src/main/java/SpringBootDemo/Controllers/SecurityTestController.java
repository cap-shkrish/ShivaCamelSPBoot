package SpringBootDemo.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import SpringBootDemo.Beans.Student;
import SpringBootDemo.Services.DataOperationsService;

@CrossOrigin
@RestController
@RequestMapping("/test")
public class SecurityTestController
{	
	@Autowired
	private DataOperationsService dop;   
	
	
	@PostMapping(value="/post", produces="application/json")
	public String testPost() throws IOException
	{
  			System.out.println("Inside  Test Post ");    	
  		     return "[{\"name\" : \"Test Post\"" + "}]";
	}

	
	@GetMapping(value="/get", produces="application/json")
    public String GetString() 
    {
        System.out.println(" Inside getString \n");      
        return "[{\"name\" : \"Simply Red\""
        		+ "}]";       
    }   

	
	@GetMapping(value="/count", produces="application/json")
    public String GetStudentCount() throws IOException
    {
        int st = dop.GetRowCount();
        
        return "[{\"count\" : " + st +"}]";
    }  
	
	   
	@PostMapping(value="/create", consumes="application/json", produces = "application/json")
	public List <Student> CreateStudent(@RequestBody Student student) throws IOException
	{
   				System.out.println("Inside  CreateStudent ");
	    		Student st = dop.Insert(student);

	    		ArrayList<Student> al = new ArrayList<Student>();
	    		if(st != null)
	    			al.add(st);
	    		
	    		return al;
	    		
	}

	   

    
}