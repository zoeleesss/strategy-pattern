package discount;

/**
 * Created by sss on 01/11/2017.
 */
public class PerDiscount extends Discount {

    @Override
    public float getPrice(float price, int quantity) {
        return (price-50)*quantity;
    }
}
