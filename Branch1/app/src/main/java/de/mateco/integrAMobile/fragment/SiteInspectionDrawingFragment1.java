package de.mateco.integrAMobile.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import de.mateco.integrAMobile.Helper.ColorPickerDialog;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.DrawingItem;
import de.mateco.integrAMobile.model.GridImage;
import de.mateco.integrAMobile.model.Language;

public class SiteInspectionDrawingFragment1 extends BaseFragment implements View.OnClickListener {

    FrameLayout layout;
    DrawingView drawView;
    //Button buttonColor,buttonClear,buttonUndo;

    ImageView imageviewColor,imageviewClear,imageviewUndo ;
    private DataBaseHandler db;
    private SharedPreferences preferences;
    Bitmap bitmap;
    private MatecoPriceApplication application;
    private Language language;
    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_site_inspection_drawing1, container, false);
        layout = (FrameLayout)rootView.findViewById(R.id.layout);

        application = (MatecoPriceApplication)getActivity().getApplication();
        language = application.getLanguage();
        db = new DataBaseHandler(getActivity());
        super.initializeFragment(rootView);
        preferences = getActivity().getSharedPreferences("SiteInspection",Context.MODE_PRIVATE);
        setLanguage();
        drawView = new DrawingView(getActivity().getApplicationContext());
        layout.addView(drawView);

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelSketch());
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);

        return rootView;
    }

    private void setLanguage() {
        imageviewColor = (ImageView) rootView.findViewById(R.id.imageviewColor);
        imageviewClear = (ImageView) rootView.findViewById(R.id.imageviewClear);
        imageviewUndo = (ImageView) rootView.findViewById(R.id.imageviewUndo);

        imageviewUndo.setOnClickListener(this);
        imageviewClear.setOnClickListener(this);
        imageviewColor.setOnClickListener(this);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        menu.findItem(R.id.actionSearch).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        switch (item.getItemId()) {
            case R.id.actionSettings:
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                if (getFragmentManager().getBackStackEntryCount() == 0)
                {
                    getActivity().finish();
                }
                else
                {
                    getFragmentManager().popBackStack();
                }
                return true;
            case R.id.actionRight:
                layout.setDrawingCacheEnabled(true);
                Bitmap bitmap = layout.getDrawingCache(true);
                Random random = new Random();
                int n = 10000;
                String image = "Mateco " + random.nextInt(n);
                String root = Environment.getExternalStorageDirectory().toString();
                File newDir = new File(root + "/Mateco");
                newDir.mkdirs();
                String imageName =  image + ".jpg";
                File file = new File(newDir, imageName);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,0, bos);
                byte[] byteArray = bos.toByteArray();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    fos.write(byteArray);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GridImage img = new GridImage();
                img.setPath(file.getAbsolutePath());
                img.setBvoId(preferences.getInt(DataHelper.SiteInspectionId,0));
                db.addPhoto(img);
                showLongToast(language.getMessageDrawingSaved());
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imageviewClear:
                drawView.clear();
                break;
            case R.id.imageviewColor:
                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(getActivity(), Color.WHITE, new ColorPickerDialog.OnColorSelectedListener() {

                    @Override
                    public void onColorSelected(int color) {
                        drawView.setColor(color);
                    }
                });
                colorPickerDialog.show();
                break;
            case R.id.imageviewUndo:
                drawView.onClickUndo();
                break;


        }
    }

    public class DrawingView extends View
    {
        private Path drawPath;
        private Paint drawPaint, canvasPaint;
        private int paintColor = Color.RED;
        private Canvas drawCanvas;
        private Bitmap canvasBitmap;
        private ArrayList<DrawingItem> drawingItems;
        private float mX, mY;
        private final float TOUCH_TOLERANCE = 4;
        private int height,width;

        public DrawingView(Context context) {
            super(context);
            drawPath = new Path();
            drawPaint = new Paint();
            drawPaint.setColor(paintColor);
            drawPaint.setAntiAlias(true);
            drawPaint.setStrokeWidth(10);
            drawPaint.setStyle(Paint.Style.STROKE);
            drawPaint.setStrokeJoin(Paint.Join.ROUND);
            drawPaint.setStrokeCap(Paint.Cap.ROUND);
            canvasPaint = new Paint(Paint.DITHER_FLAG);
            drawingItems = new ArrayList<DrawingItem>();
        }
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            drawCanvas = new Canvas(canvasBitmap);
            width = w;
            height = h;
        }
        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
            for (int i = 0; i < drawingItems.size(); i++)
            {
                if (drawingItems.get(i).getType().equals(DrawingItem.ARG_ITEM_TYPE_PATH))
                {
                    canvas.drawPath(drawingItems.get(i).getPath(),drawingItems.get(i).getPaint());
                }
                else if(drawingItems.get(i).getType().equals(DrawingItem.ARG_ITEM_TYPE_ITEM))
                {
                    Bitmap b = drawingItems.get(i).getDrawnBitmap();
                    b = Bitmap.createScaledBitmap(b,width,height, true);
                    canvas.drawBitmap(b,0,0,drawingItems.get(i).getPaint());
                }
            }
            canvas.drawPath(drawPath, drawPaint);
        }

        private void touch_up(float x, float y)
        {
            drawPath.lineTo(mX, mY);
            DrawingItem singleItem = new DrawingItem();
            singleItem.setPath(drawPath);
            singleItem.setPaint(drawPaint);
            drawPaint = new Paint(drawPaint);
            singleItem.setType(DrawingItem.ARG_ITEM_TYPE_PATH);
            drawingItems.add(singleItem);
            drawPath = new Path();
            drawPath.reset();
        }
        private void touch_start(float x, float y)
        {
            drawPath.reset();
            drawPath.moveTo(x, y);
            mX = x;
            mY = y;
        }
        private void touch_move(float x, float y)
        {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
            {
                drawPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float touchX = event.getX();
            float touchY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(touchX, touchY);
                    drawView.invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(touchX, touchY);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up(touchX, touchY);
                    invalidate();
                    break;
                default:
                    return false;
            }
            invalidate();
            return true;
        }

        public void setColor(int color)
        {
            invalidate();
            drawPaint.setColor(color);
        }
        public void setImage()
        {
            DrawingItem singleItem = new DrawingItem();
            singleItem.setType(DrawingItem.ARG_ITEM_TYPE_ITEM);
            singleItem.setPaint(drawPaint);
            singleItem.setDrawnBitmap(bitmap);
            drawingItems.add(singleItem);
            invalidate();
        }

        public void clear()
        {
            drawingItems.clear();
            invalidate();
        }
        public void onClickUndo()
        {
            try
            {
                if (drawingItems.size() > 0)
                {
                    drawingItems.remove(drawingItems.size() - 1);
                    drawView.invalidate();
                }
                else
                {
                }
            }
            catch (Exception e)
            {
                Toast.makeText(getContext(), "No more undo", Toast.LENGTH_LONG).show();
            }
        }
    }
}


