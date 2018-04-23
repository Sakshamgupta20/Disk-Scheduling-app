package labs.a.s.disk_scheduling;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FCFSAdapter extends RecyclerView.Adapter<FCFSAdapter.MyViewHolder>  {

    private Context mContext;
    private int b;
    private Integer list[]=new Integer[100];

    public FCFSAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        public MyViewHolder(View view) {
            super(view);

            text=view.findViewById(R.id.listtext);

        }


    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String temp;
        if(position==b-1)
        {
             temp=String.valueOf(list[position]);
        }
        else
        {
             temp=String.valueOf(list[position])+" -> ";
        }
        holder.text.setText(temp);
    }

    @Override
    public int getItemCount() {
        return b;
    }

    public void setBakingData(Integer[] arr) {
        list=arr;
        notifyDataSetChanged();
    }
    public void setBakingNumber(int a) {
        b=a;
    }

}