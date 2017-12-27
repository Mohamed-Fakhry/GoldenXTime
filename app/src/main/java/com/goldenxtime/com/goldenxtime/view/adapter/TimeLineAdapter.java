package com.goldenxtime.com.goldenxtime.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.Message;
import com.goldenxtime.com.goldenxtime.model.Rent;
import com.goldenxtime.com.goldenxtime.view.adapter.viewholder.MessageViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

public class TimeLineAdapter<T> extends RecyclerView.Adapter<MessageViewHolder> {

    ArrayList<T> messages = new ArrayList<>();

    public TimeLineAdapter(ArrayList<T> messages) {
        super();
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == messages.size() - 1) {
            return new MessageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bottom_message_row, null));
        } else {
            return new MessageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.messge_row, null));
        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        if (messages.get(position) instanceof Rent.Collection)
            holder.setMessage((Rent.Collection) messages.get(position));
        else
            holder.setMessage((Message) messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
