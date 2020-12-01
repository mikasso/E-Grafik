package FxControllers;

import FxHandlers.ScheduleTab;
import Entities.Worker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewMonthRequestController extends SuitedController implements Initializable {
    @FXML
    ChoiceBox<Integer> yearsChoiceBox;
    @FXML
    ChoiceBox<String> monthChoiceBox;
    @FXML
    TextField scheduleName;
    final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    private ResourceBundle resources;
    private OnChangedDialogChoice dialogChoiceListener;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        dialogChoiceListener = new OnChangedDialogChoice(monthChoiceBox,yearsChoiceBox,scheduleName);
        String[] monthsList = getMonthsListFromResources();
        initMonthChoiceBox(monthsList);
        initScheduleName();
        initYearsChoiceBox();
        dialogChoiceListener.setDeafultName();
    }

    private void initMonthChoiceBox(String [] monthsArray){
        ObservableList<String> months = FXCollections.observableList(new ArrayList<String>());
        months.addAll(monthsArray);
        monthChoiceBox.setItems(months);
        int nextMonthID = getNextMonthID();
        monthChoiceBox.getSelectionModel().select(nextMonthID);
        monthChoiceBox.getSelectionModel().selectedItemProperty().addListener(dialogChoiceListener);
    }

    private void initYearsChoiceBox() {
        ObservableList<Integer> years = FXCollections.observableList(new ArrayList<Integer>());
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        years.addAll(getListOfYears(currentYear,currentYear+10));
        yearsChoiceBox.setItems(years);
        selectYearOfNextMonth(currentDate);
        yearsChoiceBox.getSelectionModel().selectedItemProperty().addListener(dialogChoiceListener);
    }

    private void initScheduleName() {
        scheduleName.setText("");
        scheduleName.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if(!scheduleName.getText().matches("[aA-zZ]\\\\w{5,29}")){
                    scheduleName.pseudoClassStateChanged(errorClass, true);
                }
                else
                    scheduleName.pseudoClassStateChanged(errorClass, false);
            }
        });
    }

    private void selectYearOfNextMonth(LocalDate date){
        if(date.getMonth() == Month.DECEMBER)
            yearsChoiceBox.getSelectionModel().select(1);
        else
            yearsChoiceBox.getSelectionModel().select(0);
    }

    private String[] getMonthsListFromResources(){
        String monthsInOneString = resources.getString("months.names");
        return  (monthsInOneString.split(" "));
    }

    private int getNextMonthID()
    {
        final int MONTH_MAX = 12;
        int index = ((LocalDate.now().getMonth().getValue() ) % MONTH_MAX);
        return index;
    }

    private ArrayList<Integer> getListOfYears(int from, int to){
        ArrayList<Integer> years = new ArrayList<>();
        for(int i=from; i<to;i++)
            years.add(i);
        return years;
    }

    public void createSchedule(ActionEvent event) {
        if( isScheduleNameValid() && isMonthChosen() )
        {
            int monthNr = monthChoiceBox.getSelectionModel().getSelectedIndex();
            Integer year = yearsChoiceBox.getSelectionModel().getSelectedItem();
            Month month = Month.values()[monthNr];
            ObservableList<Worker> workers = rootController.getWorkersManager().getWorkers();
            String title = (scheduleName.getText());
            ScheduleTab tab = new ScheduleTab(month, Year.of(year), title, rootController.getWorkersManager(), rootController.getSymbolManager());
            rootController.addScheduleTab(tab);
            Stage stage = (Stage) monthChoiceBox.getScene().getWindow();
            stage.close();
        }else{
        }
    }

    private boolean isScheduleNameValid(){
        String name = scheduleName.getText();
        //TODO
        return true;
    }

    private boolean isMonthChosen(){
        return monthChoiceBox.getSelectionModel().getSelectedItem() != null;
    }

    public class OnChangedDialogChoice implements ChangeListener {
        ChoiceBox<String>monthChoiceBox;
        ChoiceBox <Integer> yearsChoiceBox;
        TextField scheduleName;
        OnChangedDialogChoice(ChoiceBox<String> monthChoiceBox, ChoiceBox<Integer> yearsChoiceBox, TextField scheduleName)
        {
            this.monthChoiceBox = monthChoiceBox;
            this.yearsChoiceBox = yearsChoiceBox;
            this.scheduleName = scheduleName;
        }
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            scheduleName.setText(monthChoiceBox.getValue()+"_"+yearsChoiceBox.getValue().toString());
        }

        public void setDeafultName() {
            changed(null,null,null);
        }
    }

}
