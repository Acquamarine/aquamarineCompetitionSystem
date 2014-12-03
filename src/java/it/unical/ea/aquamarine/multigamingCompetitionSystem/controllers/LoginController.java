/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.utilities.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Denise
 */
@Controller
public class LoginController {

	@org.springframework.web.bind.annotation.RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("userForm", new User());
		return "login";
	}

	@org.springframework.web.bind.annotation.RequestMapping(value = "/login", method = RequestMethod.POST)
	public String userLogin(HttpServletRequest request, @ModelAttribute("userForm") User user, Model model) {
		//you have to do something smarter!	
		if(user.getUsername().equals("ciccio")
		   && user.getPassword().equals("pasticcio")){
			request.getSession().setAttribute("userSession", user.getUsername());
			return "redirect:/loggedIn";
		}else{
			return "redirect:/login";
		}
	}
}
