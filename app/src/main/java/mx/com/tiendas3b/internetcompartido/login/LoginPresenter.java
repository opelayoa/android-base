package mx.com.tiendas3b.internetcompartido.login;

public class LoginPresenter implements LoginMVP.Presenter {


    private LoginMVP.View view;

    public LoginPresenter() {

    }

    @Override
    public void config() {

    }

    @Override
    public void setView(LoginMVP.View view) {
        this.view = view;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }
}
