package net.golbarg.tailwind;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.golbarg.tailwind.db.DatabaseHandler;
import net.golbarg.tailwind.db.TableCategory;
import net.golbarg.tailwind.db.TableContent;
import net.golbarg.tailwind.db.TableContentValues;
import net.golbarg.tailwind.model.Category;
import net.golbarg.tailwind.model.Content;
import net.golbarg.tailwind.model.ContentValue;
import net.golbarg.tailwind.util.UtilJSON;

import org.json.JSONArray;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity {
    public static final String TAG = SplashScreenActivity.class.getName();

    boolean isActive = true;
    DatabaseHandler handler;
    TableCategory tableCategory;
    TableContent tableContent;
    TableContentValues tableContentValues;
    SharedPreferences pref;

    ProgressBar progressLoading;
    TextView txtStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        progressLoading = findViewById(R.id.progress_loading);

        txtStatus = findViewById(R.id.txt_status);
        txtStatus.setText("");

        handler = new DatabaseHandler(getApplicationContext());
        tableCategory = new TableCategory(handler);
        tableContent = new TableContent(handler);
        tableContentValues = new TableContentValues(handler);
        pref = getApplicationContext().getSharedPreferences("db_content", Context.MODE_PRIVATE);

        Thread splashThread = new Thread(){
            @Override
            public void run() {
                try {
                    String dbStatus = pref.getString("db_status", null);

                    if("success".equals(dbStatus)) {
                        waitTime();
                    } else {
                        addDataToDB();
                    }

                    finish();
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                } catch(Exception e) {
                    finish();
                    e.printStackTrace();
                }
            }

            private void waitTime() {
                int waitTime = 0;

                while(waitTime <= 2) {
                    try{
                        sleep(1000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waitTime += 1;
                }
            }
        };
        splashThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            isActive = false;
        }
        return super.onTouchEvent(event);
    }

    public void addDataToDB() {
        String categoryAdded = pref.getString(TableCategory.TABLE_NAME, null);

        if(!"added".equals(categoryAdded)) {
            txtStatus.setText("reading Categories");
            JSONArray jsonArrayCategory = UtilJSON.getJSONCategories(getApplicationContext());
            ArrayList<Category> categoryArrayList = UtilJSON.mapCategoriesFromJSON(jsonArrayCategory);
            tableCategory.emptyTable();

            txtStatus.setText("loading Categories");
            for (int i = 0; i < categoryArrayList.size(); i++) {
                tableCategory.add(categoryArrayList.get(i));
            }
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(TableCategory.TABLE_NAME, "added");
            editor.commit();
        }

        String contentAdded = pref.getString(TableContent.TABLE_NAME, null);

        if(!"added".equals(contentAdded)) {
            txtStatus.setText("reading Contents");
            JSONArray jsonArrayContent = UtilJSON.getJSONContents(getApplicationContext());
            ArrayList<Content> contentArrayList = UtilJSON.mapContentsFromJSON(jsonArrayContent);
            tableContent.emptyTable();
            txtStatus.setText("loading Contents");
            for (int i = 0; i < contentArrayList.size(); i++) {
                tableContent.add(contentArrayList.get(i));
            }
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(TableContent.TABLE_NAME, "added");
            editor.commit();
        }

        String contentValuesAdded = pref.getString(TableContentValues.TABLE_NAME, null);
        if(!"added".equals(contentValuesAdded)) {
            txtStatus.setText("reading Details");
            JSONArray jsonArrayContentValues = UtilJSON.getJSONContentValues(getApplicationContext());
            ArrayList<ContentValue> contentValues = UtilJSON.mapContentValuesFromJSON(jsonArrayContentValues);
            tableContentValues.emptyTable();

            txtStatus.setText("loading Details");
            for (int i = 0; i < contentValues.size(); i++) {
                tableContentValues.add(contentValues.get(i));
            }
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(TableContentValues.TABLE_NAME, "added");
            editor.commit();
        }

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("db_status", "success");
        editor.commit();

        txtStatus.setText("Successful.");
    }
}
