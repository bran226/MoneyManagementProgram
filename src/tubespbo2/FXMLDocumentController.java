/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package tubespbo2;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author asus
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane su_registfform;

    @FXML
    private Hyperlink si_forgotpass;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private Button si_loginbtn;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_Createbtn;

    @FXML
    private Button side_alreadyHave;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private Button su_signupbtn;

    @FXML
    private TextField su_username;
    
    @FXML
    private PasswordField su_confirmpassword;

    private ResultSet result;

    @FXML
    private Hyperlink backtologin;
    
    private Alert alert;
    private PreparedStatement prepare;
    private Connection cn;
    private ResultSet rs;

    public void loginBtn() {

        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/Password");
            alert.showAndWait();
        } else {

            String selctData = "SELECT username, password FROM employee WHERE username = ? and password = ?";

            Connection cn = database.connectDB();

            try {

                prepare = cn.prepareStatement(selctData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());

                rs = prepare.executeQuery();
                // IF SUCCESSFULLY LOGIN, THEN PROCEED TO ANOTHER FORM WHICH IS OUR MAIN FORM 
                if (rs.next()) {
                    // TO GET THE USERNAME THAT USER USED
                    data.username = si_username.getText();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    // LINK YOUR MAIN FORM
                    Parent root = FXMLLoader.load(getClass().getResource("mainForm.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setTitle("Dompet");
                    stage.setMinWidth(1140);
                    stage.setMinHeight(650);
                    stage.setMaxWidth(1130);
                    stage.setMaxHeight(650);
                    stage.setResizable(false);

                    stage.setScene(scene);
                    stage.show();

                    si_loginbtn.getScene().getWindow().hide();

                } else { // IF NOT, THEN THE ERROR MESSAGE WILL APPEAR
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void regBtn() {

        if (su_username.getText().isEmpty() || su_password.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {

            Connection cn = database.connectDB();

            try {
                // CHECK IF THE USERNAME IS ALREADY RECORDED
                String checkUsername = "SELECT username FROM employee WHERE username = '"
                        + su_username.getText() + "'";
                prepare = cn.prepareStatement(checkUsername);
                rs = prepare.executeQuery();

                if (rs.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Username " + "'" + su_username.getText() + "'" + " is already taken");
                    alert.showAndWait();
                } else if (su_password.getText().length() < 8) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Password, atleast 8 characters are needed");
                    alert.showAndWait();
                }else if (!su_confirmpassword.getText().equals(su_password.getText())){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Passwords do not match");
                    alert.showAndWait();
                }else {
                    prepare = cn.prepareStatement("INSERT INTO employee (username, password, date) " + "VALUES(?,?,?)");
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(3, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully registered Account!");
                    alert.showAndWait();

                    su_username.setText("");
                    su_password.setText("");
                    su_confirmpassword.setText("");

                    su_registfform.setVisible(false);
                    si_loginForm.setVisible(true);

//                    TranslateTransition slider = new TranslateTransition();
//
//                    slider.setNode(side_form);
//
//                    slider.setToX(0);
//                    slider.setDuration(Duration.seconds(.5));
//
//                    slider.setOnFinished((ActionEvent e) -> {
//                        side_alreadyHave.setVisible(false);
//                        side_Createbtn.setVisible(true);
//                    });
//
//                    slider.play();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == side_Createbtn) {
            su_registfform.setVisible(true);
            si_loginForm.setVisible(false);
        } else if (event.getSource() == backtologin) {
            su_registfform.setVisible(false);
            si_loginForm.setVisible(true);
        }
//        TranslateTransition slider = new TranslateTransition();

//        if (event.getSource() == side_Createbtn) {
//            slider.setNode(side_form);
//            slider.setToX(300);
//            slider.setDuration(Duration.seconds(.5));
//
//            slider.setOnFinished((ActionEvent e) -> {
//                side_alreadyHave.setVisible(true);
//                side_Createbtn.setVisible(false);
//
//            });
//
//            slider.play();
//        } else if (event.getSource() == side_alreadyHave) {
//            slider.setNode(side_form);
//            slider.setToX(0);
//            slider.setDuration(Duration.seconds(.5));
//
//            slider.setOnFinished((ActionEvent e) -> {
//                side_alreadyHave.setVisible(false);
//                side_Createbtn.setVisible(true);
//            });
//
//            slider.play();
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
