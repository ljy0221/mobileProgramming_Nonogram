package com.example.nonograms;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Arrays for top TextViews and main grid elements
    private TextView[][] topTextViews = new TextView[5][8];
    private TextView[][] textViews = new TextView[8][3];
    private Button[][] buttons = new Button[8][5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TableLayout tableLayout = findViewById(R.id.nonogramTable);

        // Layout 정의, 화면사이즈에 맞게 비율조정 필요
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(100, 100);
        layoutParams.setMargins(0, 0, 0, 0);

        // 상단  TextView 초기화
        initTopTextViews(tableLayout, layoutParams);

        // Button & TextView 초기화
        initMainTable(tableLayout, layoutParams);
    }

    private void initTopTextViews(TableLayout tableLayout, TableRow.LayoutParams layoutParams) {
        for (int i = 0; i < 3; i++) {
            TableRow tableRow = new TableRow(this);
            tableLayout.addView(tableRow);

            // 왼쪽 공백 3칸
            for (int j = 0; j < 3; j++) {
                TextView emptyView = new TextView(this);
                emptyView.setLayoutParams(layoutParams);
                tableRow.addView(emptyView);
            }
            // Add TextView
            for (int j = 0; j < 5; j++) {
                topTextViews[i][j] = new TextView(this);
                topTextViews[i][j].setText("0");  // Set initial text if needed
                topTextViews[i][j].setLayoutParams(layoutParams);
                topTextViews[i][j].setGravity(Gravity.CENTER);
                tableRow.addView(topTextViews[i][j]);
            }
        }
    }

    private void initMainTable(TableLayout tableLayout, TableRow.LayoutParams layoutParams) {
        for (int i = 0; i < 8; i++) {
            TableRow tableRow = new TableRow(this);
            tableLayout.addView(tableRow);

            // Add TextViews
            for (int j = 0; j < 3; j++) {
                textViews[i][j] = new TextView(this);
                textViews[i][j].setText("0");
                textViews[i][j].setLayoutParams(layoutParams);
                textViews[i][j].setGravity(Gravity.CENTER);
                tableRow.addView(textViews[i][j]);
            }

            // Add Buttons
            for (int j = 0; j < 5; j++) {
                buttons[i][j] = new Button(this);
                buttons[i][j].setLayoutParams(layoutParams);
                tableRow.addView(buttons[i][j]);
            }
        }
    }
}
