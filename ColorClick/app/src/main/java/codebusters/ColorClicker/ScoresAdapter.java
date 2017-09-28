package codebusters.ColorClicker;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Erumaru on 26.09.17.
 */

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolder> {
    private static List<Score> scores;
    private Fragment fragment;



    public ScoresAdapter(Fragment fragment, List<Score> scores) {
        this.scores = scores;
        this.fragment = fragment;
    }

    @Override
    public ScoresAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.scores_adapter, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Score score = scores.get(position);
        holder.playerName.setText(score.getName());
        holder.playerScores.setText(score.getScores());
        holder.playerPlace.setText(score.getId());

        if (score.getPhotoUrl() != Score.EMPTY_AVATAR) {
            GlideApp.with(fragment.getActivity())
                    .load(score.getPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.playerAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.playerAvatar)
        ImageView playerAvatar;
        @BindView(R.id.playerName)
        TextView playerName;
        @BindView(R.id.playerScores)
        TextView playerScores;
        @BindView(R.id.playerPlace)
        TextView playerPlace;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
