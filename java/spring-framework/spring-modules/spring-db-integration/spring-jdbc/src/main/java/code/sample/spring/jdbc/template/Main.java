package code.sample.spring.jdbc.template;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import code.sample.spring.jdbc.dao.UserDao;

public class Main {
	private static ApplicationContext context;
	
	public static void main(String[] args) {
		context=new ClassPathXmlApplicationContext("spring-config.xml");
		UserDao userDao = (UserDao)context.getBean("userDaoImpl");
		userDao.displayData();
	}
}
