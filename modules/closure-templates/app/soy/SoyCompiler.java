package soy;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.tofu.SoyTofu;
import com.google.template.soy.tofu.SoyTofuOptions;
import play.Application;

import javax.inject.Inject;
import javax.inject.Provider;

public class SoyCompiler implements Provider<SoyTofu> {

    private final Application application;
    private final SoyFileSet soyFileSet;
    private final SoyTofuOptions soyTofuOptions;

    private final SoyTofu soyTofu;
    
    @Inject
    public SoyCompiler(final Application application,
                       final SoyFileSet soyFileSet,
                       final SoyTofuOptions soyTofuOptions) {
        this.soyTofuOptions = soyTofuOptions;
        this.soyFileSet = soyFileSet;
        this.application = application;

        soyTofu = soyFileSet.compileToTofu();
    }

    @Override
    public SoyTofu get() {
        return application.isDev() ? soyFileSet.compileToTofu(soyTofuOptions) : soyTofu;
    }

}
