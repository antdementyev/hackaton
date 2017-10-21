package dementyev.anton.myapplication;

/**
 * Created by yusi on 20/10/2017.
 */
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CordiData {
    double x,y,z;

    public CordiData( String x, String y, String z){
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.z = Double.parseDouble(z);;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getZ(){
        return z;
    }

}
