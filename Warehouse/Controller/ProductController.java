@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product product = productService.createProduct(product);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Product product = productService.updateProduct(id, product);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        boolean success = productService.deleteProduct(id);
        if (success) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(required = false) String name,
                                                     @RequestParam(defaultValue = "1") int pageNo,
                                                     @RequestParam(defaultValue = "10") int pageSize) {
        Page<Product> products = productService.getProducts(name, pageNo, pageSize);
        return ResponseEntity.ok(products);
    }
}
