package com.example.android.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements NotesDetailsFragment.NoteDetailClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_container);

        if(savedInstanceState==null){
            //FirebaseFirestore db = FirebaseFirestore.getInstance();
            Fragment fragment=new NotesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_container,fragment)
                    .commit();

        }
    }

    @Override
    public void onItemClicked(@NonNull String text) {
        Log.d("MainActivity",text);
    }
}