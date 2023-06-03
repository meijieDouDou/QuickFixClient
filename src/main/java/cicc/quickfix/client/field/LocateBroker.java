package cicc.quickfix.client.field;

import quickfix.StringField;

public class LocateBroker extends StringField {
    private static final long serialVersionUID = 737852234285227901L;
    public static final int FIELD = 5700;

    public LocateBroker(){
        super(5700);
    }

    public LocateBroker(String data) {
        super(5700,data);
    }
}
