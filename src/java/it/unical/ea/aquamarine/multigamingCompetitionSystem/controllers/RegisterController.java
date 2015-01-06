/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.UserDAO;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.UserDAOImpl;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Denise
 */
@Controller
@RequestMapping("/register")
public class RegisterController implements ApplicationContextAware{
	private ApplicationContext context;

	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model, HttpServletRequest request, @ModelAttribute("userForm") RegisteredUser user) {
		if(request.getSession().getAttribute("loggedIn") == null){
			request.getSession().setAttribute("loggedIn", false);
		}
		model.addAttribute("passwordWrong", false);
		model.addAttribute("userUnavailable", false);
		model.addAttribute("nickUnavailable", false);
		
		return "/register";
	}

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = {"registering", "repassword"})
	public String register(Model model, @ModelAttribute("userForm") RegisteredUser user, @RequestParam("repassword") String repassword) {
		boolean validForm = true;
		if(!repassword.equals(user.getPassword())){
			model.addAttribute("passwordWrong", true);
			user.setPassword("");
			validForm = false;
		}else{
			model.addAttribute("passwordWrong", false);
		}
		UserDAO userDao = (UserDAO) context.getBean("userDAO");
		if(userDao.doesUserExistByUsername(user.getUsername())){
			model.addAttribute("userUnavailable", true);
			validForm = false;
		}else{
			model.addAttribute("userUnavailable", false);
			
		}
		if(userDao.doesUserExistByNick(user.getNickname())){
			model.addAttribute("nickUnavailable", true);
			validForm = false;
		}else{
			model.addAttribute("nickUnavailable", false);
		}
		if(validForm){
			userDao.create(user);
			model.addAttribute("registrationCompleted", true);
			return "/login";
		}
		return "/register";
	}

	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		context=ac;
	}
}
