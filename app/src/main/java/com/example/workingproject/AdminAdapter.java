package com.example.workingproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class AdminAdapter extends ArrayAdapter<Information>{

        private static final String TAG = "AdminAdapter";
        private Context mContext;
        int mResource;

    public AdminAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Information> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        String email = getItem(position).getEmail();
        Double amount = getItem(position).getAmount();
        int numberOfProduct = getItem(position).getNo_products();

        Information info = new Information(email, numberOfProduct, amount);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView emailList = convertView.findViewById(R.id.emailList);
        TextView amountList = convertView.findViewById(R.id.amountList);

        emailList.setText(email);
        amountList.setText(new Double(amount).toString());

        return  convertView;
    }

}
