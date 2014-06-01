package com.quran.labs.androidquran.widgets;

import com.quran.labs.androidquran.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import static com.quran.labs.androidquran.widgets.AyahToolBar.PipPosition;

public class AyahToolBarPip extends View {
  private int mWidth;
  private int mHeight;
  private Path mPath;
  private Paint mPaint;
  private PipPosition mPosition;

  public AyahToolBarPip(Context context) {
    super(context);
    init(context);
  }

  public AyahToolBarPip(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public AyahToolBarPip(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context);
  }

  private void init(Context context) {
    final Resources resources = context.getResources();
    mPosition = PipPosition.DOWN;
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setColor(resources.getColor(R.color.toolbar_background));
    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    mHeight = resources.getDimensionPixelSize(R.dimen.toolbar_pip_height);
    mWidth = resources.getDimensionPixelSize(R.dimen.toolbar_pip_width);
  }

  public void ensurePosition(PipPosition position) {
    mPosition = position;
    updatePoints();
  }

  private void updatePoints() {
    final int width = getWidth();
    final int height = getHeight();
    final Point pointA;
    final Point pointB;
    final Point pointC;
    if (mPosition == PipPosition.DOWN) {
      pointA = new Point(width / 2, height);
      pointB = new Point(0, 0);
      pointC = new Point(width, 0);
    } else {
      pointA = new Point(width / 2, 0);
      pointB = new Point(0, height);
      pointC = new Point(width, height);
    }

    mPath = new Path();
    mPath.setFillType(Path.FillType.EVEN_ODD);
    mPath.moveTo(pointA.x, pointA.y);
    mPath.lineTo(pointB.x, pointB.y);
    mPath.lineTo(pointC.x, pointC.y);
    mPath.close();
    invalidate();
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    updatePoints();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY));
  }

  @Override
  protected void onDraw(Canvas canvas) {
    canvas.drawPath(mPath, mPaint);
  }
}
