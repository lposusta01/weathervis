import javax.json.stream;
import javax.imageio;
import java.awt.*; //i dont know if all of awt is necessary here but wtv

//rain %chance/temp = raindrop width, color (higher chance means brighten and desaturate)
//rain %chance = raindrop particle intensity
//temperature = white balance
//time of day = saturation, brightness
//wind speed = rain particle angle and speed

public static saturation_brightness_mod(Color col, float saturation, float brightness) { //todo: get brightness from time
    //convert to hsl, modify s and l, convert back to srgb
}

public static float get_angle_from(int speed) {
    //arbitrarily decide raindrop angle from wind speed
}

public static Color white_balance(Color col, int temp) {
    //do white balance on col and return the white balanced Color object
}

public class RaindropParticle {
    private int height;
    private int width;
    private int xPos;
    private int yPos;
    private float opacity;
    private int speed;
    private float angle;
    private Color color;

    public RaindropParticle(int w, int h, int x, int y, float o, int s, Color col) {
        this.height = h;
        this.width = w;
        this.xPos = x;
        this.yPos = y;
        this.opacity = o;
        this.speed = s;
        this.angle = get_angle_from(s);
        this.color = col;
    }

    public int[2] getXY() {
        return {this.xPos, this.yPos};
    }
}

public class JSONdata {
    public int temp;
    public int precipChance;
    public int rainTempValue;
    public int windSpeed;
    public int time;
}

public static RaindropParticle[] generate_raindrops(JSONdata data) {

}

public static void main(String[] args) {

}
