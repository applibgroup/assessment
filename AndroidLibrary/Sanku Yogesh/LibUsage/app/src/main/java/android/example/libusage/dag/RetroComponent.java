package android.example.libusage.dag;

import android.example.libusage.MainActivityViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RetroModule.class})
public interface RetroComponent {

    public void inject(MainActivityViewModel mainActivityViewModel);

}
