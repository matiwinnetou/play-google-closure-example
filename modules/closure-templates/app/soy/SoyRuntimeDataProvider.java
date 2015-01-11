package soy;

import com.google.common.collect.ImmutableMap;
import play.mvc.Http;

import java.util.Map;

public interface SoyRuntimeDataProvider {

    ImmutableMap<String, ?> injectData(Http.Request request, Http.Response response, Map<String, ?> model);

}
