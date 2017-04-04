/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labo.smtp;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Rhod3
 */
public class SMTPSpamGroup {

    private final String sender;
    private LinkedList<String> receivers = new LinkedList<>();
    
    public SMTPSpamGroup(String s, String ... args){
        sender = s;
        receivers.addAll(Arrays.asList(args));
    }
    
    public String getSender() {
        return sender;
    }

    public LinkedList<String> getReceivers() {
        return receivers;
    }   
}
