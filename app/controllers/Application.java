package controllers;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;
import soy.Soy;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class Application extends Controller {

//    @Inject
//    private soy.Soy soy;

//    @Inject
//    private HeaderPagelet headerPagelet;
//
//    @Inject
//    private WordsPagelet wordsPagelet;

    private final Soy soy;

    @Inject
    public Application(final Soy soy) {
        this.soy = soy;
    }

    public F.Promise<Result> index() throws IOException {
        final Html html = soy.html("soy.example.index3");

        return F.Promise.pure(ok(html));
//        final F.Promise<HeaderModel> headerModelP = headerPagelet.invoke();
//        final F.Promise<WordsModel> wordsModelP = wordsPagelet.invoke();
//
//        return Promises.zip(headerModelP, wordsModelP, (header, words)
//                -> ok(Html.apply(soy.html("pages.index", new IndexPageModel(header, words)))));
    }

//    public F.Promise<Result> index() {
//        return F.Promise.pure(ok("hello world"));
////        final F.Promise<HeaderModel> headerModelP = headerPagelet.invoke();
////        final F.Promise<WordsModel> wordsModelP = wordsPagelet.invoke();
////
////        return Promises.zip(headerModelP, wordsModelP, (header, words)
////                -> ok(Html.apply(soy.html("pages.index", new IndexPageModel(header, words)))));
//    }

}
