package com.example.chatapp1.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.chatapp1.MessageWithReceiverAndConversationInfo
import com.example.chatapp1.MessageWithUsersAndConversation

@Dao
interface AppDao {
    @Transaction
    @Query(
        """
SELECT
    messages.id AS message_id,
    messages.conversationId AS message_conversationId,
    messages.senderId AS message_senderId,
    messages.text AS message_text,
    messages.timestamp AS message_timestamp,
    messages.isDeletedByUser1 AS message_isDeletedByUser1,
    messages.isDeletedByUser2 AS message_isDeletedByUser2,
    sender.id AS user1_id,
    sender.name AS user1_name,
    sender.avatar AS user1_avatar,
    receiver.id AS user2_id,
    receiver.name AS user2_name,
    receiver.avatar AS user2_avatar,
    conversations.id AS conversation_id,
    conversations.userId1 AS conversation_userId1,
    conversations.userId2 AS conversation_userId2
FROM
    messages
INNER JOIN
    users AS sender ON messages.senderId = sender.id
INNER JOIN
    conversations ON messages.conversationId = conversations.id
INNER JOIN
    users AS receiver ON (
        CASE
            WHEN conversations.userId1 = messages.senderId THEN conversations.userId2
            ELSE conversations.userId1
        END
    ) = receiver.id
WHERE
    messages.conversationId = :conversationId AND
    (
        (conversations.userId1 = :userId AND NOT messages.isDeletedByUser1) OR
        (conversations.userId2 = :userId AND NOT messages.isDeletedByUser2)
    )
"""
    )
    fun getMessagesWithUsersAndConversation(
        conversationId: Int, userId: Int
    ): List<MessageWithUsersAndConversation>

    @Transaction
    @Query(
        """
SELECT
    m.id AS message_id,
    m.conversationId AS message_conversationId,
    m.senderId AS message_senderId,
    m.text AS message_text,
    m.timestamp AS message_timestamp,
    m.isDeletedByUser1 AS message_isDeletedByUser1,
    m.isDeletedByUser2 AS message_isDeletedByUser2,
    u.id AS receiver_id,
    u.name AS receiver_name,
    u.avatar AS receiver_avatar,
    c.id AS conversation_id,
    c.userId1 AS conversation_userId1,
    c.userId2 AS conversation_userId2
FROM (
    SELECT *, MAX(timestamp) AS max_timestamp
    FROM messages
    WHERE conversationId IN (
        SELECT id
        FROM conversations
        WHERE userId1 = :senderId OR userId2 = :senderId
    )
    GROUP BY conversationId
) AS m
INNER JOIN conversations c ON m.conversationId = c.id
INNER JOIN users u ON (
    CASE
        WHEN c.userId1 = :senderId THEN c.userId2
        ELSE c.userId1
    END
) = u.id
WHERE m.timestamp = m.max_timestamp AND
    (
        (c.userId1 = :senderId AND NOT m.isDeletedByUser1) OR
        (c.userId2 = :senderId AND NOT m.isDeletedByUser2)
    )
"""
    )
    fun getLatestMessageWithReceiverInfo(senderId: Int): List<MessageWithReceiverAndConversationInfo>
}