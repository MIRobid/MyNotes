package com.example.android.mynotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class NotesDetailsFragment extends Fragment {

    private final static String ARG_MODEL_KEY="arg_model_key";

    public static Fragment newInstance(@NonNull NoteModel model){
        Fragment fragment = new NotesDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MODEL_KEY,model);
        fragment.setArguments(bundle);
        return fragment;
    }

    private EditText titleEditText;
    private EditText descriptionEditText;
    private MaterialButton updateButton;
    private MaterialButton deleteButton;
    private MaterialToolbar toolbar;
    private Button alert1;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleEditText=view.findViewById(R.id.et_note_details_title);
        descriptionEditText=view.findViewById(R.id.et_note_details_description);
        deleteButton=view.findViewById(R.id.btn_note_detail_remove);
        updateButton =view.findViewById(R.id.btn_note_detail_update);
        toolbar=view.findViewById(R.id.toolbar_note_details);
        alert1 = view.findViewById(R.id.alertDialog1);
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null){
                    getActivity().onBackPressed();
                }
            }
        });
        if(getArguments()!=null){
            NoteModel noteModel= (NoteModel) getArguments().getSerializable(ARG_MODEL_KEY);
            titleEditText.setText(noteModel.getTitle());
            descriptionEditText.setText(noteModel.getDescription());
            alert1.setOnClickListener(clickAlertDialog1);
        }

    }
    private View.OnClickListener clickAlertDialog1 = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle(R.string.exclamation)
                    .setMessage(R.string.press_button)
                    .setIcon(R.mipmap.ic_launcher_round)
                    .setCancelable(false)
                    .setNegativeButton(R.string.no,
                            // Ставим слушатель, будем обрабатывать нажатие
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(view.getContext(), "Нет!", Toast.LENGTH_SHORT).show();
                                }
                            })
                    // Устанавливаем нейтральную кнопку
                    .setNeutralButton(R.string.dunno,
                            // Ставим слушатель, будем обрабатывать нажатие
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(view.getContext(), "Не знаю!", Toast.LENGTH_SHORT).show();
                                }
                            })
                    // Устанавливаем кнопку. Название кнопки также можно задавать
                    // строкой
                    .setPositiveButton(R.string.yes,
                            // Ставим слушатель, будем обрабатывать нажатие
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(view.getContext(), "Да!", Toast.LENGTH_SHORT).show();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            Toast.makeText(view.getContext(), "Диалог открыт", Toast.LENGTH_SHORT).show();
        }
    };
}
