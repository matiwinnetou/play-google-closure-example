package instrastructure;

import controllers.Application;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import soy.Soy;

import javax.inject.Inject;

@Configuration
@Import(SoyConfig.class)
public class GoogleClosureConfig {

    @Inject
    private Soy soy;

    @Bean
    public Application application() {
        return new Application(soy);
    }

}
