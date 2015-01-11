package soy;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.tofu.SoyTofu;
import com.google.template.soy.tofu.SoyTofuOptions;

import javax.inject.Inject;

public class SoyCompiler {

    private final SoyTofuOptions soyTofuOptions;

    @Inject
    public SoyCompiler(final SoyTofuOptions soyTofuOptions) {
        this.soyTofuOptions = soyTofuOptions;
    }

    public SoyTofu compile(final SoyFileSet soyFileSet) {
        return soyFileSet.compileToTofu(soyTofuOptions);
    }

}
