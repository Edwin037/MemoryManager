/**@author Edwin Gomez COM-310 MemMan Project
 * Memory Size is topped at 4096K
 * OS process size is 400K
 * There are 8 processes a user can add to memory and if memory gets full then 
 * these will be added to the waiting queue. The user can input the process size in K.
 * I modeled this program after the samplE site so I didn't use Burst times 
 * since the sample didn't either. I implemented the First-fit Algorithm
 * The compaction button will eliminate empty spaces
 */
package memman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Edwin Gomez COM-310 MemMan Project
 */
public class MemMan extends Application {
    double totalsize=50;
    double temp=0;
    int tempidx;
    int idx;
    
    @Override
    public void start(Stage primaryStage) {       
        
        //CREATING THE COMBOBOX THAT LETS THE USER CHOOSE THE PROCESSES
        ObservableList<String> options = FXCollections.observableArrayList("1", "2", "3","4", "5", "6","7", "8");
        ComboBox comboBox = new ComboBox(options);
        comboBox.getSelectionModel().selectFirst();
        
        //Declaring the labels used for the GUI and Processes
        Label memlbl = new Label ("Total Memory: 4096K");
        Label oslbl = new Label ("OS Memory:   400k");
        Label pidlbl = new Label ("Process ID:  ");
        Label sizelbl = new Label ("Process Size (k):");
        Label oslbl2 = new Label ("OS");
        Label nothing1 = new Label ("  ");
        Label nothing2 = new Label ("  ");
        Label nothing3 = new Label ("  ");
        Label nothing4 = new Label ("  ");
        Label p1lbl = new Label ("P1");
        Label p2lbl = new Label ("P2");
        Label p3lbl = new Label ("P3");
        Label p4lbl = new Label ("P4");
        Label p5lbl = new Label ("P5");
        Label p6lbl = new Label ("P6");
        Label p7lbl = new Label ("P7");
        Label p8lbl = new Label ("P8");
        Label waiting = new Label ("  Waiting Queue(Process/size(K)):");
        
        //Creating blank labels used for convenient spacing
        Label emptylbl1 = new Label ("-");
        Label emptylbl2 = new Label ("-");
        Label emptylbl3 = new Label ("-");
        Label emptylbl4 = new Label ("-");
        Label emptylbl5 = new Label ("-");
        Label emptylbl6 = new Label ("-");
        Label emptylbl7 = new Label ("-");
        Label emptylbl8 = new Label ("-");
        
        TextField processtxt = new TextField();
        processtxt.setPrefWidth(70);
            
        // Create a Grid pane
        GridPane root = new GridPane();
        
        //adding design elements to the GUI such as color and padding
        root.setPadding(new Insets(10, 10, 20, 20));//(top/right/bottom/left)
        pidlbl.setPadding(new Insets(11, 0, 0, 0));
        comboBox.setStyle("-fx-padding: 5px; -fx-border-insets: 5px; -fx-background-insets: 5px;");
        comboBox.setPrefWidth(100);
        
        //Creating rows for the processes in memory
        HBox row1 = new HBox();
        HBox row2 = new HBox();
        HBox p1row = new HBox();
        HBox p2row = new HBox();
        HBox p3row = new HBox();
        HBox p4row = new HBox();
        HBox p5row = new HBox();
        HBox p6row = new HBox();
        HBox p7row = new HBox();
        HBox p8row = new HBox();
        HBox osrow = new HBox();
        
        //creating rows that will be used when a process is removed from memory
        HBox empty1 = new HBox();
        HBox empty2 = new HBox();
        HBox empty3 = new HBox();
        HBox empty4 = new HBox();
        HBox empty5 = new HBox();
        HBox empty6 = new HBox();
        HBox empty7 = new HBox();
        HBox empty8 = new HBox();
        HBox empty9 = new HBox();
        
        //Creating a row for process input info
        row1.getChildren().addAll(pidlbl,comboBox);
        row2.getChildren().addAll(sizelbl,processtxt);
        
        //Creating the default OS process in memory and the rows for the Processes in memory
        osrow.setStyle(" -fx-background-color: #8080ff; -fx-border-color: black; -fx-border-width: 1;");
        osrow.setPrefWidth(100);// prefWidth
        osrow.setPrefHeight(50);// prefHeight
        osrow.getChildren().addAll(oslbl2);
        
        //Setting the style for empty spaces in memory
        empty1.setStyle(" -fx-background-color: #FFFF00; -fx-border-color: black; -fx-border-width: 1;");
        emptylbl1.setText("-");
        empty1.getChildren().addAll(emptylbl1);
        empty2.setStyle(" -fx-background-color: #FFFF00; -fx-border-color: black; -fx-border-width: 1;");
        emptylbl2.setText("-");
        empty2.getChildren().addAll(emptylbl2);
        empty3.setStyle(" -fx-background-color: #FFFF00; -fx-border-color: black; -fx-border-width: 1;");
        emptylbl3.setText("-");
        empty3.getChildren().addAll(emptylbl3);
        empty4.setStyle(" -fx-background-color: #FFFF00; -fx-border-color: black; -fx-border-width: 1;");
        emptylbl4.setText("-");
        empty4.getChildren().addAll(emptylbl4);
        empty5.setStyle(" -fx-background-color: #FFFF00; -fx-border-color: black; -fx-border-width: 1;");
        emptylbl5.setText("-");
        empty5.getChildren().addAll(emptylbl5);
        empty6.setStyle(" -fx-background-color: #FFFF00; -fx-border-color: black; -fx-border-width: 1;");
        emptylbl6.setText("-");
        empty6.getChildren().addAll(emptylbl6);
        empty7.setStyle(" -fx-background-color: #FFFF00; -fx-border-color: black; -fx-border-width: 1;");
        emptylbl7.setText("-");
        empty7.getChildren().addAll(emptylbl7);
        empty8.setStyle(" -fx-background-color: #FFFF00; -fx-border-color: black; -fx-border-width: 1;");
        emptylbl8.setText("-");
        empty8.getChildren().addAll(emptylbl8);
        
        //Setting the style for processes in memory
        p1row.setStyle(" -fx-background-color: #ff8080; -fx-border-color: black; -fx-border-width: 1;");
        p1row.setPrefWidth(100);// prefWidth
        p1row.getChildren().addAll(p1lbl);
        
        p2row.setStyle(" -fx-background-color: #ff8080; -fx-border-color: black; -fx-border-width: 1;");
        p2row.setPrefWidth(100);// prefWidth
        p2row.getChildren().addAll(p2lbl);
        
        p3row.setStyle(" -fx-background-color: #ff8080; -fx-border-color: black; -fx-border-width: 1;");
        p3row.setPrefWidth(100);// prefWidth
        p3row.getChildren().addAll(p3lbl);
        
        p4row.setStyle(" -fx-background-color: #ff8080; -fx-border-color: black; -fx-border-width: 1;");
        p4row.setPrefWidth(100);// prefWidth
        p4row.getChildren().addAll(p4lbl);
        
        p5row.setStyle(" -fx-background-color: #ff8080; -fx-border-color: black; -fx-border-width: 1;");
        p5row.setPrefWidth(100);// prefWidth
        p5row.getChildren().addAll(p5lbl);
        
        p6row.setStyle(" -fx-background-color: #ff8080; -fx-border-color: black; -fx-border-width: 1;");
        p6row.setPrefWidth(100);// prefWidth
        p6row.getChildren().addAll(p6lbl);
        
        p7row.setStyle(" -fx-background-color: #ff8080; -fx-border-color: black; -fx-border-width: 1;");
        p7row.setPrefWidth(100);// prefWidth
        p7row.getChildren().addAll(p7lbl);
        
        p8row.setStyle(" -fx-background-color: #ff8080; -fx-border-color: black; -fx-border-width: 1;");
        p8row.setPrefWidth(100);// prefWidth
        p8row.getChildren().addAll(p8lbl);
        
        //Creating the Rectangular GUI for memory and entering processes
        VBox memoryrect = new VBox();//memory rectangle
        memoryrect.getChildren().addAll(osrow);
        memoryrect.setPadding(new Insets(0, 0, 0, 0));//(top/right/bottom/left)
        memoryrect.setPrefWidth(100);// prefWidth
        memoryrect.setPrefHeight(512);// prefHeight512 or 450
        memoryrect.setStyle(" -fx-background-color: #FFFF00; -fx-border-color: black; -fx-border-width: 3;");
        
        
        //Button that adds a process to memory if it's not in memory or waiting queue
        //OR if memory is full then the process can go to the waiting queue
        //OR if process is in memory or waiting queue then nothing happens
        //OR if there is a "hole" big enough it will go into there
        //The loop for this button ends at line 883
        ObservableList<Node> list = FXCollections.observableArrayList(memoryrect.getChildren());
        idx = list.size()-1;
        Button addbtn = new Button();
        addbtn.setText("Add to Memory");
        addbtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                 int choice = Integer.parseInt((String)comboBox.getValue());
                 int size = Integer.parseInt(processtxt.getText());
                if (choice==1){
                    String text=waiting.getText();
                    temp=totalsize+(size*.125);
                    if(memoryrect.getChildren().contains(p1row)==false && temp<=512 && text.contains("P1")==false && (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6))==false){
                        p1row.setPrefHeight(size*.125);// prefHeight
                        memoryrect.getChildren().addAll(p1row);
                        totalsize+=size*.125;          
                    } else if (memoryrect.getChildren().contains(p1row)==false && temp>512 && text.contains("P1")){
                    } else if (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6)){
                        if(empty1.getHeight()*8>size){
                        p1row.setPrefHeight(size*.125);// prefHeight
                        double save = empty1.getHeight();
                        empty1.setPrefHeight((empty1.getHeight())-(size*.125));
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().add(tempidx,p1row);
                       }else if(empty1.getHeight()*8==size){
                        p1row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().remove(empty1);
                        memoryrect.getChildren().add(tempidx,p1row);
                       }else if(empty2.getHeight()*8>size){
                        p1row.setPrefHeight(size*.125);// prefHeight
                        empty2.setPrefHeight(empty2.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().add(tempidx,p1row);
                       }else if(empty2.getHeight()*8==size){
                        p1row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().remove(empty2);
                        memoryrect.getChildren().add(tempidx,p1row);
                       } else if(empty3.getHeight()*8>size){
                        p1row.setPrefHeight(size*.125);// prefHeight
                        empty3.setPrefHeight(empty3.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().add(tempidx,p1row);
                       }else if(empty3.getHeight()*8==size){
                        p1row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().remove(empty3);
                        memoryrect.getChildren().add(tempidx,p1row);
                       } else if(empty4.getHeight()*8>size){
                        p1row.setPrefHeight(size*.125);// prefHeight
                        empty4.setPrefHeight(empty4.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().add(tempidx,p1row);
                       }else if(empty4.getHeight()*8==size){
                        p1row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().remove(empty4);
                        memoryrect.getChildren().add(tempidx,p1row);
                       } else if(empty5.getHeight()*8>size){
                        p1row.setPrefHeight(size*.125);// prefHeight
                        empty5.setPrefHeight(empty5.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().add(tempidx,p1row);
                       }else if(empty5.getHeight()*8==size){
                        p1row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().remove(empty5);
                        memoryrect.getChildren().add(tempidx,p1row);
                       } else if(empty6.getHeight()*8>size){
                        p1row.setPrefHeight(size*.125);// prefHeight
                        empty6.setPrefHeight(empty6.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().add(tempidx,p1row);
                       }else if(empty6.getHeight()*8==size){
                        p1row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().remove(empty6);
                        memoryrect.getChildren().add(tempidx,p1row);
                       } else if(memoryrect.getChildren().contains(p1row)==false && temp>512){
                        text=text+"<P1 "+size+">";
                        waiting.setText(text);
                       } else if(memoryrect.getChildren().contains(p1row)==false && temp<=512){
                         p1row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p1row);
                        }
                    } else if(memoryrect.getChildren().contains(p1row)==false && temp>512){
                        text=text+"<P1 "+size+">";
                        waiting.setText(text);
                    } else if(memoryrect.getChildren().contains(p1row)==false && temp<=512){
                         p1row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p1row);
                    }
                } else if (choice==2){
                    String text=waiting.getText();
                    temp=totalsize+(size*.125);
                    if(memoryrect.getChildren().contains(p2row)==false && temp<=512 && text.contains("P2")==false && (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6))==false){
                        p2row.setPrefHeight(size*.125);// prefHeight
                        memoryrect.getChildren().addAll(p2row);
                        totalsize+=size*.125;    
                    } else if (memoryrect.getChildren().contains(p2row)==false && temp>512 && text.contains("P2")){
                    } else if (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6)){
                        if(empty1.getHeight()*8>size){
                        p2row.setPrefHeight(size*.125);// prefHeight
                        double save = empty1.getHeight();
                        empty1.setPrefHeight((empty1.getHeight())-(size*.125));
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().add(tempidx,p2row);
                       }else if(empty1.getHeight()*8==size){
                        p2row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().remove(empty1);
                        memoryrect.getChildren().add(tempidx,p2row);
                       }else if(empty2.getHeight()*8>size){
                        p2row.setPrefHeight(size*.125);// prefHeight
                        empty2.setPrefHeight(empty2.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().add(tempidx,p2row);
                       }else if(empty2.getHeight()*8==size){
                        p2row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().remove(empty2);
                        memoryrect.getChildren().add(tempidx,p2row);
                       } else if(empty3.getHeight()*8>size){
                        p2row.setPrefHeight(size*.125);// prefHeight
                        empty3.setPrefHeight(empty3.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().add(tempidx,p2row);
                       }else if(empty3.getHeight()*8==size){
                        p2row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().remove(empty3);
                        memoryrect.getChildren().add(tempidx,p2row);
                       } else if(empty4.getHeight()*8>size){
                        p2row.setPrefHeight(size*.125);// prefHeight
                        empty4.setPrefHeight(empty4.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().add(tempidx,p2row);
                       }else if(empty4.getHeight()*8==size){
                        p2row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().remove(empty4);
                        memoryrect.getChildren().add(tempidx,p2row);
                       } else if(empty5.getHeight()*8>size){
                        p2row.setPrefHeight(size*.125);// prefHeight
                        empty5.setPrefHeight(empty5.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().add(tempidx,p2row);
                       }else if(empty5.getHeight()*8==size){
                        p2row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().remove(empty5);
                        memoryrect.getChildren().add(tempidx,p2row);
                       } else if(empty6.getHeight()*8>size){
                        p2row.setPrefHeight(size*.125);// prefHeight
                        empty6.setPrefHeight(empty6.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().add(tempidx,p2row);
                       }else if(empty6.getHeight()*8==size){
                        p2row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().remove(empty6);
                        memoryrect.getChildren().add(tempidx,p2row);
                       } else if(memoryrect.getChildren().contains(p2row)==false && temp>512){
                        text=text+"<P2 "+size+">";
                        waiting.setText(text);
                       } else if(memoryrect.getChildren().contains(p2row)==false && temp<=512){
                         p2row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p2row);
                        }
                    } else if(memoryrect.getChildren().contains(p2row)==false && temp>512){
                        text=text+"<P2 "+size+">";
                        waiting.setText(text);
                    } else if(memoryrect.getChildren().contains(p2row)==false && temp<=512){
                         p2row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p2row);
                    }
                } else if (choice==3){
                    String text=waiting.getText();
                    temp=totalsize+(size*.125);
                    if(memoryrect.getChildren().contains(p3row)==false && temp<=512 && text.contains("P3")==false && (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6))==false){
                        p3row.setPrefHeight(size*.125);// prefHeight
                        memoryrect.getChildren().addAll(p3row);
                        totalsize+=size*.125;       
                    } else if (memoryrect.getChildren().contains(p3row)==false && temp>512 && text.contains("P3")){
                    } else if (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6)){
                        if(empty1.getHeight()*8>size){
                        p3row.setPrefHeight(size*.125);// prefHeight
                        double save = empty1.getHeight();
                        empty1.setPrefHeight((empty1.getHeight())-(size*.125));
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().add(tempidx,p3row);
                       }else if(empty1.getHeight()*8==size){
                        p3row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().remove(empty1);
                        memoryrect.getChildren().add(tempidx,p3row);
                       }else if(empty2.getHeight()*8>size){
                        p3row.setPrefHeight(size*.125);// prefHeight
                        empty2.setPrefHeight(empty2.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().add(tempidx,p3row);
                       }else if(empty2.getHeight()*8==size){
                        p3row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().remove(empty2);
                        memoryrect.getChildren().add(tempidx,p3row);
                       } else if(empty3.getHeight()*8>size){
                        p3row.setPrefHeight(size*.125);// prefHeight
                        empty3.setPrefHeight(empty3.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().add(tempidx,p3row);
                       }else if(empty3.getHeight()*8==size){
                        p3row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().remove(empty3);
                        memoryrect.getChildren().add(tempidx,p3row);
                       } else if(empty4.getHeight()*8>size){
                        p3row.setPrefHeight(size*.125);// prefHeight
                        empty4.setPrefHeight(empty4.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().add(tempidx,p3row);
                       }else if(empty4.getHeight()*8==size){
                        p3row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().remove(empty4);
                        memoryrect.getChildren().add(tempidx,p3row);
                       } else if(empty5.getHeight()*8>size){
                        p3row.setPrefHeight(size*.125);// prefHeight
                        empty5.setPrefHeight(empty5.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().add(tempidx,p3row);
                       }else if(empty5.getHeight()*8==size){
                        p3row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().remove(empty5);
                        memoryrect.getChildren().add(tempidx,p3row);
                       } else if(empty6.getHeight()*8>size){
                        p3row.setPrefHeight(size*.125);// prefHeight
                        empty6.setPrefHeight(empty6.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().add(tempidx,p3row);
                       }else if(empty6.getHeight()*8==size){
                        p3row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().remove(empty6);
                        memoryrect.getChildren().add(tempidx,p3row);
                       } else if(memoryrect.getChildren().contains(p3row)==false && temp>512){
                        text=text+"<P3 "+size+">";
                        waiting.setText(text);
                       } else if(memoryrect.getChildren().contains(p3row)==false && temp<=512){
                         p3row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p3row);
                        }
                    } else if(memoryrect.getChildren().contains(p3row)==false && temp>512){
                        text=text+"<P3 "+size+">";
                        waiting.setText(text);
                    } else if(memoryrect.getChildren().contains(p3row)==false && temp<=512){
                         p3row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p3row);
                    }
                } else if (choice==4){
                    String text=waiting.getText();
                    temp=totalsize+(size*.125);
                    if(memoryrect.getChildren().contains(p4row)==false && temp<=512 && text.contains("P4")==false && (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6))==false){
                        p4row.setPrefHeight(size*.125);// prefHeight
                        memoryrect.getChildren().addAll(p4row);
                        totalsize+=size*.125;      
                    } else if (memoryrect.getChildren().contains(p4row)==false && temp>512 && text.contains("P4")){
                    } else if (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6)){
                        if(empty1.getHeight()*8>size){
                        p4row.setPrefHeight(size*.125);// prefHeight
                        double save = empty1.getHeight();
                        empty1.setPrefHeight((empty1.getHeight())-(size*.125));
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().add(tempidx,p4row);
                       }else if(empty1.getHeight()*8==size){
                        p4row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().remove(empty1);
                        memoryrect.getChildren().add(tempidx,p4row);
                       }else if(empty2.getHeight()*8>size){
                        p4row.setPrefHeight(size*.125);// prefHeight
                        empty2.setPrefHeight(empty2.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().add(tempidx,p4row);
                       }else if(empty2.getHeight()*8==size){
                        p4row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().remove(empty2);
                        memoryrect.getChildren().add(tempidx,p4row);
                       } else if(empty3.getHeight()*8>size){
                        p4row.setPrefHeight(size*.125);// prefHeight
                        empty3.setPrefHeight(empty3.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().add(tempidx,p4row);
                       }else if(empty3.getHeight()*8==size){
                        p4row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().remove(empty3);
                        memoryrect.getChildren().add(tempidx,p4row);
                       } else if(empty4.getHeight()*8>size){
                        p4row.setPrefHeight(size*.125);// prefHeight
                        empty4.setPrefHeight(empty4.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().add(tempidx,p4row);
                       }else if(empty4.getHeight()*8==size){
                        p4row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().remove(empty4);
                        memoryrect.getChildren().add(tempidx,p4row);
                       } else if(empty5.getHeight()*8>size){
                        p4row.setPrefHeight(size*.125);// prefHeight
                        empty5.setPrefHeight(empty5.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().add(tempidx,p4row);
                       }else if(empty5.getHeight()*8==size){
                        p4row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().remove(empty5);
                        memoryrect.getChildren().add(tempidx,p4row);
                       } else if(empty6.getHeight()*8>size){
                        p4row.setPrefHeight(size*.125);// prefHeight
                        empty6.setPrefHeight(empty6.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().add(tempidx,p4row);
                       }else if(empty6.getHeight()*8==size){
                        p4row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().remove(empty6);
                        memoryrect.getChildren().add(tempidx,p4row);
                       } else if(memoryrect.getChildren().contains(p4row)==false && temp>512){
                        text=text+"<P4 "+size+">";
                        waiting.setText(text);
                       } else if(memoryrect.getChildren().contains(p4row)==false && temp<=512){
                         p4row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p4row);
                        }
                    } else if(memoryrect.getChildren().contains(p4row)==false && temp>512){
                        text=text+"<P4 "+size+">";
                        waiting.setText(text);
                    } else if(memoryrect.getChildren().contains(p4row)==false && temp<=512){
                         p4row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p4row);
                    }
                } else if (choice==5){
                    String text=waiting.getText();
                    temp=totalsize+(size*.125);
                    if(memoryrect.getChildren().contains(p5row)==false && temp<=512 && text.contains("P5")==false && (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6))==false){
                        p5row.setPrefHeight(size*.125);// prefHeight
                        memoryrect.getChildren().addAll(p5row);
                        totalsize+=size*.125;     
                    } else if (memoryrect.getChildren().contains(p5row)==false && temp>512 && text.contains("P5")){
                    } else if (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6)){
                        if(empty1.getHeight()*8>size){
                        p5row.setPrefHeight(size*.125);// prefHeight
                        double save = empty1.getHeight();
                        empty1.setPrefHeight((empty1.getHeight())-(size*.125));
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().add(tempidx,p5row);
                       }else if(empty1.getHeight()*8==size){
                        p5row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().remove(empty1);
                        memoryrect.getChildren().add(tempidx,p5row);
                       }else if(empty2.getHeight()*8>size){
                        p5row.setPrefHeight(size*.125);// prefHeight
                        empty2.setPrefHeight(empty2.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().add(tempidx,p5row);
                       }else if(empty2.getHeight()*8==size){
                        p5row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().remove(empty2);
                        memoryrect.getChildren().add(tempidx,p5row);
                       } else if(empty3.getHeight()*8>size){
                        p5row.setPrefHeight(size*.125);// prefHeight
                        empty3.setPrefHeight(empty3.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().add(tempidx,p5row);
                       }else if(empty3.getHeight()*8==size){
                        p5row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().remove(empty3);
                        memoryrect.getChildren().add(tempidx,p5row);
                       } else if(empty4.getHeight()*8>size){
                        p5row.setPrefHeight(size*.125);// prefHeight
                        empty4.setPrefHeight(empty4.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().add(tempidx,p5row);
                       }else if(empty4.getHeight()*8==size){
                        p5row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().remove(empty4);
                        memoryrect.getChildren().add(tempidx,p5row);
                       } else if(empty5.getHeight()*8>size){
                        p5row.setPrefHeight(size*.125);// prefHeight
                        empty5.setPrefHeight(empty5.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().add(tempidx,p5row);
                       }else if(empty5.getHeight()*8==size){
                        p5row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().remove(empty5);
                        memoryrect.getChildren().add(tempidx,p5row);
                       } else if(empty6.getHeight()*8>size){
                        p5row.setPrefHeight(size*.125);// prefHeight
                        empty6.setPrefHeight(empty6.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().add(tempidx,p5row);
                       }else if(empty6.getHeight()*8==size){
                        p5row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().remove(empty6);
                        memoryrect.getChildren().add(tempidx,p5row);
                       } else if(memoryrect.getChildren().contains(p5row)==false && temp>512){
                        text=text+"<P5 "+size+">";
                        waiting.setText(text);
                       } else if(memoryrect.getChildren().contains(p5row)==false && temp<=512){
                         p5row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p5row);
                        }
                    } else if(memoryrect.getChildren().contains(p5row)==false && temp>512){
                        text=text+"<P5 "+size+">";
                        waiting.setText(text);
                    } else if(memoryrect.getChildren().contains(p5row)==false && temp<=512){
                         p5row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p5row);
                    }
                } else if (choice==6){
                    String text=waiting.getText();
                    temp=totalsize+(size*.125);
                    if(memoryrect.getChildren().contains(p6row)==false && temp<=512 && text.contains("P6")==false && (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6))==false){
                        p6row.setPrefHeight(size*.125);// prefHeight
                        memoryrect.getChildren().addAll(p6row);
                        totalsize+=size*.125;       
                    } else if (memoryrect.getChildren().contains(p6row)==false && temp>512 && text.contains("P6")){
                    } else if (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6)){
                        if(empty1.getHeight()*8>size){
                        p6row.setPrefHeight(size*.125);// prefHeight
                        double save = empty1.getHeight();
                        empty1.setPrefHeight((empty1.getHeight())-(size*.125));
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().add(tempidx,p6row);
                       }else if(empty1.getHeight()*8==size){
                        p6row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().remove(empty1);
                        memoryrect.getChildren().add(tempidx,p6row);
                       }else if(empty2.getHeight()*8>size){
                        p6row.setPrefHeight(size*.125);// prefHeight
                        empty2.setPrefHeight(empty2.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().add(tempidx,p6row);
                       }else if(empty2.getHeight()*8==size){
                        p6row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().remove(empty2);
                        memoryrect.getChildren().add(tempidx,p6row);
                       } else if(empty3.getHeight()*8>size){
                        p6row.setPrefHeight(size*.125);// prefHeight
                        empty3.setPrefHeight(empty3.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().add(tempidx,p6row);
                       }else if(empty3.getHeight()*8==size){
                        p6row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().remove(empty3);
                        memoryrect.getChildren().add(tempidx,p6row);
                       } else if(empty4.getHeight()*8>size){
                        p6row.setPrefHeight(size*.125);// prefHeight
                        empty4.setPrefHeight(empty4.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().add(tempidx,p6row);
                       }else if(empty4.getHeight()*8==size){
                        p6row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().remove(empty4);
                        memoryrect.getChildren().add(tempidx,p6row);
                       } else if(empty5.getHeight()*8>size){
                        p6row.setPrefHeight(size*.125);// prefHeight
                        empty5.setPrefHeight(empty5.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().add(tempidx,p6row);
                       }else if(empty5.getHeight()*8==size){
                        p6row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().remove(empty5);
                        memoryrect.getChildren().add(tempidx,p6row);
                       } else if(empty6.getHeight()*8>size){
                        p6row.setPrefHeight(size*.125);// prefHeight
                        empty6.setPrefHeight(empty6.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().add(tempidx,p6row);
                       }else if(empty6.getHeight()*8==size){
                        p6row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().remove(empty6);
                        memoryrect.getChildren().add(tempidx,p6row);
                       } else if(memoryrect.getChildren().contains(p6row)==false && temp>512){
                        text=text+"<P6 "+size+">";
                        waiting.setText(text);
                       } else if(memoryrect.getChildren().contains(p6row)==false && temp<=512){
                         p6row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p6row);
                        }
                    } else if(memoryrect.getChildren().contains(p6row)==false && temp>512){
                        text=text+"<P6 "+size+">";
                        waiting.setText(text);
                    } else if(memoryrect.getChildren().contains(p6row)==false && temp<=512){
                         p6row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p6row);
                    }
                } else if (choice==7){
                    String text=waiting.getText();
                    temp=totalsize+(size*.125);
                    if(memoryrect.getChildren().contains(p7row)==false && temp<=512 && text.contains("P7")==false && (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6))==false){
                        p7row.setPrefHeight(size*.125);// prefHeight
                        memoryrect.getChildren().addAll(p7row);
                        totalsize+=size*.125;      
                    } else if (memoryrect.getChildren().contains(p7row)==false && temp>512 && text.contains("P7")){
                    } else if (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6)){
                        if(empty1.getHeight()*8>size){
                        p7row.setPrefHeight(size*.125);// prefHeight
                        double save = empty1.getHeight();
                        empty1.setPrefHeight((empty1.getHeight())-(size*.125));
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().add(tempidx,p7row);
                       }else if(empty1.getHeight()*8==size){
                        p7row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().remove(empty1);
                        memoryrect.getChildren().add(tempidx,p7row);
                       }else if(empty2.getHeight()*8>size){
                        p7row.setPrefHeight(size*.125);// prefHeight
                        empty2.setPrefHeight(empty2.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().add(tempidx,p7row);
                       }else if(empty2.getHeight()*8==size){
                        p7row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().remove(empty2);
                        memoryrect.getChildren().add(tempidx,p7row);
                       } else if(empty3.getHeight()*8>size){
                        p7row.setPrefHeight(size*.125);// prefHeight
                        empty3.setPrefHeight(empty3.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().add(tempidx,p7row);
                       }else if(empty3.getHeight()*8==size){
                        p7row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().remove(empty3);
                        memoryrect.getChildren().add(tempidx,p7row);
                       } else if(empty4.getHeight()*8>size){
                        p7row.setPrefHeight(size*.125);// prefHeight
                        empty4.setPrefHeight(empty4.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().add(tempidx,p7row);
                       }else if(empty4.getHeight()*8==size){
                        p7row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().remove(empty4);
                        memoryrect.getChildren().add(tempidx,p7row);
                       } else if(empty5.getHeight()*8>size){
                        p7row.setPrefHeight(size*.125);// prefHeight
                        empty5.setPrefHeight(empty5.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().add(tempidx,p7row);
                       }else if(empty5.getHeight()*8==size){
                        p7row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().remove(empty5);
                        memoryrect.getChildren().add(tempidx,p7row);
                       } else if(empty6.getHeight()*8>size){
                        p7row.setPrefHeight(size*.125);// prefHeight
                        empty6.setPrefHeight(empty6.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().add(tempidx,p7row);
                       }else if(empty6.getHeight()*8==size){
                        p7row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().remove(empty6);
                        memoryrect.getChildren().add(tempidx,p7row);
                       } else if(memoryrect.getChildren().contains(p7row)==false && temp>512){
                        text=text+"<P7 "+size+">";
                        waiting.setText(text);
                       } else if(memoryrect.getChildren().contains(p7row)==false && temp<=512){
                         p7row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p7row);
                        }
                    } else if(memoryrect.getChildren().contains(p7row)==false && temp>512){
                        text=text+"<P7 "+size+">";
                        waiting.setText(text);
                    } else if(memoryrect.getChildren().contains(p7row)==false && temp<=512){
                         p7row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p7row);
                    }
                }  else if (choice==8){
                    String text=waiting.getText();
                    temp=totalsize+(size*.125);
                    if(memoryrect.getChildren().contains(p8row)==false && temp<=512 && text.contains("P8")==false && (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6))==false){
                        p8row.setPrefHeight(size*.125);// prefHeight
                        memoryrect.getChildren().addAll(p8row);
                        totalsize+=size*.125;      
                    } else if (memoryrect.getChildren().contains(p8row)==false && temp>512 && text.contains("P8")){
                    } else if (memoryrect.getChildren().contains(empty1)|| memoryrect.getChildren().contains(empty2)|| memoryrect.getChildren().contains(empty3)|| memoryrect.getChildren().contains(empty4)|| memoryrect.getChildren().contains(empty5)||memoryrect.getChildren().contains(empty6)){
                        if(empty1.getHeight()*8>size){
                        p8row.setPrefHeight(size*.125);// prefHeight
                        double save = empty1.getHeight();
                        empty1.setPrefHeight((empty1.getHeight())-(size*.125));
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().add(tempidx,p8row);
                       }else if(empty1.getHeight()*8==size){
                        p8row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty1);
                        memoryrect.getChildren().remove(empty1);
                        memoryrect.getChildren().add(tempidx,p8row);
                       }else if(empty2.getHeight()*8>size){
                        p8row.setPrefHeight(size*.125);// prefHeight
                        empty2.setPrefHeight(empty2.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().add(tempidx,p8row);
                       }else if(empty2.getHeight()*8==size){
                        p8row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty2);
                        memoryrect.getChildren().remove(empty2);
                        memoryrect.getChildren().add(tempidx,p8row);
                       } else if(empty3.getHeight()*8>size){
                        p8row.setPrefHeight(size*.125);// prefHeight
                        empty3.setPrefHeight(empty3.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().add(tempidx,p8row);
                       }else if(empty3.getHeight()*8==size){
                        p8row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty3);
                        memoryrect.getChildren().remove(empty3);
                        memoryrect.getChildren().add(tempidx,p8row);
                       } else if(empty4.getHeight()*8>size){
                        p8row.setPrefHeight(size*.125);// prefHeight
                        empty4.setPrefHeight(empty4.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().add(tempidx,p8row);
                       }else if(empty4.getHeight()*8==size){
                        p8row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty4);
                        memoryrect.getChildren().remove(empty4);
                        memoryrect.getChildren().add(tempidx,p8row);
                       } else if(empty5.getHeight()*8>size){
                        p8row.setPrefHeight(size*.125);// prefHeight
                        empty5.setPrefHeight(empty5.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().add(tempidx,p8row);
                       }else if(empty5.getHeight()*8==size){
                        p8row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty5);
                        memoryrect.getChildren().remove(empty5);
                        memoryrect.getChildren().add(tempidx,p8row);
                       } else if(empty6.getHeight()*8>size){
                        p8row.setPrefHeight(size*.125);// prefHeight
                        empty6.setPrefHeight(empty6.getHeight()-(size*.125));// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().add(tempidx,p8row);
                       }else if(empty6.getHeight()*8==size){
                        p8row.setPrefHeight(size*.125);// prefHeight
                        tempidx=memoryrect.getChildren().indexOf(empty6);
                        memoryrect.getChildren().remove(empty6);
                        memoryrect.getChildren().add(tempidx,p8row);
                       } else if(memoryrect.getChildren().contains(p8row)==false && temp>512){
                        text=text+"<P8 "+size+">";
                        waiting.setText(text);
                       } else if(memoryrect.getChildren().contains(p8row)==false && temp<=512){
                         p8row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p8row);
                        }
                    } else if(memoryrect.getChildren().contains(p8row)==false && temp>512){
                        text=text+"<P8 "+size+">";
                        waiting.setText(text);
                    } else if(memoryrect.getChildren().contains(p8row)==false && temp<=512){
                         p8row.setPrefHeight(size*.125);// prefHeight
                         memoryrect.getChildren().addAll(p8row);
                    }
                } 
            }
        });
        
        //Button that removes a process from memory or waiting queue
        //IF it's a process at the end of memory then it's just removed from
        //the process but IF it's deeper in memory then it will be replaced with 
        //an empty space
        //Button ends at line 1175
        Button removebtn = new Button();
        removebtn.setText("Remove from Memory");
        removebtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Removed from Memory");
                int choice=Integer.parseInt((String)comboBox.getValue());
                String text=waiting.getText();
                if (choice==1){
                    if(memoryrect.getChildren().contains(p1row)==true && (memoryrect.getChildren().indexOf(p1row)== memoryrect.getChildren().size()-1)){
                        double size = p1row.getHeight();
                        totalsize=totalsize-size;
                        memoryrect.getChildren().remove(p1row);
                    } else if (memoryrect.getChildren().contains(p1row)==false && text.contains("P1")){
                        String[] values;
                        values = text.split("<");
                        List<String> valueslist = new ArrayList<>(Arrays.asList(values));
                        for(String listItem : valueslist){
                           if(listItem.contains("P1")){
                               tempidx=valueslist.indexOf(listItem);
                               values[tempidx] = "";
                               for(int i=1; i<values.length-1;i++){
                                   values[i]="<"+values[i];
                               }
                               text=Arrays.toString(values);
                               text=text.replace("[","");
                               text=text.replace("]","");
                               text=text.replace(",","");
                               text=text.replace(" ","");
                               waiting.setText(text);
                        }
                      }
                    } else if(memoryrect.getChildren().contains(p1row)==true && ((memoryrect.getChildren().indexOf(p1row)== memoryrect.getChildren().size()-1)==false) && (((memoryrect.getChildren().indexOf(p1row)==memoryrect.getChildren().indexOf(empty1)-1))||((memoryrect.getChildren().indexOf(p1row)==memoryrect.getChildren().indexOf(empty2)-1))||((memoryrect.getChildren().indexOf(p1row)==memoryrect.getChildren().indexOf(empty3)-1)))){
                        double size = p1row.getHeight();
                        memoryrect.getChildren().remove(p1row);
                        empty1.setPrefHeight(empty1.getHeight()+size);
                        
                    }else if(memoryrect.getChildren().contains(p1row)==true && (memoryrect.getChildren().indexOf(p1row)== memoryrect.getChildren().size()-1)==false){
                        double size = p1row.getHeight();
                        empty1.setPrefHeight(size);
                        tempidx=memoryrect.getChildren().indexOf(p1row);
                        memoryrect.getChildren().remove(p1row);
                        memoryrect.getChildren().add(tempidx,empty1);
                        
                    }
                } else if (choice==2){
                    if(memoryrect.getChildren().contains(p2row)==true && (memoryrect.getChildren().indexOf(p2row)== memoryrect.getChildren().size()-1)){
                        double size = p2row.getHeight();
                        totalsize=totalsize-size;
                        memoryrect.getChildren().remove(p2row);
                    } else if (memoryrect.getChildren().contains(p2row)==false && text.contains("P2")){
                        String[] values;
                        values = text.split("<");
                        List<String> valueslist = new ArrayList<>(Arrays.asList(values));
                        for(String listItem : valueslist){
                           if(listItem.contains("P2")){
                               tempidx=valueslist.indexOf(listItem);
                               values[tempidx] = "";
                               text=Arrays.toString(values);
                               text=text.replace("[","");
                               text=text.replace("]","");
                               text=text.replace(",","");
                               text=text.replace(" ","");
                               waiting.setText(text);
                        }
                      }
                    } else if(memoryrect.getChildren().contains(p2row)==true && ((memoryrect.getChildren().indexOf(p2row)== memoryrect.getChildren().size()-1)==false) && (((memoryrect.getChildren().indexOf(p2row)==memoryrect.getChildren().indexOf(empty1)-1))||((memoryrect.getChildren().indexOf(p2row)==memoryrect.getChildren().indexOf(empty2)-1))||((memoryrect.getChildren().indexOf(p2row)==memoryrect.getChildren().indexOf(empty3)-1)))){
                        double size = p2row.getHeight();
                        memoryrect.getChildren().remove(p2row);
                        empty1.setPrefHeight(empty1.getHeight()+size);
                        
                    }else if(memoryrect.getChildren().contains(p2row)==true && (memoryrect.getChildren().indexOf(p2row)== memoryrect.getChildren().size()-1)==false){
                        double size = p2row.getHeight();
                        empty1.setPrefHeight(size);
                        tempidx=memoryrect.getChildren().indexOf(p2row);
                        memoryrect.getChildren().remove(p2row);
                        memoryrect.getChildren().add(tempidx,empty1);
                        
                    }
                } else if (choice==3){
                    if(memoryrect.getChildren().contains(p3row)==true && (memoryrect.getChildren().indexOf(p3row)== memoryrect.getChildren().size()-1)){
                        double size = p3row.getHeight();
                        totalsize=totalsize-size;
                        memoryrect.getChildren().remove(p3row);
                    } else if (memoryrect.getChildren().contains(p3row)==false && text.contains("P3")){
                        String[] values;
                        values = text.split("<");
                        List<String> valueslist = new ArrayList<>(Arrays.asList(values));
                        for(String listItem : valueslist){
                           if(listItem.contains("P3")){
                               tempidx=valueslist.indexOf(listItem);
                               values[tempidx] = "";
                               text=Arrays.toString(values);
                               text=text.replace("[","");
                               text=text.replace("]","");
                               text=text.replace(",","");
                               text=text.replace(" ","");
                               waiting.setText(text);
                        }
                      }
                    } else if(memoryrect.getChildren().contains(p3row)==true && ((memoryrect.getChildren().indexOf(p3row)== memoryrect.getChildren().size()-1)==false) && (((memoryrect.getChildren().indexOf(p3row)==memoryrect.getChildren().indexOf(empty1)-1))||((memoryrect.getChildren().indexOf(p3row)==memoryrect.getChildren().indexOf(empty2)-1))||((memoryrect.getChildren().indexOf(p3row)==memoryrect.getChildren().indexOf(empty3)-1)))){
                        double size = p3row.getHeight();
                        memoryrect.getChildren().remove(p3row);
                        empty1.setPrefHeight(empty1.getHeight()+size);
                        
                    }else if(memoryrect.getChildren().contains(p3row)==true && (memoryrect.getChildren().indexOf(p3row)== memoryrect.getChildren().size()-1)==false){
                        double size = p3row.getHeight();
                        empty1.setPrefHeight(size);
                        tempidx=memoryrect.getChildren().indexOf(p3row);
                        memoryrect.getChildren().remove(p3row);
                        memoryrect.getChildren().add(tempidx,empty1);
                        
                    }
                } else if (choice==4){
                    if(memoryrect.getChildren().contains(p4row)==true && (memoryrect.getChildren().indexOf(p4row)== memoryrect.getChildren().size()-1)){
                        double size = p4row.getHeight();
                        totalsize=totalsize-size;
                        memoryrect.getChildren().remove(p4row);
                    } else if (memoryrect.getChildren().contains(p4row)==false && text.contains("P4")){
                        String[] values;
                        values = text.split("<");
                        List<String> valueslist = new ArrayList<>(Arrays.asList(values));
                        for(String listItem : valueslist){
                           if(listItem.contains("P4")){
                               tempidx=valueslist.indexOf(listItem);
                               values[tempidx] = "";
                               text=Arrays.toString(values);
                               text=text.replace("[","");
                               text=text.replace("]","");
                               text=text.replace(",","");
                               text=text.replace(" ","");
                               waiting.setText(text);
                        }
                      }
                    } else if(memoryrect.getChildren().contains(p4row)==true && ((memoryrect.getChildren().indexOf(p4row)== memoryrect.getChildren().size()-1)==false) && (((memoryrect.getChildren().indexOf(p4row)==memoryrect.getChildren().indexOf(empty1)-1))||((memoryrect.getChildren().indexOf(p4row)==memoryrect.getChildren().indexOf(empty2)-1))||((memoryrect.getChildren().indexOf(p4row)==memoryrect.getChildren().indexOf(empty3)-1)))){
                        double size = p4row.getHeight();
                        memoryrect.getChildren().remove(p4row);
                        empty1.setPrefHeight(empty1.getHeight()+size);
                        
                    }else if(memoryrect.getChildren().contains(p4row)==true && (memoryrect.getChildren().indexOf(p4row)== memoryrect.getChildren().size()-1)==false){
                        double size = p4row.getHeight();
                        empty1.setPrefHeight(size);
                        tempidx=memoryrect.getChildren().indexOf(p4row);
                        memoryrect.getChildren().remove(p4row);
                        memoryrect.getChildren().add(tempidx,empty1);
                        
                    }
                } else if (choice==5){
                    if(memoryrect.getChildren().contains(p5row)==true && (memoryrect.getChildren().indexOf(p5row)== memoryrect.getChildren().size()-1)){
                        double size = p5row.getHeight();
                        totalsize=totalsize-size;
                        memoryrect.getChildren().remove(p5row);
                    } else if (memoryrect.getChildren().contains(p5row)==false && text.contains("P5")){
                        String[] values;
                        values = text.split("<");
                        List<String> valueslist = new ArrayList<>(Arrays.asList(values));
                        for(String listItem : valueslist){
                           if(listItem.contains("P5")){
                               tempidx=valueslist.indexOf(listItem);
                               values[tempidx] = "";
                               text=Arrays.toString(values);
                               text=text.replace("[","");
                               text=text.replace("]","");
                               text=text.replace(",","");
                               text=text.replace(" ","");
                               waiting.setText(text);
                        }
                      }
                    } else if(memoryrect.getChildren().contains(p5row)==true && ((memoryrect.getChildren().indexOf(p5row)== memoryrect.getChildren().size()-1)==false) && (((memoryrect.getChildren().indexOf(p5row)==memoryrect.getChildren().indexOf(empty1)-1))||((memoryrect.getChildren().indexOf(p5row)==memoryrect.getChildren().indexOf(empty2)-1))||((memoryrect.getChildren().indexOf(p5row)==memoryrect.getChildren().indexOf(empty3)-1)))){
                        double size = p5row.getHeight();
                        memoryrect.getChildren().remove(p5row);
                        empty1.setPrefHeight(empty1.getHeight()+size);
                        
                    }else if(memoryrect.getChildren().contains(p5row)==true && (memoryrect.getChildren().indexOf(p5row)== memoryrect.getChildren().size()-1)==false){
                        double size = p5row.getHeight();
                        empty1.setPrefHeight(size);
                        tempidx=memoryrect.getChildren().indexOf(p5row);
                        memoryrect.getChildren().remove(p5row);
                        memoryrect.getChildren().add(tempidx,empty1);
                        
                    }
                } else if (choice==6){
                    if(memoryrect.getChildren().contains(p6row)==true && (memoryrect.getChildren().indexOf(p6row)== memoryrect.getChildren().size()-1)){
                        double size = p6row.getHeight();
                        totalsize=totalsize-size;
                        memoryrect.getChildren().remove(p6row);
                    } else if (memoryrect.getChildren().contains(p6row)==false && text.contains("P6")){
                        String[] values;
                        values = text.split("<");
                        List<String> valueslist = new ArrayList<>(Arrays.asList(values));
                        for(String listItem : valueslist){
                           if(listItem.contains("P6")){
                               tempidx=valueslist.indexOf(listItem);
                               values[tempidx] = "";
                               text=Arrays.toString(values);
                               text=text.replace("[","");
                               text=text.replace("]","");
                               text=text.replace(",","");
                               text=text.replace(" ","");
                               waiting.setText(text);
                        }
                      }
                    } else if(memoryrect.getChildren().contains(p6row)==true && ((memoryrect.getChildren().indexOf(p6row)== memoryrect.getChildren().size()-1)==false) && (((memoryrect.getChildren().indexOf(p6row)==memoryrect.getChildren().indexOf(empty1)-1))||((memoryrect.getChildren().indexOf(p6row)==memoryrect.getChildren().indexOf(empty2)-1))||((memoryrect.getChildren().indexOf(p6row)==memoryrect.getChildren().indexOf(empty3)-1)))){
                        double size = p6row.getHeight();
                        memoryrect.getChildren().remove(p6row);
                        empty1.setPrefHeight(empty1.getHeight()+size);
                        
                    }else if(memoryrect.getChildren().contains(p6row)==true && (memoryrect.getChildren().indexOf(p6row)== memoryrect.getChildren().size()-1)==false){
                        double size = p6row.getHeight();
                        empty1.setPrefHeight(size);
                        tempidx=memoryrect.getChildren().indexOf(p6row);
                        memoryrect.getChildren().remove(p6row);
                        memoryrect.getChildren().add(tempidx,empty1);
                        
                    }
                } else if (choice==7){
                    if(memoryrect.getChildren().contains(p7row)==true && (memoryrect.getChildren().indexOf(p7row)== memoryrect.getChildren().size()-1)){
                        double size = p7row.getHeight();
                        totalsize=totalsize-size;
                        memoryrect.getChildren().remove(p7row);
                    } else if (memoryrect.getChildren().contains(p7row)==false && text.contains("P7")){
                        String[] values;
                        values = text.split("<");
                        List<String> valueslist = new ArrayList<>(Arrays.asList(values));
                        for(String listItem : valueslist){
                           if(listItem.contains("P7")){
                               tempidx=valueslist.indexOf(listItem);
                               values[tempidx] = "";
                               text=Arrays.toString(values);
                               text=text.replace("[","");
                               text=text.replace("]","");
                               text=text.replace(",","");
                               text=text.replace(" ","");
                               waiting.setText(text);
                        }
                      }
                    } else if(memoryrect.getChildren().contains(p7row)==true && ((memoryrect.getChildren().indexOf(p7row)== memoryrect.getChildren().size()-1)==false) && (((memoryrect.getChildren().indexOf(p7row)==memoryrect.getChildren().indexOf(empty1)-1))||((memoryrect.getChildren().indexOf(p7row)==memoryrect.getChildren().indexOf(empty2)-1))||((memoryrect.getChildren().indexOf(p7row)==memoryrect.getChildren().indexOf(empty3)-1)))){
                        double size = p7row.getHeight();
                        memoryrect.getChildren().remove(p7row);
                        empty1.setPrefHeight(empty1.getHeight()+size);
                        
                    }else if(memoryrect.getChildren().contains(p7row)==true && (memoryrect.getChildren().indexOf(p7row)== memoryrect.getChildren().size()-1)==false){
                        double size = p7row.getHeight();
                        empty1.setPrefHeight(size);
                        tempidx=memoryrect.getChildren().indexOf(p7row);
                        memoryrect.getChildren().remove(p7row);
                        memoryrect.getChildren().add(tempidx,empty1);
                        
                    }
                } else if (choice==8){
                    if(memoryrect.getChildren().contains(p8row)==true && (memoryrect.getChildren().indexOf(p8row)== memoryrect.getChildren().size()-1)){
                        double size = p8row.getHeight();
                        totalsize=totalsize-size;
                        memoryrect.getChildren().remove(p8row);
                    } else if (memoryrect.getChildren().contains(p8row)==false && text.contains("P8")){
                        String[] values;
                        values = text.split("<");
                        List<String> valueslist = new ArrayList<>(Arrays.asList(values));
                        for(String listItem : valueslist){
                           if(listItem.contains("P8")){
                               tempidx=valueslist.indexOf(listItem);
                               values[tempidx] = "";
                               text=Arrays.toString(values);
                               text=text.replace("[","");
                               text=text.replace("]","");
                               text=text.replace(",","");
                               text=text.replace(" ","");
                               waiting.setText(text);
                        }
                      }
                    } else if(memoryrect.getChildren().contains(p8row)==true && ((memoryrect.getChildren().indexOf(p8row)== memoryrect.getChildren().size()-1)==false) && (((memoryrect.getChildren().indexOf(p8row)==memoryrect.getChildren().indexOf(empty1)-1))||((memoryrect.getChildren().indexOf(p8row)==memoryrect.getChildren().indexOf(empty2)-1))||((memoryrect.getChildren().indexOf(p8row)==memoryrect.getChildren().indexOf(empty3)-1)))){
                        double size = p8row.getHeight();
                        memoryrect.getChildren().remove(p8row);
                        empty1.setPrefHeight(empty1.getHeight()+size);
                        
                    }else if(memoryrect.getChildren().contains(p8row)==true && (memoryrect.getChildren().indexOf(p8row)== memoryrect.getChildren().size()-1)==false){
                        double size = p8row.getHeight();
                        empty1.setPrefHeight(size);
                        tempidx=memoryrect.getChildren().indexOf(p8row);
                        memoryrect.getChildren().remove(p8row);
                        memoryrect.getChildren().add(tempidx,empty1);
                        
                    }
                }
            }
        });
        
        //Button that compacts memory
        //It will remove empty spaces and if there's space in memory it will
        //add a process from the waiting queue to memory
        //Button ends at line 1393
        Button compactbtn = new Button();
        compactbtn.setText("Compact Memory");
        compactbtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Memory Compacted");
                memoryrect.getChildren().remove(empty1);
                memoryrect.getChildren().remove(empty2);
                memoryrect.getChildren().remove(empty3);
                memoryrect.getChildren().remove(empty4);
                memoryrect.getChildren().remove(empty5);
                memoryrect.getChildren().remove(empty6);
                totalsize=totalsize-empty1.getHeight();
                totalsize=totalsize-empty2.getHeight();
                totalsize=totalsize-empty3.getHeight();
                totalsize=totalsize-empty4.getHeight();
                totalsize=totalsize-empty5.getHeight();
                totalsize=totalsize-empty6.getHeight();
                String text=waiting.getText();
                String[] values;
                        values = text.split("<");
                        List<String> valueslist = new ArrayList<>(Arrays.asList(values));
                        for(String listItem : valueslist){
                           if(listItem.contains("P1")){ 
                             tempidx=valueslist.indexOf(listItem);
                             String name=values[tempidx];
                             values[tempidx] = "";
                             text=Arrays.toString(values);
                             text=text.replace("[","");
                             text=text.replace("]","");
                             //text=text.replace(">","");
                             text=text.replace(",","");
                             text=text.replace("  ","");
                             name=name.replace(">","");
                             waiting.setText(text);
                             String process=name.substring(0,2);
                             int size= Integer.valueOf(name.substring(3));
                             temp=totalsize+(size*.125);
                             if(memoryrect.getChildren().contains(p1row)==false && temp<=512){
                                 totalsize=totalsize+(size*.125);
                                 p1row.setPrefHeight(size*.125);// prefHeight
                                 memoryrect.getChildren().addAll(p1row);
                             } else if(memoryrect.getChildren().contains(p1row)==false && temp>512){
                                 text=text+"<P1 "+size+">";
                                 waiting.setText(text);
                             }
                           } else if(listItem.contains("P2")){ 
                             tempidx=valueslist.indexOf(listItem);
                             String name=values[tempidx];
                             values[tempidx] = "";
                             text=Arrays.toString(values);
                             text=text.replace("[","");
                             text=text.replace("]","");
                             //text=text.replace(">","");
                             text=text.replace(",","");
                             text=text.replace("  ","");
                             name=name.replace(">","");
                             waiting.setText(text);
                             String process=name.substring(0,2);
                             int size= Integer.valueOf(name.substring(3));
                             temp=totalsize+(size*.125);
                             if(memoryrect.getChildren().contains(p2row)==false && temp<=512){
                                 totalsize=totalsize+(size*.125);
                                 p2row.setPrefHeight(size*.125);// prefHeight
                                 memoryrect.getChildren().addAll(p2row);
                             } else if(memoryrect.getChildren().contains(p2row)==false && temp>512){
                                 text=text+"<P2 "+size+">";
                                 waiting.setText(text);
                             }
                           } else if(listItem.contains("P3")){ 
                             tempidx=valueslist.indexOf(listItem);
                             String name=values[tempidx];
                             values[tempidx] = "";
                             text=Arrays.toString(values);
                             text=text.replace("[","");
                             text=text.replace("]","");
                             //text=text.replace(">","");
                             text=text.replace(",","");
                             text=text.replace("  ","");
                             name=name.replace(">","");
                             waiting.setText(text);
                             String process=name.substring(0,2);
                             int size= Integer.valueOf(name.substring(3));
                             temp=totalsize+(size*.125);
                             if(memoryrect.getChildren().contains(p3row)==false && temp<=512){
                                 totalsize=totalsize+(size*.125);
                                 p3row.setPrefHeight(size*.125);// prefHeight
                                 memoryrect.getChildren().addAll(p3row);
                             } else if(memoryrect.getChildren().contains(p3row)==false && temp>512){
                                 text=text+"<P3 "+size+">";
                                 waiting.setText(text);
                             }
                           } else if(listItem.contains("P4")){ 
                             tempidx=valueslist.indexOf(listItem);
                             String name=values[tempidx];
                             values[tempidx] = "";
                             text=Arrays.toString(values);
                             text=text.replace("[","");
                             text=text.replace("]","");
                             //text=text.replace(">","");
                             text=text.replace(",","");
                             text=text.replace("  ","");
                             name=name.replace(">","");
                             waiting.setText(text);
                             String process=name.substring(0,2);
                             int size= Integer.valueOf(name.substring(3));
                             temp=totalsize+(size*.125);
                             if(memoryrect.getChildren().contains(p4row)==false && temp<=512){
                                 totalsize=totalsize+(size*.125);
                                 p4row.setPrefHeight(size*.125);// prefHeight
                                 memoryrect.getChildren().addAll(p4row);
                             } else if(memoryrect.getChildren().contains(p4row)==false && temp>512){
                                 text=text+"<P4 "+size+">";
                                 waiting.setText(text);
                             }
                           } else if(listItem.contains("P5")){ 
                             tempidx=valueslist.indexOf(listItem);
                             String name=values[tempidx];
                             values[tempidx] = "";
                             text=Arrays.toString(values);
                             text=text.replace("[","");
                             text=text.replace("]","");
                             //text=text.replace(">","");
                             text=text.replace(",","");
                             text=text.replace("  ","");
                             name=name.replace(">","");
                             waiting.setText(text);
                             String process=name.substring(0,2);
                             int size= Integer.valueOf(name.substring(3));
                             temp=totalsize+(size*.125);
                             if(memoryrect.getChildren().contains(p5row)==false && temp<=512){
                                 totalsize=totalsize+(size*.125);
                                 p5row.setPrefHeight(size*.125);// prefHeight
                                 memoryrect.getChildren().addAll(p5row);
                             } else if(memoryrect.getChildren().contains(p5row)==false && temp>512){
                                 text=text+"<P5 "+size+">";
                                 waiting.setText(text);
                             }
                           } else if(listItem.contains("P6")){ 
                             tempidx=valueslist.indexOf(listItem);
                             String name=values[tempidx];
                             values[tempidx] = "";
                             text=Arrays.toString(values);
                             text=text.replace("[","");
                             text=text.replace("]","");
                             //text=text.replace(">","");
                             text=text.replace(",","");
                             text=text.replace("  ","");
                             name=name.replace(">","");
                             waiting.setText(text);
                             String process=name.substring(0,2);
                             int size= Integer.valueOf(name.substring(3));
                             temp=totalsize+(size*.125);
                             if(memoryrect.getChildren().contains(p6row)==false && temp<=512){
                                 totalsize=totalsize+(size*.125);
                                 p6row.setPrefHeight(size*.125);// prefHeight
                                 memoryrect.getChildren().addAll(p6row);
                             } else if(memoryrect.getChildren().contains(p6row)==false && temp>512){
                                 text=text+"<P6 "+size+">";
                                 waiting.setText(text);
                             }
                           } else if(listItem.contains("P7")){ 
                             tempidx=valueslist.indexOf(listItem);
                             String name=values[tempidx];
                             values[tempidx] = "";
                             text=Arrays.toString(values);
                             text=text.replace("[","");
                             text=text.replace("]","");
                             //text=text.replace(">","");
                             text=text.replace(",","");
                             text=text.replace("  ","");
                             name=name.replace(">","");
                             waiting.setText(text);
                             String process=name.substring(0,2);
                             int size= Integer.valueOf(name.substring(3));
                             temp=totalsize+(size*.125);
                             if(memoryrect.getChildren().contains(p7row)==false && temp<=512){
                                 totalsize=totalsize+(size*.125);
                                 p7row.setPrefHeight(size*.125);// prefHeight
                                 memoryrect.getChildren().addAll(p7row);
                             } else if(memoryrect.getChildren().contains(p7row)==false && temp>512){
                                 text=text+"<P7 "+size+">";
                                 waiting.setText(text);
                             } 
                           } else if(listItem.contains("P8")){ 
                             tempidx=valueslist.indexOf(listItem);
                             String name=values[tempidx];
                             values[tempidx] = "";
                             text=Arrays.toString(values);
                             text=text.replace("[","");
                             text=text.replace("]","");
                             //text=text.replace(">","");
                             text=text.replace(",","");
                             text=text.replace("  ","");
                             name=name.replace(">","");
                             waiting.setText(text);
                             String process=name.substring(0,2);
                             int size= Integer.valueOf(name.substring(3));
                             temp=totalsize+(size*.125);
                             if(memoryrect.getChildren().contains(p8row)==false && temp<=512){
                                 totalsize=totalsize+(size*.125);
                                 p8row.setPrefHeight(size*.125);// prefHeight
                                 memoryrect.getChildren().addAll(p8row);
                             } else if(memoryrect.getChildren().contains(p8row)==false && temp>512){
                                 text=text+"<P8 "+size+">";
                                 waiting.setText(text);
                             }
                           }
                        }
            }
        });

        //adds objects to the GUI
        root.add(memlbl,0,0);
        root.add(oslbl,0,1);
        root.add(row1,0,3);
        root.add(row2,0,4);
        root.add(nothing1,0,5);
        root.add(addbtn,0,6);
        root.add(nothing2,0,7);
        root.add(removebtn,0,8);
        root.add(nothing3,0,9);
        root.add(compactbtn,0,10);
        root.add(nothing4,0,11);
        root.add(memoryrect,2,0,1,11);
        root.add(waiting,0,12,12,1);
        
        root.setStyle(" -fx-background-color: #40E0D0; -fx-border-color: black; -fx-border-width: 10;");
        Scene scene = new Scene(root, 600, 650);
        
        primaryStage.setTitle("Memory Manager Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
