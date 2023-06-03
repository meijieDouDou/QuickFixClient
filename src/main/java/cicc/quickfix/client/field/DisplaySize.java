package cicc.quickfix.client.field;

import quickfix.IntField;

public class DisplaySize extends IntField {


    private static final long serialVersionUID = -6204865616239601779L;
    public static final int FIELD = 6084;

    public DisplaySize() {
        super(6084);
    }

    public DisplaySize(int data) {
        super(6084, data);
    }
}
