package com.milanps.bikenetwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Milan Pop Stefanija
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {

    private List<CompanyDTO> companies;
    private List<CompanyDTO> companiesFull;
    private LayoutInflater inflater;
    private static ClickListener clickListener;
    private Context mContext;
    List<CompanyDTO> filteredList;

    DataAdapter(Context context, List<CompanyDTO> companies) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.companies = companies;
        companiesFull = new ArrayList<>(companies);
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_recycler_view_row, parent, false);
        //view.setOnClickListener(onItemClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        holder.tv_name.setText(companies.get(position).getName());
        holder.tv_city.setText(companies.get(position).getLocationDTO().getCity());
        holder.tv_country.setText(companies.get(position).getLocationDTO().getCountry());
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public List<CompanyDTO> getFilteredList() {
        return companies;
    }


    String getItem(int id) {
        return String.valueOf(companies.get(id));
    }

    void setClickListener(ClickListener clickListener) {
        DataAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    @Override
    public Filter getFilter() {
        return companiesFilter;
    }

    private Filter companiesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(companiesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CompanyDTO c : companiesFull) {
                    if (c.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(c);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            companies.clear();
            companies.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_name;
        private TextView tv_country;
        private TextView tv_city;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_city = itemView.findViewById(R.id.tv_city);
            tv_country = itemView.findViewById(R.id.tv_country);
            itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }


    }
}
