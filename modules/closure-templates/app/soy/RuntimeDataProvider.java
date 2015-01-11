package soy;

import com.google.common.collect.ImmutableMap;
import play.mvc.Http;

import java.util.Map;

public interface RuntimeDataProvider {

    void injectData(Http.Request request,
                    Http.Response response,
                    Map<String, ?> model,
                    ImmutableMap.Builder<String, Object> root);

}
