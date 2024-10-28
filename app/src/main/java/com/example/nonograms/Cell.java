package com.example.nonograms;

import android.content.Context;
import android.graphics.Color;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Random;

public class Cell extends AppCompatButton {
    boolean blackSquare;
    boolean checked;
    static int numBlackSquares = 0;
    private static final Random random = new Random();

    public Cell(@NonNull Context context) {
        super(context);
        setBackgroundResource(R.drawable.cell_selector);
        init();
    }

    private void init() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(150, 150);
        layoutParams.setMargins(0, 0, 0, 0);
        setBackgroundResource(R.drawable.cell_selector);
        this.blackSquare = random.nextBoolean();
        if (blackSquare) {
            numBlackSquares++;
        }
    }

    public boolean isBlackSquare() {
        return blackSquare;
    }

    public static int getNumBlackSquares() {
        return numBlackSquares;
    }

    public boolean markBlackSquare() {
        if (checked) {
            return true; // 이미 체크된 경우
        }

        if (isBlackSquare()) {
            setBackgroundColor(Color.BLACK); // 검정 사각형으로 표시
            setEnabled(false); // 더 이상 클릭되지 않도록 비활성화
            numBlackSquares--; // 찾지 않은 검정 사각형 개수 감소
            return true;
        } else {
            toggleX(); // 검정 사각형이 아닌 경우 X 표시로 토글
            return false;
        }
    }

    public void toggleX() {
        checked = !checked; // checked 상태를 반전시킴

        if (checked) {
            setBackgroundColor(Color.RED); // 체크된 상태일 때 빨간색으로 표시
        } else {
            setBackgroundColor(Color.WHITE); // 체크 해제 시 기본 색상으로 복원
        }
    }
}
