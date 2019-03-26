package Sensors;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sensors{
    private HashMap<String, AbstractSensor> mapping;
    public Sensors() {
        this.mapping = new HashMap<String, AbstractSensor>();
    }

    public void addSensor(String ID, AbstractSensor sensor){
        mapping.put(ID, sensor);
    }

    /**
     * point d'entrée
     */
    public void run(){
        try {
            this.addSensor("TEMP", new TempSensor());
            this.addSensor("LIGHT", new LightSensor());

            execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * BugFix : les données d'un seul capteur remontaient
     * @throws Exception
     */
    private void execute() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(mapping.size());
        PowerSwitch powerSwitch = new PowerSwitch();
        for (;;) {
            for(AbstractSensor r : mapping.values()) {
                try {

                    r.addPowerSwitch(powerSwitch);
                    executor.execute(r);
                    executor.execute(powerSwitch);
                } catch (Exception ex) {
                    //executor.shutdown();
                    throw new Exception("Executor is down : \n"+ ex.getMessage()+"\n"+ ex.getCause());
                }
            }
        }

    }

}
