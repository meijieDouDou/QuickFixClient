package cicc.quickfix.client.field;


import quickfix.StringField;

public class Strategy extends StringField {

    private static final long serialVersionUID = -7208782091440118223L;
    public static final int FIELD = 7111;

    public static final String TWAP = "TWAP";
    public static final String VWAP = "VWAP";
    public static final String POV = "POV";
    public static final String IS = "IS";
    public static final String ICEBERG = "ICEBERG";
    public static final String CLOSE = "CLOSE";
    public static final String SORDMA = "SORDMA";
    public static final String HIDDEN = "HIDDEN";
    public static final String FLOAT = "FLOAT";




    public Strategy() {
        super(7111);
    }

    public Strategy(String data) {
        super(7111, data);
    }
}
