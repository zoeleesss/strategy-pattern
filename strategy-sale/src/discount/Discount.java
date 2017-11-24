package discount;

/**
 * Created by sss on 01/11/2017.
 */
public abstract class Discount {

    int quantity;

    public abstract float getPrice(float price,int quantity);

}
