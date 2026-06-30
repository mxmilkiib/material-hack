package io.github.mxmilkiib.materialistic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FullscreenViewModel extends ViewModel {
    private final MutableLiveData<Boolean> mFullscreen = new MutableLiveData<>(false);

    public LiveData<Boolean> getFullscreen() {
        return mFullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        mFullscreen.setValue(fullscreen);
    }
}
