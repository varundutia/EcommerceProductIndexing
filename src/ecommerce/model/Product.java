package ecommerce.model;

/**
 * Represents a product in the e-commerce dataset.
 *
 * <p>The ASIN is used as the key for indexing in tree structures
 * such as BST and AVL.</p>
 */
public class Product {

    /** Unique product identifier (tree key). */
    private String asin;

    /** Product title. */
    private String title;

    /** Average rating. */
    private double stars;

    /** Number of reviews. */
    private int reviews;

    /** Product price. */
    private double price;

    /** Category identifier. */
    private int categoryId;

    /**
     * Constructs a Product.
     */
    public Product(String asin, String title, double stars, int reviews, double price, int categoryId) {
        this.asin = asin;
        this.title = title;
        this.stars = stars;
        this.reviews = reviews;
        this.price = price;
        this.categoryId = categoryId;
    }

    public String getAsin() { return asin; }

    public void setAsin(String asin) { this.asin = asin; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public double getStars() { return stars; }

    public void setStars(double stars) { this.stars = stars; }

    public int getReviews() { return reviews; }

    public void setReviews(int reviews) { this.reviews = reviews; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public int getCategoryId() { return categoryId; }

    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

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