package pagelets;

import com.google.common.collect.Lists;
import play.libs.F;

import javax.inject.Singleton;

@Singleton
public class WordsPagelet {

    public F.Promise<WordsModel> invoke() {
        return F.Promise.pure(new WordsModel(Lists.newArrayList("test", "test2")));
    }

}
