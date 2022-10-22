import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.*;
import java.sql.*;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class ErwinDictionary extends Application {

    private Group titleGroup=new Group();
    int xLine=20;
    int yLine=20;
    DictionaryUsingHash dictionary;
    
    private Pane createContent(){
        Pane root=new Pane();
        root.setPrefSize(400, 400);
        root.getChildren().addAll(titleGroup);

        dictionary=new DictionaryUsingHash();

        Label meaningLabel=new Label("Meaning");
        meaningLabel.setTranslateX(xLine);
        meaningLabel.setTranslateY(yLine+30);

        TextField wordText = new TextField("Erwin");
        wordText.setTranslateX(xLine);
        wordText.setTranslateY(yLine);

        Button searchButton = new Button("Search");
        searchButton.setTranslateX(xLine+200);
        searchButton.setTranslateY(yLine);

        searchButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent){
                String word=wordText.getText();
                if(word.isBlank()){
                    meaningLabel.setText("Please enter a word");
                }
                else{
                    meaningLabel.setText(DictionaryUsingHash.findMeaning(word));
                }
            }
        });

        titleGroup.getChildren().addAll(wordText, searchButton, meaningLabel);
        return root;
    }

    
    public static void connectToDatabase(){
        final String DB_URL="jdbc:mysql://localhost:3306/erwindictionay";
        final String USER="root";
        final String PASS="";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(DB_URL,USER,PASS);
            java.sql.Statement st = con.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM `wordlist`");

            while(rs.next()){
                int id= rs.getInt("id");
                String word= rs.getString("word");
                String meaning= rs.getString("meaning");

                System.out.println(id+" "+" "+word+" "+meaning);
                DictionaryUsingHash.addword(word, meaning);
            }
            rs.close();

            con.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Database not connected......");
        }
    }
    
    
    public void start(Stage stage){
        Scene scene = new Scene(createContent());
        stage.setTitle("Erwin Dictionary");
        stage.setScene(scene);
        stage.show();
    }
   
    public static void main(String[] args) throws IOException {
        launch();
    }
}