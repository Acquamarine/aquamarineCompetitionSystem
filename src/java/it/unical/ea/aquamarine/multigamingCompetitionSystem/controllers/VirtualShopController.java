package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.VirtualShop;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/virtualShop")
public class VirtualShopController {
    
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String virtualShop(HttpServletRequest request, Model model) {
        String game = Tressette1v1.class.getSimpleName();
        ICompetitor buyer = (ICompetitor)request.getSession().getAttribute("registeredUser");
        buildModel(game, buyer, model);
        return "/virtualShop";
    }
    
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = "game")
    public String virtualShop(HttpServletRequest request, Model model, @RequestParam("game") String game) {
        ICompetitor buyer = (ICompetitor)request.getSession().getAttribute("registeredUser");
        buildModel(game, buyer, model);
        return "/virtualShop";
    }

    private void buildModel(String game, ICompetitor buyer, Model model) {
        model.addAttribute("game", game);
        model.addAttribute("buyer", buyer);
        model.addAttribute("availableItems", VirtualShop.getInstance().getAvailableItems(buyer, game));
        
    }
    
}
