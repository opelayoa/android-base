package mx.com.tiendas3b.internetcompartido.login;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    public LoginMVP.Presenter provideLoginPresenter() {
        return new LoginPresenter();
    }

}
