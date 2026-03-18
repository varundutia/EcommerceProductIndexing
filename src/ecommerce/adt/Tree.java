package ecommerce.adt;

import java.util.List;

public interface Tree<T> {
    void insert(T item);
    T search(String key);
    boolean delete(String key);
    int size();
    boolean isEmpty();
    int height();
    List<T> inorderTraversal();
}
