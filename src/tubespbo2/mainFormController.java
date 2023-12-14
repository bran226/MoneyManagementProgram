/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubespbo2;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author asus
 */
public class mainFormController implements Initializable {

    @FXML
    private Button customers_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button inventory_addbtn;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button inventory_clearbtn;

    @FXML
    private TableColumn<?, ?> inventory_col_amount;

    @FXML
    private TableColumn<?, ?> inventory_col_category;

    @FXML
    private TableColumn<?, ?> inventory_col_comment;

    @FXML
    private TableColumn<?, ?> inventory_col_date;

    @FXML
    private TableColumn<?, ?> inventory_col_id;

    @FXML
    private TableColumn<?, ?> inventory_col_type;

    @FXML
    private Button inventory_deletebtn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private TableView<?> inventory_tableView;

    @FXML
    private Button inventory_updatebtn;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button menu_btn;

    @FXML
    private Label username;
    
    @FXML
    private ComboBox<?> inventory_type;
    
    @FXML
    private TextField inventory_comment;
    
    @FXML
    private TextField inventory_amount;
    
    @FXML
    private ComboBox<?> inventory_category;

    private Alert alert;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    
    private String[] typeList = {"Income", "Expense", "Transfer"};
    public void inventoryTypeList() {

        List<String> typeL = new ArrayList<>();

        for (String data : typeList) {
            typeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(typeL);
        inventory_type.setItems(listData);
    }
    
    private String[] categoryList = {"Available", "Unavailable"};
    
    public void inventoryStatusList() {
        
        List<String> categoryL = new ArrayList<>();
        
        for (String data : categoryList) {
            categoryL.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(categoryL);
        inventory_category.setItems(listData);
        
    }

    public void logout() {

        try {

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                // TO HIDE MAIN FORM 
                logout_btn.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM AND SHOW IT 
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Cafe Shop Management System");

                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayUsername() {

        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);

        username.setText(user);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        inventoryTypeList();
        inventoryStatusList();

    }

}
