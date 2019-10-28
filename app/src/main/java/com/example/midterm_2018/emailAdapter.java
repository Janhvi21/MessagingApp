package com.example.midterm_2018;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class emailAdapter extends RecyclerView.Adapter<emailAdapter.ViewHolder> implements AsyncTaskForDelete.idata {
    ArrayList<Messages> mdata;
    String token;
    String messageID = "";

    public emailAdapter(ArrayList<Messages> mdata, String token) {
        this.mdata = mdata;
        this.token = token;
    }

    public emailAdapter(ArrayList<Messages> mdata) {
        this.mdata = mdata;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Messages message = mdata.get(position);
        holder.tv_message.setText(message.getMessage());
        holder.tv_subject.setText(message.getSubject());

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTaskForDelete(position, token, emailAdapter.this).execute("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/inbox/delete/" + message.getId());
                Log.d("resultMsg", mdata.size() + " " + position);
                if(mdata.size()>0){
                    mdata.remove(position);
                    notifyDataSetChanged();
                }
                Log.d("resultMsg", mdata.size() + " " + position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();

    }

    @Override
    public void handleDeleteData(Integer position) {
        Log.d("resultpos", position + "");
        if (position != -1) {
            // mdata.remove(position);
            for (Messages mdata : mdata) {
                Log.d("result", mdata.toString());
            }

            //this.notifyItemRemoved(position);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_message, tv_subject;
        ImageView iv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_subject = (TextView) itemView.findViewById(R.id.tv_subject);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }
/*
@NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Messages message = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.email_list, parent, false);
        }
        TextView tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
        TextView tv_message = (TextView) convertView.findViewById(R.id.tv_message);


        tv_message.setText(message.getMessage());
        tv_subject.setText(message.getSubject());

        return convertView;
    }

    public emailAdapter(@NonNull Context context, int resource, @NonNull List<Messages> objects) {
        super(context, resource, objects);
    }
*/
}
