package pagelets;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by mati on 02/02/2014.
 */
public class WordsModel {

    private List<String> words = new ArrayList<>();
    
    private Optional<String> hello = Optional.empty();

    public WordsModel(List<String> words, Optional<String> hello) {
        this.words = words;
        this.hello = hello;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public Optional<String> getHello() {
        return hello;
    }

}
