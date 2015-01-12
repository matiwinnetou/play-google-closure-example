package controllers;

import com.google.common.collect.ImmutableList;
import pagelets.WordsModel;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;
import soy.Soy;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.Optional;

@Singleton
public class Application extends Controller {

    private final Soy soy;

    @Inject
    public Application(final Soy soy) {
        this.soy = soy;
    }

    public F.Promise<Result> index() throws IOException {
        final Html html = soy.html("soy.example.index4", new WordsModel(ImmutableList.of("word1", "word2"), Optional.empty()));

        return F.Promise.pure(ok(html));
    }

}
