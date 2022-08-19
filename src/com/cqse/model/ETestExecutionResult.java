package com.cqse.model;

/** The result of a test execution. */
public enum ETestExecutionResult {

    /** Test execution was successful. */
    PASSED,

    /** The test is currently marked as "do not execute" (e.g. JUnit @Ignore). */
    IGNORED,

    /** Caused by a failing assumption. */
    SKIPPED,

    /** Caused by a failing assertion. */
    FAILURE,

    /** Caused by an error during test execution (e.g. exception thrown in the test runner code, not the test itself). */
    ERROR;

    public static ETestExecutionResult combine(ETestExecutionResult result1, ETestExecutionResult result2) {
        return ETestExecutionResult.values()[Math.max(result1.ordinal(), result2.ordinal())];
    }

}
