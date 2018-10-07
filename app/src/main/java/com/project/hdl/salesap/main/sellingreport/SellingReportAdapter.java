package com.project.hdl.salesap.main.sellingreport;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.project.hdl.salesap.R;

/**
 * Created by hendra.dl on 26/08/2017.
 */

public class SellingReportAdapter extends RecyclerView.Adapter<SellingReportAdapter.SellingReportViewHolder> {
    Activity activity;

    public SellingReportAdapter(Activity activity){
        this.activity = activity;
    }


    public class SellingReportViewHolder extends RecyclerView.ViewHolder{
        public EditText productNameText, productCountText, productPriceText;
        public ImageView deleteListImage;

        public SellingReportViewHolder(View view){
            super(view);
            productNameText = (EditText) view.findViewById(R.id.productNameText);
            productCountText = (EditText) view.findViewById(R.id.productCountText);
            productPriceText = (EditText) view.findViewById(R.id.productPriceText);
            deleteListImage = (ImageView) view.findViewById(R.id.deleteListImage);
            productNameText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    int position = getAdapterPosition();
                    SellingReportActivity.reportDList.get(position).setProduct_name(editable.toString());
                }
            });
            productCountText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    int position = getAdapterPosition();
                    SellingReportActivity.reportDList.get(position).setProduct_count(editable.toString());
                    SellingReportActivity.recalculateTotalOnThread();
                }
            });
            productPriceText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    int position = getAdapterPosition();
                    SellingReportActivity.reportDList.get(position).setProduct_price(editable.toString());
                    SellingReportActivity.recalculateTotalOnThread();
                }
            });
        }
    }



    @Override
    public SellingReportAdapter.SellingReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selling_report_item, parent, false);

        return new SellingReportAdapter.SellingReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SellingReportViewHolder holder, final int position) {
        holder.productNameText.setText(SellingReportActivity.reportDList.get(position).getProduct_name());
        holder.productCountText.setText(SellingReportActivity.reportDList.get(position).getProduct_count());
        holder.productPriceText.setText(SellingReportActivity.reportDList.get(position).getProduct_price());

        holder.deleteListImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SellingReportActivity.reportDList.remove(position);
                notifyDataSetChanged();
                SellingReportActivity.recalculateTotalOnThread();
            }
        });

    }

    @Override
    public int getItemCount() {
        return SellingReportActivity.reportDList.size();
    }

}
