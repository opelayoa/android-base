package mx.com.tiendas3b.internetcompartido.root;

import javax.inject.Singleton;

import dagger.Component;
import mx.com.tiendas3b.internetcompartido.splash.SplashActivity;
import mx.com.tiendas3b.internetcompartido.splash.SplashModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        SplashModule.class})
public interface ApplicationComponent {

    void inject(SplashActivity target);

}
