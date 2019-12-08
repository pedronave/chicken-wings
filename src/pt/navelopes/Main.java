package pt.navelopes;

public class Main {


    public static void main(String[] args) {
	// write your code here
        Order order = Menu.bestOrder(200);

        if (order!= null) {
            order.print();
        }else {
            System.out.println("Can't find a combination for that");
        }
    }
}
