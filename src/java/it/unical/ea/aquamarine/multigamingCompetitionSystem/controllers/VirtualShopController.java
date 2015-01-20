package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.AbstractCompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.OnDemandPersistenceManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.ItemCategory;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.ItemsProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.VirtualShop;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.MarketItem;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/virtualShop")
public class VirtualShopController {
    
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String virtualShop(HttpServletRequest request, Model model) {
		if(request.getSession().getAttribute("registeredUser")==null){
			model.addAttribute("message", "You have to login to access the virtual shop!");
			model.addAttribute("userForm", new RegisteredUser());
			return "/login";
		}
        String game = Tressette1v1.class.getSimpleName();
        ICompetitor buyer = (ICompetitor)request.getSession().getAttribute("registeredUser");
		request.getSession().setAttribute("buyer", buyer);
		OnDemandPersistenceManager.getInstance().initializeInventory(buyer);
        buildModel(game, buyer, model);
        return "redirect:virtualShop";
    }
    
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = "game")
    public String virtualShop(HttpServletRequest request, Model model, @RequestParam("game") String game) {
		if(request.getSession().getAttribute("registeredUser")==null){
			model.addAttribute("message", "You have to login to access the virtual shop!");
			model.addAttribute("userForm", new RegisteredUser());
			return "/login";
		}
        ICompetitor buyer = (ICompetitor)request.getSession().getAttribute("registeredUser");
		OnDemandPersistenceManager.getInstance().initializeInventory(buyer);
		request.getSession().setAttribute("buyer", buyer);
        buildModel(game, buyer, model);
        return "/virtualShop";
    }

    private void buildModel(String game, ICompetitor buyer, Model model) {
        model.addAttribute("game", game);
		Map<ItemCategory, Set<MarketItem>> sellingItems = VirtualShop.getInstance().getSellingItems();
        model.addAttribute("availableItems", sellingItems);
        
    }
    
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params="buyItem")
    public String buyItem(HttpServletRequest request, ModelMap model,@RequestParam("buyItem") int buyItem) {
		ICompetitor buyer = (ICompetitor)request.getSession().getAttribute("buyer");
        VirtualShop.getInstance().buyItem(buyer, ItemsProvider.getInstance().getMarketItem(buyItem));
		OnDemandPersistenceManager.getInstance().updateCompetitor(buyer);
		request.getSession().setAttribute("focus", "VirtualShopButton"+buyItem);
        return "redirect:virtualShop";
    }
}
