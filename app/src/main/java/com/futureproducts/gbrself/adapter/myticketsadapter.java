package com.futureproducts.gbrself.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.futureproducts.gbrself.R;
import com.futureproducts.gbrself.fragments.viewtickets;
import com.futureproducts.gbrself.holder.holder;
import com.futureproducts.gbrself.models.njorder1;
import com.futureproducts.gbrself.viewtickets.SupportTicket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class myticketsadapter extends RecyclerView.Adapter<holder> {

    Context context;
    List<SupportTicket> list;
    ticketcallbal ticketcallbal1;
    public myticketsadapter(Context context, List<SupportTicket> itemlist, viewtickets viewtickets) {
        this.list = itemlist;
        this.context = context;
//        ticketcallbal1 = (ticketcallbal) viewtickets;
    }
    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);

        // Inflate the layout

        View photoView
                = inflater
                .inflate(R.layout.item_viewticket,
                        parent, false);

        holder viewHolder
                = new holder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        int p = holder.getAdapterPosition();

        holder.mytname.setText(list.get(p).getHeading());
        holder.mytstatus.setText(list.get(p).getStatus());

        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        try {
            date = inputFormat.parse(list.get(p).getTime());
            String str = outputFormat.format(date);
            holder.mytdate.setText(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.mytdesp.setText(list.get(p).getDescription());
        if(list.get(holder.getAdapterPosition()).getAttachment()== null){
            holder.mytdownload.setVisibility(View.GONE);
        }else{
            holder.mytdownload.setVisibility(View.VISIBLE);
        }
        holder.mytdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ticketcallbal1.download(p);
                Uri urifile = Uri.parse("https://Pallavifoods.com/retailgbr"+list.get(p).getAttachment());
                downloadfile(urifile, p);

            }
        });

    }

    private void downloadfile(Uri urifile, int p1) {
        DownloadManager downloadManager = (DownloadManager)
                context.getSystemService(Context.DOWNLOAD_SERVICE);

        DownloadManager.Request request = new DownloadManager.Request(urifile);

        request.setTitle("GBR Downloading");
        request.setDescription(list.get(p1).getHeading());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);


        Long reference = downloadManager.enqueue(request);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ticketcallbal{
        void download(int ipos);
    }
}
