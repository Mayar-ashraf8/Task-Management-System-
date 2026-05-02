/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author hp
 */
public class MainApp extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
      Parent p = FXMLLoader.load(getClass().getResource("/view/Mainfxml.fxml"));
      Scene sc=new Scene(p);
      sc.getStylesheets().add(getClass().getResource("/Css/style.css").toExternalForm());
      primaryStage.setScene(sc);
      primaryStage.setWidth(1100);
      primaryStage.setHeight(600);
      primaryStage.setTitle("Task Managment System");
      primaryStage.show();        
    }

    
  public static void main(String[] args) {
           launch(args);    
}
}