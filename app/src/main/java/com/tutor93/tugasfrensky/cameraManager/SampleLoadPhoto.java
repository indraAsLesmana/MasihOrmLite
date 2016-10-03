package com.tutor93.tugasfrensky.cameraManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;

/**
 * Created by frensky on 09/09/2016.
 */
public class SampleLoadPhoto extends Activity{
    final static int ACTIVITY_PHOTO_RESULT = 21;

    Button takePicture;
    Button chooseFromGallery;

    private String photoFilePaths;
    private Uri photoSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.sample);

        takePicture = (Button) findViewById(R.id.btnPhoto);
        chooseFromGallery = (Button) findViewById(R.id.btnGallery);
*/
        chooseFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("Choose from Gallery");
            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage("Take Photo");
            }
        });
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
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (method.equals("Choose from Gallery")) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/jpeg");
            photoFilePaths = "";
            startActivityForResult(Intent.createChooser(intent,"Select File"), ACTIVITY_PHOTO_RESULT);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ACTIVITY_PHOTO_RESULT://
                if (resultCode == Activity.RESULT_OK) {
                    if (photoSelected == null) {
                        //ini untuk gallery management
                        if (data != null && data.getData() != null) {
                            photoSelected = data.getData();
                        } else {
                            return;
                        }
                    }

                    if (photoSelected != null) {
                        //ini untuk dapetin filepaths
                        Uri mUri = PictureUtil.getImageRotated(this,photoSelected,photoFilePaths);
                        String filePathDatas = PictureUtil.getRealPathFromURI(this,mUri);
                    }
                }
                break;
        }
    }
}
