package classes;

public class CategoryList {
    private int categoryid;
    private String name;

    public CategoryList(int categoryid, String name) {
        this.categoryid = categoryid;
        this.name = name;
    }

    public String toFileFormat() {
        return categoryid + ";" + name;
    }
}