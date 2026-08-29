package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GameType
import com.example.ui.theme.BentoAccentGreen
import com.example.ui.theme.BentoAccentPink
import com.example.ui.theme.BentoBorder
import com.example.ui.theme.BentoBorderSubtle
import com.example.ui.theme.BentoOnPrimaryContainer
import com.example.ui.theme.BentoPrimary
import com.example.ui.theme.BentoPrimaryContainer
import com.example.ui.theme.BentoSurface
import com.example.ui.theme.BentoSurfaceCard
import com.example.ui.theme.BentoTextMuted
import com.example.ui.theme.BentoTextPrimary
import com.example.ui.theme.BentoTextSecondary

@Composable
fun RankBadge(
    rank: String,
    modifier: Modifier = Modifier,
    isPeak: Boolean = false
) {
    val rankColor = when {
        rank.contains("Radiant", true) || rank.contains("Predator", true) || rank.contains("Challenger", true) || rank.contains("Top 500", true) || rank.contains("Global", true) -> Color(0xFFD97706)
        rank.contains("Immortal", true) || rank.contains("Master", true) || rank.contains("Grandmaster", true) || rank.contains("Champion", true) -> Color(0xFF7C3AED)
        rank.contains("Ascendant", true) || rank.contains("Emerald", true) -> Color(0xFF059669)
        rank.contains("Diamond", true) -> Color(0xFF0284C7)
        rank.contains("Platinum", true) -> Color(0xFF0891B2)
        rank.contains("Gold", true) -> Color(0xFFD97706)
        rank.contains("Silver", true) -> Color(0xFF64748B)
        rank.contains("Bronze", true) || rank.contains("Iron", true) -> Color(0xFF8D6E63)
        else -> BentoPrimary
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(rankColor.copy(alpha = 0.12f))
            .border(1.dp, rankColor.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (isPeak) Icons.Default.Star else Icons.Default.MilitaryTech,
                contentDescription = null,
                tint = rankColor,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (isPeak) "Peak: $rank" else rank,
                color = rankColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun GameChipBadge(
    gameId: String,
    modifier: Modifier = Modifier
) {
    val game = GameType.fromId(gameId)
    val color = Color(game.primaryColor)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
            .padding(horizontal = 7.dp, vertical = 2.dp)
    ) {
        Text(
            text = game.shortName,
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun RolePill(
    role: String,
    modifier: Modifier = Modifier,
    accentColor: Color = BentoPrimary
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(BentoSurface)
            .border(1.dp, BentoBorderSubtle, RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = role,
            color = BentoTextPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun OnlineStatusIndicator(
    isOnline: Boolean = true,
    isLookingForTeam: Boolean = true,
    modifier: Modifier = Modifier
) {
    val statusColor = if (isLookingForTeam) BentoPrimary else (if (isOnline) BentoAccentGreen else Color.Gray)
    val text = if (isLookingForTeam) "LFG • Active" else (if (isOnline) "Online" else "Offline")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(statusColor)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            color = statusColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun GamerAvatarEmblem(
    gamertag: String,
    skinAccent: Color,
    modifier: Modifier = Modifier,
    size: Int = 46
) {
    val initial = gamertag.firstOrNull()?.uppercase() ?: "G"
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(BentoPrimaryContainer)
            .border(1.5.dp, BentoPrimary, CircleShape)
    ) {
        Text(
            text = initial,
            color = BentoOnPrimaryContainer,
            fontSize = (size * 0.44).sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

