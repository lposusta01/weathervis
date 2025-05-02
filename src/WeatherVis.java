import javax.imageio.*;
import java.net.*;
import java.net.spi.URLStreamHandlerProvider;
import java.io.*;
import jakarta.json.*;
import jakarta.json.stream.*;
import java.awt.*;
import javax.swing.*;
import java.nio.charset.StandardCharsets;

//rain %chance/temp = raindrop width, color (higher chance means brighten and desaturate)
//rain %chance = raindrop particle intensity
//temperature = white balance
//time of day = saturation, contrast
//wind speed = rain particle angle and speed
public class WeatherVis {
    public static void saturation_mod(Color col, float saturation) {
        //convert to hsv, modify s, convert back to srgb
    }

    public static float get_angle_from(int speed) {
        //arbitrarily decide raindrop angle from wind speed
        return 0.0f;
    }

    public static Color white_balance(Color col, int temp) {
        //do white balance on col and return the white balanced Color object
        return Color.WHITE;
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

        public String toString() {
            return String.valueOf(height) + String.valueOf(width) + String.valueOf(xPos) + String.valueOf(yPos) + String.valueOf(opacity) + String.valueOf(speed) + String.valueOf(angle) + String.valueOf(color);
        }
    }

    public class JSONdata {
        public int temp;
        public int windSpeed;
        public int precipChance;

        public JSONdata(int temp, int windSpeed, int precipChance) {
            this.temp = temp;
            this.windSpeed = windSpeed;
            this.precipChance = precipChance;
        }

        public String toString() {
            return String.valueOf(temp) + String.valueOf(windSpeed) + String.valueOf(precipChance);
        }

    }

    enum SpecialParserState {
        INIT,
        READ_TEMP,
        READ_WIND_SPEED,
        READ_PRECIP_CHANCE,
    } //i probably don't need to use a state machine for this but whatever

    public JSONdata generate_json_data(String input) {
        int temp = 0;
        int windSpeed = 0;
        int precipChance = 0;
        JSONdata out = new JSONdata(temp, windSpeed, precipChance);
        SpecialParserState state = SpecialParserState.INIT;
        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.US_ASCII));
        JsonParser parser = Json.createParser(inputStream);
        while(parser.hasNext()) {
            //System.out.println(parser.currentEvent());
            if(parser.currentEvent() == JsonParser.Event.KEY_NAME) {
                if(parser.getString() == "temperature") {
                    state = SpecialParserState.READ_TEMP;
                    System.out.println("temperature");
                    parser.next();
                } else if(parser.getString() == "windSpeed") {
                    state = SpecialParserState.READ_WIND_SPEED;
                    System.out.println("windSpeed");
                    parser.next();
                } else if(parser.getString() == "probabilityOfPrecipitation") {
                    state = SpecialParserState.READ_PRECIP_CHANCE;
                    System.out.println("probabilityOfPercipitation"); //the parser is always in state INIT for some reason
                    parser.next();
                } else {
                    state = SpecialParserState.INIT;
                    parser.next();
                }
            } else if(parser.currentEvent() == JsonParser.Event.VALUE_NUMBER) {
                if(state != SpecialParserState.INIT) {
                    switch(state) {
                        case READ_TEMP:
                            temp = parser.getInt();
                            break;
                        case READ_WIND_SPEED:
                            temp = parser.getInt();
                            break;
                        case READ_PRECIP_CHANCE:
                            temp = parser.getInt();
                            break;
                        default:
                            System.err.println("Invalid parser state (This is bad!!)");
                            break;
                    }
                } else {
                    parser.next();
                }
            } else {
                parser.next();
            }
        }
        return out;
    }

    public static RaindropParticle[] generate_raindrops(JSONdata data, int w, int h) {
        WeatherVis wv = new WeatherVis();
        RaindropParticle outputA = wv.new RaindropParticle(0, 0, 0, 0, 0.0f, 0, Color.BLACK);
        RaindropParticle[] output = {outputA};
        return output;
    }

    public static String getHttp(String uri) {
        try{
            URL url = URI.create(uri).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            if (connection.getResponseCode() == 200) {
                ByteArrayInputStream responseBais = new ByteArrayInputStream(connection.getInputStream().readAllBytes());
                String response = new String(responseBais.readAllBytes());
                return response.toString();
                //i'm literally god
            } else {
                return String.valueOf(connection.getResponseCode());
            }
        } catch (Exception e) {
            // Display exception/s on console
            return e.getMessage();
        }
    }

    public static void windowInit(Image image) {

    }

    public static void main(String[] args) {
        String test = getHttp("https://api.weather.gov/gridpoints/BOU/105,40/forecast");
        //System.out.println(test);
        WeatherVis self = new WeatherVis();//this is scuffed
        JSONdata data = self.generate_json_data(test); //OOP moment
        System.out.println("");
        System.out.println(data.toString());
    }
}
