/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubespbo2;

import java.sql.Date;

/**
 *
 * @author asus
 */
public class productData {

    private Integer id;
    private String type;
    private String category;
    private Double amount;
    private Date date;

    public productData(Integer id,String category, String type, Double amount, Date date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

}
