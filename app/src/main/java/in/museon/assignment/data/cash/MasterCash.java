package in.museon.assignment.data.cash;

import java.util.ArrayList;

import in.museon.assignment.data.domian.User;

/**
 * The type Master cash.
 * Master cash is class using for temporary  storage of data
 * help to compress loading time and also avoiding  server call
 *
 * @author dev.cobb
 * @version 1.0
 * @since 19 December 2017
 *
 *
 */
public class MasterCash {

public  static ArrayList<User> userArrayList=new ArrayList<>();

public  void  clearAllMasterCash(){
    userArrayList=new ArrayList<>();
}
}
