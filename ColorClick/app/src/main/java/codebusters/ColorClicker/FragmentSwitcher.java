package codebusters.ColorClicker;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSwitcher extends Fragment
{
    private Context con;
    private View viewer;
    private static final String TAG = "FragmentSwitcher";
    protected FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        con = getActivity().getApplicationContext();

        fm = getActivity().getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        con = getActivity();

        return viewer;
    }

    public void switchFrag(Fragment frag)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        getFragmentManager().popBackStack();
        transaction.replace(R.id.fragment_container_root, frag);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}