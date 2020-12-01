package FxControllers;

import Entities.Symbol;
import ObjectManagers.SymbolsManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class SymbolsMenuController extends SuitedController {
    public TextField symbolChar;
    public TextField symbolValue;
    public TextField colorValue;
    public ListView<String> symbolsListView;

    private SymbolsManager symbolsManager;
    private ObservableList<Symbol> symbolsList;
    private ObservableList<String> symbolsNames;
    @Override
    public void loadDataFromRootController() {
        Controller.addTextLimiter(symbolChar,1);
        this.symbolsManager = rootController.getSymbolManager();
        this.symbolsList = symbolsManager.getSymbols();
        symbolsNames = FXCollections.observableList(new ArrayList<>());
        symbolsList.forEach( s -> {
                    symbolsNames.add(s.toString());
                }
        );
        symbolsListView.setItems(symbolsNames);
    }

    public void addSymbol(ActionEvent event) {
        char c = symbolChar.getText().charAt(0);
        int v = Integer.parseInt(symbolValue.getText());
        String color = colorValue.getText();
        symbolsManager.getSymbols().add(new Symbol(c,v,color));
        symbolsNames.add(symbolsList.get(symbolsList.size()-1).toString());
    }

    public void deleteSymbol(ActionEvent event) {
        String toRemove =  symbolsListView.getSelectionModel().getSelectedItem();
        symbolsNames.remove(toRemove);
        symbolsList.removeIf( s -> {
            return s.toString().equals(toRemove);
        });
    }
}
