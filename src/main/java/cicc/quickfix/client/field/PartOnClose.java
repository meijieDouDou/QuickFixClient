package cicc.quickfix.client.field;

import quickfix.IntField;

public class PartOnClose extends IntField {

    private static final long serialVersionUID = -8858012482251501271L;
    public static final int FIELD = 6012;

    public static final int N = 0;
    public static final int Y = 1;


    public PartOnClose() {
        super(6012);
    }

    public PartOnClose(int data) {
        super(6012, data);
    }
}
