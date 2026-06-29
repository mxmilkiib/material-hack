package io.github.mxmilkiib.materialistic.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SyncDelegateParseItemIdTest {

    @Test
    public void numericIdParsedAsInteger() {
        assertEquals(12345, SyncDelegate.parseItemId("12345"));
    }

    @Test
    public void negativeNumericIdParsedAsInteger() {
        assertEquals(-42, SyncDelegate.parseItemId("-42"));
    }

    @Test
    public void nonNumericIdFallsBackToHashCode() {
        String id = "abc123";
        assertEquals(id.hashCode(), SyncDelegate.parseItemId(id));
    }

    @Test
    public void emptyStringFallsBackToHashCode() {
        assertEquals("".hashCode(), SyncDelegate.parseItemId(""));
    }

    @Test
    public void largeNumericIdFallsBackToHashCodeIfOverflow() {
        String largeId = "99999999999";
        assertEquals(largeId.hashCode(), SyncDelegate.parseItemId(largeId));
    }

    @Test
    public void sameNonNumericIdProducesSameHash() {
        String id = "test-id-xyz";
        assertEquals(SyncDelegate.parseItemId(id), SyncDelegate.parseItemId(id));
    }
}
