package edu.unicen.surfforecaster.gwt.server.util;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.ServletContextAware;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class SpringGWTServlet extends RemoteServiceServlet  implements HttpRequestHandler, ServletContextAware{

	@Override
	public void handleRequest(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		service(arg0, arg1);		
	}

	public void setServletContext(ServletContext servletContext) {
		  final ServletContext context = servletContext;
		  try {
		    init(new ServletConfig() {
		      @Override
		      public String getInitParameter(String name) {
		        return null;
		      }
		      @Override
		      public Enumeration getInitParameterNames() {
		        return null;
		      }
		      @Override
		      public ServletContext getServletContext() {
		        return context;
		      }
		      @Override
		      public String getServletName() {
		        return null;
		      }
		    });
		  } catch (ServletException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		  }
		}

}
