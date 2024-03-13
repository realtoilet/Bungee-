package com.example.bungee;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class fragment_signin extends Fragment {

    EditText et_user, et_email, et_password, et_confirm;
    Button btn_signup, btn_login;
    DBClass db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signin, container, false);

        et_user = v.findViewById(R.id.username);
        et_email = v.findViewById(R.id.email);
        et_password = v.findViewById(R.id.password);
        et_confirm = v.findViewById(R.id.confirm);

        btn_signup = v.findViewById(R.id.signup);
        btn_login = v.findViewById(R.id.login);

        db = new DBClass(getContext());
        //db.addUser("adminbetlog", "adminbetlog", "adminbetlog123@gmail.com", "admin");
        //db.addUser("sellerbetlog", "sellerbetlog", "sellerbetlog123@gmail.com", "seller");



        btn_signup.setOnClickListener(listener->{
            String username = et_user.getText().toString();
            String email = et_email.getText().toString();
            String password = et_password.getText().toString();
            String confirm = et_confirm.getText().toString();

            if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirm.isEmpty()){
                if(password.equals(confirm)){
                    if(email.endsWith("@gmail.com")){
                        boolean checkdupe = db.addUser(username, password, email, "buyer");
                        if(checkdupe){
                            Toast.makeText(getContext(), "Account added.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Similar gmail is in use already.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), "Invalid email format.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Passwords dont match.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please fill up all the fields.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_login.setOnClickListener(listener->{
            account_views ac = (account_views) getActivity();
            ac.vp.setCurrentItem(0);
        });
        return v;
    }
}