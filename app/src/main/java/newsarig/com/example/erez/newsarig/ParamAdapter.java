package newsarig.com.example.erez.newsarig;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by erez on 31/08/2016.
 */
public class ParamAdapter extends ArrayAdapter<Param>
{
    private ArrayList<Param> params;
    private final Activity context;

    public ParamAdapter(Activity context, int resource,ArrayList<Param> params) {
        super(context, resource);
        this.context = context;
        this.params = params;
    }

    public void setParams(ArrayList<Param> params) {
        this.params = params;
    }

    @Override
    public Param getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return params.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.param_layout, parent, false);
        }

        TextView key = (TextView)convertView.findViewById(R.id.keyText);
        key.setText(params.get(position).getName());

        TextView val = (TextView)convertView.findViewById(R.id.valText);
        val.setText(String.valueOf(params.get(position).getValue()));
        return convertView;
    }
}
