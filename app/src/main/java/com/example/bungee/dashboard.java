package com.example.bungee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bungee.databinding.ActivityDashboardBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class dashboard extends AppCompatActivity {
    ActivityDashboardBinding adb;
    GridView grid;
    DBClass db;
    ImageView paw;
    String email, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adb = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(adb.getRoot());

        grid = findViewById(R.id.grid);
        db = new DBClass(getApplicationContext());

        RV_DashboardAdapter gridAdap = new RV_DashboardAdapter(dashboard.this, db.getAllProducts());
        adb.grid.setAdapter(gridAdap);

        paw = findViewById(R.id.paw);
        email = readTxt();
        type = db.getType(email);

        paw.setOnClickListener(v->{
            showDiag();
        });
    }

    public void showDiag(){
        final Dialog dg = new Dialog(this);
        dg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dg.setContentView(R.layout.dashboard_bottomsheet);
        LinearLayout dashboard = dg.findViewById(R.id.todashboard);
        LinearLayout apply = dg.findViewById(R.id.apply);
        LinearLayout logout = dg.findViewById(R.id.logout);
        LinearLayout add = dg.findViewById(R.id.add);
        LinearLayout admin = dg.findViewById(R.id.admin);
        LinearLayout view = dg.findViewById(R.id.vieworders);
        LinearLayout cart = dg.findViewById(R.id.cart);

        ViewGroup parent = (ViewGroup) add.getParent();


        if(type.equals("buyer")){
            parent.removeView(add);
            parent.removeView(admin);
            parent.removeView(view);
        } else if(type.equals("seller")){
            parent.removeView(apply);
            parent.removeView(admin);
        } else {
            parent.removeView(apply);
        }


        dg.show();
        dg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dg.getWindow().getAttributes().windowAnimations = R.style.bottomsheetanim;
        dg.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dg.getWindow().setGravity(Gravity.BOTTOM);

        Animation rotationin = AnimationUtils.loadAnimation(this, R.anim.rotate_paw_in);
        paw.startAnimation(rotationin);


        dg.setOnDismissListener(ds->{
            Animation rotation = AnimationUtils.loadAnimation(dashboard.this, R.anim.rotate_paw_out);
            paw.startAnimation(rotation);
        });
    }
    public String readTxt() {
        StringBuilder c = new StringBuilder();

        try {
            File file = new File(getApplicationContext().getFilesDir(), "email.txt");

            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    c.append(line);
                }
                reader.close();
            } else {
                Log.d("FILE", "File not found: email.txt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return c.toString();
    }

}