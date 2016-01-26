package it.keisoft.garefijlkam;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        final TableBean table = getItem(position);

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
            holder.resultImageView = (ImageView) v.findViewById(R.id.result);
            holder.primoLinearLayout = (LinearLayout) v.findViewById(R.id.primo);
            holder.secondoLinearLayout = (LinearLayout) v.findViewById(R.id.secondo);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.primoTextView.setText(table.getBianco().getT_nome());
        holder.socPrimoTextView.setText(table.getBianco().getT_soc());
        if(holder.secondoTextView != null) {
            holder.secondoTextView.setText(table.getBlu().getT_nome());
            holder.socSecondoTextView.setText(table.getBlu().getT_soc());
        }
        if(table.getImage() != 0) {
            holder.resultImageView.setImageResource(table.getImage());
        }

        holder.primoLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRoute(table.getBianco().getC_atl(), table.getC_id_gara());
            }
        });
        holder.secondoLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRoute(table.getBlu().getC_atl(), table.getC_id_gara());
            }
        });
//        holder.nameTextView.setText(person.getName());
//        holder.surnameTextView.setText(person.getSurname());

        return v;
    }

    private void viewRoute(String id_atleta, String id_gara){
        Intent intent = new Intent(getContext(), RouteActivity.class);
        intent.putExtra(RouteActivity.ID_ATLETA, id_atleta);
        intent.putExtra(RouteActivity.ID_GARA, id_gara);
        getContext().startActivity(intent);
    }

    private static class ViewHolder {
        TextView primoTextView;
        TextView socPrimoTextView;
        TextView secondoTextView;
        TextView socSecondoTextView;
        ImageView resultImageView;
        LinearLayout primoLinearLayout;
        LinearLayout secondoLinearLayout;
    }
}