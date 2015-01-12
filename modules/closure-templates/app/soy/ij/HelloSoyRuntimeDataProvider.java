package soy.ij;

import com.google.common.collect.ImmutableMap;
import com.google.template.soy.data.SoyRecord;
import play.mvc.Http;

public class HelloSoyRuntimeDataProvider implements SoyRuntimeDataProvider {

    @Override
    public ImmutableMap<String, ?> injectData(final Http.Request request,
                                              final Http.Response response,
                                              final SoyRecord model) {
        return ImmutableMap.of("abc", "111");
    }

}
