package soy;

import com.google.common.collect.ImmutableSet;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.data.SoyRecord;
import com.google.template.soy.data.SoyValueHelper;
import com.google.template.soy.data.SoyValueProvider;
import play.mvc.Controller;
import play.twirl.api.Html;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public class Soy extends Controller {

    private final SoyRenderer soyRenderer;
    private final SoyValueHelper soyValueHelper;

    @Inject
    public Soy(final SoyRenderer soyRenderer,
               final SoyValueHelper soyValueHelper) {
        this.soyRenderer = soyRenderer;
        this.soyValueHelper = soyValueHelper;
    }

    public Html html(final String templateName, final Object model) {
        final Set<String> activeDelegatePackages = activeDelegatePackages();
        //final SoyValueProvider convert = soyValueHelper.convert(model);

        return Html.apply(soyRenderer.render(templateName, locale(), request(), response(), new SoyMapData(), activeDelegatePackages));
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
