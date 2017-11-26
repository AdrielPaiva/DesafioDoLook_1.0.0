package br.edu.ifsp.sbv.desafiodolook;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import br.edu.ifsp.sbv.desafiodolook.CameraActivity;
import br.edu.ifsp.sbv.desafiodolook.FriendsActivity;
import br.edu.ifsp.sbv.desafiodolook.MainActivity;
import br.edu.ifsp.sbv.desafiodolook.R;
import br.edu.ifsp.sbv.desafiodolook.RankingActivity;

/**
 * Created by Guilherme on 10/11/2017.
 */

public class FooterNavigationViewHelper {

    private static final String TAG = "FooterNavigationViewHel";

    public static void setupFooterNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.ico_camera:
                        Intent intent1 = new Intent(context, CameraActivity.class);
                        context.startActivity(intent1);
                        break;
                    case R.id.ico_challenge:
                        Intent intent2 = new Intent(context, MainActivity.class);
                        context.startActivity(intent2);
                        break;
                    case R.id.ico_friends:
                        Intent intent3 = new Intent(context, FriendsActivity.class);
                        context.startActivity(intent3);
                        break;
                    case R.id.ico_ranking:
                        Intent intent4 = new Intent(context, RankingActivity.class);
                        context.startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }
}
