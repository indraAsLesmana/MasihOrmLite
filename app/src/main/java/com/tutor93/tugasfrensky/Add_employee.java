package com.tutor93.tugasfrensky;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.j256.ormlite.dao.Dao;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import java.util.List;
import java.util.Locale;


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

    private EmployeeModel employeeModel;

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

        spinnerData();

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


    private void spinnerData() {
        mJabatan = (Spinner) findViewById(R.id.add_jabatan);
        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        list.add("Android develover");
        list.add("iOS developer");

        adapter = new ArrayAdapter<String>(getApplicationContext(),
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


    private void addNewData() {
        // Once click on "Submit", it's first creates the TeacherDetails object
        Employee employee = new Employee();


        /*    // Then, set all the values from user input
            employee. = teacher_name_et.getText().toString();
            techDetails.address = address_et.getText().toString();
        */

        /*Set all values*/
        employee.setName(mName.getText().toString());
        employee.setJobs("android developer");
        employee.setIs_male(mAdd_Male.isChecked());
        employee.setBookmark(mBookmark.isChecked());
        employee.setAge(Integer.parseInt(mAdd_Age.getText().toString()));

        /*khusus convert buat date
        * di casting lansung gagal
        * */
      /*  try {
            DateFormat formatter;
            Date date;
            formatter = new SimpleDateFormat("dd/mm/yyyy");
            date = (Date) formatter.parse(mAdd_JoinDate.getText().toString());
            employee.setJoin(date);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        employee.setJoin(mAdd_JoinDate.getText().toString());

        //employee.setJoin((Date) mAdd_JoinDate.getText());
        employee.setNotes(mNotes.getText().toString());
        employee.setAvatar("belum tau cara take picture data");

        Log.d("add employee", employee.getName()+ employee.getJobs());

        employeeModel.insertEmployee(employee);

    }

    // Clear the entered text
    private void reset() {
        mName.setText(null);

    }

    @Override
    public void onClick(View view) {
        if (view == mAdd_camera) {
            dispatchTakePictureIntent();
        } else if (view == mAdd_save) {
            if (checkForm() == true) {
                addNewData();
            }
        }
    }
}
