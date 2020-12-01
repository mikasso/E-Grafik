package ObjectManagers;

import Entities.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class WorkersManager {
    private ObservableList<Worker> workers;
    private String workersFileName;
    public WorkersManager(String workersFileName){
        this.workersFileName = workersFileName;
        workers = FXCollections.observableList(new ArrayList<Worker>());
        workers.addListener((ListChangeListener.Change<? extends Worker> c) -> {this.save();} );
        load();
    }

    public void save(){
        try (
                FileOutputStream fileOut = new FileOutputStream(workersFileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);) {
                out.writeObject(new ArrayList<>(workers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(){
        try(FileInputStream fis = new FileInputStream(workersFileName);
            ObjectInputStream ois = new ObjectInputStream(fis))
        {
            workers.addAll( (ArrayList<Worker>)ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Worker> getWorkers() {
        return workers;
    }

    public void save(ObjectOutputStream out) throws Exception {
        out.writeObject(new ArrayList<>(workers));
    }

    public void load(ObjectInputStream ois) throws Exception{
        workers.clear();
        workers.addAll( (ArrayList<Worker>)ois.readObject());
    }
}
