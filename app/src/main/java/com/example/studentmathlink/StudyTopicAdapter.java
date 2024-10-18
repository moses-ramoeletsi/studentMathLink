package com.example.studentmathlink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class StudyTopicAdapter extends ArrayAdapter<StudyTopic> {

    private Context context;
    private List<StudyTopic> topics;
    private OnDeleteClickListener deleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public StudyTopicAdapter(Context context, List<StudyTopic> topics, OnDeleteClickListener listener) {
        super(context, 0, topics);
        this.context = context;
        this.topics = topics;
        this.deleteClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.study_topic_item, parent, false);
        }

        StudyTopic currentTopic = topics.get(position);

        TextView topicTextView = listItem.findViewById(R.id.topicTextView);
        TextView durationTextView = listItem.findViewById(R.id.durationTextView);
        TextView notesTextView = listItem.findViewById(R.id.notesTextView);
        ImageButton deleteButton = listItem.findViewById(R.id.deleteButton);

        topicTextView.setText(currentTopic.getTopic());
        durationTextView.setText(currentTopic.getDuration() + " minutes");
        notesTextView.setText(currentTopic.getNotes());

        deleteButton.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(position);
            }
        });

        return listItem;
    }
}