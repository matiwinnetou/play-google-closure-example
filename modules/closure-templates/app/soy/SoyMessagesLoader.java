package soy;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.msgs.SoyMsgBundleHandler;
import com.google.template.soy.msgs.SoyMsgBundleLoader;
import com.google.template.soy.xliffmsgplugin.XliffMsgPlugin;
import play.Application;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class SoyMessagesLoader implements SoyMsgBundleLoader {

    private final Application application;

    private final SoyMsgBundleHandler soyMsgBundleHandler;

    private final Cache<Locale, SoyMsgBundle> localeSoyMsgBundleMap = CacheBuilder.newBuilder().build();

    @Inject
    public SoyMessagesLoader(final Application application) {
        this.application = application;
        soyMsgBundleHandler = new SoyMsgBundleHandler(new XliffMsgPlugin());
    }

    @Override
    public SoyMsgBundle getSoyMsgBundleForLocale(final Locale locale) {
        return application.isDev() ? getDev(locale) : getNormal(locale);
    }

    private SoyMsgBundle getDev(final Locale locale) {
        return findBundle(locale);
    }

    private SoyMsgBundle getNormal(final Locale locale) {
        try {
            return localeSoyMsgBundleMap.get(locale, () -> findBundle(locale));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private SoyMsgBundle findBundle(final Locale locale) {
        return Resources.findXliffs(Resources.resources(application))
                .filter(url -> url.getFile().endsWith(String.format("_%s.xlf", locale.getLanguage())))
                .findFirst()
                .map(url -> createFromUrl(url))
                .orElse(SoyMsgBundle.EMPTY);
    }

    private SoyMsgBundle createFromUrl(final URL url) {
        try {
            return soyMsgBundleHandler.createFromResource(url);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}
