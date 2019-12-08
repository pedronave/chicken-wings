package pt.navelopes;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

class Piece {
    static int p4 = 0;
    static int p5 = 1;
    static int p6 = 2;
    static int p7 = 3;
    static int p8 = 4;
    static int p9 = 5;
    static int p10 = 6;
    static int p11 = 7;
    static int p12 = 8;
    static int p13 = 9;
    static int p14 = 10;
    static int p15 = 11;
    static int p16 = 12;
    static int p17 = 13;
    static int p18 = 14;
    static int p19 = 15;
    static int p20 = 16;
    static int p21 = 17;
    static int p22 = 18;
    static int p23 = 19;
    static int p24 = 20;
    static int p25 = 21;
    static int p26 = 22;
    static int p27 = 23;
    static int p28 = 24;
    static int p29 = 25;
    static int p30 = 26;
    static int p35 = 27;
    static int p40 = 28;
    static int p45 = 29;
    static int p50 = 30;
    static int p60 = 31;
    static int p70 = 32;
    static int p75 = 33;
    static int p80 = 34;
    static int p90 = 35;
    static int p100 = 36;
    static int p125 = 37;
    static int p150 = 38;
    static int p200 = 39;

}

public class Menu {
    public static final int[] quantities = new int[]{4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
            24, 25, 26, 27, 28, 29, 30, 35, 40, 45, 50, 60, 70, 75, 80, 90, 100, 125,150,200};
    public static final double[] prices = new double[]{4.55, 5.7, 6.8, 7.95, 9.1, 10.2, 11.35, 12.5, 13.6, 14.75, 15.9, 17,
            18.15, 19.3, 20.4, 21.55, 22.7, 23.8, 24.95, 26.1, 27.25, 27.8, 28.95, 30.1, 31.2, 32.35, 33.5, 39.15, 44.8,
            50.5, 55.6, 67, 78.3, 83.45, 89.1, 100.45, 111.25, 139, 166.85, 222.5,};


    public static Order bestOrder(int wantedWings) {
        Model md = new Model();
        Order order = null;

        // how many items of each quantity
        IntVar[] items  = md.intVarArray(quantities.length,0, wantedWings/4);
        items[Piece.p4] = md.intVar(0,1); // getting 8 is better 2*4
        items[Piece.p5] = md.intVar(0,1); // 10 better 2*5
        items[Piece.p6] = md.intVar(0,1);
        items[Piece.p7] = md.intVar(0,1);
        items[Piece.p8] = md.intVar(0,1);
        items[Piece.p9] = md.intVar(0,1);
        items[Piece.p10] = md.intVar(0,1);
        items[Piece.p11] = md.intVar(0,1);
        items[Piece.p13] = md.intVar(0,1);
        items[Piece.p14] = md.intVar(0,1);
        items[Piece.p15] = md.intVar(0,1);
        items[Piece.p20] = md.intVar(0,1);
        items[Piece.p24] = md.intVar(0,0); //it is cheaper to get 2*12
        //items[Piece.p25] = md.intVar(0,1);
        items[Piece.p30] = md.intVar(0,1);
        items[Piece.p35] = md.intVar(0,1);
        items[Piece.p40] = md.intVar(0,1);
        items[Piece.p45] = md.intVar(0,1);
        items[Piece.p75] = md.intVar(0,1);
        items[Piece.p100] = md.intVar(0,0); //it is cheaper to get 4*25
        items[Piece.p125] = md.intVar(0,0); //it is cheaper to get 5*25
        items[Piece.p150] = md.intVar(0,0); //it is cheaper to get 6*25
        items[Piece.p200] = md.intVar(0,0); //it is cheaper to get 8*25


        IntVar[] itemsQ = md.intVarArray(quantities.length,0, 2000);
        IntVar[] itemsP = md.intVarArray(quantities.length,0, 160*wantedWings);

        IntVar total = md.intVar(wantedWings, wantedWings*5);
        IntVar price = md.intVar(100*wantedWings, 160*wantedWings);
        md.arithm(total,">=", wantedWings).post();

        for (int i=0; i < items.length; i++) {
            itemsQ[i].eq(items[i].mul(quantities[i])).post();
            itemsP[i].eq(items[i].mul((int)(prices[i]*100.0))).post();
        }
        md.sum(itemsQ,"=", total).post();
        md.sum(itemsP, "=", price).post();

        md.setObjective(Model.MINIMIZE, price);

        Solver solver = md.getSolver();
        solver.limitTime("10s");
        while(solver.solve()){
            // an improving solution has been found
            System.out.println(String.format("You get %d for %f", total.getValue(), price.getValue()/100.0));
            order = new Order();
            order.order = new int[items.length];
            for (int i = 0; i < items.length; i++) {
                order.order[i] = items[i].getValue();
                order.totalPieces = total.getValue();
                order.totalPrice = price.getValue()/100.0;
            }
            //order.print();
        }
        return order;
    }
}
