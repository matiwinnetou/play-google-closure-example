package soy;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.template.soy.data.SoyCustomValueConverter;
import com.google.template.soy.data.SoyListData;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.data.SoyValueConverter;
import com.google.template.soy.data.SoyValueProvider;
import com.google.template.soy.data.restricted.NullData;

public class ReflectionCustomDataConverter implements SoyCustomValueConverter {

    private final Gson gson;

    @Inject
    public ReflectionCustomDataConverter(final Gson gson) {
        this.gson = gson;
    }

    @Override
    public SoyValueProvider convert(final SoyValueConverter valueConverter, final Object obj) {
        return jsonToSoyData(gson.toJsonTree(obj), valueConverter);
    }

    private static @Nullable SoyValueProvider jsonToSoyData(final JsonElement el, final SoyValueConverter soyValueConverter) {
        if (el == null || el.isJsonNull()) {
            return NullData.INSTANCE;
        } 
        if (el.isJsonObject()) {
            final SoyMapData map = new SoyMapData();
            el.getAsJsonObject().entrySet().forEach(entry -> {
                map.put(entry.getKey(), jsonToSoyData(entry.getValue(), soyValueConverter));
            });
            return map;
        } 
        if (el.isJsonArray()) {
            final SoyListData list = new SoyListData();
            el.getAsJsonArray().forEach(item -> {
                list.add(jsonToSoyData(item, soyValueConverter));
            });
            return list;
        }
        if (el.isJsonPrimitive()) {
            final JsonPrimitive primitive = el.getAsJsonPrimitive();
            if (primitive.isString()) {
                return soyValueConverter.convert(primitive.getAsString());
            } 
            if (primitive.isBoolean()) {
                return soyValueConverter.convert(primitive.getAsBoolean());
            } 
            if (primitive.isNumber()) {
                if (primitive.getAsDouble() == primitive.getAsInt()) {
                    return soyValueConverter.convert(primitive.getAsInt());
                }

                return soyValueConverter.convert(primitive.getAsDouble());
            }
        }

        return null;
    }

}
