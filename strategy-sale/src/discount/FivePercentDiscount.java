package discount;

/**
 * Created by sss on 01/11/2017.
 */
public class FivePercentDiscount extends Discount {

    @Override
    public float getPrice(float price, int quantity) {
        return (float) (price*(quantity*0.95));
    }
}
