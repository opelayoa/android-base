package mx.com.tiendas3b.internetcompartido.splash;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    @Provides
    public SplashMVP.Presenter provideLoginPresenter() {
        return new SplashPresenter();
    }

}
