package newsarig.com.example.erez.newsarig;

/**
 * Created by erez on 31/08/2016.
 */
public class Param
{
    private String name;
    private int value;

    public Param(String name, int value)
    {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
