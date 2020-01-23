package com.foxhole.chatappmvvmdagger2rxjavafirestore.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;

public class InputDialog extends AppCompatDialogFragment {

    ChangeStatusListener listener;
    EditText status;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity()).setCancelable(false);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_input, null);

        builder.setView(view);
        builder.setMessage(null);
        builder.setTitle("Change Status");
        status = view.findViewById(R.id.status_input);
        Button saveBtn = view.findViewById(R.id.save_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String statusText = status.getText().toString();
                if(statusText.isEmpty()){
                    status.setError("Invalid name !");
                }else {
                    listener.saveNewStatus(statusText);
                    dismiss();
                }

            }
        });



        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (ChangeStatusListener) context;
    }

    public interface ChangeStatusListener {
        void saveNewStatus(String name);
    }
}
