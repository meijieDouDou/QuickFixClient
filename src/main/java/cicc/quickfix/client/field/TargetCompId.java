package cicc.quickfix.client.field;

import quickfix.StringField;

public class TargetCompId extends StringField {

    private static final long serialVersionUID = 2141819658632296160L;
    public static final int FIELD = 56;

    public TargetCompId() {
        super(56);
    }

    public TargetCompId(String data) {
        super(56,data);
    }
}
