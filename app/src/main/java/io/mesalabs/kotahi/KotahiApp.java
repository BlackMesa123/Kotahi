package io.mesalabs.kotahi;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

import java.util.Locale;

public class KotahiApp extends Application implements Client.ResultHandler, Client.ExceptionHandler {

    private static Context mContext;
    private static Client mClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mClient = Client.create(this, this, this);
        initParameters(this);
        initEncryption(this);
    }

    private void initParameters(Client.ResultHandler resultHandler) {
        TdApi.TdlibParameters parameters = new TdApi.TdlibParameters();
        parameters.apiId = BuildConfig.TD_API_ID;
        parameters.apiHash = BuildConfig.TD_API_HASH;
        parameters.databaseDirectory = mContext.getFilesDir() + "/telegram";
        parameters.filesDirectory = mContext.getCacheDir() + "/files"; // TODO
        parameters.systemLanguageCode = Locale.getDefault().getLanguage();
        parameters.deviceModel = Build.MODEL;
        parameters.applicationVersion = BuildConfig.VERSION_NAME;
        parameters.systemVersion = Build.VERSION.RELEASE;
        parameters.useMessageDatabase = true;
        parameters.useSecretChats = true;
        parameters.enableStorageOptimizer = true;

        mClient.send(new TdApi.SetTdlibParameters(parameters), resultHandler);
    }

    private void initEncryption(Client.ResultHandler resultHandler) {
        mClient.send(new TdApi.CheckDatabaseEncryptionKey(), resultHandler);
    }

    public static void authenticate(String number, Client.ResultHandler resultHandler) {
        mClient.send(new TdApi.SetAuthenticationPhoneNumber(number, null), resultHandler);
    }

    public static void confirmCode(String code, Client.ResultHandler resultHandler) {
        mClient.send(new TdApi.CheckAuthenticationCode(code), resultHandler);
    }

    public static void registerUser(Client.ResultHandler resultHandler) {
        mClient.send(new TdApi.RegisterUser("first", "last"), resultHandler); // TODO
    }

    @Override
    public void onResult(TdApi.Object object) {
        Log.e("TD", object.toString());
    }

    @Override
    public void onException(Throwable e) {
        Log.e("TD", e.getMessage(), e);
    }
}
