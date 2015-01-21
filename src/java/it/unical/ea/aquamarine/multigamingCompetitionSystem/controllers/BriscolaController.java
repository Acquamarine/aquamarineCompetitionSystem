package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/briscola")
public class BriscolaController {
	
@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String briscola(){
		return "/briscola";
	}
@RequestMapping(value="/gioca", method = {RequestMethod.GET,RequestMethod.POST})
	public String play(HttpServletRequest request, Model model){
		model.addAttribute("Me", "Me");
		model.addAttribute("MyTeammate", "MyTeammate");
		model.addAttribute("OpponentFirst", "OpponentFirst");
		model.addAttribute("OpponentSecond", "OpponentSecond");
		List<LinkedList<NeapolitanCard>> playersCards = new ArrayList<>();
		return "/briscola";
	}
}
