package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GameType
import com.example.ui.components.GamerAvatarEmblem
import com.example.ui.theme.BentoAccentPink
import com.example.ui.theme.BentoAccentRed
import com.example.ui.theme.BentoBg
import com.example.ui.theme.BentoBorder
import com.example.ui.theme.BentoBorderSubtle
import com.example.ui.theme.BentoOnPrimaryContainer
import com.example.ui.theme.BentoPrimary
import com.example.ui.theme.BentoPrimaryContainer
import com.example.ui.theme.BentoSurface
import com.example.ui.theme.BentoTextMuted
import com.example.ui.theme.BentoTextPrimary
import com.example.ui.theme.BentoTextSecondary
import com.example.ui.viewmodel.GamerViewModel

@Composable
fun MainHubScreen(
    viewModel: GamerViewModel
) {
    val currentMainTab by viewModel.currentMainTab.collectAsState()
    val myProfile by viewModel.myProfile.collectAsState()
    val myInvites by viewModel.myInvites.collectAsState()
    val allSquads by viewModel.allSquads.collectAsState()
    val selectedGame by viewModel.selectedGame.collectAsState()

    val pendingInvitesCount = myInvites.count { it.status == "PENDING" }

    // Dialog states
    val isEditProfileOpen by viewModel.isEditProfileDialogOpen.collectAsState()
    val isCreateSquadOpen by viewModel.isCreateSquadDialogOpen.collectAsState()
    val isJoinSquadOpen by viewModel.isJoinSquadDialogOpen.collectAsState()
    val isInvitePlayerOpen by viewModel.isInvitePlayerDialogOpen.collectAsState()
    val selectedSquadForJoin by viewModel.selectedSquadForJoin.collectAsState()
    val selectedPlayerForInvite by viewModel.selectedPlayerForInvite.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BentoBg),
        topBar = {
            // Bento Top Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .background(BentoBg)
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { viewModel.currentMainTab.value = 0 }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(BentoPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SportsEsports,
                                contentDescription = "Logo",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Squad",
                                    color = BentoPrimary,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = (-0.5).sp
                                )
                                Text(
                                    text = "Forge",
                                    color = BentoTextPrimary,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = (-0.5).sp
                                )
                            }
                            Text(
                                text = "Connect & Conquer",
                                color = BentoTextSecondary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Top Action Icons (Upload Card quick action, Invites Mailbox, Profile Avatar)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Quick Upload Profile Card CTA in Bento Pill style
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(BentoPrimaryContainer)
                                .clickable { viewModel.isEditProfileDialogOpen.value = true }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .testTag("top_upload_card_btn")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.FileUpload,
                                    contentDescription = "Upload Card",
                                    tint = BentoOnPrimaryContainer,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "My Card",
                                    color = BentoOnPrimaryContainer,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Invites mailbox badge
                        IconButton(
                            onClick = { viewModel.currentMainTab.value = 2 },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(BentoSurface)
                                .border(1.dp, BentoBorderSubtle, CircleShape)
                        ) {
                            BadgedBox(
                                badge = {
                                    if (pendingInvitesCount > 0) {
                                        Badge(containerColor = BentoAccentRed) {
                                            Text("$pendingInvitesCount", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Mail,
                                    contentDescription = "Invites",
                                    tint = if (pendingInvitesCount > 0) BentoPrimary else BentoTextSecondary,
                                    modifier = Modifier.size(19.dp)
                                )
                            }
                        }

                        // Profile avatar
                        GamerAvatarEmblem(
                            gamertag = myProfile?.gamertag ?: "Me",
                            skinAccent = BentoPrimary,
                            size = 36,
                            modifier = Modifier.clickable { viewModel.isEditProfileDialogOpen.value = true }
                        )
                    }
                }
            }
        },
        bottomBar = {
            // Bento Bottom Navigation Bar
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars),
                containerColor = BentoSurface,
                tonalElevation = 2.dp
            ) {
                NavigationBarItem(
                    selected = currentMainTab == 0,
                    onClick = { viewModel.currentMainTab.value = 0 },
                    icon = { Icon(Icons.Default.SportsEsports, contentDescription = "5 Games") },
                    label = { Text("Discover", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = navigationBarColors(),
                    modifier = Modifier.testTag("nav_tab_games")
                )
                NavigationBarItem(
                    selected = currentMainTab == 1,
                    onClick = { viewModel.currentMainTab.value = 1 },
                    icon = { Icon(Icons.Default.Style, contentDescription = "Player Cards") },
                    label = { Text("Cards", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = navigationBarColors(),
                    modifier = Modifier.testTag("nav_tab_cards")
                )
                NavigationBarItem(
                    selected = currentMainTab == 2,
                    onClick = { viewModel.currentMainTab.value = 2 },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (pendingInvitesCount > 0) {
                                    Badge(containerColor = BentoAccentRed) {
                                        Text("$pendingInvitesCount", color = Color.White, fontSize = 9.sp)
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.Group, contentDescription = "Squads")
                        }
                    },
                    label = { Text("Teams", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = navigationBarColors(),
                    modifier = Modifier.testTag("nav_tab_squads")
                )
                NavigationBarItem(
                    selected = currentMainTab == 3,
                    onClick = { viewModel.currentMainTab.value = 3 },
                    icon = { Icon(Icons.Default.Forum, contentDescription = "Chat Lounge") },
                    label = { Text("Chats", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = navigationBarColors(),
                    modifier = Modifier.testTag("nav_tab_chat")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BentoBg)
        ) {
            when (currentMainTab) {
                0 -> GameSectionScreen(viewModel = viewModel)
                1 -> PlayerCardsScreen(viewModel = viewModel)
                2 -> SquadsHubScreen(viewModel = viewModel)
                3 -> LiveChatLoungeScreen(viewModel = viewModel)
            }
        }
    }

    // Modal Dialogs
    if (isEditProfileOpen) {
        EditProfileCardDialog(
            initialProfile = myProfile,
            onDismiss = { viewModel.isEditProfileDialogOpen.value = false },
            onSave = { gamertag, tagline, mainGameId, secondaryGameId, currentRank, peakRank, preferredRole, signatureHero, playstyle, kd, winRate, hs, matches, region, platform, bio, discord, mic, skin ->
                viewModel.saveMyProfile(
                    gamertag,
                    tagline,
                    mainGameId,
                    secondaryGameId,
                    currentRank,
                    peakRank,
                    preferredRole,
                    signatureHero,
                    playstyle,
                    kd,
                    winRate,
                    hs,
                    matches,
                    region,
                    platform,
                    bio,
                    discord,
                    mic,
                    skin
                )
            }
        )
    }

    if (isCreateSquadOpen) {
        CreateSquadDialog(
            initialGame = selectedGame,
            onDismiss = { viewModel.isCreateSquadDialogOpen.value = false },
            onCreate = { name, tag, gameId, targetRank, region, gameMode, teamSize, description, scheduledTime, discordLink, myRole, requiredMic ->
                viewModel.createSquad(
                    name,
                    tag,
                    gameId,
                    targetRank,
                    region,
                    gameMode,
                    teamSize,
                    description,
                    scheduledTime,
                    discordLink,
                    myRole,
                    requiredMic
                )
            }
        )
    }

    if (isJoinSquadOpen) {
        selectedSquadForJoin?.let { sq ->
            JoinSquadDialog(
                squad = sq,
                onDismiss = {
                    viewModel.isJoinSquadDialogOpen.value = false
                    viewModel.selectedSquadForJoin.value = null
                },
                onConfirmJoin = { role ->
                    viewModel.joinSquad(sq, role)
                }
            )
        }
    }

    if (isInvitePlayerOpen) {
        selectedPlayerForInvite?.let { player ->
            val mySquads = allSquads.filter { it.leaderId == "user_me" }
            InvitePlayerDialog(
                player = player,
                mySquads = mySquads,
                onDismiss = {
                    viewModel.isInvitePlayerDialogOpen.value = false
                    viewModel.selectedPlayerForInvite.value = null
                },
                onSendInvite = { squad, role ->
                    viewModel.sendSquadInvite(squad, player.id, role)
                }
            )
        }
    }
}

@Composable
private fun navigationBarColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = BentoOnPrimaryContainer,
    selectedTextColor = BentoOnPrimaryContainer,
    indicatorColor = BentoPrimaryContainer,
    unselectedIconColor = BentoTextSecondary,
    unselectedTextColor = BentoTextSecondary
)
