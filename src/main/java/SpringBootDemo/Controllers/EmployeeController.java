package SpringBootDemo.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import SpringBootDemo.Beans.Employee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@Api(value="EmployeeController API", produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class EmployeeController {
 
	@ApiOperation("Returns the Employee Page")
	@ApiResponses (value= {@ApiResponse(code=200, message="OK", response=Employee.class)})
    @GetMapping(value = "/employee")
    public ModelAndView showEmployeeForm() {
    	//Create a new ModelAndView given (String viewName, String modelName, Object modelObject)
    	
    	ModelAndView model = new ModelAndView("employee", "employee", getEmployee());
    	
        return model;
    }
    
    private Employee getEmployee()
    {
    	
    	ArrayList<String> states = new ArrayList<String>();
    	states.add( "Alabama");
    	states.add( "Alaska");
    	states.add( "Arizona");
    	states.add( "Arkansas");
    	states.add( "California");
    	
    	Employee emp = new Employee();
    	emp.setName("Franz Beckenbauer");
       	emp.setId("234556");
       	emp.setContactNumber("2345562233");
    	emp.setEmployeeDOB(new Date());
    	emp.setEmployeeSkills(states);
    	return emp;
    	
    }
    
    /**
    @PostMapping(value = "/employeeView")
    public ModelAndView submit(@Valid @ModelAttribute("employee")Employee employee, BindingResult bindingResult) 
    {
    	
       	ModelAndView model = null;
    	
    	// form validation error
        if (bindingResult.hasErrors()) 
        {
        	System.out.println(bindingResult.getAllErrors().toString());
        	model =  new ModelAndView("employee");
        }
        else
        	model = new ModelAndView("employeeView");
        
        return model;
    }
    
        //ArrayList<User> listUser = new ArrayList<>();
		// get user list	
		//ModelAndView modelView = new ModelAndView("employeeView");
		//modelView.addObject("listUser", listUser);

    */
    
    @ModelAttribute
    public void addingCommonObjects(Model commonModel)
    {
    	commonModel.addAttribute("message",  "Ma Main Message");
    	commonModel.addAttribute("message2",  "Mama Mia Secondary Message");
    	commonModel.addAttribute("employee",  getEmployee());

    }
    
    
    @PostMapping(value = "/employeeView")
    public String submit(@Valid @ModelAttribute Employee employee, BindingResult bindingResult) 
    {
    	String gotoPage = "employee";
    	
    	// if no form validation errors
        if (!bindingResult.hasErrors()) 
        	gotoPage = "employeeView";

        return gotoPage;
    }
    
    
}