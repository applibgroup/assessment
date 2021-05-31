package android.example.libusage;

import android.example.libusage.databinding.RecyclerviewRowBinding;
import android.example.libusage.model.RecyclerData;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private List<RecyclerData> listData;

    public void setListData(List<RecyclerData> listData){
        this.listData = listData;
    }

//    @NonNull
//    @Override
//    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row,parent,false);
//        return new MyViewHolder(view);
//    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RecyclerviewRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
//        holder.tvTitle.setText(listData.get(position).getName());
//        holder.tvDesc.setText(listData.get(position).getDescription());
//        Glide.with(holder.thumbImage)
//                .load(listData.get(position).getOwner().getAvatar_url())
//                .apply(RequestOptions.centerCropTransform())
//                .into(holder.thumbImage);

        holder.recyclerviewRowBinding.tvTitle.setText(listData.get(position).getName());
        holder.recyclerviewRowBinding.tvDesc.setText(listData.get(position).getDescription());
        Glide.with(holder.recyclerviewRowBinding.thumbImage)
                .load(listData.get(position).getOwner().getAvatar_url())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.recyclerviewRowBinding.thumbImage);
    }

    @Override
    public int getItemCount() {
        if(listData == null) return 0;
        else return listData.size();
    }

//    public class MyViewHolder extends RecyclerView.ViewHolder{
//        TextView tvTitle;
//        TextView tvDesc;
//        ImageView thumbImage;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvTitle = itemView.findViewById(R.id.tvTitle);
//            tvDesc = itemView.findViewById(R.id.tvDesc);
//            thumbImage = itemView.findViewById(R.id.thumbImage);
//        }
//
//    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private  RecyclerviewRowBinding recyclerviewRowBinding;
        public MyViewHolder(RecyclerviewRowBinding recyclerviewRowBinding) {
            super(recyclerviewRowBinding.getRoot());
            this.recyclerviewRowBinding = recyclerviewRowBinding;
        }

    }

}
