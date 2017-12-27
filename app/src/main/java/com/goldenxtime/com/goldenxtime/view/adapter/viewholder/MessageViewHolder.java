package com.goldenxtime.com.goldenxtime.view.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenxtime.com.goldenxtime.R;
import com.goldenxtime.com.goldenxtime.model.Message;
import com.goldenxtime.com.goldenxtime.model.Rent;
import com.goldenxtime.com.goldenxtime.view.ProperityActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.createdAtTV)
    TextView createdAtTV;
    @BindView(R.id.headerTV)
    TextView headerTV;
    @BindView(R.id.bodyTV)
    TextView bodyTV;
    @BindView(R.id.checkNumberTV)
    TextView checkNumberTV;
    @BindView(R.id.dueDateTV)
    TextView dueDateTV;
    @BindView(R.id.fromBankTV)
    TextView fromBankTV;
    @BindView(R.id.toBankTV)
    TextView toBankTV;
    @BindView(R.id.valueTV)
    TextView valueTV;
    @BindView(R.id.checkL)
    LinearLayout checkL;
    @BindView(R.id.card)
    CardView cardView;

    public MessageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setMessage(final Message message) {
        createdAtTV.setText(message.getDate());
        headerTV.setText(message.getTitle());
        bodyTV.setText(message.getBody());
        if (message.getPropertyId() != 0) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), ProperityActivity.class);
                    intent.putExtra("id", message.getPropertyId());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    public void setMessage(Rent.Collection collection) {
        createdAtTV.setText(collection.getAt());
        headerTV.setText("طريقة تحصيل " + collection.getTypeDisplayName());

        if (collection.getGiven() != 0) {
            bodyTV.setText("تم تحصيل " + collection.getGiven());
        } else {
            bodyTV.setVisibility(View.GONE);
        }

        try {
            if (collection.getChecks() != null && collection.getChecks().get(0) != null) {
                Rent.Collection.Check check = collection.getChecks().get(0);
                checkL.setVisibility(View.VISIBLE);
                checkNumberTV.setText(check.getNumber());
                dueDateTV.setText(check.getDueDate());
                fromBankTV.setText(check.getFromBank());
                toBankTV.setText(check.getToBank());
                valueTV.setText(String.valueOf(check.getValue()));
            }
        } catch (Exception e) {}
    }
}
