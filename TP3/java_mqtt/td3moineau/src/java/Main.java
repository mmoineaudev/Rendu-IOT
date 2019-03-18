import Sensors.TempSensor;
import Sensors.LightSensor;
import Sensors.Sensors;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Main {
    static int nbSecondsRunning = 30;
    public static void main(String[] args) throws MqttException, InterruptedException {
        Sensors sensors = new Sensors();
        sensors.run();


    }



}