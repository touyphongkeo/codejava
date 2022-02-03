package com.app.APResturant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.APResturant.R;
import com.app.APResturant.database.DatabaseAccess;
import com.app.APResturant.settings.categories.EditCategoryActivity;

import java.util.HashMap;
import java.util.List;

public class ProductTableAdapter extends RecyclerView.Adapter<ProductTableAdapter.MyViewHolder> {


    MediaPlayer player;
    private List<HashMap<String, String>> categoryData;
    private Context context;
    RecyclerView recyclerView;
    ImageView imgNoProduct;
    TextView txtNoProducts;


    public ProductTableAdapter(Context context, List<HashMap<String, String>> categoryData, RecyclerView recyclerView, ImageView imgNoProduct, TextView txtNoProducts) {
        this.context = context;
        this.categoryData = categoryData;
        this.recyclerView=recyclerView;
        player = MediaPlayer.create(context, R.raw.delete_sound);

        this.imgNoProduct=imgNoProduct;
        this.txtNoProducts=txtNoProducts;

    }


    @NonNull
    @Override
    public ProductTableAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_table_item, parent, false);
        return new MyViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ProductTableAdapter.MyViewHolder holder, int position) {

        final String category_id = categoryData.get(position).get("category_id");
        String category_name = categoryData.get(position).get("category_name");
        String Status = categoryData.get(position).get("Status");

             Status = Status.trim();

                if(Status.equals("1")){
                    holder.imgStatus.setImageResource(R.drawable.tbl1);
                }else if(Status.equals("2")){
                    holder.imgStatus.setImageResource(R.drawable.tbl2);

                }else if(Status.equals("3")){
                    holder.imgStatus.setImageResource(R.drawable.tbl3);

                }else if(Status.equals("4")){
                    holder.imgStatus.setImageResource(R.drawable.tbl4);

                }else{
                    holder.imgStatus.setImageResource(R.drawable.tbl1);
                }


        holder.txtCategoryName.setText(category_name);

        holder.cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player.start();
                final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                databaseAccess.open();

                //get data from local database
                List<HashMap<String, String>> productList;
                productList = databaseAccess.getTabProducts(category_id);

                if (productList.isEmpty()) {


                    recyclerView.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    imgNoProduct.setVisibility(View.VISIBLE);
                    imgNoProduct.setImageResource(R.drawable.not_found);
                    txtNoProducts.setVisibility(View.VISIBLE);

                } else {




                    recyclerView.setVisibility(View.VISIBLE);
                    imgNoProduct.setVisibility(View.GONE);
                    txtNoProducts.setVisibility(View.GONE);

                   PosProductAdapter productAdapter = new PosProductAdapter(context, productList);

                    recyclerView.setAdapter(productAdapter);





                }



            }
        });



    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtCategoryName;
        CardView cardCategory;
        ImageView imgStatus;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategoryName = itemView.findViewById(R.id.txt_category_name);
            cardCategory=itemView.findViewById(R.id.card_category);
            imgStatus=itemView.findViewById(R.id.imgStatus);
         //   imgStatus.setImageResource(R.drawable.table4);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, EditCategoryActivity.class);
            i.putExtra("category_id", categoryData.get(getAdapterPosition()).get("category_id"));
            i.putExtra("category_name", categoryData.get(getAdapterPosition()).get("category_name"));
       //     i.putExtra("Status", categoryData.get(getAdapterPosition()).get("Status"));
           // context.startActivity(i);
        }
    }


}
