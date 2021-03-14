package com.example.android.mynotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public interface NotesFirestoreCallbacks {
    void onSuccessNotes(@NonNull List<NoteModel> items);
    void onErrorNotes(@Nullable String message);
}
