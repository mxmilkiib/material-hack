package io.github.mxmilkiib.materialistic.data;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

import io.reactivex.rxjava3.schedulers.Schedulers;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 29)
public class HackerNewsClientTest {

    private HackerNewsClient client;

    @Before
    public void setUp() {
        RestServiceFactory factory = mock(RestServiceFactory.class, RETURNS_DEEP_STUBS);
        client = new HackerNewsClient(factory, null, null);
        client.mIoScheduler = Schedulers.trampoline();
        client.mMainThreadScheduler = Schedulers.trampoline();
    }

    @Test
    public void toItemsNullIdsReturnsEmptyArray() {
        HackerNewsItem[] result = client.toItems(null);
        assertNotNull("null ids must produce a non-null array", result);
        assertEquals("null ids must produce an empty array", 0, result.length);
    }

    @Test
    public void toItemsEmptyIdsReturnsEmptyArray() {
        HackerNewsItem[] result = client.toItems(new int[0]);
        assertNotNull(result);
        assertEquals(0, result.length);
    }

    @Test
    public void toItemsSingleIdBuildsCorrectItem() {
        HackerNewsItem[] result = client.toItems(new int[]{42});
        assertEquals(1, result.length);
        assertEquals("42", result[0].getId());
        assertEquals(1, result[0].rank);
    }

    @Test
    public void toItemsMultipleIdsSetsRanksAndIds() {
        HackerNewsItem[] result = client.toItems(new int[]{10, 20, 30});
        assertEquals(3, result.length);
        assertEquals("10", result[0].getId());
        assertEquals(1, result[0].rank);
        assertEquals("20", result[1].getId());
        assertEquals(2, result[1].rank);
        assertEquals("30", result[2].getId());
        assertEquals(3, result[2].rank);
    }
}
