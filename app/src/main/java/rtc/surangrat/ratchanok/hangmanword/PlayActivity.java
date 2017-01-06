package rtc.surangrat.ratchanok.hangmanword;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {

    //Explicit
    private TextView answerTextView;
    private String answerString;
    private Button[] keyboard = new Button[26];
    private int[] keyboardInts = new int[]{R.id.btnA, R.id.btnB, R.id.btnC, R.id.btnD,
            R.id.btnE, R.id.btnF, R.id.btnG, R.id.btnH, R.id.btnI, R.id.btnJ, R.id.btnK,
            R.id.btnL, R.id.btnM, R.id.btnN, R.id.btnO, R.id.btnP, R.id.btnQ, R.id.btnR,
            R.id.btnS, R.id.btnT, R.id.btnU, R.id.btnV, R.id.btnW, R.id.btnX, R.id.btnY,
            R.id.btnZ};
    private String[] keyboardStrings = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
            "y", "z"};
    private int wordAnInt; //  จำนวน digi ของคำ
    private String[] tag = new String[]{"7janV1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mySetup();

        bindWidget();
        
    }   // Main Method

    private void mySetup() {

        wordAnInt = getIntent().getIntExtra("Word", 4);
        Log.d(tag[0], "ค่าที่ ส่งมาจาก MainHold wordAnInt ==> " + wordAnInt);

    }

    private void bindWidget() {

        answerTextView = (TextView) findViewById(R.id.textView7);
        for (int i=0;i<keyboardInts.length;i++) {
            keyboard[i] = (Button) findViewById(keyboardInts[i]);
        }

    }   // bindWidget

    public void onClickMyKeyboard(View view) {

       for (int i=0;i<keyboard.length;i++) {

           if (view.getId() == keyboardInts[i]) {

               answerTextView.append(keyboardStrings[i]);
               answerString = answerTextView.getText().toString().trim();
               checkWord();

           }    // if

       }    //for

    }   // onClick

    private void checkWord() {

        //ดูว่า digi เกิดหรือเปล่า
        if (answerString.length() >= wordAnInt) {
            answerTextView.setText("");
        }

    }   // checkWord

} // Main Class
