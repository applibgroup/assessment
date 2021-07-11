// Generated by Dagger (https://dagger.dev).
package android.example.libusage.dag;

import com.google.gson.Gson;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import retrofit2.converter.gson.GsonConverterFactory;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class RetroModule_GsonConverterFactoryFactory implements Factory<GsonConverterFactory> {
  private final RetroModule module;

  private final Provider<Gson> gsonProvider;

  public RetroModule_GsonConverterFactoryFactory(RetroModule module, Provider<Gson> gsonProvider) {
    this.module = module;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public GsonConverterFactory get() {
    return gsonConverterFactory(module, gsonProvider.get());
  }

  public static RetroModule_GsonConverterFactoryFactory create(RetroModule module,
      Provider<Gson> gsonProvider) {
    return new RetroModule_GsonConverterFactoryFactory(module, gsonProvider);
  }

  public static GsonConverterFactory gsonConverterFactory(RetroModule instance, Gson gson) {
    return Preconditions.checkNotNullFromProvides(instance.gsonConverterFactory(gson));
  }
}