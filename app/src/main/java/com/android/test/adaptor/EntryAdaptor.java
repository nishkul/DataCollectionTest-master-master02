package com.android.test.adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.test.R;
import com.android.test.database.EntryHelper;
import com.android.test.model.Entry;

import java.util.List;

/**
 * Created by Manish on 9/2/17.
 */

public class EntryAdaptor extends RecyclerView.Adapter<EntryAdaptor.ViewHolder> {
    private List<Entry> categoryList;
    private Context mContext;
    private ViewHolder viewHolder;

    public EntryAdaptor(Context context, List<Entry> arralist) {
        this.categoryList = arralist;
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_row, parent, false);
        view.setTag(view);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Entry entry = categoryList.get(position);
        holder.mtitle.setText(entry.getTitle());
        holder.mDesc.setText(entry.getTitle());

       // File file = new File(DOUBT_DIR + path);
       if (entry.getImage()!=null){
           Bitmap bmp = BitmapFactory.decodeFile(entry.getImage());

           holder.mImage.setImageBitmap(bmp);
       }
        holder.mReomve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntryHelper entryHelper = new EntryHelper(mContext);
                if (entryHelper.deleteEntry(String.valueOf(entry.getId())) != -1) {
                    Toast.makeText(mContext, "Entry deleted succssfully", Toast.LENGTH_SHORT).show();
                    categoryList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });

    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDesc;
        public final TextView mtitle;
        public final TextView mReomve;
        private final ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mtitle = (TextView) mView.findViewById(R.id.name_tv);
            mDesc = (TextView) mView.findViewById(R.id.desc_tv);
            mReomve = (TextView) mView.findViewById(R.id.remove_tv);
            mImage = (ImageView) mView.findViewById(R.id.image_iv);
        }
    }
}
