package cicc.quickfix.client.field;


import quickfix.StringField;

public class StartTime extends StringField {
    private static final long serialVersionUID = -7876825430696076284L;

    public static final int FIELD = 7113;

    public StartTime() {
        super(7113);
    }

    public StartTime(String data) {
        super(7113,data);
    }
}
