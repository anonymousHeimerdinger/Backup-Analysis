/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ggilavbe7.alumnes
 */
public class Usuari {
    private String user, password;
    
    public Usuari(){
        this.user = "";
        this.password = "";
    }

    public Usuari(String nomUsuari, String password) {
        this.user = nomUsuari;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
