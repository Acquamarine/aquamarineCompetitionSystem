package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.User;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class IndexController {
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
                model.addAttribute("loggedIn", false);
		model.addAttribute("userForm", new User());
		return "index";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String userLogin(HttpServletRequest request, @ModelAttribute("userForm") User user, Model model) {
		//you have to do something smarter!	
//		if(user.getUsername().equals("ciccio")
//		   && user.getPassword().equals("pasticcio")){
//			request.getSession().setAttribute("userSession", user.getUsername());
//		}
		String username=user.getUsername();
                request.getSession().setAttribute("username", user.getUsername());
                request.getSession().setAttribute("loggedIn", true);
		//model.addAttribute("username",username);
		//model.addAttribute("loggedIn", true);
		return "index";
	}
	
	
}