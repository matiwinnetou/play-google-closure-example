package pagelets;

import com.google.common.collect.Lists;
import play.libs.F;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class WordsPagelet {

    public F.Promise<WordsModel> invoke() {
        return F.Promise.pure(new WordsModel(Lists.newArrayList("test", "test2"), Optional.<String>empty()));
    }

    public F.Promise<WordsModel> invoke2() {
        return F.Promise.pure(new WordsModel(Lists.newArrayList("test", "test2"), Optional.of("test")));
    }

}
