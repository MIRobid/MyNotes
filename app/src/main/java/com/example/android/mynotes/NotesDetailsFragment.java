package com.example.android.mynotes;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.UUID;

public class NotesDetailsFragment extends Fragment implements NoteFirestoreCallbacks {

    private final static String ARG_MODEL_KEY="arg_model_key";

    public static Fragment newInstance(@NonNull NoteModel model){
        Fragment fragment = new NotesDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MODEL_KEY,model);
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface NoteDetailClickListener{
        void onItemClicked(@NonNull String text);
    }

    private NoteDetailClickListener clickListener;
    private final NoteRepository repository = new NoteRepositoryImpl(this);

    private EditText titleEditText;
    private EditText descriptionEditText;
    private MaterialButton updateButton;
    private MaterialToolbar toolbar;
    private Button alert1;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            clickListener=(NoteDetailClickListener) context;
        }catch(ClassCastException e){
            Log.d("OnAttach", String.valueOf(e));
        }

    }

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
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = titleEditText.getText().toString();
                final String description = descriptionEditText.getText().toString();
                update(title, description);
                sendResult(title);
                sendResultToActivity(title);
            }
        });


        if (getArguments() != null) {
            NoteModel noteModel = (NoteModel) getArguments().getSerializable(ARG_MODEL_KEY);
            if (noteModel != null) {
                updateButton.setText(getString(R.string.note_details_update_button_label));
                toolbar.inflateMenu(R.menu.note_menu);
                toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_note_delete) {
                            repository.onDeleteClicked(noteModel.getId());
                            if (getActivity() != null) {
                                getActivity().onBackPressed();
                            }
                        }
                        return false;
                    }
                });
                titleEditText.setText(noteModel.getTitle());
                descriptionEditText.setText(noteModel.getDescription());
                //alert1.setOnClickListener(View.OnClickListener);
            }
        }
    }

    @Override
    public void onSuccess(@Nullable String message) {
        showToastMessage(message);
    }

    @Override
    public void onError(@Nullable String message) {
        showToastMessage(message);
    }

    private void update(
            @Nullable String title,
            @Nullable String description){
        if(!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(description)){
            if(getArguments()!=null){
                NoteModel noteModel = (NoteModel) getArguments().getSerializable(ARG_MODEL_KEY);
                if(noteModel!=null){
                    repository.setNote(noteModel.getId(),title,description);
                }else{
                    String id= UUID.randomUUID().toString();
                    repository.setNote(id,title,description);
                }
            }
        }else{
            Toast.makeText(requireContext(),"Fill the title and description",Toast.LENGTH_SHORT).show();
        }
    }

    private void showToastMessage(@Nullable String message){
        if(message!=null){
            Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show();
        }
    }

    private void sendResult(@NonNull String text){
        Bundle result = new Bundle();
        result.putString("bundleKey",text);
        getParentFragmentManager().setFragmentResult("requestKey",result);
    }

    private void sendResultToActivity(@NonNull String text){
        clickListener.onItemClicked(text);
    }

    private void deleteNote(@NonNull String id){

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
