package com.example.zpi.alerts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.example.zpi.MainActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Kamil
 * Date: 08.12.12
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class ServerAlert {
    Context context;
    public ServerAlert(Context context){
        this.context=context;
    }
    public AlertDialog.Builder zwrocAlert(){
    AlertDialog.Builder builder=new AlertDialog.Builder(context);
    builder.setTitle("Brak połączenia z serwerem");
    builder.setMessage("Wynikł problem połączenia z serwerem . Sprawdz czy adres serwera jest wprowadzony poprawnie, bądź zrestartuj serwer");
    builder.setPositiveButton("OK", new Dialog.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();

        }
    });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.cancel();
            }
        });
    builder.create().show();
        return builder;
    }
}