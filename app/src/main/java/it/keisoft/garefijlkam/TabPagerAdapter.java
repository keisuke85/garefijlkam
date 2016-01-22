package it.keisoft.garefijlkam;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.keisoft.garefijlkam.R;
import it.keisoft.garefijlkam.TableAdapter;
import it.keisoft.garefijlkam.bean.Classifica;
import it.keisoft.garefijlkam.bean.TableBean;

/**
 * Created by mmarcheselli on 14/12/2015.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private static Map<String, ArrayList<TableBean>> mapBeans;
    private static ArrayList<Classifica> podio;
//    private int start = 0;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        mapBeans = new HashMap<>();
        podio = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        Bundle args = new Bundle();
//        args.putInt(TablePart.EXTRA_BUNDLE, i+getStart());
        args.putInt(TablePart.EXTRA_BUNDLE, getCount()-i);
        TablePart newFragment = new TablePart ();
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mapBeans != null ? mapBeans.size() : 0; //No of Tabs
    }

    public Map<String, ArrayList<TableBean>> getMapBeans() {
        return mapBeans;
    }

    public void setMapBeans(Map<String, ArrayList<TableBean>> mapBeans) {
        this.mapBeans = mapBeans;
    }

    public static ArrayList<Classifica> getPodio() {
        return podio;
    }

    public void setPodio(ArrayList<Classifica> podio) {
        this.podio = podio;
    }

    /*    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
*/

    public static class TablePart extends Fragment {
        public static final String EXTRA_BUNDLE = "tables";
        ListView listView;
        ListView listViewRep;
        LinearLayout linearLayout;
        static View android;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            Bundle bundle = this.getArguments();
            int key = bundle.getInt(TablePart.EXTRA_BUNDLE, 0);

            if(key == 0){
                // uso un layout con una singola listView
                android = inflater.inflate(R.layout.current_tournament, container, false);
                listView = (ListView) android.findViewById(R.id.listView);
                List<TableBean> podio = new ArrayList<>();
                TableBean tmp;
                for (Classifica c : getPodio()) {
                    tmp = new TableBean();
                    switch (c.getC_pos()){
                        case "1":
                            tmp.setImage(R.drawable.oro);
                            break;
                        case "2":
                            tmp.setImage(R.drawable.argento);
                            break;
                        case "3":
                            tmp.setImage(R.drawable.bronzo);
                            break;
                        case "4":
                            tmp.setImage(R.drawable.bronzo);
                            break;
                    }
                    tmp.setBianco(c.getAtleta());
                    podio.add(tmp);
                }

                TableAdapter tableAdapter = new TableAdapter(getContext(), R.layout.list_podium_adapter, podio);
                listView.setAdapter(tableAdapter);
            }else {
                android = inflater.inflate(R.layout.current_weight, container, false);

                listView = (ListView) android.findViewById(R.id.listView);
                listViewRep = (ListView) android.findViewById(R.id.listViewRep);
                linearLayout = (LinearLayout) android.findViewById(R.id.layoutInf);

                List<TableBean> regolari = new ArrayList<>();
                List<TableBean> ripescati = new ArrayList<>();
                ArrayList<TableBean> arrayList = mapBeans.get(Integer.toString(key));
                for (TableBean t : arrayList) {
                    if (t.getF_rip().equalsIgnoreCase("0")) {
                        regolari.add(t);
                    } else {
                        ripescati.add(t);
                    }
                }

                TableAdapter tableAdapter = new TableAdapter(getContext(), R.layout.list_match_adapter, regolari);
                listView.setAdapter(tableAdapter);

                TableAdapter tableAdapterRep = new TableAdapter(getContext(), R.layout.list_match_adapter, ripescati);
                listViewRep.setAdapter(tableAdapterRep);

                TextView textView = (TextView) android.findViewById(R.id.headerRep);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (linearLayout.getVisibility() == View.GONE) {
                            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            lp.addRule(RelativeLayout.ABOVE, R.id.layoutInf);
                            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                            expand(linearLayout);
                        } else {
                            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                            lp.addRule(RelativeLayout.ABOVE);
                            collapse(linearLayout);
                        }
                    }
                });
            }

            return android;
        }

        public static void expand(final View v) {
            v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//                    android.getLayoutParams().MATCH_PARENT, android.getLayoutParams().WRAP_CONTENT);
            final int targetHeight = v.getMeasuredHeight();

            // Older versions of android (pre API 21) cancel animations for views with a height of 0.
            v.getLayoutParams().height = 1;
            v.setVisibility(View.VISIBLE);
            Animation a = new Animation()
            {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    v.getLayoutParams().height = interpolatedTime == 1
                            ? WindowManager.LayoutParams.WRAP_CONTENT//v.getLayoutParams().WRAP_CONTENT
                            : (int)(targetHeight * interpolatedTime);
                    v.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // 1dp/ms
            a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);
        }

        public static void collapse(final View v) {
            final int initialHeight = v.getMeasuredHeight();

            Animation a = new Animation()
            {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if(interpolatedTime == 1){
                        v.setVisibility(View.GONE);
                    }else{
                        v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                        v.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // 1dp/ms
            a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);
        }

    }
}