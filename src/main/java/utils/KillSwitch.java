package utils;

public final class KillSwitch {

    private static final KillSwitch INSTANCE = new KillSwitch();

    private KillSwitch(){}

    public static KillSwitch getInstance() {return INSTANCE;}

    public void emergencyStop(){
        System.exit(-1);
    }

}
