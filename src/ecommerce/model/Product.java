package ecommerce.model;

public class Product {
    private String asin;
    private String title;
    private double stars;
    private int reviews;
    private double price;
    private int categoryId;

    public Product(String asin, String title, double stars, int reviews, double price, int categoryId) {
        this.asin = asin;
        this.title = title;
        this.stars = stars;
        this.reviews = reviews;
        this.price = price;
        this.categoryId = categoryId;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "asin='" + asin + '\'' +
                ", title='" + title + '\'' +
                ", stars=" + stars +
                ", reviews=" + reviews +
                ", price=" + price +
                ", categoryId=" + categoryId +
                '}';
    }
}