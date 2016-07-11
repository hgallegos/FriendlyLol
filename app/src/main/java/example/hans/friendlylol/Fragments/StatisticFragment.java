package example.hans.friendlylol.Fragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.hans.friendlylol.MainActivity;
import example.hans.friendlylol.R;

/**
 * Created by hans6 on 07-07-2016.
 */
public class StatisticFragment extends Fragment {
    private String version;
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";

    private CoordinatorLayout mLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView summonerResult;
    private EditText searchText;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        //Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_statistics, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Estadisticas");

        setHasOptionsMenu(true);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //mLayout = (CoordinatorLayout) view.findViewById(R.id.mainLayout);




        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new MejorPorcentajeFragment(), "Mejor % de Victorias");
        adapter.addFragment(new PeorPorcentajeFragment(), "Peor % de Victorias");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private String Summoner;

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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
