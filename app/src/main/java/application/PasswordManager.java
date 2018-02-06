package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import user.Client;

/**
 * Created by zuoyuzhang on 2015-12-01.
 */
public class PasswordManager implements Serializable{

    private TreeMap<String,String[]> contents;

    public PasswordManager(){
        this.contents = new TreeMap<String,String[]>();
    }
    public PasswordManager(File fileName) throws FileNotFoundException{
        this.contents = new TreeMap<String,String[]>();
        Scanner scanner = new Scanner(new FileInputStream(fileName));
        String[] record;
        while (scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            String key = record[0];
            String[] value = {record[1], record[2]};
            this.contents.put(key,value);
        }

    }
    public void readPasswordFile(File fileName)throws FileNotFoundException{
        Scanner scanner = new Scanner(new FileInputStream(fileName));
        String[] record;
        while (scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            String key = record[0];
            String[] value = {record[1], record[2]};
            this.contents.put(key,value);
        }
    }

    public String passwordMatch(String username, String password){
        if (this.contents.containsKey(username)){
            String[] value = this.contents.get(username);
            String pwd = value[0];
            if(pwd.equals(password)){
                return value[1];
            }
            else{
                return "Invalid password!";
            }
        }
        else{
            return "Invalid username!";
        }
    }

}
