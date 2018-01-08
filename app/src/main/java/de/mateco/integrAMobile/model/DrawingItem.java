package de.mateco.integrAMobile.model;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Path;

public class DrawingItem {
    public static final String ARG_ITEM_TYPE_PATH = "path";
    public static final String ARG_ITEM_TYPE_ITEM = "item";
    private Path path;
    private Paint paint;
    private String type;
    private Bitmap drawnBitmap;

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bitmap getDrawnBitmap() {
        return drawnBitmap;
    }

    public void setDrawnBitmap(Bitmap drawnBitmap) {
        this.drawnBitmap = drawnBitmap;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
