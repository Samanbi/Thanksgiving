package tekma.app.thanksgiving;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;

import tekma.app.thanksgiving.menuItem.AboutItemMenuActivity;

public class MainActivity extends AppCompatActivity {

    private SwitchMaterial switchEnergy, switchThanksgiving;
    private MaterialCardView cardEnergy, cardThanksgiving;
    private NavigationView mainNavigation;
    private DrawerLayout mainDrawer;
    private Button btnAboutApp;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        switchHandling();
        navigationHandling();
        topAppBarHandling();
    }

    private void topAppBarHandling() {
        btnAboutApp.setOnClickListener(v -> new MaterialAlertDialogBuilder(MainActivity.this)
                // Add customization options here
                .setIcon(R.drawable.notification_icon)
                .setTitle(R.string.about)
                .setMessage(R.string.about_app)
                .setNeutralButton(R.string.ok, null)
                .show());

        topAppBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.about_app_item) {
                //startActivity(new Intent(this, AboutAppItemMenuActivity.class));
                new MaterialAlertDialogBuilder(MainActivity.this)
                        // Add customization options here
                        .setIcon(R.drawable.notification_icon)
                        .setTitle(R.string.about)
                        .setMessage(R.string.about_app)
                        .setNeutralButton(R.string.ok, null)
                        .show();


            } else if (id == R.id.about_item) {
                startActivity(new Intent(this, AboutItemMenuActivity.class));
            }
            return true;
        });
    }

    private void navigationHandling() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                mainDrawer,
                topAppBar,
                R.string.open,
                R.string.close
        );
        mainDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mainNavigation.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.about_app_item) {
                new MaterialAlertDialogBuilder(MainActivity.this)
                        // Add customization options here
                        .setIcon(R.drawable.notification_icon)
                        .setTitle(R.string.about)
                        .setMessage(R.string.about_app)
                        .setNeutralButton(R.string.ok, null)
                        .show();
            } else if (id == R.id.about_item) {
                startActivity(new Intent(this, AboutItemMenuActivity.class));
            }

            mainDrawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void switchHandling() {
        cardEnergy.setOnClickListener(v -> startActivity(new Intent(this, ScreenNotifActivity.class)));
        cardThanksgiving.setOnClickListener(v -> startActivity(new Intent(this, ThanksgivingActivity.class)));

        // بازیابی تنظیمات
        SharedPreferences sharedPreferences1 = getPreferences(Context.MODE_PRIVATE);
        boolean switch1State = sharedPreferences1.getBoolean("switch1_state", true);
        boolean switch2State = sharedPreferences1.getBoolean("switch2_state", true);
        switchEnergy.setChecked(switch1State);
        switchThanksgiving.setChecked(switch2State);

        switchEnergy.setOnClickListener(v -> {
            // ذخیره تنظیمات
            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("switch1_state", switchEnergy.isChecked());
            editor.apply();

            if (switchEnergy.isChecked()) {
                Toast.makeText(this, "پیام انرژی زای روزانه برای شما فعال شد", Toast.LENGTH_SHORT).show();
            }
            if (!switchEnergy.isChecked()) {
                Toast.makeText(this, "پیام انرژی زای روزانه برای شما غیرفعال شد", Toast.LENGTH_SHORT).show();
            }
        });
        switchThanksgiving.setOnClickListener(v -> {
            // ذخیره تنظیمات
            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("switch2_state", switchThanksgiving.isChecked());
            editor.apply();

            if (switchThanksgiving.isChecked()) {
                Toast.makeText(this, "پیام شکر گزاری شبانه برای شما فعال شد", Toast.LENGTH_SHORT).show();
            }
            if (!switchThanksgiving.isChecked()) {
                Toast.makeText(this, "پیام شکر گزاری شبانه برای شما غیرفعال شد", Toast.LENGTH_SHORT).show();
            }
        });


        if (switchThanksgiving.isChecked()) {
            handelMessage(ThanksgivingActivity.class, 22);
        }

        if (switchEnergy.isChecked()) {
            handelMessage(ScreenNotifActivity.class, 8);
        }
    }

    private void handelMessage(Class myClass, int horn) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, myClass);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // تنظیم زمان برای ساعت 8 صبح
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        calendar1.set(Calendar.HOUR_OF_DAY, horn);

        // تنظیم آلارم برای باز کردن اکتیویتی در ساعت 8 صبح
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    private void bindViews() {
        cardEnergy = findViewById(R.id.card_energy);
        cardThanksgiving = findViewById(R.id.card_thanksgiving);
        switchEnergy = findViewById(R.id.switch_energy);
        switchThanksgiving = findViewById(R.id.switch_thanksgiving);
        mainDrawer = findViewById(R.id.main_drawer);
        topAppBar = findViewById(R.id.topAppBar);
        mainNavigation = findViewById(R.id.navigationView);
        btnAboutApp = findViewById(R.id.btn_about_app);
    }

}