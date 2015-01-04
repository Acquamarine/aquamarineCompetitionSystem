package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.User;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

	@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
	public String index(Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("loggedIn") == null){
			request.getSession().setAttribute("loggedIn", false);
		}
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, params = "page")
	public String userLogin(ModelMap model, @ModelAttribute("userForm") User user , @RequestParam("page") String page) {
		//you have to do something smarter!	
//		if(user.getUsername().equals("ciccio")
//		   && user.getPassword().equals("pasticcio")){
//			request.getSession().setAttribute("userSession", user.getUsername());
//		}
		/*String username = user.getUsername();
		request.getSession().setAttribute("username", user.getUsername());
		request.getSession().setAttribute("loggedIn", true);
		//model.addAttribute("username",username);
		//model.addAttribute("loggedIn", true);*/
		String subPage = page.substring(29);
		if(subPage==null){
			subPage="index";
		}
		model.addAttribute("page", subPage);
		return "/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, params={"loggigIn","page"})
	public String login(HttpServletRequest request, @ModelAttribute("userForm") User user, @RequestParam("page") String page) {
		//you have to do something smarter!	
//		if(user.getUsername().equals("ciccio")
//		   && user.getPassword().equals("pasticcio")){
//			request.getSession().setAttribute("userSession", user.getUsername());
//		}
		String username = user.getUsername();
		request.getSession().setAttribute("username", user.getUsername());
		request.getSession().setAttribute("loggedIn", true);
		//model.addAttribute("username",username);
		//model.addAttribute("loggedIn", true);
		return "redirect:"+page;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, params={"logout","page"})
	public String logout(HttpServletRequest request, @RequestParam("page") String page) {
		Enumeration<String> attributeNames = request.getSession().getAttributeNames();
		while(attributeNames.hasMoreElements()){
			String attribute = attributeNames.nextElement();
			request.getSession().removeAttribute(attribute);
		}
		String subPage = page.substring(29);
		if(subPage==null){
			subPage="index";
		}
		return "redirect:"+subPage;
	}
}
