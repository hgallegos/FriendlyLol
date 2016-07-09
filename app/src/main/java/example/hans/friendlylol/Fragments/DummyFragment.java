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

import example.hans.friendlylol.DetailAdapter;
import example.hans.friendlylol.R;

/**
 * Created by Matias on 07-07-16.
 */

public class DummyFragment extends Fragment {
    int color;
    DetailAdapter adapter;

    //private String version;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public DummyFragment() {
        //Constructor
    }

    @SuppressLint("ValidFragment")
    public DummyFragment(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //String strtext = this.getArguments().getString("nombreCampeon");
        View view = inflater.inflate(R.layout.dummy_fragment, container, false);

        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
        frameLayout.setBackgroundColor(color);

        recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //list<String> listaDeDatos = new ArrayList<String>();
        //for (int i = 0; i < VersionModel.data.length; i++) {
        //    listaDeDatos.add(VersionModel.data[i]);
        //}

        //List<String> datosCampeon = new ArrayList<>();
        //adapter = new DetailAdapter(datosCampeon);

        // Dentro de ..:: DetailAdapter ::.. hay un array[] fijo que ingresa datos para mostrar (no se utiliza List<String>)
        adapter = new DetailAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }
}
