package de.mateco.integrAMobile.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.GridImageAdapter;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.GridImage;
import de.mateco.integrAMobile.model.Language;

public class SiteInspectionPhotoFragment extends BaseFragment implements View.OnClickListener
{
    GridView gridView;
    ArrayList<GridImage> imageList = new ArrayList<GridImage>();
    private View rootView;
    private GridImageAdapter adapter;
    private MatecoPriceApplication application;
    private Language language;
    private String path,mapPath;
    private Boolean flag=true;
    private DataBaseHandler db;
    private SharedPreferences preferences;
    int PERMISSION_GELLARY_CODE = 1002;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_site_inspection_photo, container, false);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        super.initializeFragment(rootView);
        application = (MatecoPriceApplication)getActivity().getApplication();
        language = application.getLanguage();
        preferences = getActivity().getSharedPreferences("SiteInspection", Context.MODE_PRIVATE);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelPhotos());
        db = new DataBaseHandler(getActivity());
        init();
        setImages();
        return rootView;
    }

    private void init()
    {
        if ( Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==

                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        3003);
            }
        }
        gridView = (GridView)rootView.findViewById(R.id.gridPhoto);
        Button btnCamera = (Button)rootView.findViewById(R.id.btnCamera);
        Button btnGallery = (Button)rootView.findViewById(R.id.btnGallery);
        Button btnDraw = (Button)rootView.findViewById(R.id.btnDraw);

        btnCamera.setText(language.getLabelCamera());
        btnGallery.setText(language.getLabelGallery());
        btnDraw.setText(language.getLabelSketch());

        btnCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnDraw.setOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_site_inspection_permits, menu);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.actionSettings:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                //transaction.addToBackStack(SettingFragment.Tag);
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
            case R.id.actionForward:
                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction1.replace(R.id.content_frame, new SiteInspectionDeviceData());
                transaction1.addToBackStack("Photo Data");
                transaction1.commit();
                return true;
            case R.id.actionSave:
                db.updateSiteInspectionDate(preferences.getInt(DataHelper.SiteInspectionId,0));
                db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId,0),"",2);
                preferences.edit().clear().commit();
                showShortToast(language.getMessageBvoStored());
                getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                return true;
            case R.id.actionWrong:
                DialogInterface.OnClickListener positiveCallback1 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deletePhotosById(preferences.getInt(DataHelper.SiteInspectionId,0));
                        db.deleteDeviceById(preferences.getInt(DataHelper.SiteInspectionId,0));
                        db.deleteSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0));
                        preferences.edit().clear().commit();
                        getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                    }
                };
                DialogInterface.OnClickListener negativeCallback1 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                AlertDialog alert1 = showAlert(language.getLabelNote(),language.getMessageLeaveBvo(),language.getLabelYes(), language.getLabelNo(),
                        positiveCallback1, negativeCallback1);
                alert1.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1001 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //setupContactsPicker();
            openCamera();
        }
        else if(requestCode == PERMISSION_GELLARY_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
            transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction1.replace(R.id.content_frame, new GalleryGridFragment());
            transaction1.addToBackStack("Site Inspection Gellery");
            transaction1.commit();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnCamera:
                try
                {
                    if ( Build.VERSION.SDK_INT >= 23)
                    {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) ==
                                PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                                        PackageManager.PERMISSION_GRANTED)
                        {
                            openCamera();
                        }
                        else{
                            ActivityCompat.requestPermissions(getActivity(), new String[] {
                                            android.Manifest.permission.CAMERA,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE}, 1001);
                        }
                    }
                    else {
                        openCamera();
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.btnGallery:
                if ( Build.VERSION.SDK_INT >= 23)
                {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==

                            PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_GRANTED) {

                            FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            transaction1.replace(R.id.content_frame, new GalleryGridFragment());
                            transaction1.addToBackStack("Site Inspection Gellery");
                            transaction1.commit();

                    }
                    else{
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        android.Manifest.permission.READ_EXTERNAL_STORAGE },
                                PERMISSION_GELLARY_CODE);
                    }
                }else {
                    FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction1.replace(R.id.content_frame, new GalleryGridFragment());
                    transaction1.addToBackStack("Site Inspection Gellery");
                    transaction1.commit();
                }



                break;
            case R.id.btnDraw:
                FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction2.replace(R.id.content_frame, new SiteInspectionDrawingFragment1());
                transaction2.addToBackStack("Site Inspection Drawing");
                transaction2.commit();
                break;
        }
    }
    public void openCamera(){
        Random random = new Random();
        int n = 10000;
        String image = "Bvo " + random.nextInt(n);
        String root = Environment.getExternalStorageDirectory().toString();
        File newDir = new File(root + "/Bvo");
        newDir.mkdirs();
        String imageName =  image + ".jpg";
        File file = new File(newDir, imageName);

        Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file)); // for lower to nougat
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        path = file.getAbsolutePath();
        startActivityForResult(cameraIntent, 100);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100)
            {
                Bitmap bitmap = null;
                try {
                    if(path != null)
                    {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        options.inPurgeable = true;
                        options.inInputShareable = true;
                        bitmap = BitmapFactory.decodeFile(path, options);
                        File file = null;
                        file = new File(path);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        if(bitmap != null) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
                            byte[] byteArray = bos.toByteArray();
                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(byteArray);
                            GridImage image = new GridImage();
                            image.setPath(path);
                            image.setBvoId(preferences.getInt(DataHelper.SiteInspectionId, 0));
                            image.setFlag(0);
                            db.addPhoto(image);
                            setImages();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(requestCode == 300)
            {
                if(adapter != null)
                {
                    adapter.onActivityResult(requestCode, resultCode, data);
                }
            }
            else if(requestCode == 400)
            {
                if(adapter != null)
                {
                    adapter.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }


    public void setImages()
    {
        imageList.clear();
        imageList = db.getPhotos(preferences.getInt(DataHelper.SiteInspectionId,0));
        adapter = new GridImageAdapter(getActivity(),imageList,application);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
