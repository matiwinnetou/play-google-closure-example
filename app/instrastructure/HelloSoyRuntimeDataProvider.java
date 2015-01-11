package instrastructure;

import com.google.common.collect.ImmutableMap;
import play.mvc.Http;
import soy.SoyRuntimeDataProvider;

import java.util.Map;

public class HelloSoyRuntimeDataProvider implements SoyRuntimeDataProvider {

    @Override
    public ImmutableMap<String, ?> injectData(final Http.Request request,
                                              final Http.Response response,
                                              final Map<String, ?> model) {
        return ImmutableMap.of("abc", "111");
    }

}
