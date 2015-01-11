package soy;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.data.SoyRecord;
import play.mvc.Controller;
import play.twirl.api.Html;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public class Soy extends Controller {

    private final SoyRenderer soyRenderer;

    @Inject
    public Soy(final SoyRenderer soyRenderer) {
        this.soyRenderer = soyRenderer;
    }

    public Html html(final String templateName, final SoyRecord model) {
        final Set<String> activeDelegatePackages = activeDelegatePackages();

        return Html.apply(soyRenderer.render(templateName, locale(), request(), response(), model, activeDelegatePackages));
    }

    public Html html(final String templateName) {
        final Set<String> activeDelegatePackages = activeDelegatePackages();

        return Html.apply(soyRenderer.render(templateName, locale(), request(), response(), new SoyMapData(), activeDelegatePackages));
    }

    private Locale locale() {
        return request().acceptLanguages().stream()
                .findFirst().map(lang -> new Locale(lang.language()))
                .orElse(Locale.ENGLISH);
    }

    private Set<String> activeDelegatePackages() {
        return Optional.ofNullable(request().getQueryString("soyActivePackages"))
                .map(str -> str.split(","))
                .map(values -> Arrays.asList((values)))
                .map(list -> ImmutableSet.<String>copyOf(list))
                .orElse(ImmutableSet.of());
    }

}
