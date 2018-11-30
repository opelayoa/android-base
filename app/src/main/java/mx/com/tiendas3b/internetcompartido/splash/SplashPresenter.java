package mx.com.tiendas3b.internetcompartido.splash;

public class SplashPresenter implements SplashMVP.Presenter {


    private SplashMVP.View view;

    public SplashPresenter() {

    }

    @Override
    public void config() {

    }

    @Override
    public void setView(SplashMVP.View view) {
        this.view = view;
    }
}
