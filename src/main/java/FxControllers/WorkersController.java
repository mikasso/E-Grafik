package FxControllers;

import Entities.Worker;
import ObjectManagers.WorkersManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.util.ArrayList;

public class WorkersController extends SuitedController{
    private WorkersManager workersManager;
    private ObservableList<Worker> workersList;
    private ObservableList<String> workersNames;
    @FXML
    ListView<String> workersListView;
    @FXML
    TextField textName;
    @FXML
    TextField textLastName;
    @Override
    public void loadDataFromRootController() {
        this.workersManager = rootController.getWorkersManager();
        this.workersList = workersManager.getWorkers();
        workersNames = FXCollections.observableList(new ArrayList<String>());
        workersList.forEach( w -> {
                    workersNames.add(w.toString());
                }
        );
        workersListView.setItems(workersNames);
    }
    @FXML
    public void onEnter(ActionEvent ae) {
       workersManager.getWorkers().add(new Worker(textName.getText(), textLastName.getText()));
       workersNames.add(workersList.get(workersList.size()-1).toString());
    }

    public void removeWorker(ActionEvent event) {
       String toRemove =  workersListView.getSelectionModel().getSelectedItem();
       workersNames.remove(toRemove);
        String[] strings = toRemove.split(" ");
       workersList.removeIf( worker -> {
           return worker.getName().equals(strings[0]) && worker.getLastName().equals(strings[1]);
       });
    }
}
