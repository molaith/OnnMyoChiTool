package com.molaith.omyochitools;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.molaith.omyochitools.domin.Shikikami;
import com.molaith.omyochitools.utils.ShikiKamiUtil;
import com.molaith.omyochitools.utils.SizeUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppCompatEditText edinput;
    private List<String> clues=new ArrayList<>();
    private String shikikamis="";
    private TextView tv_result;
    private StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.btn_add_clue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCLueLayout();
            }
        });

        edinput=findViewById(R.id.ed_input);
        edinput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH){
                    stringBuilder.delete(0,stringBuilder.length());
                    updateResuslt();
                    String texts=edinput.getText().toString().trim();
                    if (texts.contains(",") || texts.contains("，")){

                    }else {
                        List<Shikikami> result=ShikiKamiUtil.getResult(texts);
                        stringBuilder.append("找到"+result.size()+"个结果\n");
                        updateResuslt();
                        stringBuilder.append(ShikiKamiUtil.parseResult(result,false,false,false));
                        updateResuslt();
                    }
                    return true;
                }
                return false;
            }
        });

        tv_result=findViewById(R.id.tv_result);

        stringBuilder=new StringBuilder();

        stringBuilder.append("正在初始化数据...\n");
        updateResuslt();
        ShikiKamiUtil.init(this);
        stringBuilder.append("数据初始化完成,请开始你的表演!\n");
        updateResuslt();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addCLueLayout(){
        LinearLayout clueContainer=findViewById(R.id.layout_clues);
        if (clueContainer.getChildCount()<4){
            createClueView(clueContainer);
        }
    }

    private void createClueView(LinearLayout container){
        AppCompatEditText edClue=new AppCompatEditText(this);
        edClue.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
        edClue.setHint("请输入第"+(container.getChildCount()+1)+"个式神线索");
        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin= SizeUtil.dip2px(this,10);
        container.addView(edClue,lp);
    }

    private void updateResuslt(){
        tv_result.setText(stringBuilder.toString());
    }
}
