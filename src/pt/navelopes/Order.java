package pt.navelopes;

public class Order {
    int[] order;
    int totalPieces;
    double totalPrice;

    void print() {
        System.out.println(String.format("You can get %d wings for %f$",totalPieces, totalPrice));
        for (int i = 0; i < order.length; i++) {
            if (order[i] > 0) {
                System.out.println(String.format("%d: %d",Menu.quantities[i], order[i]));
            }
        }
    }
}
