package com.example.diceapp;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.util.Locale;
import java.util.Random;


public class dice_img extends AppCompatActivity {

    private Button roll_btn;
    private ImageView dice1,dice2;
    private TextView sum_txt;
    private LinearLayout layout;
    TextToSpeech textToSpeech;

    int[] img1 = {R.drawable.dice_1 , R.drawable.dice_2 , R.drawable.dice_3 , R.drawable.dice_4 , R.drawable.dice_5 , R.drawable.dice_6};
    int i1 = 0;

    int[] img2 = {R.drawable.dice_1 , R.drawable.dice_2 , R.drawable.dice_3 , R.drawable.dice_4 , R.drawable.dice_5 , R.drawable.dice_6};
    int i2 = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_img);
        binding();

        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(dice_img.this,R.color.black));

        setImg1(i1);
        setImg2(i2);

        roll_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                blink1();
                blink2();

                Random random = new Random();
                int a = random.nextInt(img1.length);

                Random random1 = new Random();
                int b = random1.nextInt(img2.length);

                MediaPlayer mp =  MediaPlayer.create(dice_img.this , R.raw.dice_throw);
                mp.start();

                dice1.setImageResource(img1[a]);
                dice2.setImageResource(img2[b]);

                Vibrator v = (Vibrator) getSystemService(dice_img.VIBRATOR_SERVICE);
                v.vibrate(300);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        int x = Integer.parseInt(String.valueOf(a));
                        int y = Integer.parseInt(String.valueOf(b));
                        int sum = (x+1)+(y+1);
                        sum_txt.setText(""+sum);

                        textToSpeech.speak(sum_txt.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);


                    }
                }, 700);

            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

    }

    void blink1()
    {
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(200); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.RESTART); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        dice1.startAnimation(animation);
    }

    void blink2()
    {
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(200); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.RESTART); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        dice2.startAnimation(animation);
    }

    private void binding()
    {
        roll_btn = findViewById(R.id.roll_btn);
        dice1 = findViewById(R.id.dice1);
        dice2 = findViewById(R.id.dice2);
        sum_txt = findViewById(R.id.sum_txt);
        layout = findViewById(R.id.layout);
    }

    void setImg1(int p)
    {
        Glide.with(dice_img.this)
                .load(img1[p])
                .placeholder(R.color.black)
                .into(dice1);
    }

    void setImg2(int p)
    {
        Glide.with(dice_img.this)
                .load(img2[p])
                .placeholder(R.color.black)
                .into(dice2);
    }
}