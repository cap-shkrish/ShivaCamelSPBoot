package SpringBootDemo.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SpringBootDemo.Beans.Student;
import SpringBootDemo.Services.DataOperationsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation("Returns the Hello World Page")
@RestController
@RequestMapping("/students")
public class StudentServiceController
{	
	@Autowired
	private DataOperationsService dop;   

	//http://localhost:9080/students?id=10&name=Shiva+Krishnan&age=77
	@ApiOperation("Returns All Students")
	@ApiResponses (value= {
			@ApiResponse(code=200, message="OK", response=List.class)
			})	
	@GetMapping( produces="application/json")
	public List <Student> GetStudents(@RequestParam Map<String, String> queryParams) throws IOException
										
	{
		String id = queryParams.get("id");
		String name = queryParams.get("name");
		String age = queryParams.get("age");
		System.out.println(" ID: " + age + " Name: " + name + " Age: " + age);
		List <Student>  st = new ArrayList <Student>();
		
		if(id != null && !id.isEmpty())
		{
			 Student s = dop.GetById(Integer.valueOf(id));
			 st.add(s);
		}
		else if(name != null  && !name.isEmpty())
		{			
			if(age != null && !age.isEmpty())			 
				st = dop.GetRecordByNameAge(name, Integer.valueOf(age));
			else
				st = dop.GetRecordByName(name);
		}
		else if(age != null && !age.isEmpty())			 
			st = dop.GetRecordByAge(Integer.valueOf(age));
		else
			st = dop.GetAll();
		
		return st;
	}  	
	
	
	@ApiOperation("Updates Student given an ID as a param")
	@ApiResponses (value= {
			@ApiResponse(code=200, message="OK", response=List.class)
			})
	@PutMapping(value="", consumes="application/json", produces = "application/json")
	public List <Student> UpdateStd(@RequestBody Student student, @RequestParam("id") String id) throws IOException
	{
		System.out.println("Inside  UpdateStudent ");
		student.setId(Integer.valueOf(id));
		Student st = dop.Update(student);
		ArrayList<Student> al = new ArrayList<Student>();
		if(st != null)
			al.add(st);

		return al;
	}

	@ApiOperation("Deletes a Student given an ID as a param")
	@ApiResponses (value= {
			@ApiResponse(code=200, message="OK", response=ResponseEntity.class)
			})
	@DeleteMapping(value="")
	public ResponseEntity<String> DeleteStd(@RequestParam("id") String id) throws IOException
	{
		System.out.println("Inside  DeleteStd ");
		dop.Delete(Integer.valueOf(id));
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}

	@ApiOperation("Adds a Student given a Student json object")
	@ApiResponses (value= {
			@ApiResponse(code=200, message="OK", response=List.class)
			})
	@PostMapping( consumes="application/json", produces = "application/json")
	public List <Student> CreateStudent(@RequestBody Student student) throws IOException
	{
		System.out.println("Inside  CreateStudent ");
		Student st = dop.Insert(student);
		ArrayList<Student> al = new ArrayList<Student>();
		if(st != null)
			al.add(st);

		return al;
	}

	@ApiOperation("Gets a Student given a Student ID")
	@ApiResponses (value= {
			@ApiResponse(code=200, message="OK", response=List.class)
			})
	@GetMapping(value="/{id}", produces="application/json")
	public List <Student> GetStudentById(@PathVariable("id") int id) throws IOException
	{
		Student st = dop.GetById(id);
		ArrayList<Student> al = new ArrayList<Student>();
		if(st != null)
			al.add(st);
		return al;
	}  

	@ApiOperation("Updates a Student given a Student ID")
	@ApiResponses (value= {
			@ApiResponse(code=200, message="OK", response=List.class)
			})
	@PutMapping(value="/{id}", consumes="application/json", produces = "application/json")
	public List <Student> UpdateStudent(@RequestBody Student student, @PathVariable("id") int id) throws IOException
	{
		System.out.println("Inside  UpdateStudent ");
		student.setId(id);
		Student st = dop.Update(student);
		ArrayList<Student> al = new ArrayList<Student>();
		if(st != null)
			al.add(st);

		return al;
	}
	
	@ApiOperation("Deletes a Student given a Student ID")
	@ApiResponses (value= {
			@ApiResponse(code=200, message="OK", response=ResponseEntity.class)
			})
	@DeleteMapping(value="/{id}")
	public ResponseEntity<String> DeleteStudent(@PathVariable("id") int id) throws IOException
	{
		System.out.println("Inside  DeleteStudent ");
		dop.Delete(id);
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	
	@ApiOperation("Returns the total number of students")
	@ApiResponses (value= {
			@ApiResponse(code=200, message="OK", response=Map.class)
			})
	@GetMapping(value="/count", produces="application/json")
	public Map<String, Integer> GetStudentCount() throws IOException
	{
		int count = dop.GetRowCount();
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", count);		
		return map;
	}  

	//Call Stored Procedure
	@ApiOperation("Calls a stored procedure to get students by age")
	@ApiResponses (value= {
			@ApiResponse(code=200, message="OK", response=List.class)
			})
	@GetMapping(value="/call/{age}", produces="application/json")
	public List<Student> CallStoredProc(@PathVariable("age") int age) throws IOException
	{
		List<Student> st = dop.CallStoredProcedure(age);
		return st;
	}  

	//Call Stored Procedure
	@ApiOperation("Calls a stored procedure to get students by name and age")
	@ApiResponses (value= {
			@ApiResponse(code=200, message="OK", response=List.class)
			})
	@GetMapping(value="/call/{name}/{age}", produces="application/json")
	public List<Student> CallStoredProcNameAge(@PathVariable("name") String name, @PathVariable("age") int age) throws IOException
	{
		List<Student> st = dop.CallStoredProcedureNameAge(name, age);
		return st;
	}  


}