package zhang.nz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("scenebuilder.fxml"));
        primaryStage.setTitle("EddyQuake");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    /**    Scanner reader = new Scanner(System.in);
        System.out.print("Enter minimum MMI (Modified Mercalli Intensity Scale), 1 - 8: ");
        int mmi = reader.nextInt();
        GeoNet.showQuakes(mmi);
        System.out.print("Enter Quake ID to show more information: ");
        int id = reader.nextInt();
        GeoNet.quakeDetail(id); **/
    }
}
