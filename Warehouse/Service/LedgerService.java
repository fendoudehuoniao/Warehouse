// 台账服务接口
public interface LedgerService {

    //出库
    int stockOut(String productId, int quantity, String storeId);
    //入库
    int stockIn(String productId, int quantity, String storeId);
    //查询
    Page<Ledger> getProductLedgers(String productName, Date inStockTime, int pageNo, int pageSize);
    //统计
    ProductStatistics getProductStatistics(String productId);

}