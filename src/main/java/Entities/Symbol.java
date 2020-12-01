package Entities;

import java.io.Serializable;

public class Symbol implements Serializable {
    public Symbol(){};
    public Symbol(char c,int hoursValue, String color)
    {
        this.character = c;
        this.hoursValue = hoursValue;
        this.color = color;
    }
    public String toString(){
        return character+" "+hoursValue+" "+color;
    }
    public char character;
    public int hoursValue;
    public String color;

}
