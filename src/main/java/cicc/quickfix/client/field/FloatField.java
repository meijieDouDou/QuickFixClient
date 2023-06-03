package cicc.quickfix.client.field;

import quickfix.Field;

public class FloatField extends Field<Float> {
    public FloatField(int field) {
        super(field, (float) 0);
    }

    public FloatField(int field, Float data) {
        super(field, data);
    }

    public FloatField(int field, float data) {
        super(field, data);
    }

    public void setValue(Float value) {
        this.setObject(value);
    }

    public void setValue(float value) {
        this.setObject(value);
    }

    public float getValue() {
        return (Float)this.getObject();
    }

    public boolean valueEquals(Float value) {
        return ((Float)this.getObject()).equals(value);
    }

    public boolean valueEquals(float value) {
        return ((Float)this.getObject()).equals(value);
    }
}
