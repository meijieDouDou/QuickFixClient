package cicc.quickfix.client.field;

import quickfix.DoubleField;
import quickfix.IntField;

public class PercentageOfVolume extends DoubleField{
    private static final long serialVersionUID = 2044562930659784087L;

    public static final int FIELD = 6013;

    public PercentageOfVolume(){
        super(6013);
    }

    public PercentageOfVolume(double data){
        super(6013,data);
    }
}
