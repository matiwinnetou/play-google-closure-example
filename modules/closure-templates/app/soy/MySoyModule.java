package soy;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.template.soy.data.SoyCustomValueConverter;

import java.util.List;

public class MySoyModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(convertersLiteral()).toInstance(ImmutableList.of());
    }
    
    private TypeLiteral<List<SoyCustomValueConverter>> convertersLiteral() {
        final TypeLiteral<List<SoyCustomValueConverter>> convertersList = new TypeLiteral<List<SoyCustomValueConverter>>() {};

        return convertersList;
    }
    
}
