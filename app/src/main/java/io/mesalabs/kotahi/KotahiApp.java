package io.mesalabs.kotahi;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KotahiApp extends Application implements Client.ResultHandler, Client.ExceptionHandler {

    private static Context mContext;
    private static Client mClient;
    private static List<TdListener> mTdListeners;

    public interface TdListener {
        void onClientResult(TdApi.Object object);

        void onClientException(Throwable e);
    }

    @Override
    public void onResult(TdApi.Object object) {
        if (mTdListeners != null) {
            for (TdListener tdListener : mTdListeners) tdListener.onClientResult(object);
        }
    }

    @Override
    public void onException(Throwable e) {
        if (mTdListeners != null) {
            for (TdListener tdListener : mTdListeners) tdListener.onClientException(e);
        }
    }

    public static boolean addTdListener(TdListener tdListener) {
        if (mTdListeners == null) mTdListeners = new ArrayList<>();
        return mTdListeners.add(tdListener);
    }

    public static boolean removeTdListener(TdListener tdListener) {
        if (mTdListeners == null || !mTdListeners.contains(tdListener)) return false;
        return mTdListeners.remove(tdListener);
    }

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
        parameters.databaseDirectory = mContext.getDataDir().getPath() + "/databases";
        parameters.filesDirectory = mContext.getFilesDir().getPath();
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

    //Auth
    public static void authenticate(String number, Client.ResultHandler resultHandler) {
        mClient.send(new TdApi.SetAuthenticationPhoneNumber(number, null), resultHandler);
    }

    public static void confirmCode(String code, Client.ResultHandler resultHandler) {
        mClient.send(new TdApi.CheckAuthenticationCode(code), resultHandler);
    }

    public static void registerUser(String firstName, String lastName, Client.ResultHandler resultHandler) {
        mClient.send(new TdApi.RegisterUser(firstName, lastName), resultHandler);
    }

    public static void getUser(Client.ResultHandler resultHandler) {
        mClient.send(new TdApi.GetUser(), resultHandler);
    }


    public static Client getClient() {
        return mClient;
    }
}
