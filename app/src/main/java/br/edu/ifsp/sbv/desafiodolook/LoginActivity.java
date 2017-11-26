package br.edu.ifsp.sbv.desafiodolook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import br.edu.ifsp.sbv.desafiodolook.dao.DAO;
import br.edu.ifsp.sbv.desafiodolook.dao.UserInfoDAO;
import br.edu.ifsp.sbv.desafiodolook.modelo.UserInfo;
import br.edu.ifsp.sbv.desafiodolook.request.WebserviceTask;

/**
 * Created by Guilherme on 15/11/2017.
 */

public class LoginActivity extends Activity{

    private static final String TAG = "LoginActivity";

    private Context mContext = LoginActivity.this;
    private EditText edtUser;
    private EditText edtPassword;
    private UserInfoDAO userInfoDAO;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: starting in Login.");

        userInfoDAO = new UserInfoDAO(getApplicationContext());

        //Set fonts of texts
        ImageView imgLogo = (ImageView)findViewById(R.id.imgLogoLogin);
        Button btnLogar = (Button)findViewById(R.id.btnLogar);
        TextView txtLinkBackLogin = (TextView) findViewById(R.id.txtLinkBackLogin);
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        //Set fonts of texts
        txtLinkBackLogin.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gravitybold.otf"));
        btnLogar.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/robotoblack.ttf"));
        edtUser.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/robotolight.ttf"));
        edtPassword.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/robotolight.ttf"));

        //Set Listeners of elements
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if(login(v)) {
                    SharedPreferences preferences = getSharedPreferences("mYpREFERENCES_DDL", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLogged", true);
                    editor.commit();
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);

                //}else
                    //exibirMensagem("Usuário não registrado!");
            }
        });

        txtLinkBackLogin.setOnClickListener(new View.OnClickListener() {
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

    private void exibirMensagem(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private boolean login(View v) {
        JSONObject data = new JSONObject();
        Integer intCount = 0;

        try
        {
            data.put("email_user", edtUser.getText().toString());
            data.put("password_user", edtPassword.getText().toString());

            new WebserviceTask(this, new WebserviceTask.AsyncResponse() {
                @Override
                public void processFinish(Context context, String result) {
                    if(result != null) {
                        //txtInfo.setText(result);
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                    }
                }
                //}).execute("http://www.appointweb.com.br/teste_webservice/index.php", data);
                //}).execute("http://www.appointweb.com.br/desafioDoLookApp/controller/users/create_user.php", data);
            }).execute("http://www.appointweb.com.br/desafioDoLookApp/controller/users/get_user.php", data);

            while(!MainActivity.dataResult.has("return")){
                intCount++;
            }

            if(!MainActivity.dataResult.get("return").toString().equals("false")){
                userInfo = new UserInfo();

                userInfo.setUserInfoID(Integer.parseInt(MainActivity.dataResult.get("userInfoID").toString()));
                userInfo.setEmail(MainActivity.dataResult.get("email").toString());
                userInfo.setUserName(MainActivity.dataResult.get("userName").toString() + "TT");
                userInfo.setDeviceID(MainActivity.dataResult.get("deviceID").toString());
                userInfo.setStatus(Boolean.parseBoolean(MainActivity.dataResult.get("status").toString()));

                if( userInfoDAO.update(userInfo)) {
                    return true;

                }else if( userInfoDAO.save(userInfo)) {
                    return true;

                }else
                    return false;
            }else
                return false;

        } catch (Exception ex) {
            Toast.makeText(this, "Erro: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
