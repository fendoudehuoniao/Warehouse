// 台账服务实现类
@Service
public class LedgerServiceImpl implements LedgerService {

    @Autowired
    private LedgerMapper ledgerMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public int stockOut(String productId, int quantity, String storeId) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        int remainingQuantity = product.getQuantity() - quantity;
        if (remainingQuantity < 0) {
            throw new RuntimeException("Insufficient stock");
        }
        product.setStockQuantity(remainingQuantity);
        productMapper.updateById(product);

        Ledger ledger = new Ledger();
        ledger.setProductId(productId);
        ledger.setOutStockTime(new Date());
        ledger.setQuantity(quantity);
        ledger.setStoreId(storeId);
        ledgerMapper.insert(ledger);

        return remainingQuantity;
    }

    @Override
    public int stockIn(String productId, int quantity, String storeId) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        int newQuantity = product.getStockQuantity() + quantity;
        product.setStockQuantity(newQuantity);
        productMapper.updateById(product);

        Ledger ledger = new Ledger();
        ledger.setProductId(productId);
        ledger.setInStockTime(new Date());
        ledger.setQuantity(quantity);
        ledger.setStoreId(storeId);
        ledgerMapper.insert(ledger);

        return newQuantity;
    }

    @Override
    public Page<Ledger> getProductLedgers(String productName, Date inStockTime, int pageNo, int pageSize) {
        //根据名称获取商品id
        Product product = productMapper.selectByName(productName);
        String productId = product.getId();
        IPage<Ledger> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Ledger> queryWrapper = new QueryWrapper<>();
        //创建查询条件
        queryWrapper.eq("productId", productId)
                    .ge(inStockTime != null, "inStockTime", inStockTime)
                    .orderByDesc("inStockTime");
        ledgerMapper.selectPage(page, queryWrapper);
        return new Page<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    @Override
    public ProductStatistics getProductStatistics(String productId) {
        //根据商品id从两个表里分别获得数量和价格
        Product product = productMapper.selectById(productId);
        Ledger ledger = ledgerMapper.selectById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        double totalprice = product.getPrice()*ledger.getQuantity();
        
        JSONObject response = new JSONObject();
        response.put("quantity", ledger.getQuantity());
        response.put("totalprice", totalprice);

        return response;
    }
}