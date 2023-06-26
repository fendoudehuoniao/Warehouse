@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product createProduct(Product product) {
        productMapper.insert(product);
        return product;
    }

    @Override
    public Product updateProduct(String id, Product product) {
        product.setId(id);
        productMapper.updateById(product);
        return product;
    }

    @Override
    public boolean deleteProduct(String id) {
        return productMapper.deleteById(id) > 0;
    }

    @Override
    public Page<Product> getProducts(String name, int pageNo, int pageSize) {
        IPage<Product> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        //构建查询条件
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
                    .orderByDesc("createTime");
        productMapper.selectPage(page, queryWrapper);
        return new Page<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }
    
}
