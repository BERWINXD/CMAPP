package com.example.hp.myapplication.CustomComponents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ElegantNumberButton extends androidx.appcompat.widget.AppCompatButton {

    private static final String TAG = "ElegantNumberButton";
    public String counter;

    public ElegantNumberButton(@NonNull Context context) {
        super(context);
        counter = "0";
    }

    public ElegantNumberButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        counter = "0";
    }

    public ElegantNumberButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        counter = "0";
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        this.incrementCounter();
    }

    public String getCounter() {
        return counter;
    }

    public void incrementCounter() {
        try {
            int temp = Integer.parseInt(counter);
            this.counter = String.valueOf(temp + 1);
        } catch (NumberFormatException ex) {
            Log.d(TAG, "incrementCounter Failed as the value passed was not a processable Integer");
        } catch (Exception e) {
            Log.d(TAG, "incrementCounter Failed due to an unknown reason");
        }
    }

    public void decrementCounter() {
        try {
            int temp = Integer.parseInt(counter);
            if (temp - 1 >= 0)
                this.counter = String.valueOf(temp - 1);
        } catch (NumberFormatException ex) {
            Log.d(TAG, "decrementCounter Failed as the value passed was not a processable Integer");
        } catch (Exception e) {
            Log.d(TAG, "decrementCounter Failed due to an unknown reason");
        }
    }

    public void resetCounter() {
        this.counter = "0";
    }
}
