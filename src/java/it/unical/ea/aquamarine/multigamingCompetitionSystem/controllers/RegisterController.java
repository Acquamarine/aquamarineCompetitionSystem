/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.Team;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.CompetitorDAO;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.CompetitorsDAOImpl;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.OnDemandPersistenceManager;
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
		CompetitorDAO competitorDAO = (CompetitorDAO) context.getBean("competitorDAO");
		if(competitorDAO.doesUserExistByUsername(user.getUsername())){
			model.addAttribute("userUnavailable", true);
			validForm = false;
		}else{
			model.addAttribute("userUnavailable", false);
			
		}
		if(competitorDAO.doesCompetitorExistByNick(user.getNickname())){
			model.addAttribute("nickUnavailable", true);
			validForm = false;
		}else{
			model.addAttribute("nickUnavailable", false);
		}
		if(validForm){
			user.gainVirtualPoints(200);
			competitorDAO.create(user);
			
			model.addAttribute("message","Registration has been successful. You can now login!");
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
