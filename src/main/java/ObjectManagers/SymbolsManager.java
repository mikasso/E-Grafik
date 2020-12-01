package ObjectManagers;

import Entities.Symbol;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;

public class SymbolsManager {
    private ObservableList<Symbol> symbols;
    private String symbolsFile;
    public SymbolsManager(String filename){
        this.symbolsFile = filename;
        symbols = FXCollections.observableList(new ArrayList<Symbol>());
        symbols.addListener((ListChangeListener.Change<? extends Symbol> c) -> {this.save();} );
        load();
    }

    public void save(){
        try (
                FileOutputStream fileOut = new FileOutputStream(symbolsFile);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);) {
            out.writeObject(new ArrayList<>(symbols));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(){
        try(FileInputStream fis = new FileInputStream(symbolsFile);
            ObjectInputStream ois = new ObjectInputStream(fis))
        {
            symbols.addAll( (ArrayList<Symbol>)ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Symbol> getSymbols() {
        return symbols;
    }

    public Color getColor(String item)
    {
        if(item.length()==0)
            return Color.RED;
        char c = item.charAt(0);
        for(Symbol s: symbols)
        {
            if(c == s.character)
                return Color.valueOf(s.color);
        }
        return Color.RED;
    }

    public int getHoursWorked(char c)
    {
        for(Symbol s:  symbols){
            if(s.character == c)
               return s.hoursValue;
        }
        return 0;
    }

    public void save(ObjectOutputStream out) throws Exception {
        out.writeObject(new ArrayList<>(symbols));
    }

    public void load(ObjectInputStream ois) throws Exception{
        symbols.clear();
        symbols.addAll( (ArrayList<Symbol>)ois.readObject());
    }
}
