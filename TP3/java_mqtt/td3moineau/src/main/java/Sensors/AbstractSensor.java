package Sensors;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class AbstractSensor implements IMqttMessageListener, Runnable {
    public static final long TIME_TO_WAIT = 2000;
    //--
    protected String topic        = "miage1/menez/sensors/";
    protected String content      = "AbstractSensoris not meant to be used";
    protected int qos             = 2;
    protected String broker       = "tcp://localhost:1883";
    protected String clientId     = "Java";
    protected MemoryPersistence persistence = new MemoryPersistence();
    //--
    protected MqttClient client;

    /**
     * Decorator pour agir sur la led "chauffage"
     */
    protected PowerSwitch powerSwitch;
    protected void addPowerSwitch(PowerSwitch powerSwitch) {
        this.powerSwitch = powerSwitch;
    }

    /**
     * On instancie à null pour assurer que ca soit fait dans les classes filles
     * @throws MqttException
     * @throws InterruptedException
     */
    public AbstractSensor() throws MqttException, InterruptedException {
        client = null;
        powerSwitch = null;
    }

    /**
     * Attente qu'un message se poste sur le topic
     * @throws InterruptedException
     */
    public void listen() throws InterruptedException {
        Thread.sleep(TIME_TO_WAIT);
    }

    /**
     * Connexion et abonnement
     */
    public void connect() {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setAutomaticReconnect(true);
        connOpts.setCleanSession(true);
        connOpts.setConnectionTimeout(10);
        System.out.print("Connecting to broker: " + broker + " on topic " + topic + "... ");
        try{
            client.connect(connOpts);
            client.subscribe(topic, this);
        }catch(Exception e){
            System.out.println(e.getClass().getSimpleName()+" "+e.getMessage()+": "+this.topic+" : "+e.getCause());
            return;
        }
        System.out.println(topic+ " connected");

    }

    /**
     * Emission d'un message
     * @throws MqttException
     */
    public void publish() throws MqttException {
        System.out.print("Publishing message: "+content+" to topic "+topic+"... ");
        MqttMessage messageMqtt = new MqttMessage(content.getBytes());
        messageMqtt.setQos(qos);
        messageMqtt.setRetained(true);
        client.publish(topic, messageMqtt);
        System.out.println("Message published");
    }

    /**
     * Callback de subscribe
     * @param s le topic
     * @param mqttMessage le message
     */
    public void messageArrived(String s, MqttMessage mqttMessage){
        System.out.println("messageArrived on topic [\n"+this.topic+" : "+mqttMessage.toString()+"\n]");
    }

    /**
     * Interface callable : rend executable ce bout de code dans un thread par un executor service
     */
    public void run() {
        try {
            connect();
            listen();
            disconnect();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            client.disconnect();
        } catch (MqttException e) {
            System.out.println("disconnection failed :"+e.getMessage());
            return;
        }
        System.out.println("Disconnected");
    }

}
