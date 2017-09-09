package com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator;

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
    
    private final int MAX_DIGIT = 15;
    
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
        
        Intent intent = getIntent();
        String goalMoney = intent.getStringExtra("value");
        txt_input.setText(goalMoney);
    }
    
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear:
                txt_input.setText("0");
                break;
            case R.id.btn_remove:
                if (txt_input.length() > 0) {
                    StringBuilder tmp = new StringBuilder(txt_input.getText().toString());
                    tmp.delete(tmp.length() - 1, tmp.length());
                    txt_input.setText(tmp.toString());
                } else {
                    txt_input.setText("0");
                }
                break;
            case R.id.btn_equal:
                try {
                    Double.parseDouble(txt_input.getText().toString().trim());
                    resultAndFinish();
                } catch (Exception ex) {
                    try {
                        String tmp = txt_input.getText().toString().replace("x", "*");
                        tmp = tmp.replace("÷", "/");
                        Expression e = new ExpressionBuilder(tmp).build();
                        double result = e.evaluate();
                        txt_input.setText(TextUtil.doubleToString(result));
                        
                    } catch (Exception e) {
                        Toast.makeText(this, R.string.message_warning_wrong_expression,
                                  Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                Button button = (Button) view;
                if (txt_input.getText().toString().equals("0")) {
                    txt_input.setText("");
                }
                txt_input.append(button.getText());
                break;
        }
    }
    
    public void resultAndFinish() {
        if (txt_input.length() > MAX_DIGIT) {
            Toast.makeText(this, R.string.message_warning_max_digit, Toast.LENGTH_SHORT).show();
        } else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", txt_input.getText().toString());
            setResult(RESULT_OK, returnIntent);
            finish();
        }
        
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        
        return false;
    }
    
    @OnClick(R.id.check_box)
    public void onClickCheck(View view) {
        try {
            Double.parseDouble(txt_input.getText().toString().trim());
            
            if (!txt_input.getText().toString().equals("")) {
                resultAndFinish();
                return;
            }
            Toast.makeText(this, R.string.message_warning_max_digit, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, R.string.message_warning_wrong_expression, Toast.LENGTH_SHORT)
                      .show();
        }
        
    }
}
