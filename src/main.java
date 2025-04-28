import javax.json.stream;
import javax.imageio;
import java.awt.*; //i dont know if all of awt is necessary here but wtv

//rain %chance/temp = raindrop width, color (higher chance means brighten and desaturate)
//rain %chance = raindrop particle intensity
//temperature = white balance
//time of day = saturation, contrast
//wind speed = rain particle angle and speed
public class Main {
    public static void saturation_mod(Color col, float saturation) {
        //convert to hsv, modify s, convert back to srgb
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

        public int[] getXY() {
            int[] out = {this.xPos, this.yPos};
            return out;
        }
    }

    public class JSONdata {
        public int temp;
        public int precipChance;
        public int rainTempValue;
        public int windSpeed;
    }

    public static JSONdata generate_json_data(String input) {
        JsonParser parser = new JsonParser(input);

    }

    public static RaindropParticle[] generate_raindrops(JSONdata data) {

    }

    public static void main(String[] args) {

    }
}
