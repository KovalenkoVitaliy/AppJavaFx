package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DatabaseHandler;
import sample.User;
import sample.animation.Shape;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button authSignInButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    void initialize() {

        authSignInButton.setOnAction(actionEvent -> {
            String textLogin = login_field.getText().trim();
            String textPassword = password_field.getText().trim();
            if (!textLogin.equals("") && !textPassword.equals("")) {
                loginUser(textLogin, textPassword);
            } else {
                System.out.println("Login and password is empty");
            }
        });

        loginSignUpButton.setOnAction(actionEvent -> {
            loginSignUpButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/configFXML/signUp.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

        });
    }

    private void loginUser(String textLogin, String textPassword) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        User user = new User();
        user.setUsername(textLogin);
        user.setPassword(textPassword);

        ResultSet resultSet = databaseHandler.getUser(user);
        int count = 0;
        try {
            while (resultSet.next()) {
                count++;
            }
        } catch (Exception ex) {
        }

        if (count >=1) {
            System.out.println("Such user is logined in system");
            System.out.println("Count of such users with equals Login and Password is " + count);
            authSignInButton.getScene().getWindow().hide();
            openWindowByUser();
        } else {
            System.out.println("Such user is not exist in system");
            Shape shapeLogin = new Shape(login_field);
            Shape shapePassw = new Shape(password_field);
            shapeLogin.playAnimatin();
            shapePassw.playAnimatin();
        }
    }

    public void openWindowByUser() {
        FXMLLoader fxmIn =new FXMLLoader();
        fxmIn.setLocation(getClass().getResource("/sample/configFXML/app.fxml"));
        try {
            fxmIn.load();
        } catch (IOException e) {
        }
        Parent rootUser = fxmIn.getRoot();
        Stage stageUser = new Stage();
        stageUser.setScene(new Scene(rootUser));
        stageUser.showAndWait();
    }
}
