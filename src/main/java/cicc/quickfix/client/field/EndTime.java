package cicc.quickfix.client.field;


import quickfix.StringField;

public class EndTime extends StringField {


    private static final long serialVersionUID = -8708771442455227827L;

    public static final int FIELD = 7114;

    public EndTime() {
        super(7114);
    }

    public EndTime(String data) {
        super(7114,data);
    }
}
