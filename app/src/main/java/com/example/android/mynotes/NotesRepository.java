package com.example.android.mynotes;

import androidx.annotation.NonNull;

public interface NotesRepository {
    void requestNotes();

    void onDeleteClicked(@NonNull String id);
}
