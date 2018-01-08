package de.mateco.integrAMobile.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.GridImage;
import de.mateco.integrAMobile.model.Language;


public class GalleryGridFragment extends BaseFragment implements View.OnClickListener {

    private ArrayList<String> arrPath = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private DataBaseHandler db;
    private SharedPreferences preferences;
    private Button buttonSelect;
    private MatecoPriceApplication matecoPriceApplication;
    private Language language;
    private ArrayList<String> imageUrls;
    private DisplayImageOptions options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_gallery_grid, container, false);

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);

        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        db = new DataBaseHandler(getActivity());
        preferences = getActivity().getSharedPreferences("SiteInspection", Context.MODE_PRIVATE);

        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        Cursor imagecursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");

        this.imageUrls = new ArrayList<String>();

        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            imageUrls.add(imagecursor.getString(dataColumnIndex));

            System.out.println("=====> Array path => "+imageUrls.get(i));
        }

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .cacheInMemory()
                .cacheOnDisc()
                .build();

        imageAdapter = new ImageAdapter(getActivity(), imageUrls);

        GridView imagegrid = (GridView)rootView.findViewById(R.id.PhoneImageGrid);
        imagegrid.setAdapter(imageAdapter);

        buttonSelect = (Button)rootView.findViewById(R.id.buttonSelect);
        buttonSelect.setOnClickListener(this);
        setupLanguage();

        imagecursor.close();
        return rootView;
    }

    private void setupLanguage()
    {
        buttonSelect.setText(language.getLabelSelectText());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.findItem(R.id.actionWrong).setVisible(false);
        menu.findItem(R.id.actionEdit).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionSettings).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
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
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonSelect:
                final int len = imageAdapter.getCheckedItems().size();
                arrPath.addAll(imageAdapter.getCheckedItems());
                for (int i =0; i<len; i++)
                {
                    Bitmap bitmap = null;
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        options.inPurgeable = true;
                        options.inInputShareable = true;
                        bitmap = BitmapFactory.decodeFile(arrPath.get(i), options);
                        Random random = new Random();
                        int n = 10000;
                        String image = "Bvo " + random.nextInt(n);
                        String root = Environment.getExternalStorageDirectory().toString();
                        File newDir = new File(root + "/Bvo");
                        newDir.mkdirs();
                        String imageName =  image + ".jpg";
                        File file = new File(newDir, imageName);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        if(bitmap != null){
                            bitmap.compress(Bitmap.CompressFormat.JPEG,60, bos);
                        }
                        byte[] byteArray = bos.toByteArray();
                        FileOutputStream fos = new FileOutputStream(file);
                        String path = file.getAbsolutePath();
                        fos.write(byteArray);
                        GridImage img = new GridImage();
                        img.setPath(path);
                        img.setBvoId(preferences.getInt(DataHelper.SiteInspectionId,0));
                        img.setFlag(0);
                        db.addPhoto(img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (getFragmentManager().getBackStackEntryCount() == 0)
                {
                    getActivity().finish();
                }
                else
                {
                    getFragmentManager().popBackStack();
                }
                break;
        }
    }

    public class ImageAdapter extends BaseAdapter {
        ArrayList<String> mList;
        LayoutInflater mInflater;
        Context mContext;
        SparseBooleanArray mSparseBooleanArray;

        public ImageAdapter(Context context, ArrayList<String> imageList) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mSparseBooleanArray = new SparseBooleanArray();
            mList = new ArrayList<String>();
            this.mList = imageList;
        }

        public ArrayList<String> getCheckedItems() {
            ArrayList<String> mTempArry = new ArrayList<String>();

            for(int i=0;i<mList.size();i++) {
                if(mSparseBooleanArray.get(i)) {
                    mTempArry.add(mList.get(i));
                }
            }

            return mTempArry;
        }

        public int getCount() {
            return imageUrls.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.grid_gallery_item, null);
                holder.imageview = (ImageView) convertView.findViewById(R.id.imageThumb);
                holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            imageLoader.displayImage("file://" + imageUrls.get(position), holder.imageview, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(Bitmap loadedImage) {

                }
            });

            holder.checkbox.setTag(position);
            holder.checkbox.setChecked(mSparseBooleanArray.get(position));
            holder.imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.checkbox.isChecked())
                        holder.checkbox.setChecked(false);
                    else
                        holder.checkbox.setChecked(true);
                }
            });
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
                }
            });

            return convertView;
        }
    }
    class ViewHolder {
        ImageView imageview;
        CheckBox checkbox;
        int id;
    }
}
