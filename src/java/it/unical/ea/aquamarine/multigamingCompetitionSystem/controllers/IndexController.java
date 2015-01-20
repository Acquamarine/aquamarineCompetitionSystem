package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.competition.CompetitionManager;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.CompetitorDAO;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController implements ApplicationContextAware {

	private ApplicationContext context;

	@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
	public String index(Model model, HttpServletRequest request) {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, params = "page")
	public String userLogin(ModelMap model, @ModelAttribute("userForm") RegisteredUser user, @RequestParam("page") String page) {
		model.addAttribute("page", page);
		return "/login";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet(ModelMap model, @ModelAttribute("userForm") RegisteredUser user) {
		return "/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, params = {"loggigIn", "page"})
	public String login(Model model, HttpServletRequest request, @ModelAttribute("userForm") RegisteredUser user, @RequestParam("page") String page) {

		String username = user.getUsername();
		String password = user.getPassword();
		//TODO type check -> class cast exception may occour
		RegisteredUser registeredUser = (RegisteredUser) CompetitionManager.getInstance().getCompetitorByUsername(username);
		String subPage= page.substring(29);
			
		if(registeredUser != null && password.equals(registeredUser.getPassword())){
			request.getSession().setAttribute("username", registeredUser.getUsername());
			request.getSession().setAttribute("nickname", registeredUser.getNickname());
			request.getSession().setAttribute("playerId", registeredUser.getId());
			request.getSession().setAttribute("registeredUser", registeredUser);
			request.getSession().setAttribute("loggedIn", true);
			return "redirect:" + subPage;
		}
		model.addAttribute("message", "Wrong username or password.");
		model.addAttribute("page", page);
		return "/login";
		/*request.getSession().setAttribute("username", user.getUsername());
		 request.getSession().setAttribute("loggedIn", true);
		 //model.addAttribute("username",username);
		 //model.addAttribute("loggedIn", true);
		 String subPage = page.substring(29);
		 if(subPage == null){
		 subPage = "index";
		 }
		 return "redirect:" + page;*/
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, params = {"logout"})
	public String logout(HttpServletRequest request) {
		Enumeration<String> attributeNames = request.getSession().getAttributeNames();
		while(attributeNames.hasMoreElements()){
			String attribute = attributeNames.nextElement();
			request.getSession().removeAttribute(attribute);
		}
		request.getSession().setAttribute("loggedIn", false);
		
		return "/index";
	}

	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		context = ac;
	}
}
