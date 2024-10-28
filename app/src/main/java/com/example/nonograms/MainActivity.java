package com.example.nonograms;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Arrays for top TextViews and main grid elements
    private TextView[][] topTextViews = new TextView[1][8];
    private TextView[][] textViews = new TextView[5][1];
    private Cell[][] buttons = new Cell[5][5];
    private int life = 3;
    private TextView lifeTextView;
    private ToggleButton checkedToggle;
    private boolean gameResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Layout 정의
        TableLayout tableLayout = findViewById(R.id.nonogramTable);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(150, 150);
        layoutParams.setMargins(0, 0, 0, 0);

        //lifeText 초기화
        lifeTextView = findViewById(R.id.textLife);
        updateLifeText();
        // 상단  TextView 초기화
        initTopTextViews(tableLayout, layoutParams);
        // Button & TextView 초기화
        initMainTable(tableLayout, layoutParams);
        // 칸 수 계산
        calculateColumnCounts();
        calculateRowCounts();
        // 모드 변경 버튼 초기화
        checkedToggle = findViewById(R.id.toggleCheckButton);
        checkedToggle.setBackgroundResource(R.drawable.cell_selector);
    }

    private void initTopTextViews(TableLayout tableLayout, TableRow.LayoutParams layoutParams) {
        for (int i = 0; i < 1; i++) {
            TableRow tableRow = new TableRow(this);
            tableLayout.addView(tableRow);

            // 왼쪽 공백 1칸
            for (int j = 0; j < 1; j++) {
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
        for (int i = 0; i < 5; i++) {
            TableRow tableRow = new TableRow(this);
            tableLayout.addView(tableRow);

            // Add TextViews
            for (int j = 0; j < 1; j++) {
                textViews[i][j] = new TextView(this);
                textViews[i][j].setText("0");
                textViews[i][j].setLayoutParams(layoutParams);
                textViews[i][j].setGravity(Gravity.END);
                tableRow.addView(textViews[i][j]);
            }

            // Add Buttons
            for (int j = 0; j < 5; j++) {
                buttons[i][j] = new Cell(this);
                buttons[i][j].setLayoutParams(layoutParams);
                buttons[i][j].setOnClickListener(v -> handleCellClick((Cell) v));
                tableRow.addView(buttons[i][j]);
            }
        }
    }
    // 가로줄 검은 칸 계산
    private void calculateRowCounts() {
        for (int i = 0; i < 5; i++) {
            List<Integer> counts = getCounts(buttons[i]);
            String countText = counts.isEmpty() ? "0" : counts.toString().replaceAll("[\\[\\],]", " ");
            textViews[i][0].setText(countText);
        }
    }

    // 세로줄 검은 칸 계산
    private void calculateColumnCounts() {
        for (int j = 0; j < 5; j++) {
            Cell[] colTempButtons = new Cell[5];
            for (int i = 0; i < 5; i++) {
                colTempButtons[i] = buttons[i][j];
            }

            List<Integer> counts = getCounts(colTempButtons);
            StringBuilder countText = new StringBuilder();
            for (int count : counts) {
                countText.append(count).append("\n");
            }

            // 마지막 개행문자 제거
            if (countText.length() > 0) {
                countText.setLength(countText.length() - 1);
            } else {
                countText.append("0");
            }

            topTextViews[0][j].setText(countText);
        }
    }

    private List<Integer> getCounts(Cell[] buttonRow) {
        List<Integer> counts = new ArrayList<>();
        int count = 0;

        for (Cell cell : buttonRow) {
            if (cell.isBlackSquare()) {
                count++;
            } else {
                if (count > 0) {
                    counts.add(count);
                    count = 0;
                }
            }
        }
        if (count > 0) counts.add(count); // Add the last sequence if any

        return counts;
    }
    private void handleCellClick(Cell cell) {
        if (gameResult) {
            return;
        }

        if (checkedToggle.isChecked()) {
            // X 모드
            cell.toggleX();
        } else {
            // 검정 사각형 찾기 모드
            boolean success = cell.markBlackSquare();
            if (!success && !cell.isChecked()) {
                decreaseLife();
            }
        }

        checkGameEnd();
    }
    private void decreaseLife() {
        life--;
        updateLifeText();

        if (life <= 0) {
            endGame(false, Cell.getNumBlackSquares());
        }
    }

    private void updateLifeText() {
        lifeTextView.setText("Life: " + life);
    }

    private void checkGameEnd() {
        if (Cell.getNumBlackSquares() == 0) {
            endGame(true, 0);
        }
    }

    private void endGame(boolean isWin, int remaining) {
        gameResult = true;
        disableAllCells();

        String message = isWin ?
                "Win! Congratulation!!!" :
                "Game OVER~" + remaining + " left...";

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        lifeTextView.setText(message);
    }

    private void disableAllCells() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

}
