package com.example.android.mynotes;

import androidx.annotation.NonNull;

public interface NoteRepository {
    void setNote(
            @NonNull String id,
            @NonNull String title,
            @NonNull String description
    );
    void onDeleteClicked(@NonNull String id);
}
