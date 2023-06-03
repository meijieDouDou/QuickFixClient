package cicc.quickfix.client.reuse;

import cicc.quickfix.client.field.Strategy;
import quickfix.fix42.OrderCancelReplaceRequest;

public class OrderCancelReplaceRequestNew extends OrderCancelReplaceRequest {
    public void set(quickfix.field.TargetSubID value) {
        setField(value);
    }

    public void set(Strategy value) {
        setField(value);
    }
}
