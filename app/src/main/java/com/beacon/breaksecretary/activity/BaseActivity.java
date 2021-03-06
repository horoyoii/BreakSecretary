package com.beacon.breaksecretary.activity;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    public void show_progress_dialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hide_progress_dialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void show_toast_msg(String msg, Boolean is_short) {
        if (is_short)
            Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    public void show_snackbar_msg(String msg, Boolean is_short) {
        if (is_short)
            Snackbar.make(getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_SHORT).show();
        else
            Snackbar.make(getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_LONG).show();
    }

}
