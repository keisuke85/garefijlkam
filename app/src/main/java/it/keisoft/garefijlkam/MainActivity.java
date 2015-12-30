package it.keisoft.garefijlkam;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import it.keisoft.garefijlkam.util.BaseActivity;
import it.keisoft.garefijlkam.util.HomeScreenGridViewAdapter;

public class MainActivity extends BaseActivity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        initializeActivity();
    }

    private void initializeActivity(){
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new HomeScreenGridViewAdapter(this, _items));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openActivity(i+1);
            }
        });
    }
}
