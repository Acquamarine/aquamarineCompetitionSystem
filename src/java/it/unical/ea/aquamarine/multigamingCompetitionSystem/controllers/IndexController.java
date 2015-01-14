package it.unical.ea.aquamarine.multigamingCompetitionSystem.controllers;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser;
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
		if(request.getSession().getAttribute("loggedIn") == null){
			request.getSession().setAttribute("loggedIn", false);
		}
		/*
		CompetitorDAO competitorDao = DAOProvider.getCompetitorDAO();
		RegisteredUser user1 = new RegisteredUser();
		user1.setNickname("ciccio");
		user1.updateElo("Tressette1v1", 1200);
		RegisteredUser user4 = new RegisteredUser();
		user4.setNickname("ciccio4");
		user4.updateElo("Tressette1v1", 1100);
		RegisteredUser user2 = new RegisteredUser();
		user2.setNickname("ciccio2");
		user2.updateElo("Tressette1v1", 1800);
		RegisteredUser user3 = new RegisteredUser();
		user3.setNickname("ciccio3");
		user3.updateElo("Tressette1v1", 2000);
		competitorDao.create(user3);
		competitorDao.create(user1);
		competitorDao.create(user2);
		competitorDao.create(user4);
		*/
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
		CompetitorDAO competitorDAO = (CompetitorDAO) context.getBean("competitorDAO");

		String username = user.getUsername();
		String password = user.getPassword();
		//TODO type check -> class cast exception may occour
		RegisteredUser registeredUser = (RegisteredUser) competitorDAO.retrieveByUsername(username);
		String subPage= page.substring(29);
			
		if(registeredUser != null && password.equals(registeredUser.getPassword())){
			request.getSession().setAttribute("username", registeredUser.getUsername());
			request.getSession().setAttribute("nickname", registeredUser.getNickname());
			request.getSession().setAttribute("playerId", registeredUser.getId());
			request.getSession().setAttribute("loggedIn", true);
			return "redirect:" + subPage;
		}
		model.addAttribute("loginFailed", true);
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
		
		return "/index";
	}

	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		context = ac;
	}
}
