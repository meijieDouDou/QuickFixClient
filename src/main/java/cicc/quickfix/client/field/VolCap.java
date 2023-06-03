package cicc.quickfix.client.field;


import quickfix.DoubleField;

public class VolCap extends DoubleField {

    private static final long serialVersionUID = -6158499080907414737L;
    public static final int FIELD = 7126;

    public VolCap(){
        super(7126);
    }

    public VolCap(double data){
        super(7126,data);
    }
}
