package com.mepty.android.linkedtextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mepty.android.libs.LinkTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinkTextView linkTextView = (LinkTextView) findViewById(R.id.lv);
        linkTextView.setText("hahahhahahhhahhahahahahahahahhahahahahahahahahahaaaahahaahhahahah");
        linkTextView.setText("hahahhahahhhahhahahahahahahahhahahahahahahahahahaaaahahaahhahahah");


        LinkTextView linkTextView1 = (LinkTextView) findViewById(R.id.lv2);
        linkTextView1.setText("asdhgadhjaskjhdgakjhsdgkjhasdghashgkjdakjhgsd");
        linkTextView.setLinkText("000000");
    }
}
