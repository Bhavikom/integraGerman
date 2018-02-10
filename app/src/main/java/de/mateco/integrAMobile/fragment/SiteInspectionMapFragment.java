package de.mateco.integrAMobile.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.mateco.integrAMobile.Helper.Constants;
import de.mateco.integrAMobile.Helper.DataHelper;
import de.mateco.integrAMobile.Helper.GooglePlacesAutocompleteAdapter;
import de.mateco.integrAMobile.Helper.LogApp;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.asyncTask.GeocoderTask;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.databaseHelpers.DataBaseHandler;
import de.mateco.integrAMobile.model.GridImage;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.PricingCustomerOrderBasicInfo;
import de.mateco.integrAMobile.model.SiteInspectionModel;
import de.mateco.integrAMobile.model.SiteInspectionNewModel;

public class SiteInspectionMapFragment extends BaseFragment implements View.OnClickListener, GoogleMap.OnMarkerDragListener, LocationListener
{
    private MapView mapView;
    private GoogleMap map;
    private View rootView;
    private Double latitude,longitude;
    private boolean gpsEnabled = false,networkEnabled = false;

    private File file;
    private Button buttonSetelite, buttonMap, buttonRefreshLocation;
    private boolean flag = true;
    private DataBaseHandler db;
    private GridImage img = new GridImage();
    private SharedPreferences preferences;
    private MatecoPriceApplication application;
    private Language language;
    private AutoCompleteTextView autoCompleteTextPlace;
    private LocationManager locManager;
    private MatecoPriceApplication matecoPriceApplication;
    private Location location;
    private Marker currentLocationMarker;
    private GeocoderTask geocoderTask;
    private TextView labelSelectedLatitudeFormat, labelValueSelectedLatitudeFormat, labelSelectedLongitudeFormat, labelValueSelectedLongitudeFormat;
    private Activity activity;
    private String street = "", road = "", zipCode = "";
    private boolean isFromPricing;
    ImageView imgSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        if(getArguments() != null)
            isFromPricing = getArguments().getBoolean(Constants.IsFromPricing, false);
        if(!isFromPricing)
        {
            preferences = activity.getSharedPreferences("SiteInspection", Context.MODE_PRIVATE);
        }
        super.initializeFragment(rootView);
        imgSearch = (ImageView)rootView.findViewById(R.id.imageViewSearch);
        application = (MatecoPriceApplication)getActivity().getApplication();
        language = application.getLanguage();
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        mapView = (MapView) rootView.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        db = new DataBaseHandler(getActivity());
        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        try
        {
            gpsEnabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        if(!gpsEnabled) {
            if (networkEnabled)
            {

            }
            else
            {
                try
                {
                    if(getActivity()!=null)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(language.getLabelAlert());
                        builder.setMessage(language.getMessageProviders());
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if(getActivity()!=null)
                                {
                                    getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                                dialog.dismiss();
                            }
                        }).create();
                        builder.create().show();
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        if ( Build.VERSION.SDK_INT >= 23)
        {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED)
            {
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        map = googleMap;
                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        addDragEvent();
                        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                            @Override
                            public void onMapLoaded() {
                                MapsInitializer.initialize(getActivity());
                                if(!isFromPricing)
                                {
                                    if(!db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0)).getSiteInspectionNewModel().getGeoBreite().equals("") &&
                                            !db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0)).getSiteInspectionNewModel().getGeoLaenge().equals(""))
                                    {
                                        latitude = Double.parseDouble(db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0)).getSiteInspectionNewModel().getGeoBreite());
                                        longitude = Double.parseDouble(db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0)).getSiteInspectionNewModel().getGeoLaenge());
                                    }
                                }
                                else {
                                    PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                                            new Gson().toJson(new PricingCustomerOrderBasicInfo()));
                                    latitude = model.getLatitude();
                                    longitude = model.getLongitude();
                                    updateAutoComplete(model.getStreet(), model.getPlace(), model.getZipCode());

                                }
                                if(latitude !=null &&  longitude!=null)
                                {
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
                                    currentLocationMarker = map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("").draggable(true));
                                    BasicNameValuePair nameValuePair = DataHelper.getFormattedLocationInDegree(getActivity(), latitude, longitude);
                                    labelValueSelectedLongitudeFormat.setText(nameValuePair.getName());
                                    labelValueSelectedLatitudeFormat.setText(nameValuePair.getValue());
                                }
                                else
                                {
                                    map.setMyLocationEnabled(true);
                                    if(map != null)
                                    {
                                        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                                            @Override
                                            public void onMyLocationChange(Location location) {
                                                if (location != null) {
                                                    latitude = location.getLatitude();
                                                    longitude = location.getLongitude();
                                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
                                                    currentLocationMarker = map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("").draggable(true));
                                                    map.setOnMyLocationChangeListener(null);
                                                    map.setMyLocationEnabled(false);
                                                    reverseGeocode(latitude, longitude);
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                });
            }
            else
            {
                ActivityCompat.requestPermissions(getActivity(), new String[] {
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION },
                        32);
            }
        }
        else {
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    addDragEvent();
                    map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                        @Override
                        public void onMapLoaded() {
                            MapsInitializer.initialize(getActivity());
                            if(!isFromPricing)
                            {
                                if(!db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0)).getSiteInspectionNewModel().getGeoBreite().equals("") &&
                                        !db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0)).getSiteInspectionNewModel().getGeoLaenge().equals(""))
                                {
                                    latitude = Double.parseDouble(db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0)).getSiteInspectionNewModel().getGeoBreite());
                                    longitude = Double.parseDouble(db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0)).getSiteInspectionNewModel().getGeoLaenge());
                                }
                            }
                            else {
                                PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                                        new Gson().toJson(new PricingCustomerOrderBasicInfo()));
                                latitude = model.getLatitude();
                                longitude = model.getLongitude();
                                updateAutoComplete(model.getStreet(), model.getPlace(), model.getZipCode());

                            }
                            if(latitude !=null &&  longitude!=null)
                            {
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
                                currentLocationMarker = map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("").draggable(true));
                                BasicNameValuePair nameValuePair = DataHelper.getFormattedLocationInDegree(getActivity(), latitude, longitude);
                                labelValueSelectedLongitudeFormat.setText(nameValuePair.getName());
                                labelValueSelectedLatitudeFormat.setText(nameValuePair.getValue());
                            }
                            else
                            {
                                map.setMyLocationEnabled(true);
                                if(map != null)
                                {
                                    map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                                        @Override
                                        public void onMyLocationChange(Location location) {
                                            if (location != null) {
                                                latitude = location.getLatitude();
                                                longitude = location.getLongitude();
                                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
                                                currentLocationMarker = map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("").draggable(true));
                                                map.setOnMyLocationChangeListener(null);
                                                map.setMyLocationEnabled(false);
                                                reverseGeocode(latitude, longitude);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            });
        }



        autoCompleteTextPlace = (AutoCompleteTextView)rootView.findViewById(R.id.autoCompleteTextPlace);
        labelSelectedLatitudeFormat = (TextView)rootView.findViewById(R.id.labelSelectedLatitudeFormat);
        labelSelectedLongitudeFormat = (TextView)rootView.findViewById(R.id.labelSelectedLongitudeFormat);
        labelValueSelectedLatitudeFormat = (TextView)rootView.findViewById(R.id.labelValueSelectedLatitudeFormat);
        labelValueSelectedLongitudeFormat = (TextView)rootView.findViewById(R.id.labelValueSelectedLongitudeFormat);

        labelSelectedLatitudeFormat.setText(language.getLabelLatitude());
        labelSelectedLongitudeFormat.setText(language.getLabelLongitude());

        final GooglePlacesAutocompleteAdapter adapter = new GooglePlacesAutocompleteAdapter(getActivity(), language);
        autoCompleteTextPlace.setAdapter(adapter);
        autoCompleteTextPlace.setHint(language.getMessageEnterYourPlaceHere());
        autoCompleteTextPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                matecoPriceApplication.hideKeyboard(getActivity());
                String selectedItem = (String) adapter.getItem(position);
                ArrayList<BasicNameValuePair> latLong = DataHelper.getLatLongFromAddress(getActivity(), selectedItem);
                if (latLong.size() > 0) {
                    Double latitue = Double.parseDouble(latLong.get(0).getValue());
                    Double longitude = Double.parseDouble(latLong.get(1).getValue());
                    LatLng latLng = new LatLng(latitue, longitude);
                    map.clear();
                    currentLocationMarker = map.addMarker(new MarkerOptions().position(latLng).title(selectedItem).draggable(true));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                    map.animateCamera(cameraUpdate);
                    BasicNameValuePair nameValuePair = DataHelper.getFormattedLocationInDegree(getActivity(), latitue, longitude);
                    labelValueSelectedLongitudeFormat.setText(nameValuePair.getName());
                    labelValueSelectedLatitudeFormat.setText(nameValuePair.getValue());
                    updateLatLong(currentLocationMarker);
                } else {
                    showShortToast(language.getMessageError());
                }
            }
        });

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelMap());
        setHasOptionsMenu(true);
        buttonSetelite = (Button)rootView.findViewById(R.id.buttonSetelite);
        buttonMap = (Button)rootView.findViewById(R.id.buttonMap);
        buttonMap.setText(language.getLabelUseMap());
        buttonMap.setOnClickListener(this);
        buttonSetelite.setOnClickListener(this);
        buttonSetelite.setText(language.getLabelSatellite());
        buttonRefreshLocation = (Button)rootView.findViewById(R.id.buttonRefreshLocation);
        buttonRefreshLocation.setOnClickListener(this);
        buttonRefreshLocation.setText(language.getLabelUpdate());

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(autoCompleteTextPlace.getText().toString())){
                    onMapSearch(autoCompleteTextPlace.getText().toString());
                }

            }
        });
        if(isFromPricing)
        {
            buttonMap.setVisibility(View.GONE);
        }
        else
        {
            updateAutoComplete();
        }
        return rootView;
    }
    public void onMapSearch(String inputString) {
        matecoPriceApplication.hideKeyboard(getActivity());
        ArrayList<BasicNameValuePair> latLong = DataHelper.getLatLongFromAddress(getActivity(),inputString);
        if (latLong.size() > 0) {
            Double latitue = Double.parseDouble(latLong.get(0).getValue());
            Double longitude = Double.parseDouble(latLong.get(1).getValue());
            LatLng latLng = new LatLng(latitue, longitude);
            map.clear();
            currentLocationMarker = map.addMarker(new MarkerOptions().position(latLng).title(inputString).draggable(true));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            map.animateCamera(cameraUpdate);
            BasicNameValuePair nameValuePair = DataHelper.getFormattedLocationInDegree(getActivity(), latitue, longitude);
            labelValueSelectedLongitudeFormat.setText(nameValuePair.getName());
            labelValueSelectedLatitudeFormat.setText(nameValuePair.getValue());
            updateLatLong(currentLocationMarker);
        } else {
            showShortToast(language.getMessageError());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 32:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                    PackageManager.PERMISSION_GRANTED)
                    {
                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                map = googleMap;
                                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                addDragEvent();
                                map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                                    @Override
                                    public void onMapLoaded() {
                                        MapsInitializer.initialize(getActivity());
                                        if(!isFromPricing)
                                        {
                                            if(!db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0)).getSiteInspectionNewModel().getGeoBreite().equals("") &&
                                                    !db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0)).getSiteInspectionNewModel().getGeoLaenge().equals(""))
                                            {
                                                latitude = Double.parseDouble(db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0)).getSiteInspectionNewModel().getGeoBreite());
                                                longitude = Double.parseDouble(db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0)).getSiteInspectionNewModel().getGeoLaenge());
                                            }
                                        }
                                        else {
                                            PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                                                    new Gson().toJson(new PricingCustomerOrderBasicInfo()));
                                            latitude = model.getLatitude();
                                            longitude = model.getLongitude();
                                            updateAutoComplete(model.getStreet(), model.getPlace(), model.getZipCode());

                                        }
                                        if(latitude !=null &&  longitude!=null)
                                        {
                                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
                                            currentLocationMarker = map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("").draggable(true));
                                            BasicNameValuePair nameValuePair = DataHelper.getFormattedLocationInDegree(getActivity(), latitude, longitude);
                                            labelValueSelectedLongitudeFormat.setText(nameValuePair.getName());
                                            labelValueSelectedLatitudeFormat.setText(nameValuePair.getValue());
                                        }
                                        else
                                        {
                                            map.setMyLocationEnabled(true);
                                            if(map != null)
                                            {
                                                map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                                                    @Override
                                                    public void onMyLocationChange(Location location) {
                                                        if (location != null) {
                                                            latitude = location.getLatitude();
                                                            longitude = location.getLongitude();
                                                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
                                                            currentLocationMarker = map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("").draggable(true));
                                                            map.setOnMyLocationChangeListener(null);
                                                            map.setMyLocationEnabled(false);
                                                            reverseGeocode(latitude, longitude);
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                                        android.Manifest.permission.ACCESS_COARSE_LOCATION },
                                32);
                    }

                } else {
                    // Permission Denied

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
    @Override
    public void initializeComponents(View rootView)
    {
        super.initializeComponents(rootView);
        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    private void addDragEvent()
    {
        map.setOnMarkerDragListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_site_inspection_permits, menu);
        menu.findItem(R.id.actionRight).setVisible(false);
        menu.findItem(R.id.actionAdd).setVisible(false);
        if(isFromPricing)
        {
            menu.findItem(R.id.actionSettings).setVisible(false);
            menu.findItem(R.id.actionForward).setVisible(false);
            menu.findItem(R.id.actionSave).setVisible(false);
            menu.findItem(R.id.actionWrong).setVisible(false);
            menu.findItem(R.id.actionRight).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.actionSettings:
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                ((HomeActivity)getActivity()).onBackPressed();
//                if (getFragmentManager().getBackStackEntryCount() == 0)
//                {
//                    getActivity().finish();
//                }
//                else
//                {
//                    getFragmentManager().popBackStack();
//                }
                return true;
            case R.id.actionForward:
                    SiteInspectionPhotoFragment fragment = new SiteInspectionPhotoFragment();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame,fragment);
                    transaction.addToBackStack("Site Inspection Photo");
                    transaction.commit();
                return true;
            case R.id.actionSave:
                db.updateSiteInspectionDate(preferences.getInt(DataHelper.SiteInspectionId,0));
                db.updateSiteInspectionUpload(preferences.getInt(DataHelper.SiteInspectionId, 0), "", 2);
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
            case R.id.actionRight:
                PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                        new Gson().toJson(new PricingCustomerOrderBasicInfo()));
                if(zipCode != null && !TextUtils.isEmpty(zipCode)){
                    model.setZipCode(zipCode);
                }
                if(road != null && !TextUtils.isEmpty(road)){
                    model.setPlace(road);
                }
                if(street != null && !TextUtils.isEmpty(street)){
                    model.setStreet(street);
                }


                if(latitude!= null){
                    model.setLatitude(latitude);
                }
                if(longitude != null){
                    model.setLongitude(longitude);
                }

                String jsonString = new Gson().toJson(model);
                matecoPriceApplication.saveData(DataHelper.PricingCustomerBasicOrderInfo, jsonString);
                boolean fragmentPopped = getActivity().getSupportFragmentManager().popBackStackImmediate();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(mapView != null)
            mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mapView != null)
            mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapView != null)
            mapView.onDestroy();
        if(locManager != null)
        {
            locManager.removeUpdates(this);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mapView != null)
            mapView.onLowMemory();
    }

    public void CaptureMapScreen()
    {
       GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback()
        {
            @Override
            public void onSnapshotReady(Bitmap snapshot)
            {
                Bitmap bitmap = snapshot;
                Random random = new Random();
                int n = 10000;
                String image = "Bvo " + random.nextInt(n);
                String root = Environment.getExternalStorageDirectory().toString();
                File newDir = new File(root + "/Bvo");
                newDir.mkdirs();
                String imageName =  image + ".jpg";
                file = new File(newDir, imageName);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
                byte[] bitmapdata = bos.toByteArray();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    fos.write(bitmapdata);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                img.setBvoId(preferences.getInt(DataHelper.SiteInspectionId, 0));
                img.setPath(file.getAbsolutePath());
                img.setFlag(0);
                db.addPhoto(img);
                showShortToast(language.getMessageMapSaved());
            }
        };

        //mapView.getMap().snapshot(callback);
        if(map != null)
            map.snapshot(callback);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonSetelite:
                if (flag)
                {
                    flag = false;
                    buttonSetelite.setText(language.getLabelNormal());
                    if(map != null){
                        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    }

                }
                else
                {
                    flag = true;
                    buttonSetelite.setText(language.getLabelSatellite());
                    if(map != null){
                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    }

                }
                break;
            case R.id.buttonMap:
                CaptureMapScreen();
                break;
            case R.id.buttonRefreshLocation:
                autoCompleteTextPlace.setText("");
                labelValueSelectedLongitudeFormat.setText("");
                labelValueSelectedLatitudeFormat.setText("");
                updateLocation();
                break;
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker)
    {

    }

    @Override
    public void onMarkerDrag(Marker marker)
    {

    }

    @Override
    public void onMarkerDragEnd(Marker marker)
    {
        updateLatLong(marker);
    }

    private void updateLatLong(Marker marker)
    {
        if(!isFromPricing)
        {
            SiteInspectionModel siteInspectionModel = db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0));
            SiteInspectionNewModel siteInspectionNewModel = siteInspectionModel.getSiteInspectionNewModel();
            siteInspectionNewModel.setGeoLaenge(marker.getPosition().longitude + "");
            siteInspectionNewModel.setGeoBreite(marker.getPosition().latitude + "");
            siteInspectionModel.setSiteInspectionNewModel(siteInspectionNewModel);
            db.updateSiteInspection(siteInspectionModel, preferences.getInt(DataHelper.SiteInspectionId, 0));
        }
        BasicNameValuePair nameValuePair = DataHelper.getFormattedLocationInDegree(getActivity(), marker.getPosition().latitude, marker.getPosition().longitude);
        labelValueSelectedLongitudeFormat.setText(nameValuePair.getName());
        labelValueSelectedLatitudeFormat.setText(nameValuePair.getValue());

        /*PricingCustomerOrderBasicInfo model = matecoPriceApplication.getPricingCustomerOrderGeneralInfo(DataHelper.PricingCustomerBasicOrderInfo,
                new Gson().toJson(new PricingCustomerOrderBasicInfo()));
        model.setLatitude(marker.getPosition().latitude);
        model.setLongitude(marker.getPosition().longitude);*/
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;
        reverseGeocode(marker.getPosition().latitude, marker.getPosition().longitude);
    }

    private void updateLatLong(double latitude, double longitude)
    {
        if(!isFromPricing)
        {
            SiteInspectionModel siteInspectionModel = db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0));
            SiteInspectionNewModel siteInspectionNewModel = siteInspectionModel.getSiteInspectionNewModel();
            try{
                siteInspectionNewModel.setGeoLaenge(longitude + "");
                siteInspectionNewModel.setGeoBreite(latitude + "");
            }
            catch (Exception e){

            }


            siteInspectionModel.setSiteInspectionNewModel(siteInspectionNewModel);
            db.updateSiteInspection(siteInspectionModel, preferences.getInt(DataHelper.SiteInspectionId, 0));
        }
        BasicNameValuePair nameValuePair = DataHelper.getFormattedLocationInDegree(getActivity(), latitude, longitude);
        labelValueSelectedLongitudeFormat.setText(nameValuePair.getName());
        labelValueSelectedLatitudeFormat.setText(nameValuePair.getValue());
        reverseGeocode(latitude, longitude);
    }
    private void updateAutoComplete()
    {
        SiteInspectionNewModel model = db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId,0)).getSiteInspectionNewModel();
        if(model != null)
        {
            StringBuilder sb = new StringBuilder();
            String text = "";
            if(!TextUtils.isEmpty(model.getEinsatzstrasse()))
            {
                sb.append(model.getEinsatzstrasse());
                text = text + model.getEinsatzstrasse();
            }
            if(!TextUtils.isEmpty(model.getEinsatzort()))
            {
                sb.append(", "+model.getEinsatzort());
                text = text + ", "  + model.getEinsatzort();
            }
            if(!TextUtils.isEmpty(model.getEinsatzPLZ()))
            {
                sb.append(", "+model.getEinsatzPLZ());
                text = text + ", " + model.getEinsatzPLZ();
            }
            autoCompleteTextPlace.setText(sb.toString());
            LogApp.showLog("get knotakt not null", "getKontaktStrasse not null " + model.getEinsatzstrasse());
            LogApp.showLog("get knotakt not null", "getEinsatzort not null " + model.getEinsatzort());
            LogApp.showLog("get knotakt not null", "getEinsatzPLZ not null " + model.getEinsatzPLZ());
        }
    }

    private void updateAutoComplete(String street, String road, String zipCode)
    {
        String text = "";
        text = text + street;
        text = text + ", " + road;
        text = text + ", "  + zipCode;
        autoCompleteTextPlace.setText(text);
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if(location != null)
        {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            if(map != null){
                map.clear();
            }

            if(map != null){
                currentLocationMarker = map.addMarker(new MarkerOptions().position(latLng).title("").draggable(true));
            }

            if(latitude!=null && longitude!=null)
                updateLatLong(latitude, longitude);
            MapsInitializer.initialize(getActivity());
            //if(map != null)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void updateLocation()
    {
        boolean networkEnabled = false, gpsEnabled = false;
        long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
        long MIN_TIME_BW_UPDATES = 1000*60*1;
        try
        {
            gpsEnabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        if(!gpsEnabled) {
            if (networkEnabled)
            {
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                onLocationChanged(location);
            }
            else
            {
                try
                {
                    if(getActivity()!=null)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(language.getLabelAlert());
                        builder.setMessage(language.getMessageProviders());
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if(getActivity()!=null)
                                {
                                    getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                                dialog.dismiss();
                            }
                        }).create();
                        builder.create().show();
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        else
        {
            if ( Build.VERSION.SDK_INT >= 23)
            {
                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED)
                {
                    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(location != null)
                    {
                        onLocationChanged(location);
                    }
                    else
                    {
                        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location!=null)
                            onLocationChanged(location);
                    }
                }
                else {
                    ActivityCompat.requestPermissions(getActivity(), new String[] {
                                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION },
                            33);
                }
            }
            else {
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location != null)
                {
                    onLocationChanged(location);
                }
                else
                {
                    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(location!=null)
                        onLocationChanged(location);
                }
            }


        }
    }
    private void reverseGeocode(double latitude, double longitude)
    {
        location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        if(activity != null)
        {
            Geocoder geoCoder = new Geocoder(activity);
            List<Address> addressList = null;
            try {
                addressList = geoCoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
                if (addressList != null && addressList.size() > 0)
                {
                    Address address = addressList.get(0);
                    if(!isFromPricing)
                    {
                        SiteInspectionModel siteInspectionModel = db.getSiteInspection(preferences.getInt(DataHelper.SiteInspectionId, 0));
                        SiteInspectionNewModel model = siteInspectionModel.getSiteInspectionNewModel();

                        try {
                            location.setLatitude(Double.parseDouble(model.getGeoBreite()));
                            location.setLongitude(Double.parseDouble(model.getGeoLaenge()));
                        }
                        catch (Exception e){
                            location.setLatitude(0.0);
                            location.setLongitude(0.0);
                        }

                        String splite[] = address.getAddressLine(0).split(",");
                        if(splite.length > 0){
                            model.setEinsatzstrasse(splite[0].toString());
                        }else {
                            model.setEinsatzstrasse(address.getAddressLine(0));
                        }


                        model.setEinsatzort(address.getLocality());
                        model.setEinsatzPLZ(address.getPostalCode());
                        siteInspectionModel.setSiteInspectionNewModel(model);
                        db.updateSiteInspection(siteInspectionModel, preferences.getInt(DataHelper.SiteInspectionId, 0));
                        updateAutoComplete();
                    }
                    else
                    {
                        String splite[] = address.getAddressLine(0).split(",");
                        if(splite.length > 0){
                            street=splite[0].toString();
                        }else {
                            street=address.getAddressLine(0);
                        }
                        //street = address.getAddressLine(0);
                        road = address.getLocality();
                        zipCode = address.getPostalCode();
                        updateAutoComplete(street, road, zipCode);
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}