package Sensors;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TempSensor extends AbstractSensor {
    public TempSensor() throws MqttException, InterruptedException {
        super();
        this.client = new MqttClient(broker, clientId, persistence);
        this.topic = super.topic+"temp";
        this.content = "AbstractSensoris not meant to be used";

    }


    @Override
    public void publish() throws MqttException {
        //super.publish();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        super.messageArrived(s, mqttMessage);
        this.powerSwitch.setTemperature(mqttMessage.toString());

    }
}
