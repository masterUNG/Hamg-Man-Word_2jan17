package rtc.surangrat.ratchanok.hangmanword;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {

    private EditText editText;
    private TextView timeTextView, scoreTextView, questionTextView, answerTextView;
    private int scoreAnInt = 0; // ค่าของคะแนนที่ตอบถูก
    private int timeAnInt = 30;  // เวลาที่ใช้แต่ละคำ เวลาหมดจะไปที่ ShowScore
    private int wordAnInt = 0;   // index ของคำใบ้
    private int index = 0;  //
    private int timesWordAnInt = 0; // จำนวนดิจิ ของคำ
    private int indexSingleAnswerAnInt = 0; //
    private int indexHang = 0;  //
    private int falseAnInt = 0; //  จำนวนที่ตอบผิด
    private int trueAnInt = 0; // จำนวนคำที่ตอบถูก ถ้ามีค่าเท่่ากับ คำจะไปข้อต่อไป
    private int indexTest = 0; // Integer ที่เพิ่มค่า ถ้า คลิก Text
    private MyConstant myConstant;
    private String[] questionStrings, answerStrings, singleAnswerStrings;
    private String resultString;
    private int maxLengthofEditText;    // จำนวนดิจิ
    private ArrayList<String> answerArrayList, testStrings;
    private ImageView imageView;
    private ArrayList<String> strings = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        bindWidget();

        //checkTime();

        //Setup
        setUp();

        //Question & Answer
        questionAnAnswer(); // กำหนด คำใบ้ และ คำตอบ ว่าจะมีจำนวน ดิจิ 4,6,8

        changeView(wordAnInt);  // แสดงคำใบ้แรก

        checkWord(scoreAnInt);


        //About Edittext
        getOneDigit();



    }   // Main Method

    private void getOneDigit() {

        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                resultString = editText.getText().toString();
                answerTextView.setText(resultString);
                myArrayList(resultString);

            }   // onTextChange

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void setUp() {
        maxLengthofEditText = getIntent().getIntExtra("Word", 4);
        Log.d("3decV1", "maxLength ==> " + maxLengthofEditText);
        singleAnswerStrings = new String[maxLengthofEditText];
    }

    public void clickTest(View view) {
        String tab = "5janV1";
        Log.d(tab, "คลิก Test");

        indexTest += 1; // เพิ่มค่า ทุกครั้งที่ คลิก Text
        Log.d(tab, "indexTest ==> " + indexTest);

        changeView(indexTest);


    }   // clickTest

    //กำหนด โหมดว่า จะเล่นแบบ 4,6,8 โดยดูการรับค่า Intent จาก Activity ที่มา
    private void questionAnAnswer() {
        myConstant = new MyConstant();

        //Setup คำใบ้ และ เฉลย มาเก็บไว้ใน Array
        switch (maxLengthofEditText) {
            case 4:
                questionStrings = myConstant.getQuestionStrings();
                answerStrings = myConstant.getAnswerStrings();
                break;
            case 6:
                questionStrings = myConstant.getQuestion2Strings();
                answerStrings = myConstant.getAnswer2Strings();
                break;
            case 8:
                questionStrings = myConstant.getQuestion3Strings();
                answerStrings = myConstant.getAnswer3Strings();
                break;
        }   // switch
    }   // questionAnswer

    private void myArrayList(String resultString) {

        try {

            ArrayList<String> strings = new ArrayList<String>();
            String strResult = resultString.substring(timesWordAnInt, timesWordAnInt + 1);
            strings.add(strResult);
            String s = strings.toString();
            Log.d("3decV2", "resultString ==>" + strResult);
            Log.d("3decV2", "s ==> " + s);

            checkAnswer(s); // Check สิ่งที่ คีเข้าไปใน Edit text [1], [12], [123], [1234]
            timesWordAnInt += 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }   // myArrayList

    private void checkAnswer(String s) {

        Log.d("3decV5", " s ==> " + s);

        if (s.equals(singleAnswerStrings[timesWordAnInt])) {
            //คำตอบถูก
            Log.d("3decV5", "Result OK");

            trueAnInt += 1;

            if (trueAnInt >= maxLengthofEditText) {
                nextWord();
            }

        } else {
            //ตำตอบผิด

            falseAnInt += 1;
            Log.d("3decV5", "Result NO");
            Log.d("3decV5", "truefalse ==> " + falseAnInt);

            //ดูว่าผิดครบ 4 หรือยัง
            if (falseAnInt >= 4) {
                resetWord();
            }   // if

            changeImage();

        }   // if

        for (int i = 0; i < singleAnswerStrings.length; i++) {
            Log.d("3decV5", "singleAnswerString(" + i + ") ==> " + singleAnswerStrings[i]);
        }   //for

    }   // checkAnswer

    private void resetWord() {
        editText.setText("");
    }

    private void nextWord() {

        String tag = "3janV1";
        Log.d(tag, "nextWord ทำงาน");
        timeAnInt = 30;
        wordAnInt += 1;
        Log.d(tag, "wordAndInt ==> " + wordAnInt);
        changeView(wordAnInt);
        editText.setText("");
        scoreAnInt += 1;
        strings = null;
        Log.d(tag, "scoerAnIng ==> " + scoreAnInt);
        checkWord(scoreAnInt);


    }   // nextWord

    private void changeImage() {

        int[] intHang = new int[]{R.drawable.hang2, R.drawable.hang3,
                R.drawable.hang4, R.drawable.hang5};

        try {

            imageView.setImageResource(intHang[indexHang]);
            indexHang += 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }   // change


    private void changeView(int index) {

        questionTextView.setText(questionStrings[index]);

    }

    // จะทำงาน ตามจำนวน
    private void checkWord(int indexWord) {
        String tag = "3decV3";
        Log.d(tag, "คำตอบ ข้อที่ (" + indexWord + ") ==> " + answerStrings[indexWord]);

        String[] strings = null;
        strings = answerStrings[indexWord].split("");   //แยก Word เป็น digi
        Log.d(tag, "string.length ==> " + strings.length);

        for (int i = 0; i < strings.length; i++) {
            Log.d("3decV3", "strings(" + i + ")= " + strings[i]);

            createTrueAnswer(maxLengthofEditText, strings[i]);

        }   // for


        for (int i = 1; i < strings.length; i++) {
            answerArrayList = new ArrayList<String>();
            answerArrayList.add(strings[i]);
            Log.d("3decV3", "answerArrayList ==> " + answerArrayList.toString());
        }


    }   // checkWord

    private void createTrueAnswer(int maxLengthofEditText, String string) {

        strings = new ArrayList<String>();
        strings.add(string);
        String s = strings.toString();
        Log.d("3decV4", "ค่าที่ได้จากการแยกคำ s ==> " + s);

        singleAnswerStrings[indexSingleAnswerAnInt] = s;
        indexSingleAnswerAnInt += 1;


    }   // createTrue

    private void checkTime() {

        if (timeAnInt == 0) {
//            Intent intent = new Intent(PlayActivity.this, ShowScore.class);
//            startActivity(intent);
        }   // if

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeAnInt -= 1;
                timeTextView.setText(Integer.toString(timeAnInt) + " Sec");
                checkTime();
            }
        }, 1000);

    }   // checkTime

    private void bindWidget() {

        editText = (EditText) findViewById(R.id.editText);
        timeTextView = (TextView) findViewById(R.id.textView4);
        scoreTextView = (TextView) findViewById(R.id.textView5);
        questionTextView = (TextView) findViewById(R.id.txtQuestion);
        answerTextView = (TextView) findViewById(R.id.textView6);
        imageView = (ImageView) findViewById(R.id.imageView2);

    }

}   // Main Class
