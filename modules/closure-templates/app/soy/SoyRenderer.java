package soy;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.data.SoyRecord;
import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.msgs.SoyMsgBundleLoader;
import com.google.template.soy.shared.SoyIdRenamingMap;
import com.google.template.soy.tofu.SoyTofu;
import play.mvc.Http;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class SoyRenderer {

    private final SoyCompiler soyCompiler;
    private final SoyTemplateLoader soyTemplateLoader;
    private final SoyIdRenamingMap soyIdRenamingMap;
    private final SoyMsgBundleLoader soyMsgBundleLoader;
    private final List<SoyRuntimeDataProvider> soyRuntimeDataProviderList;

    @Inject
    public SoyRenderer(final SoyCompiler soyCompiler,
                       final SoyTemplateLoader soyTemplateLoader,
                       final SoyMsgBundleLoader soyMsgBundleLoader,
                       @Named("runtimeDataProviders") final List<SoyRuntimeDataProvider> soyRuntimeDataProviderList,
                       final SoyIdRenamingMap soyIdRenamingMap) {
        this.soyCompiler = soyCompiler;
        this.soyTemplateLoader = soyTemplateLoader;
        this.soyMsgBundleLoader = soyMsgBundleLoader;
        this.soyIdRenamingMap = soyIdRenamingMap;
        this.soyRuntimeDataProviderList = soyRuntimeDataProviderList;
    }

    public String render(final String templateName,
                         final Locale locale,
                         final Http.Request request,
                         final Http.Response response,
                         final Map<String, ?> model,
                         final Set<String> activeDelegatePackages) {
        final SoyFileSet soyFileSet = soyTemplateLoader.build();
        final SoyTofu soyTofu = soyCompiler.compile(soyFileSet);
        final SoyMsgBundle soyMsgBundle = soyMsgBundleLoader.getSoyMsgBundleForLocale(locale);

        return soyTofu.newRenderer(templateName)
                .setActiveDelegatePackageNames(activeDelegatePackages)
                .setIdRenamingMap(soyIdRenamingMap)
                .setData(model)
                .setIjData(ijData(request, response, model))
                .setMsgBundle(soyMsgBundle)
                .render();
    }

    public String render(final String templateName,
                         final Locale locale,
                         final Http.Request request,
                         final Http.Response response,
                         final Map<String, ?> model) {
        return render(templateName, locale, request, response, model, ImmutableSet.of());
    }

    private SoyRecord ijData(final Http.Request request,
                             final Http.Response response,
                             final Map<String, ?> model) {
        final ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        
        soyRuntimeDataProviderList.forEach(provider -> builder.putAll(provider.injectData(request, response, model)));

        return new SoyMapData(builder.build());
    }
    
}
