package FxHandlers;

import Entities.Worker;
import ObjectManagers.DateManager;
import ObjectManagers.SymbolsManager;
import ObjectManagers.WorkerScheduleManager;
import ObjectManagers.WorkersManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.GregorianCalendar;

public class ScheduleTab extends Tab {
    private  Year year;
    private  Month month;
    private GridPane grid;
    private Integer days;
    private TableView tableView;
    private SymbolsManager symbolsManager;
    private String title;
    private final ObservableList<WorkerScheduleManager> schedules  = FXCollections.observableArrayList();

    public ScheduleTab(Month month, Year year,String title, WorkersManager workersManager, SymbolsManager symbolsManager) {
        super();
        this.title = title;
        setText(title);
        this.days = month.length(year.isLeap());
        this.month = month;
        this.year = year;
        this.symbolsManager = symbolsManager;
        for(Worker w: workersManager.getWorkers())
        {
            schedules.add(new WorkerScheduleManager(w,days,symbolsManager));
        }
        createLayout();
        tableView.setStyle(".table-row-cell:hover {\n" +
                "  -fx-background-color: lightgoldenrodyellow ;\n" +
                "}");
    }


    private void createLayout() {
        tableView = new TableView();
        tableView.setEditable(true);
        TableColumn nameColumn = new TableColumn("Imię");
        nameColumn.setCellValueFactory( new PropertyValueFactory<WorkerScheduleManager,String>("workerName"));
        tableView.getColumns().add(nameColumn);
        for(Integer i=1;i<= days;i++)
        {
            TableDayColumn tableColumn = new TableDayColumn(getDayOfWeek(i),i,symbolsManager);
            tableView.getColumns().add(tableColumn);
        }
        TableColumn sumColumn = new TableColumn("Ilość godzin");
        sumColumn.setCellValueFactory(new PropertyValueFactory<WorkerScheduleManager,String>("workedHours"));
        tableView.getColumns().add(sumColumn);
        tableView.setItems(schedules);

        BorderPane pane = new BorderPane();
        pane.setCenter(tableView);
        this.setContent(pane);
    }

    private String getDayOfWeek(Integer i) {
        Date date = new GregorianCalendar(year.getValue(), month.getValue(), i).getTime();
       return i.toString()+" "+DateManager.getDayStringOld(date);
    }

    public static ScheduleTab  Load(ObjectInputStream source, WorkersManager workersManager,
                                    SymbolsManager symbolsManager) throws Exception {
        String title = (String) source.readObject();
        Month month = (Month) source.readObject();
        Year year = (Year) source.readObject();
        ScheduleTab scheduleTab = new ScheduleTab(month,year,title,workersManager,symbolsManager);
        for(WorkerScheduleManager w : scheduleTab.schedules)
        {
            w.load(source);
        }
        return scheduleTab;
    }


    public void save(ObjectOutputStream destination) throws Exception
    {
        destination.writeObject(title);
        destination.writeObject(month);
        destination.writeObject(year);
        for(WorkerScheduleManager w : schedules)
        {
            w.save(destination);
        }
    }
}
