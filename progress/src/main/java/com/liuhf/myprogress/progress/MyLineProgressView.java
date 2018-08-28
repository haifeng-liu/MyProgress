package com.liuhf.myprogress.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * 创建人：liuhaifeng
 * 时间：2018/8/28.
 * 描述：直线型进度条
 * 修改历史：
 */

public class MyLineProgressView extends View {

    private final String TAG="MyLineProgressView";
    private Bitmap bitmp ;
    private Paint mPaint; //画笔
    private int color=Color.GREEN;   //设置的颜色
    private String text;  // 文字显示进度
    private double mProcess = 0;    //当前进度值
    private double max = 100;    //进度的最大值
    private double n = 3.6;  //圆形进度条要乘的弧度
    double screenWidth;    //屏幕的宽
    private Context mcontext;
    private int  progressize=80;
    private int paddings=10;
    int width=0;
    int height=0;
    private  int strokewidth=10;

    public final int LINE = 1;
    public final int CIRCLE = 2;

    private int type =1;

    public MyLineProgressView(Context context) {

        this(context, null);
    }

    public MyLineProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyLineProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mcontext=context;
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs,R.styleable.MyLineProgressView,defStyleAttr,0);
        int n=a.getIndexCount();
        for (int i = 0; i < n; i++){
            int attr = a.getIndex(i);
            if (attr==R.styleable.MyLineProgressView_linorcri){
                type=a.getInteger(R.styleable.MyLineProgressView_linorcri,1);
            }else if (attr==R.styleable.MyLineProgressView_color){
                color=a.getColor(attr,color);
            }else if (attr==R.styleable.MyLineProgressView_max){
                max=a.getInteger(attr,100);
            }else if (attr==R.styleable.MyLineProgressView_progress){
                mProcess=a.getInteger(attr,0);
            }else if (attr==R.styleable.MyLineProgressView_progressize){
                progressize=a.getInteger(attr,progressize);
            }else if (attr==R.styleable.MyLineProgressView_strokewidth){
                strokewidth=a.getInteger(attr,strokewidth);
            }else if (attr==R.styleable.MyLineProgressView_paddings){
                paddings=a.getInteger(attr,paddings);
            }

        }
        screenWidth = getScreenWidth(context);
        bitmp=Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint=new Paint();
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokewidth);
        mPaint.setAntiAlias(true);

        if (paddings==0&&strokewidth!=0){
            paddings=(int)Math.ceil(strokewidth/2);
            Log.d(TAG,"paddings--->"+paddings);
        }else{
            paddings=(int)Math.ceil(paddings);
        }
        RectF rect = new RectF(paddings, paddings, width-paddings, height-paddings); //  坐标是相对于父布局的
        double d=mProcess/max;
        int lineProcess = (int) (d *screenWidth);
        if (type==1) {
            //绘制线性进度条
            canvas.drawLine(0, 0, lineProcess, 0, mPaint);
        } else if (type==2) {
            //绘制圆形进度条
            canvas.drawArc(rect, -90, Float.parseFloat(mProcess * 3.6 + ""), false, mPaint);
        }


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = this.getMeasuredSize(widthMeasureSpec,true);
        height = this.getMeasuredSize(heightMeasureSpec,false);
        setMeasuredDimension(width,height);
    }



    public void setType(int  type) {
        this.type = type;
    }

    public void setColor(int color){
        this.color=color;
    }


    public void setMax(int max) {
        this.max= max;
    }

    public void setProcess(int pro) {
        this.mProcess = pro;
        if(mProcess > 100){
            return;
        }else{
            invalidate();

        }
        invalidate();

    }

    public static int getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }


    private int getMeasuredSize(int widthMeasureSpec, boolean b) {
        //模式
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        //尺寸
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        //计算所得的实际尺寸，要被返回
        int retSize = 0;
        //得到两侧的留边
        int padding =(b?getPaddingLeft()+getPaddingRight():getPaddingTop()+getPaddingBottom());

        //对不同模式进行判断
        if(specMode == MeasureSpec.EXACTLY){//显示指定控件大小
            retSize = specSize;

        }else{
            retSize = (b?bitmp.getWidth()+padding:bitmp.getHeight()+padding);

            if(specMode==MeasureSpec.UNSPECIFIED){
                retSize = Math.min(retSize,specSize);
            }
        }
        return retSize;
    }
}
