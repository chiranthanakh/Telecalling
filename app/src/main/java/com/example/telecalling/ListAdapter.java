package com.example.telecalling;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Modelclass> {

    SqlDB sqlDB = new SqlDB(getContext());

    public ListAdapter(@NonNull Context context, ArrayList<Modelclass> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.listitems, parent, false);
        }

        Modelclass currentNumberPosition = getItem(position);

        TextView name = currentItemView.findViewById(R.id.tv_name);
        TextView number = currentItemView.findViewById(R.id.tv_number);
        CheckBox connected = currentItemView.findViewById(R.id.connected);
        CheckBox notintersted = currentItemView.findViewById(R.id.notinstersted);
        CheckBox unrached = currentItemView.findViewById(R.id.unreached);
        CheckBox callback = currentItemView.findViewById(R.id.callback);
        LinearLayout llcolor = currentItemView.findViewById(R.id.bag_color);

        name.setText(currentNumberPosition.name);
        number.setText(currentNumberPosition.number);

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number.getText().toString()));
                getContext().startActivity(callIntent);
                sqlDB.getUpdate(currentNumberPosition.number,1);
            }
        });

        notintersted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                   connected.setChecked(false);
                   unrached.setChecked(false);
                   callback.setChecked(false);
                   llcolor.setBackgroundColor(Color.parseColor("#FF0000"));
                   sqlDB.getUpdate(currentNumberPosition.number,2);
                }else{
                    llcolor.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    sqlDB.getUpdate(currentNumberPosition.number,0);
                }
            }
        });
        unrached.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    connected.setChecked(false);
                    notintersted.setChecked(false);
                    callback.setChecked(false);
                    llcolor.setBackgroundColor(Color.parseColor("#FFFF00"));
                    sqlDB.getUpdate(currentNumberPosition.number,3);
                }else {
                    llcolor.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    sqlDB.getUpdate(currentNumberPosition.number,0);
                }
            }
        });
        callback.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    connected.setChecked(false);
                    notintersted.setChecked(false);
                    unrached.setChecked(false);
                    llcolor.setBackgroundColor(Color.parseColor("#0000FF"));
                    sqlDB.getUpdate(currentNumberPosition.number,4);
                }else {
                    llcolor.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    sqlDB.getUpdate(currentNumberPosition.number,0);
                }
            }
        });

        connected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    notintersted.setChecked(false);
                    unrached.setChecked(false);
                    callback.setChecked(false);
                    llcolor.setBackgroundColor(Color.parseColor("#7FFF00"));
                }
                else {
                    llcolor.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    sqlDB.getUpdate(currentNumberPosition.number,0);
                }
            }
        });


        return currentItemView;
    }
}
