package me.screamingvoid.sodiumuniversal;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.JavaScriptContextHolder;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = SodiumModule.NAME)
public class SodiumModule extends ReactContextBaseJavaModule {
  public static final String NAME = "Sodium";

  public SodiumModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @NonNull
  @Override
  public String getName() {
    return NAME;
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  public boolean install() {
    try {
      Log.i(NAME, "Loading C++ library...");
      System.loadLibrary("sodiumuniversal");

      JavaScriptContextHolder jsContext = getReactApplicationContext().getJavaScriptContextHolder();

      Log.i(NAME, "Installing JSI Bindings for sodium-universal...");
      nativeInstall(jsContext.get());
      Log.i(NAME, "Successfully installed JSI Bindings for sodium-universal!");

      return true;
    } catch (Exception exception) {
      Log.e(NAME, "Failed to install JSI Bindings for sodium-universal!", exception);
      return false;
    }
  }

  private static native void nativeInstall(long jsiPtr);
}
