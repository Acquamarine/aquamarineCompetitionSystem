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
			m.addAttribute("inventoryMap", user.getInventory().getInventoryMap());
			return "/inventory";
		}
		return "/login";
	}

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = "equipItem")
	public void equipItem(HttpServletRequest request, @RequestParam("equipItem") int itemId) {
		IItem item = ItemsProvider.getInstance().getItem(itemId);
		RegisteredUser user = (RegisteredUser) request.getSession().getAttribute("registeredUser");
		item.equip(user);
		System.out.println("equipItem " + itemId);
	}

}
