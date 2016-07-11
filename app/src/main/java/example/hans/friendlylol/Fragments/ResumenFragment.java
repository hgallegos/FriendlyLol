package example.hans.friendlylol.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.exception.APIException;

import example.hans.friendlylol.DetalleAdapter;
import example.hans.friendlylol.DetalleStringAdapter;
import example.hans.friendlylol.R;

/**
 * Created by Matias on 07-07-16.
 */

public class ResumenFragment extends Fragment {
    int color;
    DetalleStringAdapter adapterStringApodo;
    DetalleAdapter adapter;
    DetalleStringAdapter adapterString;
//    DetalleAdapter adapterString;

    //private String version;
    private RecyclerView recyclerViewApodo;
    private LinearLayoutManager linearLayoutManagerApodo;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView2;
    private LinearLayoutManager linearLayoutManager2;
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";

    public ResumenFragment() {
        //Constructor
    }

    @SuppressLint("ValidFragment")
    public ResumenFragment(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //String strtext = this.getArguments().getString("nombreCampeon");
        AsyncRiotAPI.setAPIKey(API_KEY);
        AsyncRiotAPI.setRegion(Region.LAS);

        View view = inflater.inflate(R.layout.dummy_fragment, container, false);

        //final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
        //frameLayout.setBackgroundColor(color);
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.dummyfrag_bg);
        relativeLayout.setBackgroundColor(color);

        /* APODO */
        final TextView titleApodo = (TextView) view.findViewById(R.id.tituloApodo);
        titleApodo.setText("Apodo :");

        recyclerViewApodo = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableviewApodo);
        recyclerViewApodo.setHasFixedSize(true);

        linearLayoutManagerApodo = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerViewApodo.setLayoutManager(linearLayoutManagerApodo);

        /* LISTA DE CLASES */
        final TextView title = (TextView) view.findViewById(R.id.titulo);
        title.setText("Clase :");

        recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        /* SABIDURIA */
        final TextView title2 = (TextView) view.findViewById(R.id.titulo2);
        title2.setText("Sabiduría : ");

        recyclerView2 = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview2);
        recyclerView2.setHasFixedSize(true);

        linearLayoutManager2 = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView2.setLayoutManager(linearLayoutManager2);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            AsyncRiotAPI.getChampionByID(new Action<Champion>() {
                @Override
                public void handle(APIException exception) {
                    Log.e("Error", "ni idea");
                }

                @Override
                public void perform(final Champion responseData) {
                    if(isAdded()){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Title -> apodo del personaje
                                adapterStringApodo = new DetalleStringAdapter(responseData.getTitle());
                                recyclerViewApodo.setAdapter(adapterStringApodo);
                                // Tags -> Clase
                                adapter = new DetalleAdapter(responseData.getTags());
                                recyclerView.setAdapter(adapter);
                                // Blurb -> Reseumen de Sabiduria, Lore -> Sabiduria completa
                                adapterString = new DetalleStringAdapter(responseData.getLore());
                                recyclerView2.setAdapter(adapterString);
                            }
                        });
                    }

                }
            }, bundle.getLong("ChampionID"));
        }

        return view;
    }
}