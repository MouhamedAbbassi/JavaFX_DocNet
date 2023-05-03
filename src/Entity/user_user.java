/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author asus
 */
public class user_user {
    private int user_source;
    private int user_target;

    public user_user() {
    }

    public user_user(int user_source, int user_target) {
        this.user_source = user_source;
        this.user_target = user_target;
    }

    public int getUser_source() {
        return user_source;
    }

    public void setUser_source(int user_source) {
        this.user_source = user_source;
    }

    public int getUser_target() {
        return user_target;
    }

    public void setUser_target(int user_target) {
        this.user_target = user_target;
    }
    
}
