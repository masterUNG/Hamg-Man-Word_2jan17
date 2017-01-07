package rtc.surangrat.ratchanok.hangmanword;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {

    //Explicit
    private TextView answerTextView, questionTextView, scoreTextView;
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
    private String[] tag = new String[]{"7janV1", "8janV1", "8janV2"};
    private MyConstant myConstant;
    private String[] questionStrings; // คำใบ้
    private String[] answerTrueStrings; // คำตอบ
    private int indexTimes = 0;// ข้อ 0,1,2,3...
    private String[] sepAnserStrings; // คือคำตอบที่ถูกแยก
    private ImageView imageView;
    private int falseAnInt = 0;     // ถ้าผิดจะเพิ่ม
    private int scoreAnInt = 0;     // คะแนนที่ได้


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        bindWidget();

        mySetup();

        aboutAnswer(0);

    }   // Main Method

    private void myAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.doremon48);
        builder.setTitle("Game Over");
        builder.setMessage("Please Try Again");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    //เมธอด ที่ทำหน้าที่ เปลี่ยนรูปถ้า ผิด
    private void changeImage(int index) {

        int[] intHang = new int[]{R.drawable.hang1, R.drawable.hang2, R.drawable.hang3,
                R.drawable.hang4, R.drawable.hang5};

        try {

            if (index < 4) {
                imageView.setImageResource(intHang[index]);
            } else {
                myAlert();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }   // change

    //เอาคำตอบ มาแยกคำ
    private void aboutAnswer(int indexTimes) {

        Log.d(tag[1], "ข้อที่ " + indexTimes);
        Log.d(tag[1], "คำตอบที่ได้ ==> " + answerTrueStrings[indexTimes]);


        sepAnserStrings = answerTrueStrings[indexTimes].split("");
        for (int i=0;i<sepAnserStrings.length;i++) {
            Log.d(tag[1], "sepAns(" + i + ") ==> " + sepAnserStrings[i]);
        }


    }   // aboutAnswer

    private void mySetup() {

        wordAnInt = getIntent().getIntExtra("Word", 4);
        Log.d(tag[0], "ค่าที่ ส่งมาจาก MainHold wordAnInt ==> " + wordAnInt);
        myConstant = new MyConstant();

        switch (wordAnInt) {
            case 4:
                questionStrings = myConstant.getQuestionStrings();
                answerTrueStrings = myConstant.getAnswerStrings();
                break;
            case 6:
                questionStrings = myConstant.getQuestion2Strings();
                answerTrueStrings = myConstant.getAnswer2Strings();
                break;
            case 8:
                questionStrings = myConstant.getQuestion3Strings();
                answerTrueStrings = myConstant.getAnswer3Strings();
                break;
        }   // switch

        //กำหนดค่าเริ่มต้น
        Log.d(tag[0], "question0 ==> " + questionStrings[0]);
        questionTextView.setText(questionStrings[0]);


    }   // mySetUp

    private void bindWidget() {

        answerTextView = (TextView) findViewById(R.id.textView7);
        for (int i = 0; i < keyboardInts.length; i++) {
            keyboard[i] = (Button) findViewById(keyboardInts[i]);
        }
        questionTextView = (TextView) findViewById(R.id.textView3);
        imageView = (ImageView) findViewById(R.id.imageView2);
        scoreTextView = (TextView) findViewById(R.id.textView5);

    }   // bindWidget

    public void onClickMyKeyboard(View view) {

        for (int i = 0; i < keyboard.length; i++) {

            if (view.getId() == keyboardInts[i]) {

                answerTextView.append(keyboardStrings[i]);
                answerString = answerTextView.getText().toString().trim();
                Log.d(tag[1], "digi ที่ ==> " + answerString.length());

                checkTrueFalseDigi(answerString.length(), keyboardStrings[i]);

                checkWord();

            }    // if

        }    //for

    }   // onClick

    private void checkTrueFalseDigi(int index, String digiKeyboard) {

        Log.d(tag[2], "digiKeyboard ==> " + digiKeyboard);
        Log.d(tag[2], "sepAns ==>" + sepAnserStrings[index]);

        if (!digiKeyboard.equals(sepAnserStrings[index])) {
            falseAnInt += 1;
            Log.d(tag[2], "falseAnInt ==> " + falseAnInt);
            changeImage(falseAnInt);
        }

    }

    private void checkWord() {

        //ดูว่า digi เกิดหรือเปล่า
        if (answerString.length() >= wordAnInt) {

            indexTimes += 1;

            //หน่วงเวลา 1 วินาที ก่อน เครียร
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    //ตรวจคะแนน
                    if (answerString.equals(answerTrueStrings[indexTimes - 1])) {
                        scoreAnInt += 1;
                        scoreTextView.setText("Score = " + scoreAnInt);
                    }

                    clearText();
                    questionTextView.setText(questionStrings[indexTimes]);
                    aboutAnswer(indexTimes);
                }
            }, 1000);
        }

    }   // checkWord

    private void clearText() {
        answerTextView.setText("");
    }

    public void myClearAll(View view) {
        answerTextView.setText("");
    }

} // Main Class
