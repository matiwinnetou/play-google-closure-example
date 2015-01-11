package instrastructure;

import com.google.common.collect.ImmutableMap;
import play.mvc.Http;
import soy.RuntimeDataProvider;

import java.util.Map;

public class HelloRuntimeDataProvider implements RuntimeDataProvider {

    @Override
    public void injectData(final Http.Request request,
                           final Http.Response response, Map<String, ?> model,
                           final ImmutableMap.Builder<String, Object> root) {
        root.put("abc", "111");
    }

}
