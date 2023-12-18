/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubespbo2;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author asus
 */
public class mainFormController implements Initializable {

    @FXML
    private BarChart<?, ?> dashboard_IncomeandExpenses;

    @FXML
    private PieChart dashboard_ExpensesChart;

    @FXML
    private AnchorPane addbalance_form;

    @FXML
    private Button addbalance_btn;

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
    private TableColumn<productData, Double> inventory_col_amount;

    @FXML
    private TableColumn<productData, String> inventory_col_category;

    @FXML
    private TableColumn<productData, String> inventory_col_comment;

    @FXML
    private TableColumn<productData, Date> inventory_col_date;

    @FXML
    private TableColumn<productData, String> inventory_col_type;

    @FXML
    private Button inventory_deletebtn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private TableView<productData> inventory_tableView;

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

    @FXML
    private Label dashboard_balance;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button addbalance_btn2;

    @FXML
    private TextField addbalance_txtfield;

    @FXML
    private Button backtodashboard_btn;

    @FXML
    private Label dashboard_expense;

    @FXML
    private Label dashboard_income;

    private Alert alert;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void dashboardExpenses() {

        String insertExpenses = "SELECT SUM(amount) FROM product WHERE type='Expense' ";

        connect = database.connectDB();

        try {
            float te = 0;
            prepare = connect.prepareStatement(insertExpenses);
            result = prepare.executeQuery();

            if (result.next()) {
                te = result.getFloat("SUM(amount)");
            }
            dashboard_expense.setText("Rp. " + te);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardIncome() {

        String insertIncome = "SELECT SUM(amount) FROM product WHERE type='income' ";

        connect = database.connectDB();

        try {
            float ti = 0;
            prepare = connect.prepareStatement(insertIncome);
            result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getFloat("SUM(amount)");
            }
            dashboard_income.setText("Rp. " + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardBalance() {
        String sql = "SELECT SUM(bal) FROM balance";
        String insertIncome = "SELECT SUM(amount) FROM product WHERE type='income' ";
        String insertExpenses = "SELECT SUM(amount) FROM product WHERE type='Expense' ";

        connect = database.connectDB();

        float total_all = 0;

        try {
            float tb = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                tb = result.getFloat("SUM(bal)");
            }
//            dashboard_balance.setText("Rp. " + tb);
            total_all = total_all + tb;

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            float ti = 0;
            prepare = connect.prepareStatement(insertIncome);
            result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getFloat("SUM(amount)");
            }

            total_all = total_all + ti;

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            float te = 0;
            prepare = connect.prepareStatement(insertExpenses);
            result = prepare.executeQuery();

            if (result.next()) {
                te = result.getFloat("SUM(amount)");
            }

            total_all = total_all - te;

        } catch (Exception e) {
            e.printStackTrace();
        }

        dashboard_balance.setText("Rp. " + total_all);

    }

//    public void dashboardExpenseStructure() {
//        ObservableList<PieChartdata> pieChartData = FXCollections.observableArrayList();
//
//        String sql = "SELECT category, SUM(amount) WHERE type = 'Expense' FROM product GROUP BY category";
//
//        connect = database.connectDB();
//
//        try {
//
//            prepare = connect.prepareStatement(sql);
//            result = prepare.executeQuery();
//
//            while (result.next()) {
//
//                PieChart.data = new PieChart(result.getInt("id"),
//                        result.getString("prod_id"),
//                        result.getString("prod_name"),
//                        result.getString("type"),
//                        result.getInt("stock"),
//                        result.getDouble("price"),
//                        result.getString("status"),
//                        result.getString("image"),
//                        result.getDate("date"));
//
//                listData.add(prodData);
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    public void dashboardIncomeandExpensesChart() {
        dashboard_IncomeandExpenses.getData().clear();

        String sql = "SELECT type, SUM(amount) FROM product GROUP BY type";
        connect = database.connectDB();
        XYChart.Series chart = new XYChart.Series();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));

            }

            dashboard_IncomeandExpenses.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inventoryAddBtn() {

        if (inventory_amount.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_category.getSelectionModel().getSelectedItem() == null) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            connect = database.connectDB();

            try {

                String insertData = "INSERT INTO product "
                        + "(category, type, amount, comment,date) "
                        + "VALUES(?,?,?,?,?)";

                prepare = connect.prepareStatement(insertData);
                prepare.setString(1, (String) inventory_category.getSelectionModel().getSelectedItem());
                prepare.setString(2, (String) inventory_type.getSelectionModel().getSelectedItem());
                prepare.setString(3, inventory_amount.getText());
                prepare.setString(4, inventory_comment.getText());

                // TO GET CURRENT DATE
                java.util.Date date = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(5, String.valueOf(sqlDate));

                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();

                inventoryShowData();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inventoryUpdateBtn() {

        if (inventory_amount.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_category.getSelectionModel().getSelectedItem() == null
                || data.id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            String updateData = "UPDATE product SET "
                    + "amount = '" + inventory_amount.getText() + "', type = '"
                    + inventory_type.getSelectionModel().getSelectedItem() + "', category = '"
                    + inventory_category.getSelectionModel().getSelectedItem() + "', date = '"
                    + data.date + "' WHERE id = " + data.id;

            connect = database.connectDB();

            try {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE History?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void inventoryDeleteBtn() {
        if (data.id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE this history?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM product WHERE id = " + data.id;
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        }
    }

    public void inventoryClearBtn() {

        inventory_amount.setText("");
        inventory_comment.setText("");
        inventory_type.getSelectionModel().clearSelection();
        inventory_category.getSelectionModel().clearSelection();
    }

    public ObservableList<productData> inventoryDataList() {

        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prodData;

            while (result.next()) {

                prodData = new productData(result.getInt("id"),
                        result.getString("type"),
                        result.getString("category"),
                        result.getDouble("amount"),
                        result.getString("comment"),
                        result.getDate("date"));

                listData.add(prodData);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    // TO SHOW DATA ON OUR TABLE
    private ObservableList<productData> inventoryListData;

    public void inventoryShowData() {
        inventoryListData = inventoryDataList();

        inventory_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        inventory_col_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_tableView.setItems(inventoryListData);

    }

    public void inventorySelectData() {

        productData prodData = inventory_tableView.getSelectionModel().getSelectedItem();
        int num = inventory_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        inventory_amount.setText(String.valueOf(prodData.getAmount()));
        inventory_comment.setText(prodData.getComment());

        data.date = String.valueOf(prodData.getDate());
        data.id = prodData.getId();
    }

    private String[] typeList = {"Income", "Expense", "Transfer"};

    public void inventoryTypeList() {

        List<String> typeL = new ArrayList<>();

        for (String data : typeList) {
            typeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(typeL);
        inventory_type.setItems(listData);
    }

    private String[] categoryList = {"Food & Drinks", "Shopping", "Housing", "Transportation", "Vehicle", "Life & Entertainment", "Communication, PC"};

    public void inventoryCategoryList() {

        List<String> categoryL = new ArrayList<>();

        for (String data : categoryList) {
            categoryL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(categoryL);
        inventory_category.setItems(listData);

    }

    public void addBalanceBtn2() {
        if (addbalance_txtfield.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {

            Connection cn = database.connectDB();

            try {

                String deleteBalance = "DELETE FROM balance";
                
                String deleteRecords = "DELETE FROM product";

                String insertBalance = "INSERT INTO balance "
                        + "(bal) "
                        + "VALUES(?)";

                prepare = cn.prepareStatement(deleteBalance);
                prepare.executeUpdate();
                
                prepare = cn.prepareStatement(deleteRecords);
                prepare.executeUpdate();

                prepare = cn.prepareStatement(insertBalance);
                prepare.setString(1, addbalance_txtfield.getText());

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully add balance!");
                alert.showAndWait();

                dashboard_form.setVisible(true);
                inventory_form.setVisible(false);
                addbalance_form.setVisible(false);

                dashboardBalance();
                dashboardExpenses();
                dashboardIncome();
                dashboardIncomeandExpensesChart();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            inventory_form.setVisible(false);
            addbalance_form.setVisible(false);

            dashboardBalance();
            dashboardExpenses();
            dashboardIncome();
            dashboardIncomeandExpensesChart();

        } else if (event.getSource() == inventory_btn) {
            dashboard_form.setVisible(false);
            addbalance_form.setVisible(false);
            inventory_form.setVisible(true);

            inventoryTypeList();
            inventoryCategoryList();
            inventoryShowData();
        } else if (event.getSource() == addbalance_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            addbalance_form.setVisible(true);

        }

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

                stage.setTitle("Dompet");
                stage.setMinHeight(445);
                stage.setMinWidth(610);
                stage.setMaxHeight(445);
                stage.setMaxWidth(610);
                stage.setResizable(false);

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
        inventoryCategoryList();
        inventoryShowData();
        dashboardBalance();
        dashboardExpenses();
        dashboardIncome();
        dashboardIncomeandExpensesChart();

    }

}
