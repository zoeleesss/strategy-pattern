package discount;

/**
 * Created by sss on 01/11/2017.
 */
public class ZeroDiscount extends  Discount{


    @Override
    public float getPrice(float price, int quantity) {
        return price*quantity;
    }
}
