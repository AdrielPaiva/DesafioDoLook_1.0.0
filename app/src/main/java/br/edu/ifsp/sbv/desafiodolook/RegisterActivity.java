package br.edu.ifsp.sbv.desafiodolook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Guilherme on 21/11/2017.
 */

public class RegisterActivity extends Activity {

    private static final String TAG = "LoginActivity";

    private Context mContext = RegisterActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG, "onCreate: starting in Register.");

        //Set fonts of texts
        ImageView imgLogo = (ImageView)findViewById(R.id.imgLogoRegister);
        Button btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        TextView txtLinkBackRegister = (TextView) findViewById(R.id.txtLinkBackRegister);
        EditText edtEmail = (EditText) findViewById(R.id.edtEmail);
        EditText edtUserRegister = (EditText) findViewById(R.id.edtUserRegister);
        EditText edtPasswordRegister = (EditText) findViewById(R.id.edtPasswordRegister);
        EditText edtPasswordConfirm = (EditText) findViewById(R.id.edtPasswordConfirm);

        //Set fonts of texts
        txtLinkBackRegister.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gravitybold.otf"));
        btnRegistrar.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/robotoblack.ttf"));
        edtEmail.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/robotolight.ttf"));
        edtUserRegister.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/robotolight.ttf"));
        edtPasswordRegister.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/robotolight.ttf"));
        edtPasswordConfirm.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/robotolight.ttf"));

        //Set Listeners of elements
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("mYpREFERENCES_DDL", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLogged", true);
                editor.commit();
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
            }
        });

        txtLinkBackRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HomeActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(mContext, HomeActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
