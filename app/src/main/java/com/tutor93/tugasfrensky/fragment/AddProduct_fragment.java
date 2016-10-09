package com.tutor93.tugasfrensky.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tutor93.tugasfrensky.R;
import com.tutor93.tugasfrensky.pictureManager.PictureUtil;
import com.tutor93.tugasfrensky.util.TextUtil;
import com.tutor93.tugasfrensky.view.ButtonRegular;
import com.tutor93.tugasfrensky.view.EditTextLight;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by indraaguslesmana on 10/7/16.
 */

public class AddProduct_fragment extends BaseFragmentWithActionBar implements View.OnClickListener {
    //camera Handle
    final static int ACTIVITY_PHOTO_RESULT = 21;
    private Button takePicture, takeFromGallery;
    private ButtonRegular saveProduct;
    private String photoFilePaths = "";
    private Uri photoSelected;
    private String filePathDatas;
    private ImageView productPhoto;
    private LinearLayout add_fromcamera, add_fromgallery;
    private CheckBox burnable, breakable, toxic;
    private NumberPicker sales_number;
    private EditTextLight productName, companyName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView(View view) {
        takePicture = (Button) view.findViewById(R.id.add_p_product_camera);
        takeFromGallery = (Button) view.findViewById(R.id.add_p_product_fromgallery);
        productPhoto = (ImageView) view.findViewById(R.id.picture_product);
        burnable = (CheckBox) view.findViewById(R.id.burnable_chk);
        breakable = (CheckBox) view.findViewById(R.id.breakable_chk);
        toxic = (CheckBox) view.findViewById(R.id.toxic_chk);
        sales_number = (NumberPicker) view.findViewById(R.id.sales_number);

        saveProduct = (ButtonRegular) view.findViewById(R.id.save_product);
        productName = (EditTextLight) view.findViewById(R.id.product_name);
        companyName = (EditTextLight) view.findViewById(R.id.company_name);

        /*add_fromcamera = (LinearLayout) view.findViewById(R.id.add_fromcamera_product);
        add_fromgallery = (LinearLayout) view.findViewById(R.id.add_fromgallery_product);*/

        sales_number.setMinValue(1);
        sales_number.setMaxValue(100);
        sales_number.setWrapSelectorWheel(true);
    }

    @Override
    public void setUICallbacks() {
        burnable.setOnClickListener(this);
        breakable.setOnClickListener(this);
        toxic.setOnClickListener(this);

        saveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productName.getText().equals("") && companyName.getText().equals("")) {
                    Toast.makeText(activity, "Form belum diisi semua", Toast.LENGTH_SHORT).show();
                }
            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("Take Photo");

            }
        });

        takeFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("Choose from Gallery");
            }
        });


    }

    @Override
    public void updateUI() {
        getBaseActivity().setRightIcon2(R.drawable.add_purple_icon);
        getBaseActivity().setActionBarTitle(getPageTitle());
    }

    @Override
    public String getPageTitle() {
        return getResources().getString(R.string.add_product);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_addproduct;
    }

    @Override
    public void onClick(View v) {
        if (v == burnable) {
            breakable.setChecked(false);
            toxic.setChecked(false);

            checkboxSetAlpa(breakable, true);
            checkboxSetAlpa(toxic, true);
            checkboxSetAlpa(burnable, false);
        } else if (v == breakable) {
            burnable.setChecked(false);
            toxic.setChecked(false);

            checkboxSetAlpa(burnable, true);
            checkboxSetAlpa(toxic, true);
            checkboxSetAlpa(breakable, false);
        } else if (v == toxic) {
            burnable.setChecked(false);
            breakable.setChecked(false);

            checkboxSetAlpa(burnable, true);
            checkboxSetAlpa(breakable, true);
            checkboxSetAlpa(toxic, false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ACTIVITY_PHOTO_RESULT://
                if (resultCode == Activity.RESULT_OK) {
                    if (photoSelected == null) {
                        //ini untuk gallery management
                        if (data != null && data.getData() != null) {
                            photoSelected = data.getData();

                            loadFromGallery();
                        } else {
                            return;
                        }
                    }

                    if (photoSelected != null) {
                        //ini untuk dapetin filepaths
                        Uri mUri = PictureUtil.getImageRotated(getActivity(), photoSelected, photoFilePaths);
                        filePathDatas = PictureUtil.getRealPathFromURI(getActivity(), mUri);

                    }
                }
                break;
        }
    }

    private void checkboxSetAlpa(CheckBox checkBox, boolean filter) {
        AlphaAnimation alpha = new AlphaAnimation(1f, 0.5f); // from 100% visible to 50%
        alpha.setDuration(1000); // 1 second, or whatever you want
        alpha.setFillAfter(filter);
        checkBox.startAnimation(alpha);
    }

    private void selectImage(String method) {
        photoSelected = null;
        photoFilePaths = "";

        if (method.equals("Take Photo")) {
            try {
                String newImages = "Picture_" + String.valueOf(System.currentTimeMillis()) + ".jpg";

                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + "Picture_data");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File fileImage = new File(dir, newImages);

                photoSelected = Uri.fromFile(fileImage);
                photoFilePaths = fileImage.getPath();

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoSelected);
                cameraIntent.putExtra("return-data", true);

                startActivityForResult(cameraIntent, ACTIVITY_PHOTO_RESULT);

                //disini embed fotonya ke imageView
                loadPhoto();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (method.equals("Choose from Gallery")) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/jpeg");
            photoFilePaths = "";
            startActivityForResult(Intent.createChooser(intent, "Select File"), ACTIVITY_PHOTO_RESULT);

        }
    }

    private void loadPhoto() {

        if (!photoFilePaths.equals("")) {

            Glide.with(this)
                    .load(photoFilePaths)
                    .into(productPhoto);
        } else{
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("noimage", R.drawable.no_image_available);
            productPhoto.setImageResource(map.get("noimage"));
        }
    }

    private void loadFromGallery() {
        if (photoSelected != null) {

            Glide.with(this)
                    .load(photoSelected)
                    .into(productPhoto);
        }else{
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("noimage", R.drawable.no_image_available);
            productPhoto.setImageResource(map.get("noimage"));
        }
    }

    private void customDialog() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(activity);
        // Include dialog.xml file
        dialog.setContentView(R.layout.diaolog_addpicture);

        /*add_fromgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("Choose from Gallery");

            }
        });

        add_fromcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("Take Photo");
            }
        });*/

        dialog.show();

    }

    public void refreshNavBar() {
        getBaseActivity().setActionBarTitle(getPageTitle());
        updateUI();
        setUICallbacks();
    }

}
