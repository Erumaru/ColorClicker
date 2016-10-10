package koben.bubbles;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

public class MenuFragment extends FragmentSwitcher implements OnClickListener
{
	private View viewRoot;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		viewRoot = inflater.inflate(R.layout.fragment_menu, parent, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		init();

		return viewRoot;
	}


	private void init()
	{
		Button newGameButton = (Button) viewRoot.findViewById(R.id.newGameButton);
		newGameButton.setOnClickListener(this);
		Button historyButton = (Button) viewRoot.findViewById(R.id.historyButton);
		historyButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
        {
			case R.id.newGameButton:
				GameFragment GF = new GameFragment();
				switchFrag(GF);
				break;
			case R.id.historyButton:
				HistoryFragment LF = new HistoryFragment();
				switchFrag(LF);
				break;
		}
	}

	@Override
	public void onResume()
	{
		Log.i(TAG, "onResume");
		super.onResume();
		init();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		Log.i(TAG, "onPause");
	}

	@Override
	public void onStop()
	{
		super.onStop();
		Log.i(TAG, "onStop");
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}

	@Override
	public void onStart()
	{
		super.onStart();
	}
}