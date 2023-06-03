package cicc.quickfix.client.field;

import quickfix.IntField;

public class PriceImprove extends IntField {
    private static final long serialVersionUID = -5654030222218005937L;

    public static final int FIELD = 6069;

    public PriceImprove() {
        super(6069);
    }

    public PriceImprove(int data) {
        super(6069,data);
    }


}
