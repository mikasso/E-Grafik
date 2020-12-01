package FxControllers;

import FxHandlers.ScheduleTab;
import ObjectManagers.SymbolsManager;
import ObjectManagers.WorkersManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public class FXML_PATHS
    {
        public static final String SYMBOLS_MENU = "../SymbolsMenu.fxml";
        public static final String MONTH_REQUEST = "../NewMonthRequest.fxml";
        public static final String WORKERS_LIST = "../WorkersList.fxml";
    }
    static final String WORKERS_TXT = "Workers.txt";
    static final String SYMBOLS_TXT = "Symbols.txt";

    @FXML
    private TabPane monthsTabPane;
    private ResourceBundle bundle;
    private WorkersManager workersManager;
    private SymbolsManager symbolManager;
    private final LocalDate currentDate = LocalDate.now();
    private ScheduleTab topScheduleTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        workersManager = new WorkersManager(WORKERS_TXT);
        symbolManager = new SymbolsManager(SYMBOLS_TXT);
        bundle = resources;
    }

    public void showAddNewMonth(ActionEvent event) {
       displayNewFXWindow(FXML_PATHS.MONTH_REQUEST);
    }

    public void showWorkersList(ActionEvent event) {
        displayNewFXWindow(FXML_PATHS.WORKERS_LIST);
    }

    public void showSymbolsSettings(ActionEvent event) {
        displayNewFXWindow(FXML_PATHS.SYMBOLS_MENU);
    }

    public void displayNewFXWindow(String fxmlFilePath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilePath), bundle);
            Parent root = (Parent) fxmlLoader.load();
            SuitedController newWindowController = fxmlLoader.getController();
            newWindowController.setRootController(this);
            newWindowController.loadDataFromRootController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error during opening FXML file "+fxmlFilePath);
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public void chooseMonthFileToSave(ActionEvent event) {
        if(topScheduleTab == null)
            return;
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            saveMonth(file);
        }
    }

    public void chooseMonthFileToLoad(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            loadMonth(file);
        }
    }

    public void loadMonth(File file)
    {
        String path = file.getAbsolutePath();
        try(FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis))
        {
           workersManager.load(ois);
           symbolManager.load(ois);
           topScheduleTab = ScheduleTab.Load(ois,workersManager,symbolManager);
           monthsTabPane.getTabs().add(topScheduleTab);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveMonth(File file) {
        String path = file.getAbsolutePath();
        topScheduleTab = (ScheduleTab) monthsTabPane.getSelectionModel().getSelectedItem();
        try (
                FileOutputStream fileOut = new FileOutputStream(path);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);)
        {
                workersManager.save(out);
                symbolManager.save(out);
                topScheduleTab.save(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WorkersManager getWorkersManager() {
        return workersManager;
    }


    public SymbolsManager getSymbolManager() {
        return symbolManager;
    }

    public void addScheduleTab (ScheduleTab tab) {
        topScheduleTab = tab;
        monthsTabPane.getTabs().add(tab);
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }
}
