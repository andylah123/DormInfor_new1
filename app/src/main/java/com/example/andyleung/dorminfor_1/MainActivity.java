package com.example.andyleung.dorminfor_1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyHelper myHelper;
    private EditText ETname;
    private EditText ETclass;
    private EditText ETxuehao;
    private EditText ETsushehao;
    private TextView TVshow;
    private Button BTNadd;
    private Button BTNquery;
    private Button BTNalter;
    private Button BTNdelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHelper = new MyHelper(this);
        init();
    }
    private void init(){
        ETname = (EditText) findViewById(R.id.et_name);
        ETclass = (EditText) findViewById(R.id.et_class);
        ETxuehao = (EditText) findViewById(R.id.et_xuehao);
        ETsushehao = (EditText) findViewById(R.id.et_sushehao);
        TVshow = (TextView) findViewById(R.id.tv_show);
        BTNadd = (Button) findViewById(R.id.btn_add);
        BTNquery = (Button) findViewById(R.id.btn_query);
        BTNalter = (Button) findViewById(R.id.btn_alter);
        BTNdelete = (Button) findViewById(R.id.btn_delete);
        BTNadd.setOnClickListener(this);
        BTNquery.setOnClickListener(this);
        BTNalter.setOnClickListener(this);
        BTNdelete.setOnClickListener(this);
    }
    public void onClick(View v){
        String name;
        String class1;
        String xuehao;
        String sushehao;
        SQLiteDatabase db;
        ContentValues values;
        switch (v.getId()){
            case R.id.btn_add://添加数据
                name = ETname.getText().toString();
                class1 = ETclass.getText().toString();
                xuehao = ETxuehao.getText().toString();
                sushehao = ETsushehao.getText().toString();
                db = myHelper.getWritableDatabase();//获取可读写SQLiteDatabase对象
                values = new ContentValues();//创建ContentValues对象
                values.put("name",name);
                values.put("class",class1);
                values.put("xuehao",xuehao);
                values.put("sushehao",sushehao);
                db.insert("biao_dormitory",null,values);
                Toast.makeText(this,"信息已添加",Toast.LENGTH_SHORT).show();
                db.close();
                break;
            case R.id.btn_query://查询数据
                db = myHelper.getReadableDatabase();
                Cursor cursor = db.query("biao_dormitory",null,null,null,null,null,null);
                if (cursor.getCount() == 0){
                    TVshow.setText("");
                    Toast.makeText(this,"" + "没有数据",Toast.LENGTH_SHORT).show();
                }else {
                    cursor.moveToFirst();
                    TVshow.setText("姓名:"+cursor.getString(0)+"\n"+"班级:"+cursor.getString(1)+"\n"+
                    "学号:"+cursor.getString(2)+"\n"+"宿舍号:"+cursor.getString(3)+"\n");
                }while (cursor.moveToNext()){
                    TVshow.append("\n"+"姓名:"+cursor.getString(0)+"\n"+"班级:"+cursor.getString(1)+"\n"+
                            "学号:"+cursor.getString(2)+"\n"+"宿舍号:"+cursor.getString(3));
                }
                cursor.close();
                db.close();
                break;
            case R.id.btn_alter:
                db = myHelper.getWritableDatabase();
                values = new ContentValues();   //要修改的数据
                values.put("name",name = ETname.getText().toString());
                db.update("biao_dormitory",values,"name=?",new String[]{ETname.getText().toString()});//更新并得到行数
                Toast.makeText(this,"信息已修改",Toast.LENGTH_SHORT).show();
                db.close();
                break;
            case R.id.btn_delete:
                db = myHelper.getWritableDatabase();
                db.delete("biao_dormitory",null,null);
                Toast.makeText(this,"信息已删除",Toast.LENGTH_SHORT).show();
                TVshow.setText("");
                db.close();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
