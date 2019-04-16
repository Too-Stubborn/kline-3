package com.huobi.klinelib.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huobi.klinelib.base.BaseKLineChartView;
import com.huobi.klinelib.base.IChartDraw;
import com.huobi.klinelib.base.IValueFormatter;
import com.huobi.klinelib.entity.IRSI;
import com.huobi.klinelib.formatter.ValueFormatter;

/*************************************************************************
 * Description   :
 *
 * @PackageName  : com.huobi.klinelib.utils
 * @FileName     : RSIDraw.java
 * @Author       : chao
 * @Date         : 2019/1/8
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/
public class RSIDraw implements IChartDraw<IRSI> {

    private Paint mRSI1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mRSI2Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mRSI3Paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public RSIDraw(BaseKLineChartView view) {

    }

    @Override
    public void drawTranslated(@Nullable IRSI lastPoint, @NonNull IRSI curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKLineChartView view, int position) {
        if (lastPoint.getRsi() != 0) {
            view.drawChildLine(canvas, mRSI1Paint, lastX, lastPoint.getRsi(), curX, curPoint.getRsi());
        }
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKLineChartView view, int position, float x, float y) {
        IRSI point = (IRSI) view.getItem(position);
        if (point.getRsi() != 0) {
            String text = "RSI(14)  ";
            canvas.drawText(text, x, y, view.getTextPaint());
            x += view.getTextPaint().measureText(text);
            text = view.formatValue(point.getRsi());
            canvas.drawText(text, x, y, mRSI1Paint);
        }
    }

    @Override
    public float getMaxValue(IRSI point) {
        return point.getRsi();
    }

    @Override
    public float getMinValue(IRSI point) {
        return point.getRsi();
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    @Override
    public void setItemCount(int mItemCount) {

    }

    public void setRSI1Color(int color) {
        mRSI1Paint.setColor(color);
    }

    public void setRSI2Color(int color) {
        mRSI2Paint.setColor(color);
    }

    public void setRSI3Color(int color) {
        mRSI3Paint.setColor(color);
    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        mRSI1Paint.setStrokeWidth(width);
        mRSI2Paint.setStrokeWidth(width);
        mRSI3Paint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize) {
        mRSI2Paint.setTextSize(textSize);
        mRSI3Paint.setTextSize(textSize);
        mRSI1Paint.setTextSize(textSize);
    }
}
