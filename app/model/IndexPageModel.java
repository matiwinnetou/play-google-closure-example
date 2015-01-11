package model;

import pagelets.HeaderModel;
import pagelets.WordsModel;

public class IndexPageModel {

    private HeaderModel headerModel;
    private WordsModel wordsModel;

    public IndexPageModel(final HeaderModel headerModel, final WordsModel wordsModel) {
        this.headerModel = headerModel;
        this.wordsModel = wordsModel;
    }

    public WordsModel getWordsModel() {
        return wordsModel;
    }

    public HeaderModel getHeaderModel() {
        return headerModel;
    }

}
