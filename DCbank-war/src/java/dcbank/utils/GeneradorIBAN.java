/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcbank.utils;

import java.util.Random;

/**
 *
 * @author Pedro Avila
 */
public class GeneradorIBAN {
    
    public static String generadorIBAN(){
        String iban = "ES21";
        
        Random r = new Random();

        for(int i=0; i<20; i++){
            iban += r.nextInt(10);
            
        }
        
        return iban;
    }
}
