package instrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.Application;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import soy.Soy;
import soy.SoyConfig;
import utils.OptionalTypeAdapter;

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

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(OptionalTypeAdapter.FACTORY)
                .create();
    }

}
