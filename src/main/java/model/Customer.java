package model;

public class Customer {
    private int id;
    private String customerName;

    public Customer(String customerName) {
        this.customerName = customerName;
    }

    public Customer(int id, String customerName) {
        this(customerName);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id == 0) {
            this.id = id;
        }
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
