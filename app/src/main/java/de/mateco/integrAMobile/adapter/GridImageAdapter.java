package de.mateco.integrAMobile.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.fragment.SiteInspectionDrawingFragment;
import de.mateco.integrAMobile.model.CustomerOpenOrderModel;
import de.mateco.integrAMobile.model.GridImage;
import de.mateco.integrAMobile.model.Language;


public class GridImageAdapter extends BaseAdapter {

    private ArrayList<GridImage> listImages;
    private Context context;
    private String path;
    int pos;
    private MatecoPriceApplication application;
    private DataBaseHandler db;
    private Language language;

    public GridImageAdapter(Context context,ArrayList<GridImage> listImages,
                            MatecoPriceApplication application) {
        this.context = context;
        this.listImages = listImages;
        this.application = application;
        language = application.getLanguage();
    }

    @Override
    public int getCount() {
        return listImages.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Bitmap decodeFile(File f){
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            //The new size we want to scale to
            final int REQUIRED_SIZE=480;

            //Find the correct scale value. It should be the power of 2.
            int scale=1;
            while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
                scale*=2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }

    protected class ViewHolder{
        ImageView image;
        EditText edtName;
        ImageButton imgDelete;
        ImageButton imgRefresh;
        ImageButton imgDraw;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.fragment_oprational_data_gridview_row, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.edtName = (EditText)convertView.findViewById(R.id.edtPhotoName);
            viewHolder.image = (ImageView)convertView.findViewById(R.id.imgPhoto);
            viewHolder.imgDelete = (ImageButton)convertView.findViewById(R.id.imgDelete);
            viewHolder.imgRefresh = (ImageButton)convertView.findViewById(R.id.imgRefresh);
            viewHolder.imgDraw = (ImageButton)convertView.findViewById(R.id.imgDraw);
            convertView.setTag(viewHolder);
        }
        initializeViews((CustomerOpenOrderModel)getItem(position),
                (ViewHolder) convertView.getTag(),position,convertView);
        return convertView;
    }

    private void initializeViews(final CustomerOpenOrderModel object, final ViewHolder holder,
                                 final int position, final View convertView)
    {
        db = new DataBaseHandler(context);

        File file = new File(listImages.get(position).getPath());
        //final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        final Bitmap bitmap = decodeFile(file);
        holder.image.setImageBitmap(bitmap);
        holder.edtName.setFocusable(false);
        holder.edtName.setText(listImages.get(position).getName());
        holder.imgDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog alert = new AlertDialog.Builder(context)
                        .setMessage(language.getMessageDelete())
                        .setPositiveButton(language.getLabelYes(),
                                new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        pos = position;
                                        db.deletePhoto(listImages.get(position).getId());
                                        listImages.remove(position);
                                        notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton(language.getLabelNo(), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                alert.show();
            }
        });
        holder.imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                AlertDialog alert = new AlertDialog.Builder(context)
                        .setMessage(language.getMessageSelectImageFrom())
                        .setPositiveButton(language.getLabelCamera(),
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Random random = new Random();
                                        int n = 10000;
                                        String image = "Mateco " + random.nextInt(n);
                                        String root = Environment.getExternalStorageDirectory().toString();
                                        File newDir = new File(root + "/Mateco");
                                        newDir.mkdirs();
                                        String imageName =  image + ".jpg";
                                        File file = new File(newDir, imageName);
                                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                        path = file.getAbsolutePath();
                                        ((Activity)context).startActivityForResult(cameraIntent, 300);
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton(language.getLabelGallery(), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                ((Activity)context).startActivityForResult(intent, 400);
                                dialog.dismiss();
                            }
                        }).create();
                alert.show();
            }
        });
        holder.imgDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                Bundle args = new Bundle();
                Fragment fragment = new SiteInspectionDrawingFragment();
                args.putString("path",listImages.get(pos).getPath());
                fragment.setArguments(args);
                FragmentTransaction transaction =
                        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack("Drawing View");
                transaction.replace(R.id.content_frame,fragment);
                transaction.commit();
            }
        });
        holder.edtName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final EditText editText = new EditText(context);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                editText.setLayoutParams(lp);
                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(1000);
                editText.setFilters(FilterArray);
                editText.setText(listImages.get(position).getName());
                builder.setView(editText);
                builder.setTitle(language.getLabelNotes());
                builder.setPositiveButton(language.getLabelOk(), null);
                builder.setNegativeButton(language.getLabelAbort(), null);
                final AlertDialog alert1 = builder.create();
                alert1.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialog)
                    {
                        Button ok = alert1.getButton(AlertDialog.BUTTON_POSITIVE);
                        ok.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                final String str = editText.getText().toString();
                                if(str.equals(""))
                                    editText.setError(language.getMessageEnterNotes());
                                else{
                                    listImages.get(position).setName(str);
                                    db.updatePhoto(str,listImages.get(position).getId());
                                    alert1.dismiss();
                                    holder.edtName.setText(str);
                                    notifyDataSetChanged();
                                }
                            }
                        });
                        ok = alert1.getButton(AlertDialog.BUTTON_NEGATIVE);
                        ok.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                alert1.dismiss();
                            }
                        });
                    }
                });
                alert1.show();

            }
        });
    }

    public ArrayList<GridImage> getImages()
    {
        return listImages;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 300)
            {
                Bitmap bitmap = null;
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    options.inPurgeable = true;
                    options.inInputShareable = true;
                    bitmap = BitmapFactory.decodeFile(path, options);
                    File file = null;
                    file = new File(path);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,0, bos);
                    byte[] byteArray = bos.toByteArray();
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(byteArray);
                    listImages.get(pos).setPath(path);
                    db.updatePhotoByPath(listImages.get(pos).getPath(),listImages.get(pos).getId());
                    notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(requestCode == 400)
            {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                path = cursor.getString(columnIndex);
                Bitmap bitmap = null;
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    options.inPurgeable = true;
                    options.inInputShareable = true;
                    bitmap = BitmapFactory.decodeFile(path, options);
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
                    FileOutputStream fos = new FileOutputStream(file);
                    path = file.getAbsolutePath();
                    fos.write(byteArray);
                    listImages.get(pos).setPath(path);
                    db.updatePhotoByPath(listImages.get(pos).getPath(),listImages.get(pos).getId());
                    this.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cursor.close();
            }
        }
    }
}
