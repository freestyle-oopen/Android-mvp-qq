package aiyagirl.nanchen.com.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hyphenate.util.DensityUtil;

import aiyagirl.nanchen.com.myapplication.R;

import static android.R.attr.y;


/**
 * Created by Administrator on 2017/10/26.
 */

public class Slidebar extends View {

    private static String[] BARTEXT = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M", "N", "O", "P", "Q", "R", "S"
            , "T", "U", "V", "W", "X", "Y", "Z"};
    private float x;
    private int measuredHeight;
    private Paint paint;
    private float itemHeight;
    private int paddingBottom;
    private int paddingTop;
    private OnBarIndexChanged listener;
    private boolean isShow=false;
    private String index="";

    public Slidebar(Context context) {
        this(context, null);
    }

    public Slidebar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Slidebar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Slidebar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        //anti alias flag  抗锯齿配置参数
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
       paint.setColor(Color.parseColor("#8c8c8c"));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(DensityUtil.sp2px(getContext(), 14f));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        paddingBottom = getPaddingBottom();
        paddingTop = getPaddingTop();
        measuredHeight = getMeasuredHeight() - paddingBottom - paddingTop;
        x = getMeasuredWidth() / 2;
        itemHeight = (measuredHeight + 0f) / BARTEXT.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < BARTEXT.length; i++) {
            float y = i * itemHeight + itemHeight / 2 + paddingTop;
            canvas.drawText(BARTEXT[i], x, y, paint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float startY = event.getY();
                setBackgroundResource(R.drawable.slidebar_bg);
                yChanged(startY);
                break;
            case MotionEvent.ACTION_MOVE:
                float Y = event.getY();
                yChanged(Y);
                break;
            case MotionEvent.ACTION_UP:
              index="";
                setBackgroundResource(android.R.color.transparent);
                if(listener!=null){
                    listener.indexChanged(false,null);
                }
                break;
        }
        return true;
    }

    private void yChanged(float y) {
        if(y<paddingTop){
            y=paddingTop+0f;
        }
        if(y>measuredHeight-paddingBottom){
            y=measuredHeight-paddingBottom;
        }
        int v = (int) ((y-paddingTop) / itemHeight);
        if(!index.equals(BARTEXT[v])){
            index=BARTEXT[v];
            if(listener!=null){
                listener.indexChanged(true,index);
            }
        }
    }

    public void setOnBarIndexChanged(OnBarIndexChanged listener) {
        this.listener = listener;
    }

    public interface OnBarIndexChanged {
        void indexChanged(boolean isShow, String index);
    }

}
