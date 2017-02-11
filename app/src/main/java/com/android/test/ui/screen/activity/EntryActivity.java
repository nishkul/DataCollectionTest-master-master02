package com.android.test.ui.screen.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.test.R;
import com.android.test.database.CategoryHelper;
import com.android.test.database.EntryHelper;
import com.android.test.model.Category;
import com.android.test.model.Entry;
import com.android.test.utils.Common;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Manish on 9/2/17.
 */

public class EntryActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int GALLERY_RESULT = 2;
    private static final int CAMERA_REQUEST = 1;
    private TextInputLayout itTitle;
    private TextInputLayout itDescription;
    private TextInputLayout itAddress;
    private EditText edt_title;
    private EditText edt_Address;
    private EditText edt_description;
    private RadioButton curentRadio;
    private RadioButton customlocRatio;
    private Button btn_submit;
    private Button btn_cancel;
    private ImageView mGallary;
    private ImageView captureImage;
    private ImageView mCamera;
    private CategoryHelper categoryHelper;

    private LinearLayout camera_lay;
    private LinearLayout gallery_lay;
    private String mImagePath;
    private long result = -1;
    private Spinner mCategorySpinner;
    private TextView mError;
    private ArrayList<Category> mCategoryLists;
    private TextView addCategorytv;
    private ArrayAdapter<Category> myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data_layout);
        initView();
        setData();
        listener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        myAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, mCategoryLists);
        mCategorySpinner.setAdapter(myAdapter);
    }

    private void setData() {
        categoryHelper = new CategoryHelper(this);
        mCategoryLists = new ArrayList<>();

        mCategoryLists = categoryHelper.getCategoryLists();
        mCategoryLists.add(0, new Category());
        myAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, mCategoryLists);

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCamera = (ImageView) findViewById(R.id.camera);
        mGallary = (ImageView) findViewById(R.id.gallery);
        camera_lay = (LinearLayout) findViewById(R.id.camera_lay);
        gallery_lay = (LinearLayout) findViewById(R.id.gallery_lay);
        captureImage = (ImageView) findViewById(R.id.final_image);


        itTitle = (TextInputLayout) findViewById(R.id.id_edt_title);
        edt_title = (EditText) findViewById(R.id.edt_title);


        itDescription = (TextInputLayout) findViewById(R.id.it_edit_description);
        edt_description = (EditText) findViewById(R.id.edt_description);


        itAddress = (TextInputLayout) findViewById(R.id.it_edit_address);
        edt_Address = (EditText) findViewById(R.id.edt_address);
        addCategorytv = (TextView) findViewById(R.id.addCategory_tv);

        curentRadio = (RadioButton) findViewById(R.id.radio_currentloc);
        customlocRatio = (RadioButton) findViewById(R.id.ratio_customloc);
        mCategorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        mError = (TextView) findViewById(R.id.error_tv);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);


    }

    private void listener() {
        mCamera.setOnClickListener(this);
        mGallary.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        curentRadio.setOnClickListener(this);
        customlocRatio.setOnClickListener(this);
        addCategorytv.setOnClickListener(this);

//        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position != -1) {
//                    mError.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:

                Intent cameraIntent = new Intent(
                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,
                        CAMERA_REQUEST);
                break;
            case R.id.gallery:
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, GALLERY_RESULT);
                break;
            case R.id.btn_cancel:
                cancel();
                break;
            case R.id.btn_submit:
                submit();
                break;
            case R.id.addCategory_tv:
                addUserCategory();
                break;
            case R.id.radio_currentloc:
                if (curentRadio.isChecked()) {
                    getCurrentLocation();
                }
                break;
            case R.id.ratio_customloc:
                if (customlocRatio.isChecked()) {
                    addCustomLocation();

                }
                break;
        }
    }

    private void addUserCategory() {
        LayoutInflater inflater = EntryActivity.this.getLayoutInflater();
        View content = inflater.inflate(R.layout.add_category, null);
        final EditText editText = (EditText) content.findViewById(R.id.category);


        AlertDialog.Builder builder = new AlertDialog.Builder(EntryActivity.this);
        builder.setView(content)
                .setTitle("Add book")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if (editText.getText() == null || editText.getText().toString().equals("") || editText.getText().toString().equals(" ")) {

                            Toast.makeText(EntryActivity.this, getResources().getString(R.string.blank_error), Toast.LENGTH_SHORT).show();
                        } else {

                            Category category = new Category();
                            category.setName(editText.getText().toString());
                            result = categoryHelper.insertCategory(category);
                            if (result != -1) {
                                Toast.makeText(EntryActivity.this, "Your Category added successfully", Toast.LENGTH_SHORT).show();
                                mCategoryLists.clear();
                                mCategoryLists = categoryHelper.getCategoryLists();
                                mCategoryLists.add(0, new Category());

                                myAdapter = new ArrayAdapter<Category>(EntryActivity.this, android.R.layout.simple_spinner_item, mCategoryLists);
                                mCategorySpinner.setAdapter(myAdapter);
                            }


                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private Boolean validate() {

        boolean flag = true;
        if (edt_title.getText().toString().trim().isEmpty()) {
            itTitle.setErrorEnabled(true);
            itTitle.setError(getResources().getString(R.string.blank_error));
            flag = false;
            return flag;
        } else {
            itTitle.setErrorEnabled(false);
        }

        if (edt_description.getText().toString().trim().isEmpty()) {
            itDescription.setErrorEnabled(true);
            itDescription.setError(getResources().getString(R.string.blank_error));
            flag = false;
            return flag;
        } else {
            itDescription.setErrorEnabled(false);
        }


        if (mCategorySpinner.getSelectedItem() == null) {
            mError.setVisibility(View.VISIBLE);
            mError.setText(getResources().getString(R.string.blank_error));
            return false;
        } else {
            mError.setVisibility(View.GONE);
        }

        if (customlocRatio.isChecked()) {
            if (edt_Address.getText().toString().trim().isEmpty()) {
                itAddress.setErrorEnabled(true);
                itAddress.setError(getResources().getString(R.string.blank_error));
                flag = false;
                return flag;
            } else {
                itAddress.setErrorEnabled(false);
            }
        }

        return flag;
    }

    //call after images capture from gallary or from Camera
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //take from camera
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap profileBitmap = (Bitmap) data.getExtras().get("data");
            captureImage.setImageBitmap(profileBitmap);

            //converting bitmap to byteArray

            camera_lay.setVisibility(View.INVISIBLE);
            gallery_lay.setVisibility(View.INVISIBLE);
            captureImage.setVisibility(View.VISIBLE);

            //creating byteArray file and uploading to AWS
            new SavePhotoTask(profileBitmap).execute();
        }
        //choose from gallery
        else if (requestCode == GALLERY_RESULT && resultCode == RESULT_OK && null != data) {
            try {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String fileLocation = cursor.getString(columnIndex);
                cursor.close();
                System.out.println("picture path====+++++++ :" + fileLocation);
                Bitmap bm = Common.decodeFile(new File(fileLocation));
                Bitmap profileBitmap = null;
                if (fileLocation.contains("Camera")) {

                    ExifInterface exif = new ExifInterface(fileLocation);
                    String orientString = exif
                            .getAttribute(ExifInterface.TAG_ORIENTATION);
                    int orientation = orientString != null ? Integer
                            .parseInt(orientString)
                            : ExifInterface.ORIENTATION_NORMAL;
                    int rotationAngle = 90;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                        rotationAngle = 90;
                    }
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                        rotationAngle = 180;
                    }
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                        rotationAngle = 270;
                    }

                    Matrix matrix = new Matrix();
                    matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2,
                            (float) bm.getHeight() / 2);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0,
                            bm.getWidth(), bm.getHeight(), matrix, true);
                    profileBitmap = rotatedBitmap;
                    captureImage.setImageBitmap(profileBitmap);
                    camera_lay.setVisibility(View.INVISIBLE);
                    gallery_lay.setVisibility(View.INVISIBLE);
                    captureImage.setVisibility(View.VISIBLE);

                } else {
                    profileBitmap = Common.getRectShape(bm);
                    captureImage.setImageBitmap(profileBitmap);
                    camera_lay.setVisibility(View.INVISIBLE);
                    gallery_lay.setVisibility(View.INVISIBLE);
                    captureImage.setVisibility(View.VISIBLE);
                }
                //converting bitmap to byteArray

                //creating byteArray file and uploading to AWS
                new SavePhotoTask(profileBitmap).execute();

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("onActivityResult", e.toString());
            }
        }
    }

    private void addCustomLocation() {
        curentRadio.setChecked(false);
        itAddress.setVisibility(View.VISIBLE);
    }

    private void getCurrentLocation() {
        customlocRatio.setChecked(false);
        itAddress.setVisibility(View.GONE);
    }


    private void submit() {
        if (validate()) {
            EntryHelper entryHelper = new EntryHelper(this);
            Entry entry = new Entry();
            entry.setTitle(edt_title.getText().toString());
            entry.setDescription(edt_description.getText().toString());
            entry.setLat("22");
            entry.setLang("22");
            entry.setImage(mImagePath);
            Category category = (Category) mCategorySpinner.getSelectedItem();
            entry.setCategory(category.getId());
            result = entryHelper.insertEntry(entry);
            if (result != -1) {
                Toast.makeText(this, "Record inserted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void cancel() {
        finish();
    }


    class SavePhotoTask extends AsyncTask<byte[], String, String> {
        private Bitmap bitmap;

        public SavePhotoTask(Bitmap profileBitmap) {
            this.bitmap = profileBitmap;
        }


        @Override
        protected String doInBackground(byte[]... jpeg) {
            String time = Common.getCurrentDateTime();
            File myDir = new File(Environment.getExternalStorageDirectory(), "DataCollection/photo/");
            myDir.mkdirs();
            String fname = time + ".jpg";
            File file = new File(myDir, fname);
            if (file.exists()) file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return (file.getAbsolutePath());
        }

        @Override
        protected void onPostExecute(String path) {
            mImagePath = path;
            super.onPostExecute(path);
        }
    }
}
