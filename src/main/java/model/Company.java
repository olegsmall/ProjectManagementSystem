package model;

public class Company {
    private int id;
    private String companyName;

    public Company(String companyName) {
        this.companyName = companyName;
    }

    public Company(int id, String companyName) {
        this(companyName);
        this.id = id;
    }

    public void setId(int id) {
        if (this.id == 0) {
            this.id = id;
        }
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
