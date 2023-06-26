@RestController
@RequestMapping("/ledger")
public class LedgerController {
    private final LedgerService ledgerService;

    @Autowired
    private LedgerService ledgerService;

    @PostMapping("/outStockTime")
    public ResponseEntity<Integer> performoutStockTime(@RequestParam String productId,
                                                   @RequestParam int quantity,
                                                   @RequestParam String storeId) {
        int stockQuantity = ledgerService.stockOut(productId, quantity, storeId);
        return ResponseEntity.ok(stockQuantity);
    }

    @PostMapping("/inStockTime")
    public ResponseEntity<Integer> performinStockTime(@RequestParam String productId,
                                                  @RequestParam int quantity,
                                                  @RequestParam String storeId) {
        int stockQuantity = ledgerService.stockIn(productId, quantity, storeId);
        return ResponseEntity.ok(stockQuantity);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Ledger>> searchLedger(@RequestParam(required = false) String productName,
                                                     @RequestParam(required = false) LocalDate startDate,
                                                     @RequestParam int page,
                                                     @RequestParam int pageSize) {
        Page<Ledger> ledgerPage = ledgerService.getProductLedgers(productName, startDate, page, pageSize);
        return ResponseEntity.ok(ledgerPage);
    }

    @GetMapping("/stock/{productId}")
    public ResponseEntity<Integer> getTotal(@PathVariable String productId) {
        int Total = ledgerService.getProductStatistics(productId);
        return ResponseEntity.ok(Total);
    }
}
