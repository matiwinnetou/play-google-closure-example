package soy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.SoyModule;
import com.google.template.soy.data.SoyValueHelper;
import com.google.template.soy.msgs.SoyMsgBundleLoader;
import com.google.template.soy.shared.SoyCssRenamingMap;
import com.google.template.soy.shared.SoyGeneralOptions;
import com.google.template.soy.shared.SoyIdRenamingMap;
import com.google.template.soy.tofu.SoyTofuOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import play.Application;
import soy.ij.HelloSoyRuntimeDataProvider;
import soy.ij.SoyRuntimeDataProvider;
import soy.modules.SoyModule2;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Configuration
public class SoyConfig {

    @Inject
    private Application application;

    @Inject
    private Gson gson;

    @Bean
    public SoyTemplateLoader soyTemplateLoader() {
        return new SoyTemplateLoader(application, soyFleSetBuilder(), soyGeneralOptions());
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
    @Named("soyRuntimeDataProviders")
    public List<SoyRuntimeDataProvider> runtimeDataProviderList() {
        return ImmutableList.of(new HelloSoyRuntimeDataProvider());
    }

    @Bean
    @Named("googleClosureInjector")
    public Injector googleClosureInjector() {
        return Guice.createInjector(new SoyModule(), new SoyModule2(gson));
    }

    @Bean
    public SoyFileSet.Builder soyFleSetBuilder() {
        return googleClosureInjector().getInstance(SoyFileSet.Builder.class);
    }

    @Bean
    public SoyValueHelper soyValueHelper() {
        return googleClosureInjector().getInstance(SoyValueHelper.class);
    }

    @Bean
    public SoyGeneralOptions.CssHandlingScheme cssHandlingScheme() {
        return SoyGeneralOptions.CssHandlingScheme.LITERAL;
    }
    
    @Bean
    public SoyGeneralOptions soyGeneralOptions() {
        final SoyGeneralOptions soyGeneralOptions = new SoyGeneralOptions();
        soyGeneralOptions.setCssHandlingScheme(cssHandlingScheme());
        soyGeneralOptions.setCompileTimeGlobals(compileTimeGlobals());

        return soyGeneralOptions;
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
                soyIdRenamingMap(),
                soyCssRenamingMap());
    }

    @Bean
    public Soy soy() {
        return new Soy(soyRenderer(), soyValueHelper());
    }

    @Bean
    public SoyMsgBundleLoader soyMsgBundleLoader() {
        return new SoyMessagesLoader(application);
    }

    @Bean
    public SoyIdRenamingMap soyIdRenamingMap() {
        return key -> "prefix.key";
    }

    @Bean
    public SoyCssRenamingMap soyCssRenamingMap() {
        return key -> key;
    }

}
