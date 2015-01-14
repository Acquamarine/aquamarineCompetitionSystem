package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.items.IItem;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.items.ItemCategory;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.items.ItemsProvider;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/inventory")
public class InventoryController {
	
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String inventory(HttpServletRequest request, Model m){
		if(request.getSession().getAttribute("nickname")!=null){
			m.addAttribute("user",request.getSession().getAttribute("nickname"));
			//TODO get from DAO
			ItemsProvider.getInstance().init();//TODO delegate to ApplicationManager
			Map<String, Set<IItem>> inventory = new HashMap<>();
			Set<IItem> items = new HashSet<>();
			items.add(ItemsProvider.getInstance().getEloRewardItem(2));
			items.add(ItemsProvider.getInstance().getMarketItem(1));
			inventory.put("card", items);
			inventory.put("avatar", new HashSet<>());
			m.addAttribute("inventoryMap", inventory);
			return "/inventory";
		}
		return "/login";
	}
	
}
