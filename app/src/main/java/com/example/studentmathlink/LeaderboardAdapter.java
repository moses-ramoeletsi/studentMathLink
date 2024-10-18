//package com.example.studentmathlink;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import java.util.List;
//
//public class LeaderboardAdapter  extends ArrayAdapter<UserScore> {
//    public LeaderboardAdapter(Context context, List<UserScore> userScores) {
//        super(context, 0, userScores);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.leaderboard_item, parent, false);
//        }
//
//        UserScore userScore = getItem(position);
//
//        TextView positionTextView = convertView.findViewById(R.id.positionTextView);
//        TextView userNameTextView = convertView.findViewById(R.id.userNameTextView);
//        TextView scoreTextView = convertView.findViewById(R.id.scoreTextView);
//
//        positionTextView.setText(String.valueOf(userScore.getPosition()));
//        userNameTextView.setText(userScore.getUserName());
//        scoreTextView.setText(String.valueOf(userScore.getScore()));
//
//        if (userScore.isCurrentUser()) {
//            convertView.setBackgroundColor(Color.YELLOW);
//        } else {
//            convertView.setBackgroundColor(Color.TRANSPARENT);
//        }
//
//        return convertView;
//    }
//}
package com.example.studentmathlink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private Context context;
    private List<UserScore> userScores;
    private OnItemClickListener listener;

    public LeaderboardAdapter(Context context, List<UserScore> userScores) {
        this.context = context;
        this.userScores = userScores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserScore userScore = userScores.get(position);

        holder.positionTextView.setText(String.valueOf(userScore.getPosition()));
        holder.userNameTextView.setText(userScore.getUserName());
        holder.scoreTextView.setText(String.valueOf(userScore.getScore()));

        if (userScore.isCurrentUser()) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.currentUserBackground));
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(userScore);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userScores.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(UserScore userScore);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView positionTextView;
        TextView userNameTextView;
        TextView scoreTextView;
        MaterialCardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            positionTextView = itemView.findViewById(R.id.positionTextView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            cardView = (MaterialCardView) itemView;
        }
    }
}