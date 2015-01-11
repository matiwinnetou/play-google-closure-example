package soy;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.msgs.SoyMsgBundleLoader;
import com.google.template.soy.shared.SoyIdRenamingMap;
import com.google.template.soy.tofu.SoyTofu;

import javax.inject.Inject;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class SoyRenderer {

    private final SoyCompiler soyCompiler;
    private final SoyTemplateLoader soyTemplateLoader;
    private final SoyIdRenamingMap soyIdRenamingMap;
    private final SoyMsgBundleLoader soyMsgBundleLoader;

    @Inject
    public SoyRenderer(final SoyCompiler soyCompiler,
                       final SoyTemplateLoader soyTemplateLoader,
                       final SoyMsgBundleLoader soyMsgBundleLoader,
                       final SoyIdRenamingMap soyIdRenamingMap) {
        this.soyCompiler = soyCompiler;
        this.soyTemplateLoader = soyTemplateLoader;
        this.soyMsgBundleLoader = soyMsgBundleLoader;
        this.soyIdRenamingMap = soyIdRenamingMap;
    }

    public String render(final String templateName,
                         final Locale locale,
                         final Map<String, ?> model,
                         final Map<String, ?> ijData,
                         final Set<String> activeDelegatePackages) {
        final SoyFileSet soyFileSet = soyTemplateLoader.build();
        final SoyTofu soyTofu = soyCompiler.compile(soyFileSet);
        final SoyMsgBundle soyMsgBundle = soyMsgBundleLoader.getSoyMsgBundleForLocale(locale);

        return soyTofu.newRenderer(templateName)
                .setActiveDelegatePackageNames(activeDelegatePackages)
                .setIdRenamingMap(soyIdRenamingMap)
                .setIjData(ijData)
                .setData(model)
                .setMsgBundle(soyMsgBundle)
                .render();
    }

    public String render(final String templateName,
                         final Locale locale,
                         final Map<String, ?> model,
                         final Map<String, ?> ijData) {
        return render(templateName, locale, model, ijData, ImmutableSet.of());
    }

}
