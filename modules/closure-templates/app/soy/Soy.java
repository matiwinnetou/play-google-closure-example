package soy;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.data.SoyRecord;
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

    public Html html(final String templateName, final SoyRecord model) {
        return Html.apply(soyRenderer.render(templateName, locale(), request(), response(), model, ImmutableSet.of()));
    }

    public Html html(final String templateName) {
        return Html.apply(soyRenderer.render(templateName, locale(), request(), response(), new SoyMapData()));
    }

    private Locale locale() {
        return request().acceptLanguages().stream()
                .findFirst().map(lang -> new Locale(lang.language()))
                .orElse(Locale.ENGLISH);
    }

}
