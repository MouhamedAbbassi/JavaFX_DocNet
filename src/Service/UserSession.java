package Service;

import Entity.User;
 
public class UserSession 
{    
  public static String userEmail;
  private final ServiceUser ServiceUser = new ServiceUser();

    public String getUserEmail() {return userEmail;}

    public void setUserEmail(String userEmail)
    {UserSession.userEmail = userEmail;}
    
    public User getUser()
    {return ServiceUser.GetUserByMailSession(userEmail);}
}
