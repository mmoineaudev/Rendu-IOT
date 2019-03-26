package run;

import Sensors.Sensors;

public class EntryPoint {
    public static void main(String[] args) {
        System.out.println(EntryPoint.class.getName());
        try {
            Sensors sensors = new Sensors();
            sensors.run();
        } catch (Exception e) {
            System.out.println("Exception : "+e.getMessage());
            System.exit(1);
        }

    }
}