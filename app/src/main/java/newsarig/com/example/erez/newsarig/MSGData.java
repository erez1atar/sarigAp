package newsarig.com.example.erez.newsarig;

import java.util.ArrayList;

/**
 * Created by erez on 31/08/2016.
 */
public class MSGData
{
    private String type;
    private String action;
    private ArrayList<Param> params;

    public MSGData(String type)
    {
        this.type = type;
        this.action = "";
        this.params = new ArrayList<>();
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList<Param> getParams() {
        return params;
    }

    public String getType() {
        return type;
    }

    public String getAction() {
        return action;
    }

    public void addParam(Param param)
    {
        params.add(param);
    }
}
