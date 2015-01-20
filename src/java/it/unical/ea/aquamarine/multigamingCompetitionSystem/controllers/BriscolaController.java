package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/briscola")
public class BriscolaController {
	
@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String briscola(){
		return "/briscola";
	}
}
