package com.example.orderapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderapp.CallBack.IRecyclerClickListener;
import com.example.orderapp.Common.Common;
import com.example.orderapp.EventBus.StuffItemClick;
import com.example.orderapp.Model.StuffModel;
import com.example.orderapp.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ConcurrentModificationException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyStuffListAdapter extends RecyclerView.Adapter<MyStuffListAdapter.MyViewHolder> {

    private Context context;
    private List<StuffModel> stuffModelList;

    public MyStuffListAdapter(Context context, List<StuffModel> stuffModelList) {
        this.context = context;
        this.stuffModelList = stuffModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.layout_stuff_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(stuffModelList.get(position).getImage()).into(holder.img_stuff_image);
        holder.txt_stuff_price.setText(new StringBuilder("$")
        .append(stuffModelList.get(position).getPrice()));
        holder.txt_stuff_name.setText(new StringBuilder("")
        .append(stuffModelList.get(position).getName()));

        //Event
        holder.setListener((view, pos) -> {
            Common.selectedStuff = stuffModelList.get(pos);
            Common.selectedStuff.setKey(String.valueOf(pos));
            EventBus.getDefault().postSticky(new StuffItemClick(true, stuffModelList.get(pos)));
        });

    }

    @Override
    public int getItemCount() {
        return stuffModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Unbinder unbinder;

        @BindView(R.id.txt_stuff_name)
        TextView txt_stuff_name;
        @BindView(R.id.txt_stuff_price)
        TextView txt_stuff_price;
        @BindView(R.id.img_stuff_image)
        ImageView img_stuff_image;
        @BindView(R.id.img_fav)
        ImageView img_fav;
        @BindView(R.id.img_quick_cart)
        ImageView img_quick_cart;

        IRecyclerClickListener listener;

        public void setListener(IRecyclerClickListener listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(v, getAdapterPosition());
        }
    }
}
