/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isdcm.DTO;

import java.io.Serializable;

/**
 *
 * @author Alvaro
 */
public class UserDTO  implements Serializable{
    String name;
    String surname;
    String email;
    String username;
    int numVidUploaded;
    
    public UserDTO(String name, String surname, String email, String username){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.numVidUploaded = 0;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getSurname(){
        return this.surname;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public int getNumVidUploaded(){
        return this.numVidUploaded;
    }
    
    public void setNumVidUploaded(int numVidUploaded){
        this.numVidUploaded = numVidUploaded;
    }
}
