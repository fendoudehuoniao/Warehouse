@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Message createMessage(Message message) {
        messageMapper.insert(message);
        sendNotification(message);
        return message;
    }

    @Override
    public Page<Message> getMessageList(Date beforeOutTime, Date afterOutTime, int pageNo, int pageSize) {
        IPage<Message> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.le(beforeOutTime != null, "outTime", beforeOutTime)
                    .ge(afterOutTime != null, "outTime", afterOutTime)
                    .orderByDesc("outTime");
        messageMapper.selectPage(page, queryWrapper);
        return new Page<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    @Override
    public boolean readMessage(String messageId) {
        Message message = messageMapper.selectById(messageId);
        message.setReadStatus(1);
        messageMapper.updateById(message);
        return message;
    }

    private void sendNotification(Message message) {
        // 发送短信
    }

}