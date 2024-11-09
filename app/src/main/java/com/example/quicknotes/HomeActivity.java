package com.example.quicknotes;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView notesRecyclerView;
    private FloatingActionButton addNoteButton;
    private Spinner categoryFilter;
    private NoteAdapter noteAdapter;
    private List<Note> allNotes; // Assume this list is populated with all notes
    private List<Note> filteredNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        addNoteButton = findViewById(R.id.addNoteButton);
        categoryFilter = findViewById(R.id.categoryFilter);

        allNotes = new ArrayList<>(); // Initialize or retrieve this list from a database
        filteredNotes = new ArrayList<>(allNotes);

        // Set up RecyclerView
        noteAdapter = new NoteAdapter(filteredNotes, note -> openNoteDetail(note));
        notesRecyclerView.setAdapter(noteAdapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up FAB to add note
        addNoteButton.setOnClickListener(v -> openAddNoteScreen());

        // Set up category filter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                Arrays.asList("All", "Work", "Personal", "Ideas"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryFilter.setAdapter(adapter);
        categoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterNotes(categoryFilter.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void openAddNoteScreen() {
        // Intent to AddNoteActivity (not implemented here)
    }

    private void openNoteDetail(Note note) {
        // Intent to DetailActivity with note details
    }

    private void filterNotes(String category) {
        filteredNotes.clear();
        if (category.equals("All")) {
            filteredNotes.addAll(allNotes);
        } else {
            for (Note note : allNotes) {
                if (note.getCategory().equals(category)) {
                    filteredNotes.add(note);
                }
            }
        }
        noteAdapter.notifyDataSetChanged();
    }
}
