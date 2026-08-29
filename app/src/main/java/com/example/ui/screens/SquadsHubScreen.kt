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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.local.entities.SquadEntity
import com.example.data.local.entities.SquadInviteEntity
import com.example.ui.components.GameChipBadge
import com.example.ui.components.SquadCardItem
import com.example.ui.theme.BentoAccentGreen
import com.example.ui.theme.BentoBg
import com.example.ui.theme.BentoBorderSubtle
import com.example.ui.theme.BentoPrimary
import com.example.ui.theme.BentoPrimaryContainer
import com.example.ui.theme.BentoSurfaceCard
import com.example.ui.theme.BentoTextMuted
import com.example.ui.theme.BentoTextPrimary
import com.example.ui.theme.BentoTextSecondary
import com.example.ui.viewmodel.GamerViewModel

@Composable
fun SquadsHubScreen(
    viewModel: GamerViewModel,
    modifier: Modifier = Modifier
) {
    var tabIndex by remember { mutableIntStateOf(0) } // 0: Browse Squads, 1: Invites & My Teams
    val allSquads by viewModel.allSquads.collectAsState()
    val myInvites by viewModel.myInvites.collectAsState()
    val pendingInvites = myInvites.filter { it.status == "PENDING" }

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
                Text("Team Hub & Squads", color = BentoTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Organize, recruit, & collaborate", color = BentoTextSecondary, fontSize = 11.sp)
            }

            ElevatedButton(
                onClick = { viewModel.isCreateSquadDialogOpen.value = true },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = BentoPrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("create_squad_hub_btn")
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Create Squad", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Tabs: Browse All Squads vs Invites & My Squads
        TabRow(
            selectedTabIndex = tabIndex,
            containerColor = BentoSurfaceCard,
            contentColor = BentoPrimary,
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .border(1.dp, BentoBorderSubtle, RoundedCornerShape(14.dp)),
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = BentoPrimary,
                    height = 3.dp
                )
            }
        ) {
            Tab(
                selected = tabIndex == 0,
                onClick = { tabIndex = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Group,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp),
                            tint = if (tabIndex == 0) BentoPrimary else BentoTextMuted
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "All Open Squads (${allSquads.size})",
                            fontSize = 12.sp,
                            fontWeight = if (tabIndex == 0) FontWeight.Bold else FontWeight.Medium,
                            color = if (tabIndex == 0) BentoPrimary else BentoTextMuted
                        )
                    }
                }
            )
            Tab(
                selected = tabIndex == 1,
                onClick = { tabIndex = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Mail,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp),
                            tint = if (tabIndex == 1) BentoPrimary else BentoTextMuted
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Invites (${pendingInvites.size})",
                            fontSize = 12.sp,
                            fontWeight = if (tabIndex == 1) FontWeight.Bold else FontWeight.Medium,
                            color = if (tabIndex == 1) BentoPrimary else BentoTextMuted
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (tabIndex == 0) {
            // Browse All Squads List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(allSquads, key = { it.id }) { squad ->
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
        } else {
            // Invites & Team Roster view
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(
                        text = "RECEIVED SQUAD INVITATIONS",
                        color = BentoTextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (pendingInvites.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(BentoSurfaceCard)
                                .border(1.dp, BentoBorderSubtle, RoundedCornerShape(16.dp))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.Mail, contentDescription = null, tint = BentoTextMuted, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("No pending squad invites right now", color = BentoTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("Upload your card to get invited by team captains!", color = BentoTextSecondary, fontSize = 10.sp)
                            }
                        }
                    }
                } else {
                    items(pendingInvites, key = { it.id }) { invite ->
                        SquadInviteItem(
                            invite = invite,
                            onAccept = { viewModel.acceptInvite(invite) },
                            onDecline = { viewModel.declineInvite(invite.id) }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "YOUR ACTIVE SQUADS",
                        color = BentoTextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                val mySquads = allSquads.filter { it.leaderId == "user_me" }
                if (mySquads.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(BentoSurfaceCard)
                                .border(1.dp, BentoBorderSubtle, RoundedCornerShape(16.dp))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("You haven't formed any squads yet", color = BentoTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(4.dp))
                                ElevatedButton(
                                    onClick = { viewModel.isCreateSquadDialogOpen.value = true },
                                    colors = ButtonDefaults.elevatedButtonColors(containerColor = BentoPrimary, contentColor = Color.White),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Create Your First Squad", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                } else {
                    items(mySquads, key = { it.id }) { squad ->
                        val members by viewModel.getMembersForSquad(squad.id).collectAsState(initial = emptyList())
                        SquadCardItem(
                            squad = squad,
                            members = members,
                            onJoinClick = {},
                            onManageClick = {
                                viewModel.selectedSquadForJoin.value = squad
                                viewModel.isJoinSquadDialogOpen.value = true
                            }
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
private fun SquadInviteItem(
    invite: SquadInviteEntity,
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, BentoBorderSubtle, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = BentoSurfaceCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    GameChipBadge(gameId = invite.gameId)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = invite.squadName,
                        color = BentoTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Invited by ${invite.fromGamerName} as ${invite.proposedRole}",
                    color = BentoPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                IconButton(
                    onClick = onAccept,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(BentoAccentGreen)
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Accept", tint = Color.White, modifier = Modifier.size(18.dp))
                }

                IconButton(
                    onClick = onDecline,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEF4444).copy(alpha = 0.2f))
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Decline", tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

