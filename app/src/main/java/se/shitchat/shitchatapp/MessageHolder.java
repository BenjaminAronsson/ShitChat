package se.shitchat.shitchatapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageHolder extends RecyclerView.ViewHolder {

    //Fields
    public TextView dateView;
    public TextView messageView;
    public ImageView pictureView;

    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        messageView = itemView.findViewById(R.id.text_view_message);
        dateView = itemView.findViewById(R.id.text_view_time);
        pictureView = itemView.findViewById(R.id.image_view_profile);

    }


}
