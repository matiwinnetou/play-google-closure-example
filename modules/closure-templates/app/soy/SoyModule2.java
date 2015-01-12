package soy;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.template.soy.data.SoyCustomValueConverter;

import java.util.List;

public class SoyModule2 extends AbstractModule {

    private final Gson gson;

    public SoyModule2(final Gson gson) {
        this.gson = gson;
    }

    @Override
    protected void configure() {
        bind(new TypeLiteral<List<SoyCustomValueConverter>>() {})
                .toInstance(ImmutableList.of(new ReflectionCustomDataConverter(gson)));
    }

}
