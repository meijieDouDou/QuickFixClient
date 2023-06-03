package cicc.quickfix.client.field;

import org.apache.bcel.generic.PUSH;
import quickfix.StringField;

public class SenderSubId extends StringField {

    private static final long serialVersionUID = 7590448101629072968L;

    public static final int FIELD = 50;

    public SenderSubId() {
        super(50);
    }

    public SenderSubId(String data){
        super(50,data);
    }
}
