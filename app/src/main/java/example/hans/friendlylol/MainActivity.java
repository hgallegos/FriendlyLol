package example.hans.friendlylol;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.champion.ChampionStatus;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.exception.APIException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private String version;
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";

    private RelativeLayout mLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = (RelativeLayout) findViewById(R.id.main_layout);

        AsyncRiotAPI.setRegion(Region.LAS);
        AsyncRiotAPI.setAPIKey(API_KEY);

        verificarInternet();
    }

    public void verificarInternet(){
        if(isOnline()){
            listarCampeones();
        }else{
            snackBarNoInternet();
        }
    }

    private void listarCampeones(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,5,GridLayoutManager.VERTICAL,false));

        AsyncRiotAPI.getChampionStatuses(new Action<Map<Champion, ChampionStatus>>() {
            @Override
            public void handle(APIException exception) {
                Log.e("Error campeones", "ni idea");
            }

            @Override
            public void perform(final Map<Champion, ChampionStatus> responseData) {
                final List<ChampionStatus> championsFree;
                version = RiotAPI.getVersions().get(0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ChampionStatusAdapter championAdapterStatus =
                                new ChampionStatusAdapter(getApplicationContext(),new ArrayList<ChampionStatus>(responseData.values()), version);
                        recyclerView.setAdapter(championAdapterStatus);
                    }
                });
            }
        },true);

       /* AsyncRiotAPI.getChampions(new Action<List<Champion>>() {
            @Override
            public void handle(APIException exception) {
                Log.e("Error campeones", "ni idea");
            }

            @Override
            public void perform(List<Champion> responseData) {
                final List<Champion> champions = new ArrayList<Champion>();

                int cont = 0;
                for (int i = 0; i<responseData.size();i++){
                    if(responseData.get(i).getStatus().getFreeToPlay()) {
                        champions.add(responseData.get(i));
                        Log.d("Gratis",""+responseData.get(i).getStatus().getFreeToPlay());
                        cont++;
                        if(cont == 11)
                            break;
                    }
                }
                Collections.sort(champions, new Comparator<Champion>() {
                    @Override
                    public int compare(Champion c1, Champion c2) {
                        return c1.getName().compareTo(c2.getName());
                    }
                });

                version = RiotAPI.getVersions().get(0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ChampionAdapter championAdapter = new ChampionAdapter(getApplicationContext(),champions, version);
                        recyclerView.setAdapter(championAdapter);
                    }
                });
            }
        });*/
    }

    private void snackBarPermiso(){
        Snackbar snackbar = Snackbar
                .make(mLayout, R.string.no_permission, Snackbar.LENGTH_LONG)
                .setAction(R.string.try_permission, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openSettings();
                    }
                });
        snackbar.show();
    }

    private void snackBarInfoPermiso(){
        Snackbar snackbar = Snackbar
                .make(mLayout, R.string.permission_internet, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    private void snackBarNoInternet(){
        Snackbar snackbar = Snackbar
                .make(mLayout, R.string.no_internet, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        verificarInternet();
                    }
                });
        snackbar.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:"+getPackageName()));
        startActivity(intent);
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}


