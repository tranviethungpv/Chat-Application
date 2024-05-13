package com.example.chatapp1;

parcelable Conversation;
parcelable Message;
parcelable User;
parcelable ConversationWithUserAndLatestMessage;
parcelable MessageWithUsersAndConversation;
parcelable MessageWithReceiverAndConversationInfo;

interface IChatService {
    long insertConversation(inout Conversation conversation);
    int updateConversation(inout Conversation conversation);
    int deleteConversation(inout Conversation conversation);
    List<Conversation> getAllConversations();
    Conversation getConversation(int userId1, int userId2);
    int hideMessagesInConversation(inout Conversation conversation, int userId);

    long insertMessage(inout Message message);
    int updateMessage(inout Message message);
    int deleteMessage(inout Message message);
    List<Message> getAllMessages();

    long insertUser(inout User user);
    int updateUser(inout User user);
    int deleteUser(inout User user);
    List<User> getAllUsers();
    User getUserById(int id);

    List<MessageWithUsersAndConversation> getMessagesWithUsersAndConversation(int conversationId, int userId);
    List<MessageWithReceiverAndConversationInfo> getMessagesWithReceiverAndConversationInfo(int userId);
}