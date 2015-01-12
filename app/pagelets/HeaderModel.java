package pagelets;

import java.util.Optional;

public class HeaderModel {

    private Optional<String> title;

    public Optional<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Optional.of(title);
    }

}
