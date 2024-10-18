package com.example.studentmathlink;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyPlan extends AppCompatActivity implements StudyTopicAdapter.OnDeleteClickListener {
    private TextInputEditText topicEditText, durationEditText, notesEditText;
    private MaterialButton addTopicButton, saveButton, startDateButton, endDateButton;
    private ListView topicListView;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private List<StudyTopic> topics = new ArrayList<>();
    private StudyTopicAdapter adapter;
    private String startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_plan);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        topicEditText = findViewById(R.id.topicEditText);
        durationEditText = findViewById(R.id.durationEditText);
        notesEditText = findViewById(R.id.notesEditText);
        addTopicButton = findViewById(R.id.addTopicButton);
        saveButton = findViewById(R.id.saveButton);
        startDateButton = findViewById(R.id.startDateButton);
        endDateButton = findViewById(R.id.endDateButton);
        topicListView = findViewById(R.id.topicListView);

        adapter = new StudyTopicAdapter(this, topics, this);
        topicListView.setAdapter(adapter);

        addTopicButton.setOnClickListener(v -> addTopicToStudyPlan());
        saveButton.setOnClickListener(v -> saveStudyPlan());
        startDateButton.setOnClickListener(v -> showDatePicker(true));
        endDateButton.setOnClickListener(v -> showDatePicker(false));

        loadStudyPlan();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addTopicToStudyPlan() {
        String topic = topicEditText.getText().toString().trim();
        String durationStr = durationEditText.getText().toString().trim();
        String notes = notesEditText.getText().toString().trim();

        if (!topic.isEmpty() && !durationStr.isEmpty()) {
            int duration = Integer.parseInt(durationStr);
            StudyTopic studyTopic = new StudyTopic(topic, duration, notes);
            topics.add(studyTopic);
            adapter.notifyDataSetChanged();

            topicEditText.setText("");
            durationEditText.setText("");
            notesEditText.setText("");
        } else {
            Toast.makeText(this, "Please enter both topic and duration", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadStudyPlan() {
        String userId = mAuth.getCurrentUser().getUid();
        firestore.collection("users").document(userId).collection("studyPlans")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    topics.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        List<Map<String, Object>> loadedTopics = (List<Map<String, Object>>) document.get("topics");
                        if (loadedTopics != null) {
                            for (Map<String, Object> topicMap : loadedTopics) {
                                String topic = (String) topicMap.get("topic");
                                Long durationLong = (Long) topicMap.get("duration");
                                int duration = durationLong != null ? durationLong.intValue() : 0;
                                String notes = (String) topicMap.get("notes");
                                topics.add(new StudyTopic(topic, duration, notes));
                            }
                        }
                        startDate = document.getString("startDate");
                        endDate = document.getString("endDate");
                    }
                    adapter.notifyDataSetChanged();
                    updateDateButtons();
                })
                .addOnFailureListener(e -> Toast.makeText(StudyPlan.this, "Failed to load study plan", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onDeleteClick(int position) {
        if (position >= 0 && position < topics.size()) {
            topics.remove(position);
            adapter.notifyDataSetChanged();
            saveStudyPlan();
        }
    }

    private void saveStudyPlan() {
        if (startDate == null || endDate == null) {
            Toast.makeText(this, "Please set start and end dates", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = mAuth.getCurrentUser().getUid();
        Map<String, Object> studyPlan = new HashMap<>();
        studyPlan.put("topics", topics);
        studyPlan.put("startDate", startDate);
        studyPlan.put("endDate", endDate);

        firestore.collection("users").document(userId).collection("studyPlans")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Update existing study plan
                        String documentId = queryDocumentSnapshots.getDocuments().get(0).getId();
                        firestore.collection("users").document(userId).collection("studyPlans")
                                .document(documentId)
                                .set(studyPlan)
                                .addOnSuccessListener(aVoid -> Toast.makeText(StudyPlan.this, "Study plan updated successfully", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(StudyPlan.this, "Failed to update study plan", Toast.LENGTH_SHORT).show());
                    } else {
                        // Create new study plan
                        firestore.collection("users").document(userId).collection("studyPlans")
                                .add(studyPlan)
                                .addOnSuccessListener(documentReference -> Toast.makeText(StudyPlan.this, "Study plan created successfully", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(StudyPlan.this, "Failed to create study plan", Toast.LENGTH_SHORT).show());
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(StudyPlan.this, "Failed to save study plan", Toast.LENGTH_SHORT).show());
    }

    private void showDatePicker(final boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String date = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    if (isStartDate) {
                        startDate = date;
                        startDateButton.setText("Start Date: " + date);
                    } else {
                        endDate = date;
                        endDateButton.setText("End Date: " + date);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void updateDateButtons() {
        if (startDate != null) {
            startDateButton.setText("Start Date: " + startDate);
        }
        if (endDate != null) {
            endDateButton.setText("End Date: " + endDate);
        }
    }
}