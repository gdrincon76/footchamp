package net.jaumebalmes.grincon17.futchamp.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnLoginDialogListener;

/**
 * crea una ventana emergente para que el usuario pueda hacer login
 */
public class LoginDialogFragment extends DialogFragment {

    private OnLoginDialogListener mListener;
    private EditText userName;
    private EditText pwd;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_login_dialog, null);

        userName = view.findViewById(R.id.textEditUser);
        pwd = view.findViewById(R.id.textEditPwd);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(view);
        builder.setPositiveButton(R.string.login_txt, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Obtiene los datos del usuario para verificar autorizacion
                String name = String.valueOf(userName.getText());
                String pass = String.valueOf(pwd.getText());
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass)) {
                    mListener.onLoginClickListener(name, pass);
                } else {
                    Toast.makeText(getContext(), "Must not be empty " + name, Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton(R.string.cancel_txt, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (OnLoginDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
