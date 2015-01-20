package it.unical.ea.aquamarine.multigamingCompetitionSystem.interceptors;

import it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.DAOProvider;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author Denise
 */
@Component
public class CheckLoginInterceptors extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(request.getSession().getAttribute("loggedIn") ==null){
			request.getSession().setAttribute("loggedIn", false);
			return true;
		}
		if((boolean) request.getSession().getAttribute("loggedIn") == true){
			if(DAOProvider.getCompetitorDAO().retrieveByNick((String) request.getSession().getAttribute("nickname")) == null){
				Enumeration<String> attributeNames = request.getSession().getAttributeNames();
				while(attributeNames.hasMoreElements()){
					String attribute = attributeNames.nextElement();
					request.getSession().removeAttribute(attribute);
				}
				request.getSession().setAttribute("loggedIn", false);
			}
		}
		return true;
	}
}
