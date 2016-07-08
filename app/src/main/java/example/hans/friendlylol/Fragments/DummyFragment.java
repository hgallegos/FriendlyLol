package example.hans.friendlylol.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.core.common.Region;

import example.hans.friendlylol.DetailAdapter;
import example.hans.friendlylol.R;

/**
 * Created by Matias on 07-07-16.
 */
public class DummyFragment extends Fragment {
    int color;
    DetailAdapter adapter;

    //private String version;
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public DummyFragment() {
        //Constructor
    }

    @SuppressLint("ValidFragment")
    public DummyFragment(int color) {
        //this.idCampeon = idCampeon;
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //String strtext = this.getArguments().getString("nombreCampeon");
        View view = inflater.inflate(R.layout.dummy_fragment, container, false);

        AsyncRiotAPI.setAPIKey(API_KEY);
        AsyncRiotAPI.setRegion(Region.LAS);

        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
        frameLayout.setBackgroundColor(color);

        recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //<String> listaDeDatos = new ArrayList<String>();
        //for (int i = 0; i < VersionModel.data.length; i++) {
        //    listaDeDatos.add(VersionModel.data[i]);
        //}

        adapter = new DetailAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }
}
