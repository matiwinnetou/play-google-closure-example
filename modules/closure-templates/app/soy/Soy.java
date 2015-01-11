package soy;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import play.mvc.Controller;
import play.twirl.api.Html;

import javax.inject.Inject;
import java.util.Locale;

public class Soy extends Controller {

    private final SoyRenderer soyRenderer;

    @Inject
    public Soy(final SoyRenderer soyRenderer) {
        this.soyRenderer = soyRenderer;
    }

    public Html html(final String templateName, final Object model) {
        return Html.apply(soyRenderer.render(templateName, locale(), request(), response(), ImmutableMap.of(), ImmutableSet.of()));
    }

    public Html html(final String templateName) {
        return Html.apply(soyRenderer.render(templateName, locale(), request(), response(), ImmutableMap.of()));
    }

    private Locale locale() {
        return request().acceptLanguages().stream()
                .findFirst().map(lang -> new Locale(lang.language()))
                .orElse(Locale.ENGLISH);
    }

}
