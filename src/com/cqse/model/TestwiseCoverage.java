package com.cqse.model;

import java.util.Objects;
import java.util.Set;

public final class TestwiseCoverage {
    private final Set<Test> tests;

    public TestwiseCoverage(Set<Test> tests) {
        this.tests = tests;
    }

    public Set<Test> tests() {
        return tests;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TestwiseCoverage) obj;
        return Objects.equals(this.tests, that.tests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tests);
    }

    @Override
    public String toString() {
        return "TestwiseCoverage[" +
                "tests=" + tests + ']';
    }
}
