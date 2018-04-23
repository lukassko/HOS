package com.app.hos.security.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.DispatcherType;
import com.app.hos.security.states.StatesAuthenticator;

@WebFilter(
		filterName = "AuthenticationFilter",
        urlPatterns = "/*",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
)
public class AuthenticationFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {}
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		StatesAuthenticator statesAuthenticator = getStatesAuthenticator(request);
		statesAuthenticator.doAuthentication(request, response, chain);
	}

	private StatesAuthenticator getStatesAuthenticator(ServletRequest req) {
		StatesAuthenticator statesAuthenticator;
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession(false);
		if (session == null) {
			statesAuthenticator = new StatesAuthenticator();
		} else {
			statesAuthenticator = (StatesAuthenticator)session.getAttribute("authenticator");
			if (statesAuthenticator == null) {
				statesAuthenticator = new StatesAuthenticator();
			}
		}
		return statesAuthenticator;
	}

}
