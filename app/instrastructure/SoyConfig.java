package instrastructure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.template.soy.msgs.SoyMsgBundleLoader;
import com.google.template.soy.shared.SoyGeneralOptions;
import com.google.template.soy.shared.SoyIdRenamingMap;
import com.google.template.soy.tofu.SoyTofuOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import play.Application;
import soy.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SoyConfig {

    @Inject
    private Application application;

    @Bean
    public SoyTemplateLoader soyTemplateLoader() {
        return new SoyTemplateLoader(application, compileTimeGlobals(), soyGeneralOptions());
    }

    @Bean
    @Named("compileTimeGlobals")
    public Map<String, ?> compileTimeGlobals() {
        return ImmutableMap.of("hello", "world");
    }

    @Bean
    public SoyTofuOptions soyTofuOptions() {
        final SoyTofuOptions soyTofuOptions = new SoyTofuOptions();
        soyTofuOptions.setUseCaching(!application.isDev());

        return soyTofuOptions;
    }

    @Bean
    @Named("runtimeDataProviders")
    public List<SoyRuntimeDataProvider> runtimeDataProviderList() {
        return ImmutableList.of(new HelloSoyRuntimeDataProvider());
    }

    @Bean
    public SoyGeneralOptions soyGeneralOptions() {
        return new SoyGeneralOptions();
    }

    @Bean
    public SoyCompiler soyCompiler() {
        return new SoyCompiler(soyTofuOptions());
    }

    @Bean
    public SoyRenderer soyRenderer() {
        return new SoyRenderer(soyCompiler(),
                soyTemplateLoader(),
                soyMsgBundleLoader(),
                runtimeDataProviderList(),
                soyIdRenamingMap());
    }

    @Bean
    public Soy soy() {
        return new Soy(soyRenderer());
    }

    @Bean
    public SoyMsgBundleLoader soyMsgBundleLoader() {
        return new SoyMessagesLoader(application);
    }

    @Bean
    public SoyIdRenamingMap soyIdRenamingMap() {
        final HashMap<String, String> map = Maps.<String, String>newHashMap();

        return key -> map.get(key);
    }

}
