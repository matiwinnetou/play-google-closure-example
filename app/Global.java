import instrastructure.GoogleClosureConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {

    private AnnotationConfigApplicationContext annotationConfigApplicationContext;

    @Override
    public void onStart(final Application app) {
        annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.getBeanFactory().registerSingleton("playApp", app);
        annotationConfigApplicationContext.register(GoogleClosureConfig.class);
        annotationConfigApplicationContext.refresh();
    }

    @Override
    public <A> A getControllerInstance(final Class<A> controllerClass) throws Exception {
        return annotationConfigApplicationContext.getBean(controllerClass);
    }

}
