package Entities;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class WorkerSchedule implements Serializable {
    public ArrayList<String> daysValue;
    public String workerName;
    public WorkerSchedule(){}
    public WorkerSchedule(SimpleStringProperty [] daysValue, SimpleStringProperty workerName){
        this.daysValue = new ArrayList<String>();
        for(SimpleStringProperty p : daysValue)
            this.daysValue.add(p.getValue());
       this.workerName = workerName.getValue();
    }
}
