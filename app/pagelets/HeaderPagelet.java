package pagelets;

import play.libs.F;

import javax.inject.Singleton;

@Singleton
public class HeaderPagelet {

    public F.Promise<HeaderModel> invoke() {

        final HeaderModel headerModel = new HeaderModel();
        headerModel.setTitle("Test Title");

        return F.Promise.pure(headerModel);
    }

}
