package io.github.mxmilkiib.materialistic.data;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 29)
public class FileDownloaderTest {

    private FileDownloader fileDownloader;
    private File cacheDir;

    @Before
    public void setUp() {
        Call.Factory callFactory = new OkHttpClient();
        cacheDir = RuntimeEnvironment.getApplication().getCacheDir();
        fileDownloader = new FileDownloader(RuntimeEnvironment.getApplication(), callFactory);
    }

    @Test
    public void urlWithTraversalCharactersSanitizedByFileNameExtraction() {
        String url = "http://example.com/../../etc/passwd";
        String fileName = new File(url).getName();
        assertEquals("File.getName() strips path components, preventing traversal",
                "passwd", fileName);
    }

    @Test
    public void cachedFileReturnsImmediately() throws Exception {
        File existing = new File(cacheDir, "cached.txt");
        existing.createNewFile();

        CountDownLatch latch = new CountDownLatch(1);
        String[] result = new String[1];

        fileDownloader.downloadFile("http://example.com/cached.txt", "text/plain",
                new FileDownloader.FileDownloaderCallback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        result[0] = "failure";
                        latch.countDown();
                    }

                    @Override
                    public void onSuccess(String filePath) {
                        result[0] = filePath;
                        latch.countDown();
                    }
                });

        ShadowLooper.runUiThreadTasks();
        assertTrue("Callback should fire immediately for cached file",
                latch.await(5, TimeUnit.SECONDS));
        assertTrue("Should return cached file path",
                existing.getPath().equals(result[0]));

        existing.delete();
    }

    @Test
    public void normalFileNameExtractedFromUrl() {
        String url = "http://example.com/document.pdf";
        String fileName = new File(url).getName();
        assertEquals("document.pdf", fileName);
    }
}
