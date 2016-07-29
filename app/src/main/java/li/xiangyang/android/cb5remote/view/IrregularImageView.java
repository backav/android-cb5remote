package li.xiangyang.android.cb5remote.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import li.xiangyang.android.touchable_controls.TouchableImageView;

/**
 * Created by bac on 16/7/29.
 */
public class IrregularImageView extends ImageView {
    private int width = -1;
    private int height = -1;
    private Bitmap bitmap;

    public IrregularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {



        int x = (int) event.getX();

        int y = (int) event.getY();

        if (width == -1 || height == -1) {
            Drawable drawable = getDrawable();
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            width = bitmap.getWidth();
            height = bitmap.getHeight();
        }

        if (null == bitmap || x < 0 || y < 0 || x >= width || y >= height) {
            return false;
        }

        int pixel = bitmap.getPixel(x, y);
        if (Color.TRANSPARENT == pixel) {
            return false;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.setAlpha(0.5f);
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            this.setAlpha(1.0f);
        }

        return super.onTouchEvent(event);
    }
}
