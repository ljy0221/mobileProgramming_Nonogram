package com.example.nonograms;

import android.content.Context;
import android.graphics.Color;
import android.widget.TableRow;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Collection;
import java.util.Random;

public class Cell extends AppCompatButton {
    private boolean blackSquare;
    private boolean checked;
    private static int numBlackSquares = 0;
    private static final Random random = new Random();

    public Cell(@NonNull Context context) {
        super(context);

        // 레이아웃 파라미터 설정
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(150, 150);
        layoutParams.setMargins(0, 0, 0, 0);
        setLayoutParams(layoutParams);

        // 배경 리소스 설정
        setBackgroundResource(R.drawable.cell_selector);

        // 검정 사각형 초기화
        this.blackSquare = random.nextBoolean();
        if (blackSquare) {
            numBlackSquares++;
        }
    }

    public boolean isBlackSquare() {
        return blackSquare;
    }

    public boolean isChecked() {
        return checked;
    }

    public static int getNumBlackSquares() {
        return numBlackSquares;
    }

    public boolean markBlackSquare() {
        if (checked) {
            return true; // X 표시가 된 경우는 아무 동작도 하지 않음
        }

        if (blackSquare) {
            setBackgroundColor(Color.BLACK); // 검정 사각형용 shape 사용
            setEnabled(false);
            numBlackSquares--;
            return true;
        } else {
            return false; // 실패 시 GameControl에서 처리하도록 함
        }
    }

    public boolean toggleX() {
        checked = !checked;
        if (checked) {
            setBackgroundResource(R.drawable.red_clear); // X 표시용 shape 사용
        } else {
            setBackgroundResource(R.drawable.cell_selector); // 기본 상태로 복귀
        }
        return checked;
    }
}