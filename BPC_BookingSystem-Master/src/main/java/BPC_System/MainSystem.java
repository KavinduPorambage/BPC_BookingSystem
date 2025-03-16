package BPC_System;

import BPC_System.Controllers.MasterController;

public class MainSystem {

    public static void main(String[] args) {
        MasterController master = new MasterController();
        master.Head();
        master.MainStart();
    }
    public MainSystem() {
    }
}
