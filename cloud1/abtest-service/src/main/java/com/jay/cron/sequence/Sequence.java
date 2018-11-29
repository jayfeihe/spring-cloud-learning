package com.jay.cron.sequence;

public class Sequence {
    private SequenceID id = SequenceID.forValue(0);
    private int current;
    private int last;

    public final SequenceID getID() {
        return id;
    }

    public final void setID(SequenceID value) {
        id = value;
    }

    public final int getCurrent() {
        return current;
    }

    public final void setCurrent(int value) {
        current = value;
    }

    public final int getLast() {
        return last;
    }

    public final void setLast(int value) {
        last = value;
    }
}
