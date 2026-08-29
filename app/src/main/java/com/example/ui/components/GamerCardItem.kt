package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.GamerProfileEntity
import com.example.data.model.CardSkin
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
fun GamerCardItem(
    profile: GamerProfileEntity,
    onLike: () -> Unit,
    onEndorse: (String) -> Unit,
    onInviteToSquad: () -> Unit,
    onDirectMessage: () -> Unit,
    modifier: Modifier = Modifier,
    onCardClick: (() -> Unit)? = null
) {
    var expandedStats by remember { mutableStateOf(false) }
    var likedLocally by remember { mutableStateOf(false) }

    val skin = try {
        CardSkin.valueOf(profile.skinName)
    } catch (e: Exception) {
        CardSkin.NEON_CYAN
    }

    val skinAccent = Color(skin.accentColor)
    val game = GameType.fromId(profile.mainGameId)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, BentoBorderSubtle, RoundedCornerShape(20.dp))
            .clickable {
                if (onCardClick != null) onCardClick() else expandedStats = !expandedStats
            }
            .testTag("gamer_card_${profile.id}"),
        colors = CardDefaults.cardColors(
            containerColor = BentoSurfaceCard
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Column {
                // Top Row: Avatar, Gamertag, Tagline, Game Chip, Likes
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GamerAvatarEmblem(
                        gamertag = profile.gamertag,
                        skinAccent = BentoPrimary,
                        size = 48
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = profile.gamertag,
                                color = BentoTextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (profile.isUserUploaded) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(BentoPrimaryContainer)
                                        .padding(horizontal = 5.dp, vertical = 1.dp)
                                ) {
                                    Text("YOU", color = BentoOnPrimaryContainer, fontSize = 9.sp, fontWeight = FontWeight.Black)
                                }
                            }
                        }

                        Text(
                            text = profile.tagline,
                            color = BentoPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            GameChipBadge(gameId = profile.mainGameId)
                            if (profile.secondaryGameId.isNotBlank()) {
                                Spacer(modifier = Modifier.width(4.dp))
                                GameChipBadge(gameId = profile.secondaryGameId)
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = profile.platform,
                                color = BentoTextMuted,
                                fontSize = 10.sp
                            )
                        }
                    }

                    // Like button & Counter in Bento Pill style
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (likedLocally) BentoAccentPink.copy(alpha = 0.15f) else BentoSurfaceVariant)
                            .border(
                                1.dp,
                                if (likedLocally) BentoAccentPink else BentoBorderSubtle,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                likedLocally = true
                                onLike()
                            }
                            .padding(horizontal = 9.dp, vertical = 5.dp)
                            .testTag("like_card_button_${profile.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Like",
                            tint = if (likedLocally) BentoAccentPink else BentoTextSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${profile.likesCount + if (likedLocally) 1 else 0}",
                            color = if (likedLocally) BentoAccentPink else BentoTextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Rank & Role row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RankBadge(rank = profile.currentRank)
                        Spacer(modifier = Modifier.width(6.dp))
                        RolePill(role = profile.preferredRole, accentColor = BentoPrimary)
                    }

                    OnlineStatusIndicator(
                        isOnline = true,
                        isLookingForTeam = profile.isLookingForTeam
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Bio
                if (profile.bio.isNotBlank()) {
                    Text(
                        text = profile.bio,
                        color = BentoTextSecondary,
                        fontSize = 12.sp,
                        maxLines = if (expandedStats) 4 else 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // In-Game Stats Grid (Bento style 4-column module)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    StatBentoTile(modifier = Modifier.weight(1f), label = "K/D", value = String.format("%.2f", profile.kdRatio), color = BentoPrimary)
                    StatBentoTile(modifier = Modifier.weight(1f), label = "WIN %", value = "${profile.winRate}%", color = BentoAccentGreen)
                    if (profile.headshotPercentage > 0) {
                        StatBentoTile(modifier = Modifier.weight(1f), label = "HS %", value = "${profile.headshotPercentage}%", color = Color(0xFFD97706))
                    }
                    StatBentoTile(modifier = Modifier.weight(1f), label = "MATCHES", value = "${profile.totalMatches}", color = BentoTextPrimary)
                }

                // Expanded Section: Signature Hero, Peak Rank, Region, Discord, Praise Endorsements
                AnimatedVisibility(visible = expandedStats) {
                    Column(modifier = Modifier.padding(top = 10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("SIGNATURE HERO / AGENT", color = BentoTextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                Text(profile.signatureHero, color = BentoTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("PEAK RANK", color = BentoTextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                RankBadge(rank = profile.peakRank, isPeak = true)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (profile.hasMic) Icons.Default.Mic else Icons.Default.MicOff,
                                    contentDescription = null,
                                    tint = if (profile.hasMic) BentoAccentGreen else Color.Gray,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (profile.hasMic) "Mic Ready (${profile.region})" else "No Mic (${profile.region})",
                                    color = BentoTextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                            Text(
                                text = "Discord: ${profile.discordTag}",
                                color = BentoPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Interactive Endorsements / Praise Tags
                        Text(
                            text = "PLAYER ENDORSEMENTS",
                            color = BentoTextMuted,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            EndorsementPill(tag = "⭐ Clutch God", onClick = { onEndorse("CLUTCH_GOD") })
                            EndorsementPill(tag = "🎙️ Top Comms", onClick = { onEndorse("TOP_COMMS") })
                            EndorsementPill(tag = "🎯 God Aim", onClick = { onEndorse("GOD_AIM") })
                            EndorsementPill(tag = "🤝 Team Player", onClick = { onEndorse("TEAM_PLAYER") })
                            EndorsementPill(tag = "🧠 Smart IGL", onClick = { onEndorse("SMART_IGL") })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Bottom Action Buttons in Bento style
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ElevatedButton(
                        onClick = onInviteToSquad,
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                            .testTag("invite_player_button_${profile.id}"),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = BentoPrimary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.GroupAdd, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Invite to Squad", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    FilledTonalButton(
                        onClick = onDirectMessage,
                        modifier = Modifier
                            .height(38.dp)
                            .testTag("direct_msg_button_${profile.id}"),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = BentoPrimaryContainer,
                            contentColor = BentoOnPrimaryContainer
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.ChatBubble, contentDescription = "Whisper", modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Chat", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }

                    OutlinedButton(
                        onClick = { expandedStats = !expandedStats },
                        modifier = Modifier.height(38.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = BentoPrimary)
                    ) {
                        Text(if (expandedStats) "Less" else "Stats", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatBentoTile(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(BentoSurfaceVariant)
            .border(0.5.dp, BentoBorderSubtle, RoundedCornerShape(10.dp))
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, color = BentoTextMuted, fontSize = 8.sp, fontWeight = FontWeight.Bold)
            Text(text = value, color = color, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
private fun EndorsementPill(
    tag: String,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (pressed) BentoPrimaryContainer else BentoSurface)
            .border(1.dp, if (pressed) BentoPrimary else BentoBorderSubtle, RoundedCornerShape(12.dp))
            .clickable {
                pressed = true
                onClick()
            }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = tag,
            color = if (pressed) BentoOnPrimaryContainer else BentoTextPrimary,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

