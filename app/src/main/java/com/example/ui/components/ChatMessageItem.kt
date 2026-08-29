package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.ChatMessageEntity
import com.example.ui.theme.BentoBorderSubtle
import com.example.ui.theme.BentoOnPrimaryContainer
import com.example.ui.theme.BentoPrimary
import com.example.ui.theme.BentoPrimaryContainer
import com.example.ui.theme.BentoSurface
import com.example.ui.theme.BentoSurfaceCard
import com.example.ui.theme.BentoSurfaceVariant
import com.example.ui.theme.BentoTextMuted
import com.example.ui.theme.BentoTextPrimary
import com.example.ui.theme.BentoTextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatMessageItem(
    message: ChatMessageEntity,
    isMe: Boolean,
    onSenderClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val timeStr = rememberFormattedTime(message.timestamp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        if (!isMe) {
            GamerAvatarEmblem(
                gamertag = message.senderName,
                skinAccent = BentoPrimary,
                size = 36,
                modifier = Modifier.clickable { onSenderClick() }
            )
            Spacer(modifier = Modifier.width(8.dp))
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

        Column(
            horizontalAlignment = if (isMe) Alignment.End else Alignment.Start,
            modifier = Modifier.weight(1f, fill = false)
        ) {
            // Sender info header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 3.dp)
            ) {
                Text(
                    text = if (isMe) "You" else message.senderName,
                    color = if (isMe) BentoPrimary else BentoTextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onSenderClick() }
                )
                Spacer(modifier = Modifier.width(4.dp))
                RankBadge(rank = message.senderRank)
                Spacer(modifier = Modifier.width(4.dp))
                RolePill(role = message.senderGameRole, accentColor = BentoPrimary)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = timeStr,
                    color = BentoTextMuted,
                    fontSize = 10.sp
                )
            }

            // Chat bubble in Bento style
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isMe) 16.dp else 4.dp,
                            bottomEnd = if (isMe) 4.dp else 16.dp
                        )
                    )
                    .background(if (isMe) BentoPrimary else BentoSurface)
                    .border(
                        1.dp,
                        if (isMe) BentoPrimary else BentoBorderSubtle,
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isMe) 16.dp else 4.dp,
                            bottomEnd = if (isMe) 4.dp else 16.dp
                        )
                    )
                    .padding(horizontal = 14.dp, vertical = 9.dp)
            ) {
                Text(
                    text = message.text,
                    color = if (isMe) Color.White else BentoTextPrimary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

        if (isMe) {
            Spacer(modifier = Modifier.width(8.dp))
            GamerAvatarEmblem(
                gamertag = message.senderName,
                skinAccent = BentoPrimary,
                size = 36
            )
        }
    }
}

@Composable
private fun rememberFormattedTime(timestamp: Long): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(Date(timestamp))
}

