package Sensors;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class PowerSwitch extends AbstractSensor {
    private double temperature = -270;
    private double enlightment= -1;


    public PowerSwitch() throws MqttException, InterruptedException {
        this.client = new MqttClient(broker, clientId, persistence);
        this.topic = super.topic+"led";
    }


    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        //super.messageArrived(s, mqttMessage);
        //do nothing
    }

    public void react() throws MqttException {
        //si le message est cohérent
        //si la lumière est allumée et qu'il fait froid
        if(enlightment>2000 && temperature < 30){
            heat();
        }else {
            //si la lumière est éteinte et qu'il fait froid
            //on n'allume pas le chauffage
            //si la lumiere est éteinte et qu'il fait chaud
            //on n'allume pas le chauffage

            //si la lumière est allumée et qu'il fait chaud
            //on n'allume pas le chauffage
            cool();
        }
    }


    private void cool() throws MqttException {
        content = "off";
        publish();
    }

    private void heat() throws MqttException {
        content = "on";
        publish();
    }

    private boolean isValidDoubleValue(String message) {
        try{
            Double.parseDouble(message);
        }catch(Exception e){
            return false;
        }return true;
    }

    public void setTemperature(String s){
        if(isValidDoubleValue(s)){
            temperature = Double.parseDouble(s);
        }

    }
    public void setEnlightment(String s){
        if(isValidDoubleValue(s)){
            enlightment = Double.parseDouble(s);
        }

    }

    public void run(){
        try {
            connect();
            Thread.sleep(AbstractSensor.TIME_TO_WAIT);
            react();
            Thread.sleep(AbstractSensor.TIME_TO_WAIT);
        } catch (MqttException | InterruptedException e) {
            //e.printStackTrace();
            System.out.print(this.getClass().getSimpleName()+" : "+e.getMessage()+"...");
            //trop fréquent, l'api future aurait sans doute permi de le faire mieux
        }
    }

}