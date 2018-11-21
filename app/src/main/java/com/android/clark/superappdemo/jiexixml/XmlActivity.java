package com.android.clark.superappdemo.jiexixml;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.android.clark.superappdemo.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlActivity extends Activity implements View.OnClickListener {

    private static final int SAX = 1;
    private static final int DOM = 2;
    private static final int PULL = 3;

    private TextView xmlText;
    private Button saxButton;
    private Button domButton;
    private Button pullButton;

    private List<Person> persons = new ArrayList<>();

    private Handler mHandler = new Handler() {

        private StringBuffer textBuffer= new StringBuffer();

        public void handleMessage(Message msg) {
            if (textBuffer.length()!=0){
                textBuffer.delete(0,textBuffer.length());//清除StringBuffer中保存的数据,textBuffer.setLength(0);
            }
            switch (msg.what) {
                case SAX:
                case DOM:
                case PULL:
                    for (Person person : persons) {
                        textBuffer.append(person.toString() + "\n");
                    }
                    xmlText.setText(textBuffer);
                    break;
                default:
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);

        saxButton = findViewById(R.id.btn_sax_xml);
        domButton = findViewById(R.id.btn_dom_xml);
        pullButton = findViewById(R.id.btn_pull_xml);

        xmlText = findViewById(R.id.result_text);

        saxButton.setOnClickListener(this);
        domButton.setOnClickListener(this);
        pullButton.setOnClickListener(this);
    }

    /**
     * 使用Sax将person.xml文件解析成person对象
     *
     * @return
     * @throws Exception
     */
    private ArrayList<Person> readxmlForSAX() throws Exception {
        //获取文件资源建立输入流对象
        InputStream is = getAssets().open("person1.xml");
        //①创建XML解析处理器
        SaxHelper ss = new SaxHelper();
        //②得到SAX解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //③创建SAX解析器
        SAXParser parser = factory.newSAXParser();
        //④将xml解析处理器分配给解析器,对文档进行解析,将事件发送给处理器
        parser.parse(is, ss);
        is.close();
        return ss.getPersons();
    }
    /**
     * 使用Pull将person.xml文件解析成person对象
     *
     * @return
     * @throws Exception
     */
    private ArrayList<Person> readxmlForPULL() throws Exception {
        //获取文件资源建立输入流对象
        InputStream is = getAssets().open("person3.xml");
        ArrayList<Person> list=PullHelper.getPersons(is);
        is.close();
        return list;
    }


    @Override
    public void onClick(View v) {
        if (!persons.isEmpty()){
            persons.clear();//清除persons中保存的数据
        }
        switch (v.getId()) {
            case R.id.btn_sax_xml://使用sax解析xml数据
                try {
                    persons.addAll(readxmlForSAX());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("XmlActivity", "解析失败");
                }
                Log.i("XmlActivity","persons.size:"+persons.size());
                mHandler.sendEmptyMessage(SAX);
                break;
            case R.id.btn_dom_xml://使用dom解析xml数据
                persons.addAll(DomHelper.queryXML(this));//解析person2.xml的数据
                mHandler.sendEmptyMessage(DOM);
                break;
            case R.id.btn_pull_xml:
                try {
                    persons.addAll(readxmlForPULL());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(PULL);
                break;
        }

    }
}
