
package in.museon.assignment.data.preferences;


import in.museon.assignment.data.domian.User;

/**
 * The class BasicAppPreference
 *
 */
public class AppPreference {
private  final String PREF_USERDATA="user_data";
    private  final String PREF_IS_LOGGED="user_logged";
    /**
     * The Preference wrapper instance.
     *
     */
    private final PreferenceWrapper pWrapper = new PreferenceWrapper();

public void setUserData(User userData){
    pWrapper.putObject(PREF_USERDATA,userData);
}

    public User getUserData(){
       return  pWrapper.getObject(PREF_USERDATA,User.class);
    }



    public  void isLogged(Boolean status){
        pWrapper.putBooleanPref(PREF_IS_LOGGED,status);
    }
    public  boolean isLogged(){
       return pWrapper.getBooleanPref(PREF_IS_LOGGED,false);
    }



}


