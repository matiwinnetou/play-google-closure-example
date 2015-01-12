package soy.ij;

import com.google.common.collect.ImmutableMap;
import com.google.template.soy.data.SoyRecord;
import play.mvc.Http;

public interface SoyRuntimeDataProvider {

    ImmutableMap<String, ?> injectData(Http.Request request, Http.Response response, SoyRecord model);

}
