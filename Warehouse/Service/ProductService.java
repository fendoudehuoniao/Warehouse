public interface ProductService {

    Product createProduct(Product product);

    Product updateProduct(long productId, Product product);
    
    boolean deleteProduct(long productId);

    Page<Product> searchProducts(String name, int page, int pageSize);
    
}
