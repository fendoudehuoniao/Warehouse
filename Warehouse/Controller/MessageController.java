@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<Page<Message>> getMessages(@RequestParam(required = false) LocalDateTime beforeDateTime,
                                                        @RequestParam(required = false) LocalDateTime afterDateTime,
                                                        @RequestParam(defaultValue = "1") int pageNo,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        Page<Message> messagePage = messageService.getMessages(beforeDateTime, afterDateTime, pageNo, pageSize);
        return ResponseEntity.ok(messagePage);
    }

    @PostMapping("/{messageId}/read")
    public ResponseEntity<Void> readMessage(@PathVariable String messageId) {
        boolean success = messageService.readMessage(messageId);
        if (success) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
