package mx.com.tiendas3b.internetcompartido.root;

import android.app.Application;

import com.datami.smi.SmiSdk;

import java.util.List;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        String apiKey = "ak-2987a13e-ecb0-4b5e-946d-d22d395c3547";
        String userId = "opelayoa";
        List exclusionDomains = null;
        int iconId = -1;
        boolean showMessaging = true;

        SmiSdk.initSponsoredData(apiKey, this, userId, iconId, showMessaging, exclusionDomains);

        component = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }


}
