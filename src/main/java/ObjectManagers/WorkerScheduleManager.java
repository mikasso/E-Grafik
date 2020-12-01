package ObjectManagers;

import Entities.Worker;
import Entities.WorkerSchedule;
import javafx.beans.property.SimpleStringProperty;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WorkerScheduleManager {
    private SimpleStringProperty workerName;
    private SimpleStringProperty workedHours;
    private SimpleStringProperty[] workDays;
    private SymbolsManager symbolsManager;
    private final boolean SUBTRACT = false;
    private final boolean ADD = true;

    public WorkerScheduleManager(Worker worker, int days, SymbolsManager symbolsManager)
    {
        this.symbolsManager = symbolsManager;
        this.workerName = new SimpleStringProperty(worker.toString());
        workedHours = new SimpleStringProperty("0");
        workDays = new SimpleStringProperty[days];
        for(int i = 0; i< days; i++)
            workDays[i] = new SimpleStringProperty("");
    }

    public void save(ObjectOutputStream destination) throws Exception
    {
        WorkerSchedule workerSchedule = new WorkerSchedule(workDays,workerName);
        destination.writeObject(workerSchedule);
    }

    public void load(ObjectInputStream source)throws Exception {
        WorkerSchedule workerSchedule = (WorkerSchedule) source.readObject();
        for(int i = 0; i< workerSchedule.daysValue.size(); i++)
        {
            setWorkDay(i, workerSchedule.daysValue.get(i));
        }
        this.workerName = new SimpleStringProperty(workerSchedule.workerName);
    }

    public String getWorkDay(int i){
        return workDays[i].get();
    }

    public SimpleStringProperty getWorkDayProperty(int i){
        return workDays[i];
    }

    public void setWorkDay(int i, String val)
    {
        if(workDays[i].getValue().length()>0) {
            char oldChar = workDays[i].get().charAt(0) ;
            countWorkedHours(oldChar ,SUBTRACT);
        }
        workDays[i].set(val);
        if(val.length()>0)
            countWorkedHours(val.charAt(0) ,ADD);
    }

    private void countWorkedHours(char symbol, boolean add) {
        int multiply = add ? 1:-1;
        Integer sum = Integer.parseInt(workedHours.get());
        sum += multiply * symbolsManager.getHoursWorked(symbol);
        setWorkedHours(sum.toString());
    }


    public String getWorkerName(){
        return workerName.get();
    }

    public String getWorkedHours() {
        return workedHours.get();
    }

    public SimpleStringProperty workedHoursProperty() {
        return workedHours;
    }

    public void setWorkedHours(String workedHours) {
        this.workedHours.set(workedHours);
    }
}
