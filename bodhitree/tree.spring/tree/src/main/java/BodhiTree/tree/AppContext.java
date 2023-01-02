package bodhitree.tree;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext get() {
        return context;
    }
    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static MongoTemplate mongoTemplate () {
        return (MongoTemplate) getBean("mongoTemplate");
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        context = ac;
    }
}
