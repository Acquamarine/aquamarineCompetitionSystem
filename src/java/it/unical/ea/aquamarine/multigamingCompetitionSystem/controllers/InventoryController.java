package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.OnDemandPersistenceManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.ItemsProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.IItem;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/inventory")
public class InventoryController {

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String inventory(HttpServletRequest request, Model m) {
		if(request.getSession().getAttribute("nickname") != null){
			m.addAttribute("user", request.getSession().getAttribute("nickname"));
			//TODO get from DAO
			RegisteredUser user = (RegisteredUser) request.getSession().getAttribute("registeredUser");
			OnDemandPersistenceManager.getInstance().initializeInventory(user);
			OnDemandPersistenceManager.getInstance().initializeEquip(user);
			return "/inventory";
		}
		return "/login";
	}

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = "Equip")
	public String equipItem(HttpServletRequest request, @RequestParam("Equip") int itemId) {
		IItem item = ItemsProvider.getInstance().getItem(itemId);
		RegisteredUser user = (RegisteredUser) request.getSession().getAttribute("registeredUser");
		item.equip(user);
		OnDemandPersistenceManager.getInstance().updateCompetitor(user);
		System.out.println("equipItem " + itemId);
		return "redirect:inventory";
	}
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = "Unequip")
	public String unequipItem(HttpServletRequest request, @RequestParam("Unequip") int itemId) {
		IItem item = ItemsProvider.getInstance().getItem(itemId);
		RegisteredUser user = (RegisteredUser) request.getSession().getAttribute("registeredUser");
		item.unequip(user);
		OnDemandPersistenceManager.getInstance().updateCompetitor(user);
		System.out.println("unequipItem " + itemId);
		return "redirect:inventory";
	}

}
