package io.devpl.spring;

<<<<<<< HEAD
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
=======
import io.devpl.spring.boot.DevplSpringApplication;
import io.devpl.spring.web.utils.ParamWrapper;
>>>>>>> d7decddfa4eccb3a3e8acaeefa61fbd3de189d6b
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.MultiValueMap;

@SpringBootApplication
public class MainApplication {

    private static final Logger LOG = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
<<<<<<< HEAD
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
        ServerProperties serverInfo = context.getBean(ServerProperties.class);
        LOG.info("启动成功，访问地址 => {}", serverInfo.getAddress());
=======
        ConfigurableApplicationContext context = DevplSpringApplication.run(MainApplication.class, args);

        ParamWrapper wrapper;
>>>>>>> d7decddfa4eccb3a3e8acaeefa61fbd3de189d6b
    }
}