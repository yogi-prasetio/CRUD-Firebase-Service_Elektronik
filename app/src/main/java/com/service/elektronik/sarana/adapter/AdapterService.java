package com.service.elektronik.sarana.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.service.elektronik.sarana.R;
import com.service.elektronik.sarana.database.DBAddService;
import com.service.elektronik.sarana.database.DBReadService;
import com.service.elektronik.sarana.model.DataService;

import java.util.ArrayList;

public class AdapterService extends RecyclerView.Adapter<AdapterService.ViewHolder> {
    private ArrayList<DataService> dataService;
    private Context context;
    FirebaseDataListener listener;

    public AdapterService(ArrayList<DataService> customer, Context ctx){
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        dataService = customer;
        context = ctx;
        listener = (DBReadService)ctx;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * Inisiasi View
         * Di tutorial ini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
        TextView tvName;
        TextView tvPhone;
        ViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tv_name);
            tvPhone = (TextView) v.findViewById(R.id.tv_phone);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rows_data, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         * Menampilkan data pada view
         */
        final String name = dataService.get(position).getNama();
        final String phone = dataService.get(position).getNohp();
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Read detail data
                 */
            }
        });
        holder.tvName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /**
                 * Delete dan update data
                 */
                //Update
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.dialog_view);
                dialog.setTitle("Pilih Aksi");
                dialog.show();
                Button editButton=(Button)dialog.findViewById(R.id.bt_edit_data);
                Button delButton=(Button)dialog.findViewById(R.id.bt_delete_data);
                //aksi tombol edit di klik
                editButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                context.startActivity(DBAddService.getActIntent((Activity)
                                        context).putExtra("data", dataService.get(position)));
                            }
                        }
                );
                //aksi buttondelete di klik
                delButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                /**
                                 * Delete data
                                 */
                                dialog.dismiss();
                                listener.onDeleteData(dataService.get(position), position);
                            }
                        }
                );
                return true;
            }
        });
        holder.tvName.setText(name);
        holder.tvPhone.setText(phone);
    }

    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item
         */
        return dataService.size();
    }

    public interface FirebaseDataListener{
        void onDeleteData(DataService customer, int position);
    }
}