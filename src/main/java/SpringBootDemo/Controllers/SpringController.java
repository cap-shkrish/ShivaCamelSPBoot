package SpringBootDemo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringController {

	@GetMapping(value = "/index")
	public ModelAndView visitHome() {
		
		return new ModelAndView("index");
	}

	@GetMapping(value = "/")
	public ModelAndView visitDefault() {
		int i = 0;

		ModelAndView model = new ModelAndView("index");
		if (i == 0)
			model = new ModelAndView("redirect:/employee");

		return model;
	}
	
	@GetMapping(value = "/test")
	public ModelAndView Test() {
		
		ModelAndView model = new ModelAndView("test");
		return model;
	}

	@PostMapping(value = "/cap")
	public ModelAndView redirectToCap(
			@RequestParam("kitNumber") String kitNumber,
			@RequestParam("capId") String capId) 
	{
		System.out.println(" Kit Number: " + kitNumber + " CAP ID: " + capId);
		ModelAndView model = new ModelAndView("redirect:http://d2ahedifs7q05c.cloudfront.net");
		return model;
	}

	@GetMapping(value = "/admin")
	public ModelAndView visitAdmin() {
		ModelAndView model = new ModelAndView("admin");
		model.addObject("title", "Admministrator Control Panel");
		model.addObject("message", "This page demonstrates how to use SpringBoot with ThymeLeaf.");
		return model;
	}

	@PostMapping(value = "/submit")
	public ModelAndView visitSubmit(@RequestParam("studentName") String name,
			@RequestParam("studentHobby") String hobby) {
		ModelAndView model = new ModelAndView("submit");
		model.addObject("name", name);
		model.addObject("hobby", hobby);

		return model;
	}

	@GetMapping(value = "/welcome/{userName}")
	public ModelAndView visitWelcome(@PathVariable("userName") String name) {

		ModelAndView model = new ModelAndView("welcome");
		model.addObject("title", "Hello " + name);

		return model;
	}

}