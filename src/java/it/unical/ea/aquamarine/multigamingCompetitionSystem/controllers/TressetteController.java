/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Denise
 */

@Controller
public class TressetteController {
	
	@RequestMapping(value = "/tressette", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("userForm", new User());
		return "/tressette";
	}
	@RequestMapping(value = "/tressette/gioca", method = RequestMethod.GET)
	public String play(Model model) {
		model.addAttribute("userForm", new User());
		return "/tressette/gioca";
	}
}
