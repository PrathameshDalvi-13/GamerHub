package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.data.local.entities.GamerProfileEntity
import com.example.data.local.entities.SquadEntity
import com.example.data.model.GameType
import com.example.ui.components.ChatMessageItem
import com.example.ui.components.GameHeaderBanner
import com.example.ui.components.GameSelectorBar
import com.example.ui.components.GamerCardItem
import com.example.ui.components.SquadCardItem
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
fun GameSectionScreen(
    viewModel: GamerViewModel,
    modifier: Modifier = Modifier
) {
    val selectedGame by viewModel.selectedGame.collectAsState()
    val squads by viewModel.currentSelectedGameSquads.collectAsState()
    val profiles by viewModel.currentSelectedGameProfiles.collectAsState()
    val messages by viewModel.currentChannelMessages.collectAsState()
    val activeChannel by viewModel.activeChatChannel.collectAsState()
    val myProfile by viewModel.myProfile.collectAsState()
    val currentSubTab by viewModel.selectedGameSubTab.collectAsState()

    val gameColor = Color(selectedGame.primaryColor)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BentoBg)
            .padding(horizontal = 14.dp)
    ) {
        // 5 Games Horizontal Switcher
        GameSelectorBar(
            selectedGame = selectedGame,
            onGameSelected = { viewModel.selectedGame.value = it }
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Game Header Hero Banner
        GameHeaderBanner(
            game = selectedGame,
            squadCount = squads.size,
            profileCount = profiles.size
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Sub Tabs: 0 -> Squads / LFG, 1 -> Live Chatbox, 2 -> Player Cards
        TabRow(
            selectedTabIndex = currentSubTab,
            containerColor = BentoSurfaceCard,
            contentColor = BentoPrimary,
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .border(1.dp, BentoBorderSubtle, RoundedCornerShape(14.dp)),
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[currentSubTab]),
                    color = BentoPrimary,
                    height = 3.dp
                )
            }
        ) {
            Tab(
                selected = currentSubTab == 0,
                onClick = { viewModel.selectedGameSubTab.value = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Group,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp),
                            tint = if (currentSubTab == 0) BentoPrimary else BentoTextMuted
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Squads (${squads.size})",
                            fontSize = 12.sp,
                            fontWeight = if (currentSubTab == 0) FontWeight.Bold else FontWeight.Medium,
                            color = if (currentSubTab == 0) BentoPrimary else BentoTextMuted
                        )
                    }
                }
            )
            Tab(
                selected = currentSubTab == 1,
                onClick = { viewModel.selectedGameSubTab.value = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Chat,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp),
                            tint = if (currentSubTab == 1) BentoPrimary else BentoTextMuted
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Live Chatbox",
                            fontSize = 12.sp,
                            fontWeight = if (currentSubTab == 1) FontWeight.Bold else FontWeight.Medium,
                            color = if (currentSubTab == 1) BentoPrimary else BentoTextMuted
                        )
                    }
                }
            )
            Tab(
                selected = currentSubTab == 2,
                onClick = { viewModel.selectedGameSubTab.value = 2 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp),
                            tint = if (currentSubTab == 2) BentoPrimary else BentoTextMuted
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Roster (${profiles.size})",
                            fontSize = 12.sp,
                            fontWeight = if (currentSubTab == 2) FontWeight.Bold else FontWeight.Medium,
                            color = if (currentSubTab == 2) BentoPrimary else BentoTextMuted
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        when (currentSubTab) {
            0 -> GameSquadsTab(viewModel = viewModel, squads = squads, game = selectedGame)
            1 -> GameChatboxTab(
                viewModel = viewModel,
                game = selectedGame,
                messages = messages,
                activeChannel = activeChannel
            )
            2 -> GamePlayerRosterTab(viewModel = viewModel, profiles = profiles, game = selectedGame)
        }
    }
}

@Composable
private fun GameSquadsTab(
    viewModel: GamerViewModel,
    squads: List<SquadEntity>,
    game: GameType
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "OPEN ${game.shortName} SQUADS",
                color = BentoTextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )

            ElevatedButton(
                onClick = { viewModel.isCreateSquadDialogOpen.value = true },
                modifier = Modifier
                    .height(34.dp)
                    .testTag("create_squad_btn_section"),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = BentoPrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Create Squad", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (squads.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(BentoSurfaceCard)
                    .border(1.dp, BentoBorderSubtle, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Group, contentDescription = null, tint = BentoTextMuted, modifier = Modifier.size(36.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No active squads for ${game.title} yet", color = BentoTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text("Be the first captain to recruit a team!", color = BentoTextSecondary, fontSize = 11.sp)
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(squads, key = { it.id }) { squad ->
                    val members by viewModel.getMembersForSquad(squad.id).collectAsState(initial = emptyList())
                    SquadCardItem(
                        squad = squad,
                        members = members,
                        onJoinClick = {
                            viewModel.selectedSquadForJoin.value = squad
                            viewModel.isJoinSquadDialogOpen.value = true
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
private fun GameChatboxTab(
    viewModel: GamerViewModel,
    game: GameType,
    messages: List<com.example.data.local.entities.ChatMessageEntity>,
    activeChannel: String
) {
    var messageInput by remember { mutableStateOf("") }
    val channels = listOf("general", "lfg-ranked", "scrims-tournaments", "tips-strats")

    Column(modifier = Modifier.fillMaxSize()) {
        // Channel Selector Tabs (#general, #lfg-ranked, etc.)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(channels) { ch ->
                val isSel = ch == activeChannel
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSel) BentoPrimaryContainer else BentoSurface)
                        .border(
                            1.dp,
                            if (isSel) BentoPrimary else BentoBorderSubtle,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { viewModel.activeChatChannel.value = ch }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "#$ch",
                        color = if (isSel) BentoOnPrimaryContainer else BentoTextSecondary,
                        fontSize = 11.sp,
                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Quick Callout Chips for Gamers
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val quickChips = listOf(
                "Need +1 for Ranked 🎯",
                "Looking for Squad with Mics 🎙️",
                "Down for Tournament Scrims 🔥",
                "Warmup matches anyone? ⚔️",
                "Check my player card! 🃏"
            )
            items(quickChips) { chip ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(BentoSurfaceVariant)
                        .border(0.5.dp, BentoBorderSubtle, RoundedCornerShape(12.dp))
                        .clickable { viewModel.sendGameChatMessage(chip) }
                        .padding(horizontal = 9.dp, vertical = 4.dp)
                ) {
                    Text(chip, color = BentoPrimary, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Messages List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                ChatMessageItem(
                    message = msg,
                    isMe = msg.senderId == "user_me",
                    onSenderClick = {}
                )
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
        }

        // Send Message Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = messageInput,
                onValueChange = { messageInput = it },
                placeholder = { Text("Message #${activeChannel} in ${game.shortName}...", color = BentoTextMuted, fontSize = 12.sp) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("game_chat_input"),
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
                    if (messageInput.isNotBlank()) {
                        viewModel.sendGameChatMessage(messageInput)
                        messageInput = ""
                    }
                },
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(BentoPrimary)
                    .testTag("send_game_chat_button")
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
private fun GamePlayerRosterTab(
    viewModel: GamerViewModel,
    profiles: List<GamerProfileEntity>,
    game: GameType
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${game.title} PLAYER CARDS",
                color = BentoTextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${profiles.size} Active Players",
                color = BentoPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(profiles, key = { it.id }) { profile ->
                GamerCardItem(
                    profile = profile,
                    onLike = { viewModel.likeCard(profile.id) },
                    onEndorse = { tag -> viewModel.endorseCard(profile.id, tag) },
                    onInviteToSquad = {
                        viewModel.selectedPlayerForInvite.value = profile
                        viewModel.isInvitePlayerDialogOpen.value = true
                    },
                    onDirectMessage = {
                        viewModel.activeDmRecipient.value = profile
                        viewModel.currentMainTab.value = 3 // Jump to chat
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

