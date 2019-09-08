package fpt.edu.RetailManagementSystem.persistent.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bill_input")
public class BillInput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "account_id")
    private Integer accountID;

    @Column(name = "supplier")
    private Integer supplier;

    @Column(name = "code")
    private String code;

    @Column(name = "total")
    private float total;

    @Column(name = "tax")
    private float tax;

    @Column(name = "time_created")
    private Date timeCreated;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "status")
    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}
