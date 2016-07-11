package example.hans.friendlylol;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.champion.ChampionStatus;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.exception.APIException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import example.hans.friendlylol.Fragments.ChampionsFragment;
import example.hans.friendlylol.Fragments.MainFragment;
import example.hans.friendlylol.Fragments.StatisticFragment;
import example.hans.friendlylol.Fragments.SummonerFragment;

public class MainActivity extends AppCompatActivity {

    private String version;
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";

    private RelativeLayout mLayout;
    private RecyclerView recyclerView;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if(navigationView != null){
            setupNavigationDrawerContent(navigationView);

        }

        setupNavigationDrawerContent(navigationView);

        //firs fragment
        setFragment(0);


        //verificarInternet();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_main:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                if(actionBar.getTitle().equals("Friendly Lol")){
                                    return true;
                                }else{
                                    setFragment(0);
                                }
                                return true;
                            case R.id.item_navigation_drawer_champions:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                if(actionBar.getTitle().equals("Campeones")){
                                    return true;
                                }else{
                                    setFragment(1);
                                }
                                return true;
                            case R.id.item_navigation_drawer_Summoners:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                if(actionBar.getTitle().equals("Invocadores")){
                                    return true;
                                }else{
                                    setFragment(2);
                                }
                                return true;
                            case R.id.item_navigation_drawer_statistics:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer((GravityCompat.START));
                                if(actionBar.getTitle().equals("Estadisticas")){
                                    return true;
                                }else{
                                    setFragment(3);
                                }
                                return true;
                            case R.id.item_navigation_drawer_settings:
                                menuItem.setChecked(true);
                                Toast.makeText(MainActivity.this, "Launching " + menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.item_navigation_drawer_help_and_feedback:
                                menuItem.setChecked(true);
                                Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                MainFragment mainFragment = new MainFragment();
                fragmentTransaction.replace(R.id.fragment, mainFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ChampionsFragment championsFragment = new ChampionsFragment();
                fragmentTransaction.replace(R.id.fragment, championsFragment);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                SummonerFragment summonerFragment = new SummonerFragment();
                fragmentTransaction.replace(R.id.fragment, summonerFragment);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                StatisticFragment statisticFragment = new StatisticFragment();
                fragmentTransaction.replace(R.id.fragment, statisticFragment);
                fragmentTransaction.commit();
        }
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

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    private void createNotificacion(long when, String data){
        String notificationTitle = "ROTACIÓN SEMANAL DE CAMPEONES FreeToPlay";
        String notificationContent = "Hoy se renuevan los personajes jugables GRATIS";

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        int smalIcon = R.mipmap.ic_launcher;
        //String notificationData = "El dato es: "+data;

        Intent intent = new Intent(getApplicationContext(), NotificationDetailsActivity.class);
        //intent.putExtra(NOTIFICATION_DATA, notificationData);

        intent.setData(Uri.parse("content://"+when));
        PendingIntent pendingIntent = null;
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(
                getApplicationContext())
                .setWhen(when)
                .setContentText(notificationContent)
                .setContentTitle(notificationTitle)
                .setSmallIcon(smalIcon)
                .setAutoCancel(true)
                .setTicker(notificationTitle)
                .setLargeIcon(largeIcon)
                .setDefaults(Notification.DEFAULT_VIBRATE|Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .setLights(Color.RED,2,2)
                ;

        Notification notification = notificationBuilder.build();
        notificationManager.notify((int) when, notification);
    }


}


