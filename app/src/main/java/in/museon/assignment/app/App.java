package in.museon.assignment.app;

import android.app.Application;

import in.museon.assignment.data.cash.MasterCash;
import in.museon.assignment.data.preferences.AppPreference;
import in.museon.assignment.data.rest.ApiClient;
import in.museon.assignment.data.rest.ServiceGenerator;
import in.museon.assignment.service.navigation.Navigator;
import in.museon.assignment.util.Util_Factory;

/**
 * The Application is Base class for maintaining global application state.
 * Application class, is instantiated before any other class when the process
 * for your application/package is created.
 *
 * @author Binush
 * @version 1.0
 * @since 11 06 2017
 */
public class App extends Application {
    /**
     * instance of application class
     */
    private static App instance = null;
    /**
     * instance of Navigator class
     */
    private static Navigator navigator = null;


    /**
     * instance of Preference class
     */
    private static AppPreference appPreference = null;

    /**
     * instance of apiClient
     */
    private static ApiClient apiClient = null;



    private  static Util_Factory util_factory=null;

    private  static MasterCash masterCash=null;

    /**
     * This method is inherited from application class working
     * before any method of this class as well ass all program
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;


    }

    /**
     * Gets the application context.
     *
     * @return the application context
     */
    public static synchronized App getContext() {
        if (instance == null) {
            synchronized (App.class) {
                if (instance == null) {
                    instance = new App();
                }
            }
        }
        return instance;
    }

    /**
     * Gets the navigator.
     *
     * @return the navigator singleton instance
     */
    public static Navigator getNavigator() {
        if (navigator == null) {
            navigator = new Navigator();
        }
        return navigator;
    }


    /**
     * Gets the navigator.
     *
     * @return the navigator singleton instance
     */
    public static AppPreference getAppPreference() {
        if (appPreference == null) {
            appPreference = new AppPreference();
        }
        return appPreference;
    }


    /**
     * Gets the ApiClient.
     *
     * @return the ApiClient singleton instance
     */
    public static ApiClient getApiClient() {
        if (apiClient == null) {
            ServiceGenerator serviceGenerator=new ServiceGenerator();
            apiClient = serviceGenerator.createService(ApiClient.class);
        }
        return apiClient;
    }

    public static Util_Factory getUtil_factory() {
        if (util_factory == null) {
            util_factory=new Util_Factory();
        }
        return util_factory;
    }
    public static MasterCash getMasterCash(){
        if(masterCash==null){
            masterCash =new MasterCash();
        }
        return masterCash;
    }




}