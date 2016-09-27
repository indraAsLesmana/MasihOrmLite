package com.tutor93.tugasfrensky;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;


public class Add_employee extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    // Reference of DatabaseHelper class to access its DAOs and other components
    private DatabaseHelper databaseHelper = null;

    static final int REQUEST_IMAGE_CAPTURE = 1;


    /*define all object add_employe class*/
    private EditText mName;
    private Spinner mJabatan;
    private RadioButton mAdd_Male;
    private RadioButton mAdd_Female;
    private EditText mAdd_JoinDate;
    private CheckBox mBookmark;
    private EditText mAdd_Age;
    private EditText mNotes;

    private Button mAdd_camera;
    private Button mAdd_save;
    private ImageView mProfile_pict;
    /*define all object add_employe class*/

    private String array_spinner[];

    private EmployeeModel helper;
    private EmployeEntity employeeList;
    private Bitmap imageCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addemployee);

        mAdd_camera = (Button) findViewById(R.id.add_picture);
        mProfile_pict = (ImageView) findViewById(R.id.profile_pict);


        /*define, locate & set START*/
        mName = (EditText) findViewById(R.id.add_name);
        mJabatan = (Spinner) findViewById(R.id.add_jabatan);
        mAdd_Male = (RadioButton) findViewById(R.id.add_male);
        mAdd_Female = (RadioButton) findViewById(R.id.add_female);
        mAdd_JoinDate = (EditText) findViewById(R.id.add_joindate);
        mBookmark = (CheckBox) findViewById(R.id.add_bookmark);
        mAdd_Age = (EditText) findViewById(R.id.add_age);
        mNotes = (EditText) findViewById(R.id.add_note);
        /*button*/
        mAdd_save = (Button) findViewById(R.id.btn_saveemployee);
        /*define, locate & set END*/

        /*difine listener*/
        mAdd_camera.setOnClickListener(this);
        mAdd_save.setOnClickListener(this);

        mAdd_Female.setOnClickListener(this);
        mAdd_Male.setOnClickListener(this);

        //solving masalah null pake ini.
        this.helper = new EmployeeModel(this);
        this.employeeList = new EmployeEntity();

        spinnerData();
        checkIntent();

        mAdd_JoinDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        Add_employee.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }


    private void checkIntent() {
        Intent intent = getIntent();

        if (intent.getIntExtra("id", 0) != 0) {
            //load dari employee dari database berdasarkan value intent dgn query ORMlite
            Toast.makeText(this, "masuk query", Toast.LENGTH_SHORT).show();
            employeeList = helper.getEmployeeById(intent.getIntExtra("id", 0));


            mName.setText(employeeList.getName());
            mJabatan.setSelection(spinerCheck(mJabatan, employeeList.getJobs()));

            if (employeeList.is_male() == true) {
                mAdd_Male.setChecked(true);
                mAdd_Female.setChecked(false);
            } else if (employeeList.is_male() == false) {
                mAdd_Male.setChecked(false);
                mAdd_Female.setChecked(true);
            }
            //mMele
            mAdd_JoinDate.setText(employeeList.getJoin());
            mBookmark.setSelected(employeeList.isBookmark());
            // mAdd_Age.setText(employeeList.getAge());
            mNotes.setText(employeeList.getNotes());

        }
    }

    /*cek spinner by value
    * */
    private int spinerCheck(Spinner spinner, String value) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                index = i;
            }
        }
        return index;
    }

    /*
    * tring compareValue = "some value";
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_state, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mSpinner.setAdapter(adapter);
    if (!compareValue.equals(null)) {
    int spinnerPosition = adapter.getPosition(compareValue);
    mSpinner.setSelection(spinnerPosition);
}
    *
    * */


    private void spinnerData() {
        mJabatan = (Spinner) findViewById(R.id.add_jabatan);
        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<>();
        list.add("Android develover");
        list.add("iOS developer");

        adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mJabatan.setAdapter(adapter);
    }

    // This is how, DatabaseHelper can be initialized for future use
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        mAdd_JoinDate.setText(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*
         * You'll need this in your class to release the helper when done.
		 */
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    /*ambil camera START
        source developer.android
        * fungsi: ambil camera dari
        *
        * inget inget... bisi butuh
        * */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mProfile_pict.setImageBitmap(imageBitmap);

            //temp buat bitmap
            imageCache = imageBitmap;
        }
    }
    /*ambil camera END*/

    private boolean checkForm() {
        if (mName.getText().equals(null) || mAdd_JoinDate.getText().equals(null)) {
            Toast.makeText(Add_employee.this, "Please input all data!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void showToast(String setMessage) {
        Toast.makeText(Add_employee.this, setMessage, Toast.LENGTH_SHORT).show();

    }

    public void addNewEmployee() {
        EmployeeModel helper = new EmployeeModel(this);

        String strName = mName.getText().toString();
        String strJabatan = mJabatan.getSelectedItem().toString();
        boolean bolIs_male = mAdd_Male.isChecked();
        boolean bolBookmark = mBookmark.isChecked();

        int strAge = mAdd_Age.getText().toString().equals("")
                ? 0 : Integer.parseInt(mAdd_Age.getText().toString());

        String strJoin = mAdd_JoinDate.getText().toString();
        String strNote = mNotes.getText().toString();
        String strAvatar = "masih di ulik";

        if (TextUtils.isEmpty(strName)) {
            showToast("Please add your name !!!");
            return;
        }

        Employee person = new Employee();

        person.setName(strName);
        person.setJobs(strJabatan);
        person.setIs_male(bolIs_male);
        person.setBookmark(bolBookmark);
        person.setAge(strAge);
        person.setJoin(strJoin);
        person.setNotes(strNote);

        showToast("Data susccesfully added");

        //coba save image
        new ImageSaver(this).
                setFileName(strName + ".png").
                setDirectoryName("images").
                save(imageCache);

        person.setAvatar("images" + strName + strAge + ".png");

        helper.insertEmployee(person);
    }


    // Clear the entered text
    private void reset() {
        mName.setText(null);

    }

    @Override
    public void onClick(View view) {
        if (view == mAdd_save) {
            addNewEmployee();
        } else if (view == mAdd_camera) {
            dispatchTakePictureIntent();
        } else if (view == mAdd_Female) {
            mAdd_Male.setChecked(false);
        } else if (view == mAdd_Male) {
            mAdd_Female.setChecked(false);
        }
    }
}
