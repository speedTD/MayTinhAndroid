package com.example.admin.caculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private TextView txt_ketqua;
    //check state Error Not or yes
    private boolean State;
    //check result
    private boolean Result;
    //check and add . in text
    private boolean mdot;
    private Animation animation;
    //number before  and operator affter
    private int [] operator={R.id.id_btn_operator_chia,R.id.id_btn_Operator_nhan,
            R.id.id_btn_operator_cong,R.id.id_btn_operator_tru,R.id.id_btn_mu};
    private int [] number={R.id.id_btn_so_0,R.id.id_btn_so_1,R.id.id_btn_so_2,R.id.id_btn_so_3,R.id.id_btn_so_4,
            R.id.id_btn_so_5,R.id.id_btn_so_6,R.id.id_btn_so_7,R.id.id_btn_so_8,R.id.id_btn_so_9,R.id.id_btn_mo,
            R.id.id_btn_dong,R.id.id_btn_dau_cham,R.id.id_btn_sqrt};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animation= AnimationUtils.loadAnimation(this,R.anim.anim_ketqua);
        txt_ketqua=(TextView) findViewById(R.id.id_txt_ketqua);
        addOperator();
        addNumber();
    }

    private void addNumber() {
           View.OnClickListener listener=new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Button button=(Button) view;
                   if(State){

                       txt_ketqua.setText(button.getText());
                       State=false;
                   }else {
                       // exist Number we are append number in textview
                       txt_ketqua.append(button.getText());
                   }
                   Result=true;

               }
           };
           for (int id:number){
               findViewById(id).setOnClickListener(listener);
           }

           findViewById(R.id.id_btn_dau_bang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logic();

            }
        });
    }

    private void addOperator() {
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button=(Button) view;
                if(!State&&Result) {
                    txt_ketqua.append(button.getText());
                    mdot=false;
                    State=false;
                }


            }
        };
        //append .
        findViewById(R.id.id_btn_dau_cham).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Result&&!State&&!mdot) {
                    txt_ketqua.append(".");
                    State=false;
                    mdot=true;
                }
            }
        });

        //clear
        findViewById(R.id.id_btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_ketqua.setHint("0");
                txt_ketqua.setText("");
                Result=false;
                mdot=false;
                State=false;
            }
        });

        for (int id:operator){
            findViewById(id).setOnClickListener(listener);
        }
    }
    private void Logic(){
        try {

            if(!State&&Result){
                String txt =txt_ketqua.getText().toString();
                Expression expression=new ExpressionBuilder(txt).build();
                double ketqua=expression.evaluate();
                NumberFormat numberFormat=new DecimalFormat("#0.000000");
                txt_ketqua.setText(Double.toString((Double.parseDouble(numberFormat.format(ketqua)))));
                mdot=true;
                txt_ketqua.setAnimation(animation);
                txt_ketqua.startAnimation(animation);
            }
        }catch (Exception e){
            txt_ketqua.setText("Error");
            Result=false;
            State=false;


        }
    }
}
