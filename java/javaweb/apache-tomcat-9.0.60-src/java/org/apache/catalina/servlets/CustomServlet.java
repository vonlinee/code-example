package org.apache.catalina.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多余加的Servlet， web.xml配置
 * 
 * enable.custom.init.exception
 */
public class CustomServlet extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(CustomServlet.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
		super.destroy();
		log.info("销毁");
	}

	@Override
	public void init() throws ServletException {
		super.init();
		log.info("初始化");
		if (System.getProperty("enable.custom.init.exception") != null) {
			int i = 1 / 0; // 即使报错，也不会抛异常
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (System.getProperty("enable.custom.doget.exception") != null) {
			int i = 1 / 0;
		}
		Map<String, String[]> parameterMap = req.getParameterMap();
		
		Set<String> keySet = parameterMap.keySet();
		for(String key : keySet) {
			System.out.println(Arrays.deepToString(parameterMap.get(key)));
		}
		
		resp.getWriter().write(parameterMap.toString());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().write("Hello World");
	}
}