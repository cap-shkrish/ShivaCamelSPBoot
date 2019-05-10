package SpringBootDemo.Controllers;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringBootDemo.Beans.Address;
import SpringBootDemo.Beans.Person;
import SpringBootDemo.Services.MybatisDemoService;



@RestController
public class MybatisController {
	
    @Autowired
    private MybatisDemoService mybatisDemoService;
	
	@GetMapping("/mybatisAddress")
	public Address callMybatisAddress()
	{		
		return mybatisDemoService.selectAddress(2);
	}
	
	@GetMapping("/mybatisPersons")
	public List<Person> callMybatisPerson()
	{		
		return mybatisDemoService.selectPerson(2);
	}


}
