package tekma.app.thanksgiving;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class ThanksgivingActivity extends AppCompatActivity {

    private TextView description;
    private Button btnFinish;
    private String[] stringArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanksgiving);
        init();
    }
    private void init(){
        bindViews();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        description.setText(stringArray[day]);
        description.setTextColor(Color.BLUE);

        btnFinish.setTextColor(Color.BLUE);
        btnFinish.setOnClickListener(v -> finish());

        // پخش صدای پیش‌فرض اعلان‌ها
        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone notificationSound = RingtoneManager.getRingtone(this, notificationSoundUri);
        notificationSound.play();

        // پخش صدای دلخواه
        // MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.notification_sound);
        // mediaPlayer.start();
    }

    private void bindViews(){
        stringArray = getResources().getStringArray(R.array.thanksgiving);
        description = findViewById(R.id.txt_description_moon);
        btnFinish = findViewById(R.id.btn_finish_t);
    }
}