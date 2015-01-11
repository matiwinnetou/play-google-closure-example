package instrastructure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.SoyModule;
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
        return new SoyTemplateLoader(application, soyFleSetBuilder(), compileTimeGlobals(), soyGeneralOptions());
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
    @Named("googleClosureInjector")
    public Injector googleClosureInjector() {
        return Guice.createInjector(new SoyModule());
    }

    @Bean
    public SoyFileSet.Builder soyFleSetBuilder() {
        return googleClosureInjector().getInstance(SoyFileSet.Builder.class);
    }

    @Bean
    public SoyGeneralOptions soyGeneralOptions() {
        return new SoyGeneralOptions();
    }

    @Bean
    public SoyFileSet soyFileSet() {
        return soyTemplateLoader().build();
    }

    @Bean
    public SoyCompiler soyCompiler() {
        return new SoyCompiler(application, soyFileSet(), soyTofuOptions());
    }

    @Bean
    public SoyRenderer soyRenderer() {
        return new SoyRenderer(soyCompiler(),
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
