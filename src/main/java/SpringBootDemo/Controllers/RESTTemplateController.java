package SpringBootDemo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringBootDemo.Beans.Student;
import SpringBootDemo.RESTTemplateDemo.RESTTemplateService;


@RestController
public class RESTTemplateController {
	
    @Autowired
    private RESTTemplateService restTemplateService;
   
	@GetMapping("/restTempGet")
	public List<Student> getStudents()
	{
		ResponseEntity<List> result = restTemplateService.getStudents();
        return result.getBody();
    }

	@GetMapping("/restTempUpdate")
	public void updateStudents()
	{
	    Student student = new Student(32, "RESTTEMPLATE", "222-333-9999", "update@resttemplate.com" ); 
		restTemplateService.updateStudent(student, 110);

    }


	
}
