package com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.validate.TextUtil;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Created by kunsubin on 9/1/2017.
 */

public class CalculatorActivity extends BaseActivity implements OnTouchListener {
    @BindView(R.id.txt_input)
    TextView txt_input;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_calculator;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void initializePresenter() {
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent=getIntent();
        String goalMoney=intent.getStringExtra("goal_money");
        //txt_input.setText(goalMoney.substring(1));
        txt_input.setText("0");
        
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_clear:
                txt_input.setText("0");
                break;
            case R.id.btn_remove:
                if(txt_input.length()>0){
                    StringBuilder tmp=new StringBuilder(txt_input.getText().toString());
                    tmp.delete(tmp.length()-1,tmp.length());
                    txt_input.setText(tmp.toString());
                }else{
                    txt_input.setText("0");
                }
                break;
            case R.id.btn_equal:
                try {
                    Double.parseDouble(txt_input.getText().toString().trim());
                    resultAndFinish();
                }catch (Exception ex){
                    //Toast.makeText(this,"Bom",Toast.LENGTH_LONG).show();
                    try{
                        String tmp=txt_input.getText().toString().replace("x","*");
                        tmp=tmp.replace("÷","/");
                        Expression e =new ExpressionBuilder(tmp).build();
                        double result = e.evaluate();
                        txt_input.setText(TextUtil.doubleToString(result));
                        
                    }catch (Exception e){
                        Toast.makeText(this,"Not Input",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                Button button=(Button)view;
                if(txt_input.getText().toString().equals("0")){
                      txt_input.setText("");
                }
                txt_input.append(button.getText());
                
                /*  double ab=337747493838383.0d;
                MyLogger.d("fist: ",String.valueOf(ab));
                MyLogger.d("second: ", TextUtil.doubleToString(ab));*/
                break;
        }
    }
    public void resultAndFinish(){
        if(txt_input.length()>15){
            Toast.makeText(this,"15 max digit",Toast.LENGTH_LONG).show();
        }else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",txt_input.getText().toString());
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
       
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        
        return false;
    }
    @OnClick(R.id.check_box)
    public void onClickCheck(View view){
        try{
            Double.parseDouble(txt_input.getText().toString().trim());
            
            if(!txt_input.getText().toString().equals("")){
                resultAndFinish();
            }else{
                Toast.makeText(this,"Not",Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Toast.makeText(this,"Not",Toast.LENGTH_LONG).show();
        }
       
    }
}
