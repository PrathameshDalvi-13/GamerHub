package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.ChatMessageEntity
import com.example.data.local.entities.GamerProfileEntity
import com.example.data.model.GameType
import com.example.ui.components.ChatMessageItem
import com.example.ui.components.GamerAvatarEmblem
import com.example.ui.theme.BentoAccentGreen
import com.example.ui.theme.BentoAccentPink
import com.example.ui.theme.BentoBg
import com.example.ui.theme.BentoBorder
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
import com.example.ui.viewmodel.GamerViewModel

@Composable
fun LiveChatLoungeScreen(
    viewModel: GamerViewModel,
    modifier: Modifier = Modifier
) {
    var chatTab by remember { mutableIntStateOf(0) } // 0: Global Lounge, 1: Direct Whispers
    val globalMessages by viewModel.globalMessages.collectAsState()
    val activeDmRecipient by viewModel.activeDmRecipient.collectAsState()
    val allProfiles by viewModel.allProfiles.collectAsState()

    var chatInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BentoBg)
            .padding(horizontal = 14.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        // Header Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Chatbox Lounge", color = BentoTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Live cross-game network & direct whispers", color = BentoTextSecondary, fontSize = 11.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Tabs: Global Lounge vs Direct Whispers
        TabRow(
            selectedTabIndex = chatTab,
            containerColor = BentoSurfaceCard,
            contentColor = BentoPrimary,
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .border(1.dp, BentoBorderSubtle, RoundedCornerShape(14.dp)),
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[chatTab]),
                    color = BentoPrimary,
                    height = 3.dp
                )
            }
        ) {
            Tab(
                selected = chatTab == 0,
                onClick = { chatTab = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Forum,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp),
                            tint = if (chatTab == 0) BentoPrimary else BentoTextMuted
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Global Lounge",
                            fontSize = 12.sp,
                            fontWeight = if (chatTab == 0) FontWeight.Bold else FontWeight.Medium,
                            color = if (chatTab == 0) BentoPrimary else BentoTextMuted
                        )
                    }
                }
            )
            Tab(
                selected = chatTab == 1,
                onClick = { chatTab = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.ChatBubble,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp),
                            tint = if (chatTab == 1) BentoPrimary else BentoTextMuted
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (activeDmRecipient != null) "DM (${activeDmRecipient?.gamertag})" else "Direct Whispers",
                            fontSize = 12.sp,
                            fontWeight = if (chatTab == 1) FontWeight.Bold else FontWeight.Medium,
                            color = if (chatTab == 1) BentoPrimary else BentoTextMuted
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (chatTab == 0) {
            // Global Lounge
            Column(modifier = Modifier.fillMaxSize()) {
                // Quick Callout Chips
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                    val chips = listOf(
                        "Looking for teammates 🎮",
                        "Premier 5-stack tonight 🔥",
                        "GGs everyone! ⭐",
                        "Drop your gamer cards below 🃏",
                        "Any Apex players on? ⚡"
                    )
                    items(chips) { chip ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(BentoSurfaceVariant)
                                .border(0.5.dp, BentoBorderSubtle, RoundedCornerShape(12.dp))
                                .clickable { viewModel.sendGlobalChatMessage(chip) }
                                .padding(horizontal = 9.dp, vertical = 4.dp)
                        ) {
                            Text(chip, color = BentoPrimary, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Messages list
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(globalMessages, key = { it.id }) { msg ->
                        ChatMessageItem(
                            message = msg,
                            isMe = msg.senderId == "user_me",
                            onSenderClick = {}
                        )
                    }
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                }

                // Input Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = chatInput,
                        onValueChange = { chatInput = it },
                        placeholder = { Text("Message the global gamer lounge...", color = BentoTextMuted, fontSize = 12.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("global_chat_input"),
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BentoPrimary,
                            unfocusedBorderColor = BentoBorderSubtle,
                            focusedContainerColor = BentoSurfaceCard,
                            unfocusedContainerColor = BentoSurfaceCard,
                            focusedTextColor = BentoTextPrimary,
                            unfocusedTextColor = BentoTextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    IconButton(
                        onClick = {
                            if (chatInput.isNotBlank()) {
                                viewModel.sendGlobalChatMessage(chatInput)
                                chatInput = ""
                            }
                        },
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(BentoPrimary)
                            .testTag("send_global_chat_button")
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
            }
        } else {
            // Direct Whispers / DMs
            val recipient = activeDmRecipient
            if (recipient == null) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("SELECT A GAMER TO WHISPER", color = BentoTextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
                        val otherPlayers = allProfiles.filter { it.id != "user_me" }
                        items(otherPlayers, key = { it.id }) { player ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(BentoSurfaceCard)
                                    .border(1.dp, BentoBorderSubtle, RoundedCornerShape(16.dp))
                                    .clickable { viewModel.activeDmRecipient.value = player }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    GamerAvatarEmblem(gamertag = player.gamertag, skinAccent = BentoPrimary, size = 40)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(player.gamertag, color = BentoTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                        Text("${player.mainGameId.uppercase()} • ${player.currentRank}", color = BentoTextSecondary, fontSize = 11.sp)
                                    }
                                }
                                ElevatedButton(
                                    onClick = { viewModel.activeDmRecipient.value = player },
                                    colors = ButtonDefaults.elevatedButtonColors(containerColor = BentoPrimary, contentColor = Color.White),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Whisper", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            } else {
                val dmMessages by viewModel.getDirectMessages(recipient.id).collectAsState(initial = emptyList())
                Column(modifier = Modifier.fillMaxSize()) {
                    // Recipient Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(BentoSurfaceCard)
                            .border(1.dp, BentoBorderSubtle, RoundedCornerShape(14.dp))
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            GamerAvatarEmblem(gamertag = recipient.gamertag, skinAccent = BentoPrimary, size = 34)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Whispering with ${recipient.gamertag}", color = BentoTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                Text(recipient.discordTag, color = BentoPrimary, fontSize = 10.sp)
                            }
                        }
                        IconButton(onClick = { viewModel.activeDmRecipient.value = null }) {
                            Icon(Icons.Default.Close, contentDescription = "Close DM", tint = BentoTextMuted)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(dmMessages, key = { it.id }) { msg ->
                            ChatMessageItem(
                                message = msg,
                                isMe = msg.senderId == "user_me",
                                onSenderClick = {}
                            )
                        }
                        item { Spacer(modifier = Modifier.height(8.dp)) }
                    }

                    // Input Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = chatInput,
                            onValueChange = { chatInput = it },
                            placeholder = { Text("Whisper to ${recipient.gamertag}...", color = BentoTextMuted, fontSize = 12.sp) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("dm_chat_input"),
                            singleLine = true,
                            shape = RoundedCornerShape(24.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BentoPrimary,
                                unfocusedBorderColor = BentoBorderSubtle,
                                focusedContainerColor = BentoSurfaceCard,
                                unfocusedContainerColor = BentoSurfaceCard,
                                focusedTextColor = BentoTextPrimary,
                                unfocusedTextColor = BentoTextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        IconButton(
                            onClick = {
                                if (chatInput.isNotBlank()) {
                                    viewModel.sendDirectMessage(recipient.id, chatInput)
                                    chatInput = ""
                                }
                            },
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(BentoPrimary)
                                .testTag("send_dm_button")
                        ) {
                            Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }
    }
}

