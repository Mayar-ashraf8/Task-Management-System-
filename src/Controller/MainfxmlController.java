/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Task;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class MainfxmlController implements Initializable {

    @FXML
    private TextField namefield;
    @FXML
    private TextField titlefield;
    @FXML
    private DatePicker datepicker;
    @FXML
    private ComboBox<String> statusField;
    @FXML
    private Button adddtaskbtn;
    @FXML
    private Label totaltaskLabel;
    @FXML
    private Label openTaskNum;
    @FXML
    private Label closedTaskNum;
    @FXML
    private HBox addTaskSection;
    @FXML
    private ListView<Task> ListView;
    @FXML
    private TextField taskbynamefield;
    @FXML
    private Button showfirst4taskbtn;
    @FXML
    private Button findspecialtasksbtn;
    @FXML
    private Button counttaskbtn;
    @FXML
    private TextField usernametaskcountfield;
    @FXML
    private Label topuser;
    @FXML
    private MenuItem existbtn;
    @FXML
    private RadioMenuItem boldbtn;
    @FXML
    private RadioMenuItem italicbtn;
    @FXML
    private RadioMenuItem arialbtn;
    @FXML
    private RadioMenuItem timesnewbtn;
    @FXML
    private RadioMenuItem SansSerif;
    @FXML
    private RadioMenuItem smallbtn;
    @FXML
    private RadioMenuItem mediamfont;
    @FXML
    private RadioMenuItem LargeFont;
    @FXML
    private MenuItem aboutbtn;
    @FXML
    private ToggleGroup g1;
    @FXML
    private ToggleGroup g2;
    @FXML
    private ToggleGroup g3;
    @FXML
    private MenuBar menuBar;  
    Map<Integer,Task> taskMap=null;


    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        statusField.getItems().addAll("open", "closed");
           try {
    taskMap =Files.lines(Paths.get("src/data/task.csv")).skip(1)
            .map(line -> line.split(","))
            .map(data -> new Task(
                    Integer.parseInt(data[0]),
                    data[1], data[2], data[3],
                    LocalDate.parse(data[4].trim())))
            .collect(Collectors.toMap(Task::getId, t -> t));

    ListView.getItems().addAll(taskMap.values());
         } catch (IOException ex) {
    Logger.getLogger(MainfxmlController.class.getName()).log(Level.SEVERE, null, ex);

    }  
           useraddedhigesttasks();//the user has the higest number tasks
           openandclosedtasknNum();
           totalTasknum();
    
    }
//---------------------------------------------------------------------------------
    @FXML
    private void adddtaskbtnHandle(ActionEvent event) {
        String name=namefield.getText();
        String title=titlefield.getText();
       String Status= statusField.getValue();
       LocalDate date=datepicker.getValue();
       if(title.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Please fill the Title Field");
        alert.showAndWait();
        return;
       }else if(Status  == null){
          Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Please Choose a Status");
        alert.showAndWait();
        return;
       }else if(date == null){
         Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Please Choose a valid Date");
        alert.showAndWait();
        return;
       }else{
    Task newTask = new Task(taskMap.size() + 1, title, Status, name, date);
      taskMap.put(newTask.getId(), newTask);
      ListView.getItems().add(newTask);
        }
        useraddedhigesttasks();
        openandclosedtasknNum();
        totalTasknum();
    }
   //-------------------------------------------------------------------------------
    @FXML
    private void showfirst4taskbtnHandle(ActionEvent event) {
    List<Task> tasks = new ArrayList<>(taskMap.values());
    tasks.sort(Comparator.comparing(Task::getCreationdate));
    List<Task> earliestTasks = new ArrayList<>();
    for (int i = 0; i < tasks.size() && i < 4; i++) {
        earliestTasks.add(tasks.get(i));
    }
    ListView.getItems().setAll(earliestTasks);
}  
//-----------------------------------------------------------------------------------------
    @FXML
    private void findspecialtasksbtnHandle(ActionEvent event) {
    List<Task> tasks = new ArrayList<>(taskMap.values());
    List<Task> charatask = new ArrayList<>();
    for (Task t : tasks) {
        String title = t.getTitle();
        if (title != null && title.length() == 7 && title.toLowerCase().startsWith("a")) {
            charatask.add(t);
        }
    }
    ListView.getItems().setAll(charatask);
}
  //----------------------------------------------------------------------------------------  
   @FXML
    private void counttaskbtnHandle(ActionEvent event) {
      String userName = usernametaskcountfield.getText();
    int count = 0;
    for (Task t : taskMap.values()) {
        if (t.getPersonName().equalsIgnoreCase(userName)) {
            count++;
        }
    }
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Task Number");
    alert.setHeaderText(null);
    alert.setContentText(userName + " added " + count + " tasks.");
    alert.showAndWait();
    }
//----------------------------------------------------------------------------------
    @FXML
    private void taskbynamefieldHandle(ActionEvent event) {
         String username = taskbynamefield.getText();

    List<Task> title = taskMap.values().stream()
            .filter(t -> t.getPersonName().equalsIgnoreCase(username))
            .sorted(Comparator.comparing(Task::getId))
            .collect(Collectors.toList());

    ListView.getItems().setAll(title);
       
    }
   //----------------------------------------------------------------------------------
      private void useraddedhigesttasks() {
    List<Task> tasks = new ArrayList<>(taskMap.values());
    Map<String, Integer> userCounts = new HashMap<>();
    for (Task t : tasks) {
        String name = t.getPersonName();
        if (userCounts.containsKey(name)) {
            int oldCount = userCounts.get(name);
            userCounts.put(name, oldCount + 1);
        } else {
            userCounts.put(name, 1);
        }
    }
    String userhighentry = "";
    int max = 0;
    for (String name : userCounts.keySet()) {
        int count = userCounts.get(name);
        if (count > max) {
            max = count;
            userhighentry = name;
        }
    }
    topuser.setText("Top User: " + userhighentry);
}
  //----------------------------------------------------------------------------    
      private void totalTasknum(){
           int totaltask = taskMap.size();
    totaltaskLabel.setText("Total Tasks: " + totaltask);
}
  //------------------------------------------------------------------------------------    
    private void openandclosedtasknNum() {
    int opentask = 0;
    int closedtask = 0;

    for (Task t : taskMap.values()) {
        if (t.getStatus().equalsIgnoreCase("open")) {
            opentask++;
        } else if (t.getStatus().equalsIgnoreCase("closed")) {
            closedtask++;
        }
    }
    openTaskNum.setText("Open Tasks: " + opentask);
    closedTaskNum.setText("Closed Tasks: " + closedtask);
}
    @FXML
    private void existbtnHandle(ActionEvent event) {
            ((Stage) menuBar.getScene().getWindow()).close();

    }

    @FXML
    private void boldbtnHandle(ActionEvent event) {
         if (boldbtn.isSelected()) {
        menuBar.getScene().getRoot().setStyle("-fx-font-weight: bold;");
    }
    }

    @FXML
    private void italicbtnHandle(ActionEvent event) {
         if (italicbtn.isSelected()) {
        menuBar.getScene().getRoot().setStyle("-fx-font-style: italic;");
    }
    }

    @FXML
    private void arialbtnHandle(ActionEvent event) {
         if (arialbtn.isSelected()) {
        menuBar.getScene().getRoot().setStyle("-fx-font-family: 'Arial';");
    }
    }

    @FXML
    private void timesnewbtnHandle(ActionEvent event) {
           if (timesnewbtn.isSelected()) {
        menuBar.getScene().getRoot().setStyle("-fx-font-family: 'Times New Roman';");
    }
    }

    @FXML
    private void SansSerifHandle(ActionEvent event) {
          if (SansSerif.isSelected()) {
        menuBar.getScene().getRoot().setStyle("-fx-font-family: 'Sans Serif';");
    }
      
    }

    @FXML
    private void smallbtnHandle(ActionEvent event) {
          if (smallbtn.isSelected()) {
        menuBar.getScene().getRoot().setStyle("-fx-font-size: 10px;");
    }
    }

    @FXML
    private void mediamfontHandle(ActionEvent event) {
         if (mediamfont.isSelected()) {
        menuBar.getScene().getRoot().setStyle("-fx-font-size: 13px;");
    }
    }

    @FXML
    private void LargeFontHandle(ActionEvent event) {
          if (LargeFont.isSelected()) {
        menuBar.getScene().getRoot().setStyle("-fx-font-size: 15px;");
    }
    }

    @FXML
    private void aboutbtnHandle(ActionEvent event) {
        Alert about=new Alert(Alert.AlertType.INFORMATION);
      about.setTitle("About");
      about.setHeaderText("Task Managment System");
      about.setContentText("purpose:Organize and Managment Your Tasks\n Developer:Mayar Abo Alageen");
      about.showAndWait();
    
    }
      }
        
    
    
