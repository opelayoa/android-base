package mx.com.tiendas3b.internetcompartido.util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import mx.com.tiendas3b.internetcompartido.R;

public class DialogFactory {

    public static AlertDialog getProgressDialog(Context context, String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_progress, null);
        ((AnimationDrawable)view.findViewById(R.id.animationProgress).getBackground()).start();
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme)
                .setView(view)
                .setCancelable(false)
                .setTitle(title);

        return builder.create();
    }

}
