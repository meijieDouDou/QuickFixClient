package cicc.quickfix.client.field;

import quickfix.CharField;
import quickfix.IntField;

public class CrossSpread extends IntField {
    private static final long serialVersionUID = 4190078132022646005L;

    public static final int FIELD = 6059;

    public CrossSpread(){
        super(6059);
    }

    public CrossSpread(int data) {
        super(6059,data);
    }
}
