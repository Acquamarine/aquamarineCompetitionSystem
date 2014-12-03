/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Denise
 */
@Controller
public class LoggedInController {

	@RequestMapping(value="/loggedIn",method = RequestMethod.GET)
	public String logged(Model m){
		
		return "loggedIn";
	}
	@RequestMapping(value="/loggedIn",method = RequestMethod.POST)
	public String loggedPost(Model m){
		
		return "loggedIn";
	}
}
