//package com.walhalla.pcleaner.activity;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.PorterDuff;
//import android.graphics.PorterDuffXfermode;
//import android.graphics.drawable.Drawable;
//import android.graphics.drawable.ShapeDrawable;
//import android.graphics.drawable.shapes.RectShape;
//import android.view.View;
//
//import kgbook.ru.R;
//
//public class LighterView extends View {
//
//    private static final int FLAME_COLOR = Color.rgb(255, 152, 0); // оранжевый цвет пламени
//
//    private final Paint mPaint = new Paint();
//    private final ShapeDrawable mFlameDrawable;
//
//    public LighterView(Context context) {
//        super(context);
//
//        // Настройка свойств Paint для рисования пламени
//        mPaint.setColor(FLAME_COLOR);
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setAntiAlias(true);
//
//        // Создание ShapeDrawable для рисования пламени
//        mFlameDrawable = new ShapeDrawable(new RectShape());
//        mFlameDrawable.setAlpha(128); // Установка непрозрачности 50%
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        // Очистить экран
//        canvas.drawColor(Color.BLACK);
//
//        // Нарисовать горящее пламя
//        int width = getWidth();
//        int height = getHeight();
//        int flameHeight = height / 2; // высота пламени = половина высоты экрана
//        int flameWidth = width / 4; // ширина пламени = четверть ширины экрана
//        int flameX = (width - flameWidth) / 2; // позиция пламени по горизонтали
//        int flameY = (height - flameHeight); // позиция пламени по вертикали
//        mFlameDrawable.setBounds(flameX, flameY, flameX + flameWidth, height);
//        mFlameDrawable.draw(canvas);
//
//        // Нарисовать маску для пламени
//        Bitmap maskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flame_mask);
//        int maskWidth = maskBitmap.getWidth();
//        int maskHeight = maskBitmap.getHeight();
//        int maskX = (width - maskWidth) / 2; // позиция маски по горизонтали
//        int maskY = (height - maskHeight) / 2; // позиция маски по вертикали
//        canvas.drawBitmap(maskBitmap, maskX, maskY, null);
//
//        // Применить маску для пламени
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        Drawable flameDrawable = getResources().getDrawable(R.drawable.flame);
//        flameDrawable.setBounds(flameX, flameY, flameX + flameWidth, height);
//        flameDrawable.draw(canvas);
//        mPaint.setXfermode(null);
//    }
//}