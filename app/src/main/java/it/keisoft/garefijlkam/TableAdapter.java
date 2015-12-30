package it.keisoft.garefijlkam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import it.keisoft.garefijlkam.bean.TableBean;

/**
 * Created by mmarcheselli on 15/12/2015.
 */
public class TableAdapter extends ArrayAdapter<TableBean> {

    private int resource;
    private LayoutInflater inflater;

    public TableAdapter(Context context, int resourceId, List<TableBean> objects) {
        super(context, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        // Recuperiamo l'oggetti che dobbiamo inserire a questa posizione
        TableBean table = getItem(position);

        ViewHolder holder;

        if (v == null) {
            v = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.primoTextView = (TextView) v.findViewById(R.id.namePrimo);
            holder.socPrimoTextView = (TextView) v.findViewById(R.id.socPrimo);
            holder.secondoTextView = (TextView) v.findViewById(R.id.nameSecondo);
            holder.socSecondoTextView = (TextView) v.findViewById(R.id.socSecondo);
//            holder.nameTextView = (TextView) v.findViewById(R.id.personName);
//            holder.surnameTextView = (TextView) v.findViewById(R.id.personSurname);
            holder.personImageView = (ImageView) v.findViewById(R.id.primoRes);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.primoTextView.setText(table.getBianco());
        holder.socPrimoTextView.setText(table.getSocBianco());
        if(holder.secondoTextView != null) {
            holder.secondoTextView.setText(table.getBlu());
            holder.socSecondoTextView.setText(table.getSocBlu());
        }
        if(table.getImage() != 0) {
            holder.personImageView.setImageResource(table.getImage());
        }
//        holder.nameTextView.setText(person.getName());
//        holder.surnameTextView.setText(person.getSurname());

        return v;
    }

    private static class ViewHolder {
        TextView primoTextView;
        TextView socPrimoTextView;
        TextView secondoTextView;
        TextView socSecondoTextView;
        ImageView personImageView;
    }
}