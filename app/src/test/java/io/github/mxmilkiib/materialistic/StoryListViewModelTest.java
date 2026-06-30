package io.github.mxmilkiib.materialistic;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.core.util.Pair;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.os.Looper;

import io.github.mxmilkiib.materialistic.data.Item;
import io.github.mxmilkiib.materialistic.data.ItemManager;
import io.reactivex.rxjava3.schedulers.Schedulers;

import org.robolectric.shadows.ShadowLooper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 29)
public class StoryListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private StoryListViewModel viewModel;
    private ItemManager itemManager;

    @Before
    public void setUp() {
        itemManager = mock(ItemManager.class);
        viewModel = new StoryListViewModel();
        viewModel.inject(itemManager, Schedulers.trampoline());
    }

    @Test
    public void setItemsFirstTimeHasNullPrevious() {
        Item[] items = new Item[]{mock(Item.class)};
        when(itemManager.getStories(anyString(), anyInt())).thenReturn(new Item[0]);

        final Pair<?, ?>[] received = new Pair[1];
        viewModel.getStories("top", ItemManager.MODE_DEFAULT)
                .observeForever(pair -> received[0] = pair);

        viewModel.setItems(items);

        assertNotNull(received[0]);
        assertNull("first explicit setItems should have null as previous", received[0].first);
        assertEquals(items, received[0].second);
    }

    @Test
    public void setItemsSecondTimeCarriesPreviousAsFirst() {
        Item[] first = new Item[]{mock(Item.class)};
        Item[] second = new Item[]{mock(Item.class), mock(Item.class)};
        when(itemManager.getStories(anyString(), anyInt())).thenReturn(new Item[0]);

        final Pair<?, ?>[] received = new Pair[1];
        viewModel.getStories("top", ItemManager.MODE_DEFAULT)
                .observeForever(pair -> received[0] = pair);

        viewModel.setItems(first);
        viewModel.setItems(second);

        assertNotNull(received[0]);
        assertEquals("second setItems should carry first array as previous", first, received[0].first);
        assertEquals(second, received[0].second);
    }

    @Test
    public void getStoriesReturnsNonNullLiveData() {
        Item[] items = new Item[0];
        when(itemManager.getStories(anyString(), anyInt())).thenReturn(items);

        assertNotNull(viewModel.getStories("top", ItemManager.MODE_DEFAULT));
    }

    @Test
    public void getStoriesPopulatesLiveData() {
        Item[] items = new Item[]{mock(Item.class)};
        when(itemManager.getStories(anyString(), anyInt())).thenReturn(items);

        final Pair<?, ?>[] received = new Pair[1];
        viewModel.getStories("top", ItemManager.MODE_DEFAULT)
                .observeForever(pair -> received[0] = pair);
        ShadowLooper.shadowMainLooper().idle();

        assertNotNull("LiveData should emit a value", received[0]);
        assertEquals(items, received[0].second);
    }

    @Test
    public void refreshStoriesBeforeGetStoriesIsNoOp() {
        viewModel.refreshStories("top", ItemManager.MODE_DEFAULT);
    }

    @Test
    public void onClearedDisposesWithoutError() {
        Item[] items = new Item[0];
        when(itemManager.getStories(anyString(), anyInt())).thenReturn(items);
        viewModel.getStories("top", ItemManager.MODE_DEFAULT);

        viewModel.onCleared();
    }

    @Test
    public void refreshStoriesAfterGetStoriesTriggersNewLoad() {
        Item[] initial = new Item[0];
        Item[] updated = new Item[]{mock(Item.class)};
        when(itemManager.getStories(anyString(), anyInt()))
                .thenReturn(initial)
                .thenReturn(updated);

        final Pair<?, ?>[] received = new Pair[1];
        viewModel.getStories("top", ItemManager.MODE_DEFAULT)
                .observeForever(pair -> received[0] = pair);
        ShadowLooper.shadowMainLooper().idle();

        viewModel.refreshStories("top", ItemManager.MODE_DEFAULT);
        ShadowLooper.shadowMainLooper().idle();

        assertNotNull(received[0]);
        assertEquals("after refresh, second array becomes current", updated, received[0].second);
        assertEquals("after refresh, initial array becomes previous", initial, received[0].first);
    }
}
