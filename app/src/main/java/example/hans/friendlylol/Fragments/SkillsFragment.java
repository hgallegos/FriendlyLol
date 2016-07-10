package example.hans.friendlylol.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.hans.friendlylol.R;

/**
 * Created by hans6 on 09-07-2016.
 */
public class SkillsFragment extends Fragment {

    public SkillsFragment(){
        //esto es necesario por alguna razón, aunque no lo uso en los otros fragment que cree xD
    }

    @Override
    public void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        //return the layout for this fragment
        return inflater.inflate(R.layout.fragment_skills,container,false);
    }

}
