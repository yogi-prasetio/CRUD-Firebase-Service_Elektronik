package com.service.elektronik.sarana.database;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.service.elektronik.sarana.R;
import com.service.elektronik.sarana.adapter.AdapterService;
import com.service.elektronik.sarana.model.DataService;

import java.util.ArrayList;

public class DBReadService extends AppCompatActivity implements AdapterService.FirebaseDataListener {
    /**
     * Mendefinisikan variable yang akan dipakai
     */
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataService> dataCustomer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Mengeset layout
         */
        setContentView(R.layout.activity_dbread_service);
        rvView = (RecyclerView) findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();
        database.child("customer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                dataCustomer = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    DataService cutomer = noteDataSnapshot.getValue(DataService.class);
                    cutomer.setKey(noteDataSnapshot.getKey());
                    dataCustomer.add(cutomer);
                }
                adapter = new AdapterService(dataCustomer, DBReadService.this);
                rvView.setAdapter(adapter);
                if (dataCustomer==null){
                    Toast.makeText(DBReadService.this, "Data is Empty!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }

        });
    }
    public static Intent getActIntent(Activity activity){
        return new Intent(activity, DBReadService.class);
    }

    public void onDeleteData(DataService customer, final int position) {
        if(database!=null){
            database.child("customer").child(customer.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(DBReadService.this,"Delete Success!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}