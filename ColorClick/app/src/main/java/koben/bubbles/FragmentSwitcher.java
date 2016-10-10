package koben.bubbles;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSwitcher extends Fragment
{
    private Context con;
    private View viewer;
    protected final String TAG = this.getClass().getSimpleName().toString();
    protected FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // setRetainInstance(true);

        con = getActivity().getApplicationContext();

        Log.i("F_Base", "onCreate");

        fm = getActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i("F_Base", "onCreateView");

        con = getActivity();

        return viewer;
    }

    public void switchFrag(Fragment frag)
    {
        //m.beginTransaction().add(R.id.fragment_container_root, frag).addToBackStack(null).commit();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        getFragmentManager().popBackStack();
        transaction.replace(R.id.fragment_container_root, frag);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
    }
}