package com.cqse.model;

import java.util.Objects;

public final class LineRange {
    private final int start;
    private final int end;

    public LineRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int start() {
        return start;
    }

    public int end() {
        return end;
    }

    public int negativeEnd() {
        return -end;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (LineRange) obj;
        return this.start == that.start &&
                this.end == that.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "LineRange[" +
                "start=" + start + ", " +
                "end=" + end + ']';
    }
}
