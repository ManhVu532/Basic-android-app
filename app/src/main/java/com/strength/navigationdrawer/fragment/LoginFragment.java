package com.strength.navigationdrawer.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.strength.navigationdrawer.MainActivity;
import com.strength.navigationdrawer.R;

public class LoginFragment extends Fragment {
    Button btnLogin;
    EditText edt_username, edt_password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        edt_username = view.findViewById(R.id.edname);
        edt_password = view.findViewById(R.id.password);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();

                if(username.length() == 0 || password.length() == 0){
                    Snackbar.make(view, "Thiếu thông tin username hoặc password!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("12345678")){
                    Snackbar.make(view, "Đăng nhập thành công! Chào mừng bạn!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    MainActivity parent = (MainActivity)getActivity();
                    parent.saveUser(username);
                    parent.getToolbar().setTitle("Trang chủ");
                    parent.replaceFragment(new HomeFragment());
                }else{
                    Snackbar.make(view, "Đăng nhập thất bại vui lòng kiểm tra lại!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }


            }
        });
        return view;
    }
}
