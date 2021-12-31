package socket4.SDN;

import socket4.Root.Router;

public class MRouter {
    public static final int X = 1000;
    public static final int A = 1001;
    public static final int B = 1002;
    public static final int C = 1003;
    public static final int D = 1004;
    public static final int E = 1005;
    public static final int F = 1006;
    public static final int G = 1007;
    public static final int H = 1008;
    public static final int Y = 1009;

    public static void main(String[] args) {
        
        Router r1 = new Router(H,Y);
        Router r2 = new Router(G,Y);
        Router r3 = new Router(F,new int[]{G,H});
        Router r4 = new Router(E,new int[]{F,H});
        Router r5 = new Router(D,new int[]{G,F});
        Router r6 = new Router(C,new int[]{D,E});
        Router r7 = new Router(B,new int[]{C,E});
        Router r8 = new Router(A,new int[]{D,C});
        Router r9 = new Router(X,new int[]{A,B});

        r9.start();
        r8.start();
        r7.start();
        r6.start();
        r5.start();
        r4.start();
        r3.start();
        r2.start();
        r1.start();


    }
}
