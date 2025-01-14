package sample.spring.boot.ext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        printApplicationEvent(event);
    }

    private void printApplicationEvent(ApplicationEvent event) {
        log.info("[处理事件] {}, {}, {}", event.getTimestamp(), event.getSource(), event.getClass());
    }
}
