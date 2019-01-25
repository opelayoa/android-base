package mx.com.tiendas3b.internetcompartido.login;

import android.content.Context;

public interface LoginMVP {

    interface Model {

    }

    interface View {
        Context getContext();
    }

    interface Presenter {
        void config();

        void setView(View view);
        boolean login(String username, String password);
    }
}
