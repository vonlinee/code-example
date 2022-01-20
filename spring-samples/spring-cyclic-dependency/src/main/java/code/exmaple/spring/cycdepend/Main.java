package code.exmaple.spring.cycdepend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import code.exmaple.spring.cycdepend.bean.Model;
import code.exmaple.spring.cycdepend.bean.Student;

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		ApplicationContext context = SpringApplication.run(Main.class, args);
		Model model = context.getBean(Model.class);
		Student student = context.getBean(Student.class);
		System.out.println(model); 			//bean.Model@2392212b
		System.out.println(student); 		//bean.Student@5b43e173
		System.out.println(model.student); 	//bean.Student@5b43e173
		System.out.println(student.model); 	//bean.Model@2392212b
	}
}