package SpringBootDemo.Controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

	
	private static Logger logger = LogManager.getLogger(ExceptionController.class.getName());

	@ExceptionHandler(value = Exception.class)
	public ModelAndView handleAllException(HttpServletRequest request,Exception ex) {
		logger.info("This is an info message from ExceptionController");
		String str = "Request " + request.getRequestURL() + " Threw an exception " + ex;
		logger.error(str);
		
		ModelAndView model = new ModelAndView("/error");
		model.addObject("errMsg", str);

		return model;	

	}	

	@ModelAttribute
    public void globalAttributes(Model model) 
	{
		model.addAttribute("msg", "Welcome to My World!");
    }

	
}