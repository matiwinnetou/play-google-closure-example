package pagelets;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 02/02/2014.
 */
public class WordsModel {

    private List<String> words = new ArrayList<>();

    public WordsModel(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

}
