package uz.nt.userservice.config;

// Java code to explain how to generate random
// password

// Here we are using random() method of util
// class in Java
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PasswordGenerator
{


    public String geek_Password()
    {

        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";


        String values = Capital_chars + Small_chars +
                numbers + symbols;

        Random rndm_method = new Random();

        char[] password = new char[6];

        for (int i = 0; i < 6; i++)
        {

            password[i] =
                    values.charAt(rndm_method.nextInt(values.length()));

        }
        return password.toString();
    }
}
