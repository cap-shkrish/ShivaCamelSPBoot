package SpringBootDemo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringBootDemo.Beans.Student;
import SpringBootDemo.JDBCTemplateDemo.StudentDAOImpl;

@RestController
public class JDBCController {

	@Autowired StudentDAOImpl studentDAOImpl;
	
	@Transactional
	@GetMapping(value = "/jdbc")
	public Student jdbc() {
		
	      System.out.println("------Records Creation--------" );
	      studentDAOImpl.create("Zara", 11, "312-448-3367", "testZara@spring.com");
	      studentDAOImpl.create("Nuha", 2, "312-448-3367", "testNuha@spring.com");
	      studentDAOImpl.create("Ayan", 15, "312-448-3367", "testAyan@spring.com");

	      System.out.println("------Listing Multiple Records--------" );
	      List<Student> students = studentDAOImpl.listStudents();
	      
	      for (Student record : students) {
	         System.out.print("ID : " + record.getId() );
	         System.out.print(", Name : " + record.getName() );
	         System.out.println(", Age : " + record.getAge());
	      }

	      System.out.println("----Updating Record with ID = 30 -----" );
	      studentDAOImpl.update(30, 80);
	      studentDAOImpl.update(31, 100);

	      System.out.println("----Listing Record with ID = 30 -----" );
	      Student student = studentDAOImpl.getStudent(30);
	      System.out.print("ID : " + student.getId() );
	      System.out.print(", Name : " + student.getName() );
	      System.out.println(", Age : " + student.getAge());
	      
	      studentDAOImpl.count();
	      
	      return student;
	   }
	
}