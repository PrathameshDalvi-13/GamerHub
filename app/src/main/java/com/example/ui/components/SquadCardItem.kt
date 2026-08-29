package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.SquadEntity
import com.example.data.local.entities.SquadMemberEntity
import com.example.data.model.GameType
import com.example.ui.theme.BentoAccentGreen
import com.example.ui.theme.BentoAccentPink
import com.example.ui.theme.BentoAccentRed
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SquadCardItem(
    squad: SquadEntity,
    members: List<SquadMemberEntity>,
    onJoinClick: () -> Unit,
    onManageClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val game = GameType.fromId(squad.gameId)
    val gameColor = Color(game.primaryColor)
    val isFull = members.size >= squad.teamSize
    val isUserMember = members.any { it.gamerId == "user_me" }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, BentoBorderSubtle, RoundedCornerShape(20.dp))
            .testTag("squad_card_${squad.id}"),
        colors = CardDefaults.cardColors(containerColor = BentoSurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Tag + Name, Target Rank, Member count indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(BentoPrimaryContainer)
                            .padding(horizontal = 7.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "[${squad.tag}]",
                            color = BentoOnPrimaryContainer,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = squad.name,
                        color = BentoTextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Member Slot Counter (e.g. 3/5) in Bento Pill style
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isFull) BentoAccentRed.copy(alpha = 0.12f) else BentoPrimaryContainer)
                        .border(
                            1.dp,
                            if (isFull) BentoAccentRed.copy(alpha = 0.4f) else BentoBorderSubtle,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Group,
                            contentDescription = null,
                            tint = if (isFull) BentoAccentRed else BentoPrimary,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${members.size}/${squad.teamSize}",
                            color = if (isFull) BentoAccentRed else BentoOnPrimaryContainer,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Game Mode, Target Rank, Region, Mic Required
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                RolePill(role = squad.gameMode, accentColor = BentoPrimary)
                RolePill(role = "Target: ${squad.targetRank}", accentColor = Color(0xFFD97706))
                RolePill(role = squad.region, accentColor = BentoTextSecondary)

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(BentoSurfaceVariant)
                        .border(0.5.dp, BentoBorderSubtle, RoundedCornerShape(12.dp))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (squad.requiredMic) Icons.Default.Mic else Icons.Default.MicOff,
                            contentDescription = null,
                            tint = if (squad.requiredMic) BentoAccentGreen else Color.Gray,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = if (squad.requiredMic) "Mic Req" else "No Mic Req",
                            color = BentoTextSecondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            if (squad.description.isNotBlank()) {
                Text(
                    text = squad.description,
                    color = BentoTextSecondary,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Schedule & Captain
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = BentoPrimary,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = squad.scheduledTime,
                        color = BentoPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Text(
                    text = "Captain: ${squad.leaderName}",
                    color = BentoTextMuted,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Visual Roster Role Slots in Bento modular container
            Text(
                text = "TEAM ROSTER & SLOTS",
                color = BentoTextMuted,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))

            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                // List existing members
                members.forEach { member ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(BentoSurfaceVariant)
                            .padding(horizontal = 9.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(if (member.isReady) BentoAccentGreen else Color(0xFFD97706))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = member.gamerName,
                                color = BentoTextPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (member.isLeader) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("(Leader)", color = BentoPrimary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = member.assignedRole,
                                color = BentoTextSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            RankBadge(rank = member.gamerRank)
                        }
                    }
                }

                // Show empty open slots
                val openSlotsCount = (squad.teamSize - members.size).coerceAtLeast(0)
                repeat(openSlotsCount) { slotIndex ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(BentoSurface)
                            .border(0.8.dp, BentoBorderSubtle, RoundedCornerShape(8.dp))
                            .clickable { onJoinClick() }
                            .padding(horizontal = 9.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = null,
                                tint = BentoTextMuted,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Open Slot ${members.size + slotIndex + 1}",
                                color = BentoTextMuted,
                                fontSize = 11.sp
                            )
                        }
                        Text(
                            text = "Tap to Join",
                            color = BentoPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action: Join Team / Manage Team
            if (isUserMember) {
                OutlinedButton(
                    onClick = { if (onManageClick != null) onManageClick() else onJoinClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .testTag("manage_squad_${squad.id}"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BentoPrimary)
                ) {
                    Icon(Icons.Default.Shield, contentDescription = null, tint = BentoPrimary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("You're In This Squad (Manage)", color = BentoPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            } else if (!isFull) {
                ElevatedButton(
                    onClick = onJoinClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .testTag("join_squad_button_${squad.id}"),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = BentoPrimary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Apply & Join Squad", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(BentoSurfaceVariant)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Squad is Currently Full", color = BentoTextMuted, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

