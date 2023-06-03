package cicc.quickfix.client.field;

import quickfix.IntField;

public class PartOnOpen extends IntField {


    private static final long serialVersionUID = 6382292835508013111L;

    public static final int FIELD = 6011;

    public static final int N = 0;
    public static final int Y = 1;


    public PartOnOpen(){
        super(6011);
    }

    public PartOnOpen(int data) {
        super(6011,data);
    }
}
