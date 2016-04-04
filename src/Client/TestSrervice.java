package Client;

import All.RunService;

/**
 * Created by Ice on 4/4/2559.
 */
public class TestSrervice {
    public static void main(String[] args) {
        RunService rs = new RunService();
        Thread C1 = new Thread(rs);
        C1.start();
    }
}

