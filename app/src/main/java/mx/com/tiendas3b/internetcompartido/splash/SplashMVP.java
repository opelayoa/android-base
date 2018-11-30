package mx.com.tiendas3b.internetcompartido.splash;

import android.content.Context;

public interface SplashMVP {

    interface Model {

    }

    interface View {
        Context getContext();
    }

    interface Presenter {
        void config();

        void setView(View view);
    }
}
