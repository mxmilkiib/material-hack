package io.github.mxmilkiib.materialistic.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 29)
public class RestServiceFactoryTest {

    @Test
    public void rxEnabledReturnsNewInstance() {
        Call.Factory callFactory = new OkHttpClient();
        RestServiceFactory.Impl original = new RestServiceFactory.Impl(callFactory);
        RestServiceFactory derived = original.rxEnabled(true);

        assertNotSame("rxEnabled should return a new instance, not mutate original", original, derived);
    }

    @Test
    public void rxEnabledDoesNotMutateOriginal() {
        Call.Factory callFactory = new OkHttpClient();
        RestServiceFactory.Impl original = new RestServiceFactory.Impl(callFactory);
        RestServiceFactory derived = original.rxEnabled(true);
        RestServiceFactory derived2 = original.rxEnabled(true);

        assertNotSame("rxEnabled should return new instances each time", derived, derived2);
        assertNotSame("neither derived should be the same as original", original, derived);
    }

    @Test
    public void createReturnsServiceInstance() {
        Call.Factory callFactory = new OkHttpClient();
        RestServiceFactory factory = new RestServiceFactory.Impl(callFactory);
        TestService service = factory.create("https://example.com/", TestService.class);
        assertTrue("create should return a non-null proxy", service != null);
    }

    @Test
    public void rxEnabledCreateReturnsServiceInstance() {
        Call.Factory callFactory = new OkHttpClient();
        RestServiceFactory factory = new RestServiceFactory.Impl(callFactory).rxEnabled(true);
        TestService service = factory.create("https://example.com/", TestService.class);
        assertTrue("create with rxEnabled should return a non-null proxy", service != null);
    }

    @Test
    public void createWithExecutorReturnsServiceInstance() {
        Call.Factory callFactory = new OkHttpClient();
        RestServiceFactory factory = new RestServiceFactory.Impl(callFactory);
        TestService service = factory.create("https://example.com/", TestService.class,
                Runnable::run);
        assertTrue("create with executor should return a non-null proxy", service != null);
    }

    interface TestService {
    }
}
