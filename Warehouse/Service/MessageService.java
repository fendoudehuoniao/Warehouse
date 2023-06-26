public interface MessageService {

    Message createMessage(Message message);

    Page<Message> getMessageList(Date beforeOutTime, Date afterOutTime, int pageNo, int pageSize);

    boolean readMessage(String messageId);

}