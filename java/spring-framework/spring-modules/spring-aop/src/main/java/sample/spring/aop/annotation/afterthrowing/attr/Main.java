package sample.spring.aop.annotation.afterthrowing.attr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	private static ApplicationContext context;

	public static void main(String[] args) {  
		context = new ClassPathXmlApplicationContext("abc.xml");  
		Performer performer=(Performer)context.getBean("performer"); 
		performer.perform("K.K.",42);
	}
}
