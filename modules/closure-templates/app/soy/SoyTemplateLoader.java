package soy;

import com.google.common.reflect.ClassPath;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.shared.SoyAstCache;
import com.google.template.soy.shared.SoyGeneralOptions;
import play.Application;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.util.Map;
import java.util.Set;

public class SoyTemplateLoader {

    private final Application application;
    private final Set<ClassPath.ResourceInfo> resourceInfos;
    private final SoyGeneralOptions soyGeneralOptions;
    private final Map<String,?> compileTimeGlobals;

    @Inject
    public SoyTemplateLoader(final Application application,
                             @Named("compileTimeGlobals") final Map<String,?> compileTimeGlobals,
                             final SoyGeneralOptions soyGeneralOptions) {
        this.application = application;
        this.resourceInfos = Resources.resources(application);
        this.compileTimeGlobals = compileTimeGlobals;
        this.soyGeneralOptions = soyGeneralOptions;
    }

    public SoyFileSet build() {
        final SoyFileSet.Builder builder = SoyFileSet.builder();
        builder.setGeneralOptions(soyGeneralOptions);
        builder.setAllowExternalCalls(true);
        builder.setCompileTimeGlobals(compileTimeGlobals);

        if (application.isDev()) {
            prepareDevFileSet(builder);
        } else {
            prepareNormalFileSet(builder);
        }

        return builder.build();
    }

    private void prepareNormalFileSet(final SoyFileSet.Builder builder) {
        Resources.findSoyFiles(resourceInfos).forEach(url -> builder.add(url));
    }

    private void prepareDevFileSet(final SoyFileSet.Builder builder) {
        builder.setSoyAstCache(new SoyAstCache());
        Resources.findSoyFiles(Resources.resources(application)).forEach(url -> builder.addVolatile(new File(url.getFile())));
    }

}
