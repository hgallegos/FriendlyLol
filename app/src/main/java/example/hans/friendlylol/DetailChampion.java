package example.hans.friendlylol;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class DetailChampion extends AppCompatActivity {
    private Champion champion;
    private TextView nombreChampion;
    private ImageView imagenFullCampeon;

    private static final String URL_SPLASH = "http://ddragon.leagueoflegends.com/cdn/img/champion/splash/";
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";

    private Toolbar toolbar;
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
        if(bundle != null){
            AsyncRiotAPI.setAPIKey(API_KEY);
            AsyncRiotAPI.setRegion(Region.LAS);

            AsyncRiotAPI.getChampionByID(new Action<Champion>() {
                @Override
                public void handle(APIException exception) {
                    Log.e("Error","asdasd");
                }

                @Override
                public void perform(final Champion responseData) {
                    getSupportActionBar().setTitle(responseData.getName());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getApplicationContext())
                                    .load(URL_SPLASH+""+responseData.getName()+"_0.jpg")
                                    .into(imagenFullCampeon);
                        }
                    });

                }
            }, bundle.getLong("championID"));
        }

    }
}
