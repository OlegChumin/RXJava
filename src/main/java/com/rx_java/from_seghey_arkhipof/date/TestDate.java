package com.rx_java.from_seghey_arkhipof.date;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TestDate {
    public static void main(String[] args) {
        Observable<Date> timeObservable = Observable.create(new ObservableOnSubscribe<Date>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Date> emitter) throws Throwable {
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        emitter.onNext(new Date());
                    }
                }, 0, 1000);
            }
        });

        timeObservable.subscribe(new Consumer<Date>() {
            @Override
            public void accept(Date date) throws Throwable {
                System.out.println(date.toString());
            }
        });

        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        JLabel label = new JLabel();
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setVisible(true);

        timeObservable.subscribe(new Consumer<Date>() {
            @Override
            public void accept(Date date) throws Throwable {
                label.setText("   " + date.toString());
            }
        });
    }
}
