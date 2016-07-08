package example.hans.friendlylol;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.exception.APIException;

import java.util.ArrayList;
import java.util.List;

import example.hans.friendlylol.Fragments.DummyFragment;

public class DetailChampion extends AppCompatActivity {
    private Champion champion;
    private TextView nombreChampion;
    private ImageView imagenFullCampeon;

    private static final String URL_SPLASH = "http://ddragon.leagueoflegends.com/cdn/img/champion/splash/";
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";

    private Toolbar toolbar;
    Bundle bundleAenviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_champion);

        imagenFullCampeon = (ImageView) findViewById(R.id.image_full_campeon);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //  TABS
        final ViewPager viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);
        //  FIN-TABS


        TypedValue typedValueColorPrimaryDark = new TypedValue();
        DetailChampion.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            AsyncRiotAPI.setAPIKey(API_KEY);
            AsyncRiotAPI.setRegion(Region.LAS);

            AsyncRiotAPI.getChampionByID(new Action<Champion>() {
                @Override
                public void handle(APIException exception) {
                    Log.e("Error", "ni idea");
                }

                @Override
                public void perform(final Champion responseData) {
                    champion = responseData;
                    getSupportActionBar().setTitle(responseData.getName());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getApplicationContext())
                                    .load(URL_SPLASH + "" + responseData.getName() + "_0.jpg")
                                    .into(imagenFullCampeon);
                        }
                    });

                }
            }, bundle.getLong("championID"));
        }//fin if

        bundleAenviar = new Bundle();
        bundleAenviar.putString("nombreCampeon", champion+" probando");

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        Toast.makeText(getApplicationContext(), champion.getName()+" 1", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), champion.getName()+" 2", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), champion.getName()+" 3", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), champion.getName()+" 4", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });//fin tabLayout

    }//fin onCreate


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //DummyFragment prueba = new DummyFragment();
        //prueba.setArguments(bundleAenviar);

        //adapter.addFrag(prueba, "Resumen");
        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), "Resumen");
        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), "Oponentes & Tips");
        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), "Habilidades");
        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), "Skins");
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}//fin
