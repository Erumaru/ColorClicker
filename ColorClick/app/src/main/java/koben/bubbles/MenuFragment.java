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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuFragment extends FragmentSwitcher implements OnClickListener
{
    @BindView(R.id.settingsButton) Button settingsButton;
    @BindView(R.id.historyButton) Button historyButton;
    @BindView(R.id.newGameButton) Button newGameButton;

	private View viewRoot;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		viewRoot = inflater.inflate(R.layout.fragment_menu, parent, false);
        ButterKnife.bind(this, viewRoot);
        settingsButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);
        newGameButton.setOnClickListener(this);

		return viewRoot;
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
            case R.id.settingsButton:
                SettingsFragment SF = new SettingsFragment();
                switchFrag(SF);
                break;
		}
	}

}