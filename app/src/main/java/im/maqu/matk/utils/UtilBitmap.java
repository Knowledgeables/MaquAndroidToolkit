package im.maqu.matk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UtilBitmap {

    private final static String TAG = UtilBitmap.class.getSimpleName();

    /**
     * 画圆形图片
     *
     * @param bitmap 输入的图片bitmap
     * @param pixels 圆形弧度
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, float pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = Color.parseColor("#ffffffff");
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        paint.setAntiAlias(true);// 设置画笔无锯齿
        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        paint.setColor(color);
        // 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }

    public static String compressImage(Context ctx, String input, Bitmap.CompressFormat format, int compressSize){
        String fileName = System.currentTimeMillis() + ".jpg";
        Bitmap outBm = null;
        Bitmap inBm = null;
        try{
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            inBm = BitmapFactory.decodeFile(input,opts);
            opts.inSampleSize = computeSampleSize(opts, 128*128, 1024*1024);
            //这里一定要将其设置回false，因为之前我们将其设置成了true
            opts.inJustDecodeBounds = false;
            inBm = BitmapFactory.decodeFile(input, opts);
            if(null != inBm){
                outBm = compressImage(inBm, format, compressSize);
                if(null != outBm){
                    String outPath = UtilStorage.createFilePath(ctx, fileName);
                    boolean isDone = outBm.compress(format, 100, new FileOutputStream(new File(outPath)));
                    if(isDone){
                        return outPath;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(null != inBm){
                if(!inBm.isRecycled()){
                    inBm.recycle();
                }
            }
            if(null != outBm){
                if(!outBm.isRecycled()){
                    outBm.recycle();
                }
            }
        }
        return null;
    }

    /**
     * 压缩图片到指定dax
     *
     * @param image 压缩的图片
     * @param format 图片格式
     * @param compressSize 压缩到的大小 byte[].length
     * @return
     */
    public static Bitmap compressImage(Bitmap image, Bitmap.CompressFormat format, int compressSize) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(format, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int quality = 100;
        while (baos.toByteArray().length > compressSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(format, quality, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            quality -= 5;// 每次都减少5
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, Bitmap.CompressFormat format,
                                        final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(format, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取图片信息，主要是宽，高和大小
     *
     * @param input
     * @return int[] [0] 宽|[1] 高 |[2] 文件大小
     */
    public static int[] getImageInfo(String input) {
        int[] values = new int[3];
        Bitmap inImage = null;
        try {
            File f = new File(input);
            if (f.exists()) {
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                inImage = BitmapFactory.decodeFile(f.getAbsolutePath(), opts);
                values[0] = opts.outWidth;
                values[1] = opts.outHeight;
                values[2] = (int) f.length();
            }
        } catch (Exception e) {
            Log.e(TAG, ">>>>>> getImageWH.error:" + e.getMessage());
        } finally {
            if (null != inImage) {
                if (!inImage.isRecycled()) {
                    inImage.recycle();
                    System.gc();
                }
            }
        }
        return values;
    }

    /**
     * 载入指定大小的图片
     *
     * @param path 图片路径
     * @param size 图片的显示比例
     * @return
     */
    public static Bitmap getBitmap(String path, int size) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inSampleSize = size;
        Bitmap bt = BitmapFactory.decodeFile(path, op);
        return bt;
    }

    /**
     * 按宽高比压缩图片
     *
     * @param path 图片路径
     * @param width 指定的宽
     * @param heigh 指定的高
     * @return
     */
    public static Bitmap getBitmap(String path, int width, int heigh) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        Bitmap bt = BitmapFactory.decodeFile(path, op);
        int xScale = op.outWidth / width;
        int yScale = op.outHeight / heigh;
        op.inSampleSize = xScale > yScale ? xScale : yScale;
        op.inJustDecodeBounds = false;
        bt = BitmapFactory.decodeFile(path, op);
        return bt;
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm 需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 放大/缩小位图
     * @param bitmap
     * @param scaleWidth
     * @param scaleHeight
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, float scaleWidth, float scaleHeight) {
        if (bitmap == null || scaleWidth<=0 || scaleHeight<=0) return null;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 计算缩放大小
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    /**
     * 计算初始化的缩放大小
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }



}
