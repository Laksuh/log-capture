package de.dm.infrastructure.logcapture;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class CapturingAppenderIntegrationTest { //TODO: move this test to the right place/unit

    private final String TEST_KEY = "test_key";
    private final String OTHER_KEY = "test_key_2";

    @Test
    void containsMdcEntries() {
        ExpectedMdcEntry expectedMdcEntry1 = ExpectedMdcEntry.withMdc(TEST_KEY, "test value");
        ExpectedMdcEntry expectedMdcEntry2 = ExpectedMdcEntry.withMdc(OTHER_KEY, "good value");
        List<ExpectedMdcEntry> expectedMdcEntries = new LinkedList<>();

        expectedMdcEntries.add(expectedMdcEntry1);
        expectedMdcEntries.add(expectedMdcEntry2);

        Map<String, String> mdcContents = new HashMap<>();
        mdcContents.put(TEST_KEY, "this is a test value, cool!");
        mdcContents.put(OTHER_KEY, "this is a good value, cool!");

        LoggedEvent loggedEvent = LoggedEvent.builder()
                .mdcData(mdcContents)
                .build();

        Assertions.assertTrue(CapturingAppender.isMatchedByAll(loggedEvent, expectedMdcEntries));
    }

    @Test
    void nullEntriesShouldNotThrowNullPointerException() {
        Map<String, String> mdcContents = new HashMap<>();

        LoggedEvent loggedEvent = LoggedEvent.builder()
                .mdcData(mdcContents)
                .build();

        Assertions.assertTrue(CapturingAppender.isMatchedByAll(loggedEvent, null));
    }
}
