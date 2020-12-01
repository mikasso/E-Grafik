package FxHandlers;


import ObjectManagers.SymbolsManager;
import ObjectManagers.WorkerScheduleManager;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class TableDayColumn extends TableColumn<WorkerScheduleManager,String> {
    public TableDayColumn(String name, int day, SymbolsManager symbolsManager) {
        super(name);
        this.setCellFactory(TextFieldTableCell.<WorkerScheduleManager>forTableColumn());
        this.setCellFactory(tc -> new TextFieldTableCell<WorkerScheduleManager, String>(TextFormatter.IDENTITY_STRING_CONVERTER) {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!isEmpty() && item.length() == 1) {
                    Color color = symbolsManager.getColor(item);
                    this.setTextFill(color);
                    setText(item);
                    this.setStyle("-fx-font-weight: bold;");
                }else
                    setText("");
            }
        });

        this.setCellValueFactory(new Callback<CellDataFeatures<WorkerScheduleManager, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<WorkerScheduleManager, String> w) {
                return w.getValue().getWorkDayProperty(day - 1);
            }
        });
        this.setOnEditCommit((CellEditEvent<WorkerScheduleManager, String> t) -> {
            ((WorkerScheduleManager) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
            ).setWorkDay(t.getTablePosition().getColumn() - 1, t.getNewValue());
        });
        this.setPrefWidth(40.0);
    }


}
