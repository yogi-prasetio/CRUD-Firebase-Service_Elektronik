package com.service.elektronik.sarana.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.service.elektronik.sarana.R;
import com.service.elektronik.sarana.model.DataService;

public class DBAddService extends AppCompatActivity {
    // variable yang merefers ke Firebase Realtime Database
    private DatabaseReference database;
    // variable fields EditText dan Button
    private Button btSubmit;
    private EditText etNama;
    private EditText etAlamat;
    private EditText etPhone;
    private EditText etMerk;
    private EditText etJenis;
    private EditText etWarna;
    private EditText etRusak;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbadd_customer);
        // inisialisasi fields EditText dan Button
        etNama = (EditText) findViewById(R.id.et_nama);
        etAlamat = (EditText) findViewById(R.id.et_alamat);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etMerk = (EditText) findViewById(R.id.et_merk);
        etJenis = (EditText) findViewById(R.id.et_jenis);
        etWarna = (EditText) findViewById(R.id.et_warna);
        etRusak = (EditText) findViewById(R.id.et_rusak);
        btSubmit = (Button) findViewById(R.id.bt_submit);
        // mengambil referensi ke Firebase Database
        database = FirebaseDatabase.getInstance().getReference();
        //Final Update
        final DataService customer=(DataService) getIntent().getSerializableExtra("data");
        if(customer!=null){
            //ini untuk update
            etNama.setText(customer.getNama());
            etAlamat.setText(customer.getAlamat());
            etPhone.setText(customer.getNohp());
            etMerk.setText(customer.getMerk());
            etJenis.setText(customer.getJenis());
            etWarna.setText(customer.getWarna());
            etRusak.setText(customer.getKerusakan());
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customer.setNama(etNama.getText().toString());
                    customer.setAlamat(etAlamat.getText().toString());
                    customer.setNohp(etPhone.getText().toString());
                    customer.setMerk(etMerk.getText().toString());
                    customer.setJenis(etJenis.getText().toString());
                    customer.setWarna(etWarna.getText().toString());
                    customer.setKerusakan(etRusak.getText().toString());
                    updatecustomer(customer);
                }
            });
        }
        else {
            // kode yang dipanggil ketika tombol Submit diklik
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isEmpty(etPhone.getText().toString()) && !isEmpty(etNama.getText().toString()) && !isEmpty(etAlamat.getText().toString()))
                        submitcustomer(
                                new DataService(
                                        etNama.getText().toString(),
                                        etAlamat.getText().toString(),
                                        etPhone.getText().toString(),
                                        etMerk.getText().toString(),
                                        etJenis.getText().toString(),
                                        etWarna.getText().toString(),
                                        etRusak.getText().toString()
                                    ));
                    else
                        Snackbar.make(findViewById(R.id.bt_submit), "Data Service tidak boleh kosong!",
                                Snackbar.LENGTH_LONG).show();
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            etNama.getWindowToken(), 0);
                }
            });
        }
    }
    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }
    private void updatecustomer(DataService customer) {
        // Update customer
        database.child("customer")
                .child(customer.getKey())
                .setValue(customer)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.bt_submit), "Data Berhasil di Update",
                                Snackbar.LENGTH_LONG).setAction("OKE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        }).show();
                        startActivity(new Intent(DBAddService.this, DBReadService.class));
                    }
                });
    }

    //fungsi Simpan data customer
    private void submitcustomer(DataService customer) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        database.child("customer").push().setValue(customer).addOnSuccessListener(this, new
                OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        etNama.setText("");
                        etAlamat.setText("");
                        etPhone.setText("");
                        etMerk.setText("");
                        etJenis.setText("");
                        etWarna.setText("");
                        etRusak.setText("");
                        Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                    }
                });
    }
    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, DBAddService.class);
    }
}