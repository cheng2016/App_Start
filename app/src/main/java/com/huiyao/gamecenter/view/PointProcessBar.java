package com.huiyao.gamecenter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 任务天数节点进度条
 *
 **/

public class PointProcessBar extends ViewGroup {
    /**
     * 未选中时的连线画笔
     */
    private Paint mLinePaint;
    /**
     * 选中时的连线画笔
     */
    private Paint mLineSelectedPaint;
    /**
     * 未选中时的文字画笔
     */
    private Paint mTextPaint;
    /**
     * 选中时的文字画笔
     */
    private Paint mTextSelPaint;
    /**
     * 未选中时的实心圆画笔
     */
    private Paint mCirclePaint;
    /**
     * 选中时的内部实心圆画笔
     */
    private Paint mCircleSelPaint;
    /**
     * 选中时文字 圆形 颜色
     */
    private int mColorSelected = Color.parseColor("#F57300");
    /**
     * 未选中的文字 及 进度条 颜色
     */
    private int mColorDef = Color.parseColor("#B4B4B4");
    /**
     * 连线的高度
     */
    float mLineHeight = 20f;
    //圆的直径
    //float mCircleHeight = 50f;
    //float mCircleSelStroke = 8f;
    //圆半径
    float mCircleFillRadius = 25f;
    //文字大小
    float mTextSize = 35f;
    //文字离顶部圆形节点的距离
    float mTextMarginTop = 40f;
    float mCircleMarginTop = 20f;

    int widthVipImage = 100;
    int heightVipImage = 100;

    int defaultHeight;
    /**
     * 节点底部的文字列表
     */
    List<String> textList = new ArrayList<> ();
    /**
     * 文字同宽高的矩形，用来测量文字
     */
    List<Rect> mBounds;

    //此控件进度条绘制长度
    float viewWidth = 0;
    //已经选中的长度
    float viewSelectWidth = 0;

    //任务总共天数 默认80天
    private int totalDay = 80;
    //任务当前完成天数
    private int currentDay = 0;



    /**
     * 需要绘制圆形节点的 "第多少天"
     */
    List<Integer> nodeDayDaysList = new ArrayList<>();

    LinkedHashMap<Integer,Float> nodeDayPostionXMap = new LinkedHashMap<>();


    Canvas canvas;
    public PointProcessBar(Context context) {
        super(context);
    }
    public PointProcessBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        setWillNotDraw(false);
    }
    public PointProcessBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


   /* public PointProcessBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/
    /**
     * 初始化画笔属性
     */
    private void initPaint(){
        mLinePaint = new Paint();
        mLineSelectedPaint = new Paint();
        mTextPaint = new Paint();
        //mCircleStrokeSelPaint = new Paint();
        mTextSelPaint=new Paint();


        mCirclePaint = new Paint();
        mCircleSelPaint = new Paint();

        mLinePaint.setColor(mColorDef);
        //设置填充
        mLinePaint.setStyle(Paint.Style.FILL);
        //笔宽像素
        mLinePaint.setStrokeWidth(mLineHeight);
        //锯齿不显示
        mLinePaint.setAntiAlias(true);
        mLineSelectedPaint.setColor(mColorSelected);
        mLineSelectedPaint.setStyle(Paint.Style.FILL);
        mLineSelectedPaint.setStrokeWidth(mLineHeight);
        mLineSelectedPaint.setAntiAlias(true);
        mCirclePaint.setColor(mColorDef);
        //设置填充
        mCirclePaint.setStyle(Paint.Style.FILL);
        //笔宽像素
        mCirclePaint.setStrokeWidth(1);
        //锯齿不显示
        mCirclePaint.setAntiAlias(true);

        mCircleSelPaint.setStyle(Paint.Style.FILL);
        mCircleSelPaint.setStrokeWidth(1);
        mCircleSelPaint.setAntiAlias(true);
        mCircleSelPaint.setColor(mColorSelected);
        //普通状态的文本 画笔
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mColorDef);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //选中后的文本画笔
        mTextSelPaint.setTextSize(mTextSize);
        mTextSelPaint.setColor(mColorSelected);
        mTextSelPaint.setAntiAlias(true);
        mTextSelPaint.setTextAlign(Paint.Align.CENTER);


    }
    /**
     * 测量文字的长宽，将文字视为rect矩形
     */
    private void measureText(){
        mBounds = new ArrayList<> ();
        for(String name : textList){
            Rect mBound = new Rect();
            mTextPaint.getTextBounds(name, 0, name.length(), mBound);
            mBounds.add(mBound);
        }
    }
    /**
     * 测量view的高度
     */
    private void measureHeight(){
        if (mBounds!=null && mBounds.size()!=0) {
            defaultHeight = (int) (mCircleFillRadius*2 + heightVipImage+mTextMarginTop+mCircleMarginTop +mBounds.get(0).height());
        } else {
            defaultHeight = (int) (mCircleFillRadius*2 + heightVipImage+mTextMarginTop+mCircleMarginTop);
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //宽高都设置为wrap_content
        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            //宽设置为wrap_content
            setMeasuredDimension(widthSpecSize,defaultHeight);
        }else if(widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,heightSpecSize);
        }else if(heightSpecMode == MeasureSpec.AT_MOST){
            //高设置为wrap_content
            setMeasuredDimension(widthSpecSize, defaultHeight);
        }else{
            //宽高都设置为match_parent或具体的dp值
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i("hy","onLayout进入");

        if (isCreatImgComplte){
            return;
        }
        handler.sendEmptyMessage(0);
    }




    boolean isCreatImgComplte = false;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            viewWidth = getWidth();
            if(!nodeDayPostionXMap.isEmpty()){
                nodeDayPostionXMap.clear();
            }
            for (int i =0;i<nodeDayDaysList.size();i++){
                int nodeDay = nodeDayDaysList.get(i);
                if(nodeDay<=totalDay){
                    float nodeDayPotionX = viewWidth*nodeDay/totalDay;
                    nodeDayPostionXMap.put(nodeDay,nodeDayPotionX);
                }
            }
            Log.i("hy","nodedayMap长度"+nodeDayPostionXMap.size());
            int i = 0;
            for(Map.Entry entry : nodeDayPostionXMap.entrySet()){
                Log.i("hy","onLayout");
                int nodeDay = (int) entry.getKey();
                float nodeCx = (float) entry.getValue();
                nodeCx = nodeCx+mCircleFillRadius;
                int imgStartX = (int) (nodeCx-widthVipImage/2);
                int imgStarty = 0;

                ImageView imageView = new ImageView(getContext());
//            LayoutParams param = new MarginLayoutParams(imageInfoList.get(i).width, imageInfoList.get(i).height);
//            addView(imageView, param);
                addView(imageView);
                imageView.layout(imgStartX, 0, imgStartX+widthVipImage, heightVipImage);
                Log.i("hy","创建imagevew"+i);
                Glide.with(getContext()).load("http://res.huiyaohuyu.com//picture//20220714//62cff6958a9e3.png").fitCenter().into(imageView);
                i = i+1;
            }
            isCreatImgComplte = true;
            return true;
        }
    });









    @Override
    protected void onDraw(Canvas canvas) {
        //若未设置节点标题或者选中项的列表，则取消绘制

        Log.i("hy","onDraw进入");

        this.canvas = canvas;
        if (textList == null || textList.isEmpty() ||
                nodeDayDaysList == null || nodeDayDaysList.isEmpty() ||
                mBounds == null || mBounds.isEmpty()) {
            Log.i("hy","未设置进度天数数据不绘制ui");
            return;
        }
        //绘制线条的长度为整个控件长度减去 最后一个圆形节点直径长度否则会被圆形覆盖住
        viewWidth = getWidth();
        Log.i("hy控件长度",viewWidth+"");
        viewSelectWidth = viewWidth*currentDay/totalDay;
        Log.i("hy控件长度",viewSelectWidth+"选中");
        //绘制一条横向贯穿的线条
        float lineY = heightVipImage+mCircleMarginTop+mCircleFillRadius;
        canvas.drawLine(0,lineY,viewWidth,lineY,mLinePaint);
        canvas.drawLine(0,lineY,viewSelectWidth,lineY,mLineSelectedPaint);

        //计算需要显示节点圆形 在 x 轴上的位置
        for (int i =0;i<nodeDayDaysList.size();i++){
            int nodeDay = nodeDayDaysList.get(i);
            if(nodeDay<=totalDay){
                float nodeDayPotionX = viewWidth*nodeDay/totalDay;
                nodeDayPostionXMap.put(nodeDay,nodeDayPotionX);
            }
        }

        int i = 0;
        for(Map.Entry entry : nodeDayPostionXMap.entrySet()){
            int nodeDay = (int) entry.getKey();
            float nodeCx = (float) entry.getValue();
            nodeCx = nodeCx+mCircleFillRadius;

            float circleY = heightVipImage+mCircleMarginTop+mCircleFillRadius;
            float textY = heightVipImage+mCircleMarginTop+mCircleFillRadius*2+mTextMarginTop;
            if(nodeDay<=currentDay) {
                //当前需要绘制圆形节点的位置 已经是 进度超过的位置
                canvas.drawCircle(nodeCx, circleY, mCircleFillRadius, mCircleSelPaint);
                if(i<=textList.size()) {
                    canvas.drawText(textList.get(i), nodeCx, textY, mTextSelPaint);
                }
            }else{
                //绘制还未达到的进度 圆形节点 及文字
                canvas.drawCircle(nodeCx, circleY, mCircleFillRadius, mCirclePaint);
                if(i<=textList.size()) {
                    canvas.drawText(textList.get(i), nodeCx, textY, mTextPaint);
                }
            }
            i = i+1;
        }
    }










    /**
     * 供外部调用，显示控件
     * @param titles 底部标题内容列表
     *
     */
    public void show(List<String>  titles , List<Integer> nodeDayDaysList){
        if(titles != null && ! titles.isEmpty()){
            this.textList = titles;
        }
        if(nodeDayDaysList != null && ! nodeDayDaysList.isEmpty()){
            this.nodeDayDaysList = nodeDayDaysList;
        }
        measureText();
        measureHeight();
        //绘制
        invalidate();
    }


    /**
     * 更新底部节点标题内容
     * @param textList 节点标题内容列表
     */
    public void refreshTextList(List<String>  textList) {
        this.textList = textList;
        measureText();
        measureHeight();
        invalidate();
    }




    //获取任务总共天数
    public int getTotalDay() {
        return totalDay;
    }
    //设置任务总共天数
    public void setTotalDay(int totalDay) {
        //此处加20 是给进度条末端留下余量否则会沿边显示
        this.totalDay = totalDay+20;
    }

    //获取当前任务完成天数
    public int getCurrentDay() {
        return currentDay;
    }

    /**
     * 设置任务完成天数 用于签到后刷新连续签到天数设置进度ui
     */
    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
        measureText();
        measureHeight();
        invalidate();
    }


}
