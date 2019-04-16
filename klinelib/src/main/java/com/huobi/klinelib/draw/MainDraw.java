package com.huobi.klinelib.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.huobi.klinelib.base.BaseKLineChartView;
import com.huobi.klinelib.R;
import com.huobi.klinelib.base.IChartDraw;
import com.huobi.klinelib.base.IValueFormatter;
import com.huobi.klinelib.entity.ICandle;
import com.huobi.klinelib.entity.IKLine;
import com.huobi.klinelib.formatter.ValueFormatter;
import com.huobi.klinelib.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************
 * Description   :
 *
 * @PackageName  : com.huobi.klinelib.utils
 * @FileName     : MainDraw.java
 * @Author       : chao
 * @Date         : 2019/1/8
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/
public class MainDraw implements IChartDraw<ICandle> {

    private int itemCount;

    public void setItemCount(int mItemCount) {
        itemCount = mItemCount;
    }


    private float mCandleWidth = 0;
    private float mCandleLineWidth = 0;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mGreenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma5Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma10Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma30Paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint mSelectorTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mSelectorBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Context mContext;
    private List<String> marketInfoText = new ArrayList();

    private boolean mCandleSolid = true;
    // 是否分时
    private boolean isLine = false;
    private Status status = Status.MA;
//    private KLineChartView kChartView;

    public MainDraw(BaseKLineChartView view) {
        Context context = view.getContext();
//        kChartView = (KLineChartView) view;
        mContext = context;
        mRedPaint.setColor(ContextCompat.getColor(context, R.color.chart_up));
        mGreenPaint.setColor(ContextCompat.getColor(context, R.color.chart_down));
        mLinePaint.setColor(ContextCompat.getColor(context, R.color.chart_line));
        paint.setColor(ContextCompat.getColor(context, R.color.chart_line_background));
        marketInfoText.add("时间 ");
        marketInfoText.add("开    ");
        marketInfoText.add("高    ");
        marketInfoText.add("低    ");
        marketInfoText.add("收    ");
        marketInfoText.add("涨跌额 ");
        marketInfoText.add("涨跌幅 ");
        marketInfoText.add("成交量 ");
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public void drawTranslated(@Nullable ICandle lastPoint, @NonNull ICandle curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKLineChartView view, int position) {
        if (isLine) {
            if (position < itemCount - 1) {
                float lastClosePrice = lastPoint.getClosePrice();
                float closePrice = curPoint.getClosePrice();
                view.drawLine(canvas, mLinePaint, lastX, lastClosePrice, curX, closePrice);
                view.drawMinutsLineArea(canvas, paint, lastX, lastClosePrice, curX, closePrice);

            } else {
                float lastClosePrice = lastPoint.getClosePrice();
                float closePrice = curPoint.getClosePrice();
                view.drawLastMinutsLine(canvas, mLinePaint, lastX, lastClosePrice, curX, closePrice);
                view.drawLastMinutsLineArea(canvas, paint, lastX, lastClosePrice, curX, closePrice);
            }
            //绘制分时线最后的点
            if (position == itemCount - 1) {
                view.drawEndPoint(canvas, curX, curPoint.getClosePrice());
            }


            if (status == Status.MA) {
                //画ma60
                if (lastPoint.getMA60Price() != 0) {
                    view.drawLine(canvas, ma10Paint, lastX, lastPoint.getMA60Price(), curX, curPoint.getMA60Price());
                }
            } else if (status == Status.BOLL) {
                //画boll
                if (lastPoint.getMb() != 0) {
                    view.drawLine(canvas, ma10Paint, lastX, lastPoint.getMb(), curX, curPoint.getMb());
                }
            }
        } else {
            drawCandle(view, canvas, curX, curPoint.getHighPrice(), curPoint.getLowPrice(), curPoint.getOpenPrice(), curPoint.getClosePrice());
            if (status == Status.MA) {
                //画ma5
                if (lastPoint.getMA5Price() != 0) {
                    view.drawLine(canvas, ma5Paint, lastX, lastPoint.getMA5Price(), curX, curPoint.getMA5Price());
                }
                //画ma10
                if (lastPoint.getMA10Price() != 0) {
                    view.drawLine(canvas, ma10Paint, lastX, lastPoint.getMA10Price(), curX, curPoint.getMA10Price());
                }
                //画ma30
                if (lastPoint.getMA30Price() != 0) {
                    view.drawLine(canvas, ma30Paint, lastX, lastPoint.getMA30Price(), curX, curPoint.getMA30Price());
                }
            } else if (status == Status.BOLL) {
                //画boll
                if (lastPoint.getUp() != 0) {
                    view.drawLine(canvas, ma5Paint, lastX, lastPoint.getUp(), curX, curPoint.getUp());
                }
                if (lastPoint.getMb() != 0) {
                    view.drawLine(canvas, ma10Paint, lastX, lastPoint.getMb(), curX, curPoint.getMb());
                }
                if (lastPoint.getDn() != 0) {
                    view.drawLine(canvas, ma30Paint, lastX, lastPoint.getDn(), curX, curPoint.getDn());
                }
            }
        }

    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKLineChartView view, int position, float x, float y) {
        ICandle point = (IKLine) view.getItem(position);
        //修改头文字显示在顶部
        Paint.FontMetrics fm = ma10Paint.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        y = textHeight;
//        y = y - 5;
        if (isLine) {
            if (status == Status.MA) {
                if (point.getMA60Price() != 0) {
                    String text = "MA60:" + view.formatValue(point.getMA60Price()) + "  ";
                    canvas.drawText(text, x, y, ma10Paint);
                }
            } else if (status == Status.BOLL) {
                if (point.getMb() != 0) {
                    String text = "BOLL:" + view.formatValue(point.getMb()) + "  ";
                    canvas.drawText(text, x, y, ma10Paint);
                }
            }
        } else {
            if (status == Status.MA) {
                String text;
                if (point.getMA5Price() != 0) {
                    text = "MA5:" + view.formatValue(point.getMA5Price()) + "  ";
                    canvas.drawText(text, x, y, ma5Paint);
                    x += ma5Paint.measureText(text);
                }
                if (point.getMA10Price() != 0) {
                    text = "MA10:" + view.formatValue(point.getMA10Price()) + "  ";
                    canvas.drawText(text, x, y, ma10Paint);
                    x += ma10Paint.measureText(text);
                }
                if (point.getMA20Price() != 0) {
                    text = "MA30:" + view.formatValue(point.getMA30Price());
                    canvas.drawText(text, x, y, ma30Paint);
                }
            } else if (status == Status.BOLL) {
                if (point.getMb() != 0) {
                    String text = "BOLL:" + view.formatValue(point.getMb()) + "  ";
                    canvas.drawText(text, x, y, ma10Paint);
                    x += ma5Paint.measureText(text);
                    text = "UB:" + view.formatValue(point.getUp()) + "  ";
                    canvas.drawText(text, x, y, ma5Paint);
                    x += ma10Paint.measureText(text);
                    text = "LB:" + view.formatValue(point.getDn());
                    canvas.drawText(text, x, y, ma30Paint);
                }
            }
        }
        if (view.isLongPress()) {
            drawSelector(view, canvas);
        }
    }

    @Override
    public float getMaxValue(ICandle point) {
        if (status == Status.BOLL) {
            if (Float.isNaN(point.getUp())) {
                if (point.getMb() == 0) {
                    return point.getHighPrice();
                } else {
                    return point.getMb();
                }
            } else if (point.getUp() == 0) {
                return point.getHighPrice();
            } else {
                return point.getUp();
            }
        } else {
            return Math.max(point.getHighPrice(), point.getMA30Price());
        }
    }

    @Override
    public float getMinValue(ICandle point) {
        if (status == Status.BOLL) {
            if (point.getDn() == 0) {
                return point.getLowPrice();
            } else {
                return point.getDn();
            }
        } else {
            if (point.getMA30Price() == 0f) {
                return point.getLowPrice();
            } else {
                return Math.min(point.getMA30Price(), point.getLowPrice());
            }
        }
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    /**
     * 画Candle
     *
     * @param canvas
     * @param x      x轴坐标
     * @param high   最高价
     * @param low    最低价
     * @param open   开盘价
     * @param close  收盘价
     */
    private void drawCandle(BaseKLineChartView view, Canvas canvas, float x, float high, float low, float open, float close) {
        high = view.getMainY(high);
        low = view.getMainY(low);
        open = view.getMainY(open);
        close = view.getMainY(close);
        float r = mCandleWidth / 2;
        float lineR = mCandleLineWidth / 2;
        if (open > close) {
            //实心
            if (mCandleSolid) {
                canvas.drawRect(x - r, close, x + r, open, mRedPaint);
                canvas.drawRect(x - lineR, high, x + lineR, low, mRedPaint);
            } else {
                mRedPaint.setStrokeWidth(mCandleLineWidth);
                canvas.drawLine(x, high, x, close, mRedPaint);
                canvas.drawLine(x, open, x, low, mRedPaint);
                canvas.drawLine(x - r + lineR, open, x - r + lineR, close, mRedPaint);
                canvas.drawLine(x + r - lineR, open, x + r - lineR, close, mRedPaint);
                mRedPaint.setStrokeWidth(mCandleLineWidth * view.getScaleX());
                canvas.drawLine(x - r, open, x + r, open, mRedPaint);
                canvas.drawLine(x - r, close, x + r, close, mRedPaint);
            }

        } else if (open < close) {
            canvas.drawRect(x - r, open, x + r, close, mGreenPaint);
            canvas.drawRect(x - lineR, high, x + lineR, low, mGreenPaint);
        } else {
            canvas.drawRect(x - r, open, x + r, close + 1, mRedPaint);
            canvas.drawRect(x - lineR, high, x + lineR, low, mRedPaint);
        }
    }

    /**
     * draw选择器
     *
     * @param view
     * @param canvas
     */
    private void drawSelector(BaseKLineChartView view, Canvas canvas) {
        Paint.FontMetrics metrics = mSelectorTextPaint.getFontMetrics();
        float textHeight = metrics.descent - metrics.ascent;

        int index = view.getSelectedIndex();
        float padding = ViewUtil.Dp2Px(mContext, 5);
        float margin = ViewUtil.Dp2Px(mContext, 5);


        ICandle point = (ICandle) view.getItem(index);
        List<String> strings = new ArrayList<>();
//        strings.add(view.getAdapter().getDate(index));

        strings.add(String.valueOf(point.getDate()));
        strings.add(String.valueOf(point.getOpenPrice()));
        strings.add(String.valueOf(point.getHighPrice()));
        strings.add(String.valueOf(point.getLowPrice()));
        strings.add(String.valueOf(point.getClosePrice()));
        strings.add(String.valueOf(point.getOpenPrice() - point.getClosePrice()));
        strings.add(String.valueOf((point.getOpenPrice() - point.getClosePrice()) / point.getOpenPrice()));
        strings.add(String.valueOf(point.getVolume()));

        float width = 0;
        float left;
        float top = margin + view.getTopPadding();
        //上下多加两个padding值的间隙
        float height = padding * ((strings.size() - 1) + 4) + textHeight * strings.size();
        for (int i = 0; i < strings.size(); i++) {
            String tempString = marketInfoText.get(i) + strings.get(i);
            width = Math.max(width, mSelectorTextPaint.measureText(tempString));
        }
        width += padding * 2;

        float x = view.translateXtoX(view.getX(index));
        if (x > view.getChartWidth() / 2) {
            left = margin;
        } else {
            left = view.getChartWidth() - width - margin;
        }

        RectF r = new RectF(left, top, left + width, top + height);
        canvas.drawRoundRect(r, padding, padding, mSelectorBackgroundPaint);
        float y = top + padding * 2 + (textHeight - metrics.bottom - metrics.top) / 2;

        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            canvas.drawText(marketInfoText.get(i), left + padding, y, mSelectorTextPaint);
            canvas.drawText(s, left + width - padding - mSelectorTextPaint.measureText(s), y, mSelectorTextPaint);
            y += textHeight + padding;
        }

    }

    /**
     * 设置蜡烛宽度
     *
     * @param candleWidth
     */
    public void setCandleWidth(float candleWidth) {
        mCandleWidth = candleWidth;
    }

    /**
     * 设置蜡烛线宽度
     *
     * @param candleLineWidth
     */
    public void setCandleLineWidth(float candleLineWidth) {
        mCandleLineWidth = candleLineWidth;
    }

    /**
     * 设置ma5颜色
     *
     * @param color
     */
    public void setMa5Color(int color) {
        this.ma5Paint.setColor(color);
    }

    /**
     * 设置ma10颜色
     *
     * @param color
     */
    public void setMa10Color(int color) {
        this.ma10Paint.setColor(color);
    }

    /**
     * 设置ma30颜色
     *
     * @param color
     */
    public void setMa30Color(int color) {
        this.ma30Paint.setColor(color);
    }

    /**
     * 设置选择器文字颜色
     *
     * @param color
     */
    public void setSelectorTextColor(int color) {
        mSelectorTextPaint.setColor(color);
    }

    /**
     * 设置选择器文字大小
     *
     * @param textSize
     */
    public void setSelectorTextSize(float textSize) {
        mSelectorTextPaint.setTextSize(textSize);
    }

    /**
     * 设置选择器背景
     *
     * @param color
     */
    public void setSelectorBackgroundColor(int color) {
        mSelectorBackgroundPaint.setColor(color);
    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        ma30Paint.setStrokeWidth(width);
        ma10Paint.setStrokeWidth(width);
        ma5Paint.setStrokeWidth(width);
        mLinePaint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize) {
        ma30Paint.setTextSize(textSize);
        ma10Paint.setTextSize(textSize);
        ma5Paint.setTextSize(textSize);
    }

    /**
     * 蜡烛是否实心
     */
    public void setCandleSolid(boolean candleSolid) {
        mCandleSolid = candleSolid;
    }

    public void setLine(boolean line) {
//        if (isLine != line) {
        isLine = line;
//            if (isLine) {
//                kChartView.setCandleWidth(kChartView.dp2px(7f));
//            } else {
//                kChartView.setCandleWidth(kChartView.dp2px(6f));
//            }
//        }
    }

    public boolean isLine() {
        return isLine;
    }

}
