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

import com.bumptech.glide.Glide;
import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.exception.APIException;

import java.util.ArrayList;
import java.util.List;

import example.hans.friendlylol.Fragments.ResumenFragment;
import example.hans.friendlylol.Fragments.SkillsFragment;
import example.hans.friendlylol.Fragments.SkinsFragment;
import example.hans.friendlylol.Fragments.TipsFragment;

public class DetailChampion extends AppCompatActivity {
    private Champion champion;
    private TextView nombreChampion;
    private ImageView imagenFullCampeon;

    private static final String URL_SPLASH = "http://ddragon.leagueoflegends.com/cdn/img/champion/splash/";
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";

    private Toolbar toolbar;
    long idCampeon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_champion);

        imagenFullCampeon = (ImageView) findViewById(R.id.image_full_campeon);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        TypedValue typedValueColorPrimaryDark = new TypedValue();
        DetailChampion.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {

            idCampeon = bundle.getLong("championID");
            // de la misma forma (bundle) pasar el nombre, para obtener el correcto en ingles
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
                    String nombre = responseData.getName();
                    String nombreSinSignos = nombre.replaceAll("\\W","");
                    String nombreSinEspaciosSignos = nombreSinSignos.replaceAll("\\s","");

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
            }, idCampeon);
        }//fin if

        //esto tenia que ir después de obtner el id, o sino estaba enviando un id que aun no existia
        //  TABS
        final ViewPager viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);
        //  FIN-TABS

        //idCampeon = bundle.getLong("championID");

        //esta funcion no es necesaria, solo muestra toast para saber donde estoy
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        //Toast.makeText(getApplicationContext(), champion.getName()+" - Id: "+idCampeon, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        //es raro que puedas obtener el campeon aqui
                        //aún así si intentas obtener cualquier cosa que esta dentro del response, en alguna de parte
                        //de la intefaz principal no funciona, hazlo dentro del response del Async...

                        //Toast.makeText(getApplicationContext(), champion.getName()+" 2", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        //Toast.makeText(getApplicationContext(), champion.getName()+" 3", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //Toast.makeText(getApplicationContext(), champion.getName()+" 4", Toast.LENGTH_SHORT).show();
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
        //considero que la mejor manera de traspasar info es por el metodo Bundle
        Bundle bundle = new Bundle();
        bundle.putLong("ChampionID", idCampeon);

        /* A la clase DummyFragment le paso un entero (puedo pasarle la id del campeon), en este caso un color (es el color del background del frag)
        pero dentro de la clase se llama al arreglo con los datos que deseo mostrar */
        // DEBO HACER UN ..:: new fragmento(int idCampeon) ::.. PARA CADA TAB, ASI TRAIGO LOS DATOS ASINCRONOS

        // Fragmento Resumen
        ResumenFragment resumenFragment = new ResumenFragment(getResources().getColor(R.color.button_material_dark));
        resumenFragment.setArguments(bundle);
        adapter.addFrag(resumenFragment, "Resumen");

        // Fragmento Tips
        TipsFragment tipsFragment = new TipsFragment(getResources().getColor(R.color.button_material_dark));
        tipsFragment.setArguments(bundle);
        adapter.addFrag(tipsFragment, "Oponentes & Tips");

        // Fragmento habilidades
        SkillsFragment skillsFragment = new SkillsFragment(getResources().getColor(R.color.button_material_dark));
        skillsFragment.setArguments(bundle);
        adapter.addFrag(skillsFragment, "Habilidades");

        // Fragmento skins
        SkinsFragment skinsFragment = new SkinsFragment(getResources().getColor(R.color.button_material_dark));
        skinsFragment.setArguments(bundle);
        adapter.addFrag(skinsFragment, "Skins");

        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        // AQUI SOLO ORGANISO LOS FRAGMENTOS A MOSTRAR EN CADA TAB
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

