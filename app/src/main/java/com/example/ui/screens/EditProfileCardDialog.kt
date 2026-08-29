package com.example.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.entities.GamerProfileEntity
import com.example.data.model.CardSkin
import com.example.data.model.GameType
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.DarkNavySurface
import com.example.ui.theme.DeepVoidBlack
import com.example.ui.theme.NeonViolet
import com.example.ui.theme.RadiantPink
import com.example.ui.theme.SurfaceCardDark
import com.example.ui.theme.TextMutedGamer
import com.example.ui.theme.TextPrimaryGamer
import com.example.ui.theme.TextSecondaryGamer

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditProfileCardDialog(
    initialProfile: GamerProfileEntity?,
    onDismiss: () -> Unit,
    onSave: (
        gamertag: String,
        tagline: String,
        mainGameId: String,
        secondaryGameId: String,
        currentRank: String,
        peakRank: String,
        preferredRole: String,
        signatureHero: String,
        playstyle: String,
        kdRatio: Float,
        winRate: Int,
        headshotPercentage: Int,
        totalMatches: Int,
        region: String,
        platform: String,
        bio: String,
        discordTag: String,
        hasMic: Boolean,
        skinName: String
    ) -> Unit
) {
    var gamertag by remember { mutableStateOf(initialProfile?.gamertag ?: "ShadowViper") }
    var tagline by remember { mutableStateOf(initialProfile?.tagline ?: "AIM#9941") }
    var mainGameId by remember { mutableStateOf(initialProfile?.mainGameId ?: "valorant") }
    var secondaryGameId by remember { mutableStateOf(initialProfile?.secondaryGameId ?: "apex") }
    var currentRank by remember { mutableStateOf(initialProfile?.currentRank ?: "Ascendant 2") }
    var peakRank by remember { mutableStateOf(initialProfile?.peakRank ?: "Immortal 1") }
    var preferredRole by remember { mutableStateOf(initialProfile?.preferredRole ?: "Duelist / IGL") }
    var signatureHero by remember { mutableStateOf(initialProfile?.signatureHero ?: "Jett / Reyna") }
    var playstyle by remember { mutableStateOf(initialProfile?.playstyle ?: "Competitive Grinder") }
    var kdText by remember { mutableStateOf(initialProfile?.kdRatio?.toString() ?: "1.82") }
    var winRateText by remember { mutableStateOf(initialProfile?.winRate?.toString() ?: "64") }
    var headshotText by remember { mutableStateOf(initialProfile?.headshotPercentage?.toString() ?: "39") }
    var totalMatchesText by remember { mutableStateOf(initialProfile?.totalMatches?.toString() ?: "480") }
    var region by remember { mutableStateOf(initialProfile?.region ?: "NA East") }
    var platform by remember { mutableStateOf(initialProfile?.platform ?: "PC") }
    var bio by remember { mutableStateOf(initialProfile?.bio ?: "Calm comms, aggressive entry fragger. Looking for a serious 5-stack for weekend premier tournaments.") }
    var discordTag by remember { mutableStateOf(initialProfile?.discordTag ?: "shadow_viper#0001") }
    var hasMic by remember { mutableStateOf(initialProfile?.hasMic ?: true) }
    var selectedSkin by remember { mutableStateOf(initialProfile?.skinName ?: "NEON_CYAN") }

    val selectedMainGame = GameType.fromId(mainGameId)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(20.dp))
                .border(1.5.dp, CyberCyan, RoundedCornerShape(20.dp))
                .testTag("edit_profile_dialog"),
            colors = CardDefaults.cardColors(containerColor = DarkNavySurface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(CyberCyan.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.FileUpload, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Upload Gamer Profile Card", color = TextPrimaryGamer, fontSize = 16.sp, fontWeight = FontWeight.Black)
                            Text("Showcase your stats & recruit status", color = TextSecondaryGamer, fontSize = 11.sp)
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMutedGamer)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Gamertag & Tagline
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = gamertag,
                        onValueChange = { gamertag = it },
                        label = { Text("Gamertag / IGN") },
                        modifier = Modifier.weight(1.3f).testTag("input_gamertag"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )

                    OutlinedTextField(
                        value = tagline,
                        onValueChange = { tagline = it },
                        label = { Text("Tagline / Code") },
                        modifier = Modifier.weight(1f).testTag("input_tagline"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Main Game Selection (5 Games)
                Text("MAIN GAME", color = TextMutedGamer, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    GameType.entries.forEach { game ->
                        val isSel = game.id == mainGameId
                        val color = Color(game.primaryColor)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) color.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.05f))
                                .border(1.dp, if (isSel) color else Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                .clickable {
                                    mainGameId = game.id
                                    if (game.ranks.isNotEmpty()) currentRank = game.ranks.first()
                                    if (game.availableRoles.isNotEmpty()) preferredRole = game.availableRoles.first()
                                }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(game.title, color = if (isSel) Color.White else TextSecondaryGamer, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Current Rank & Peak Rank
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = currentRank,
                        onValueChange = { currentRank = it },
                        label = { Text("Current Rank") },
                        modifier = Modifier.weight(1f).testTag("input_current_rank"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )

                    OutlinedTextField(
                        value = peakRank,
                        onValueChange = { peakRank = it },
                        label = { Text("Peak Rank") },
                        modifier = Modifier.weight(1f).testTag("input_peak_rank"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Preferred Role & Signature Hero / Agent
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = preferredRole,
                        onValueChange = { preferredRole = it },
                        label = { Text("Preferred Role") },
                        modifier = Modifier.weight(1f).testTag("input_role"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )

                    OutlinedTextField(
                        value = signatureHero,
                        onValueChange = { signatureHero = it },
                        label = { Text("Signature Agent / Hero") },
                        modifier = Modifier.weight(1f).testTag("input_signature_hero"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // In-Game Stats Row (K/D, Win Rate, Headshot %, Total Matches)
                Text("IN-GAME STATS", color = TextMutedGamer, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedTextField(
                        value = kdText,
                        onValueChange = { kdText = it },
                        label = { Text("K/D") },
                        modifier = Modifier.weight(1f).testTag("input_kd"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                    OutlinedTextField(
                        value = winRateText,
                        onValueChange = { winRateText = it },
                        label = { Text("Win %") },
                        modifier = Modifier.weight(1f).testTag("input_winrate"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                    OutlinedTextField(
                        value = headshotText,
                        onValueChange = { headshotText = it },
                        label = { Text("HS %") },
                        modifier = Modifier.weight(1f).testTag("input_hs"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                    OutlinedTextField(
                        value = totalMatchesText,
                        onValueChange = { totalMatchesText = it },
                        label = { Text("Matches") },
                        modifier = Modifier.weight(1f).testTag("input_matches"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Region & Platform
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = region,
                        onValueChange = { region = it },
                        label = { Text("Region (e.g. NA East, EUW)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                    OutlinedTextField(
                        value = platform,
                        onValueChange = { platform = it },
                        label = { Text("Platform (PC, PS5, Xbox)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Playstyle Chips
                Text("PLAYSTYLE", color = TextMutedGamer, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf("Competitive Grinder", "Casual Chill", "Tournament Grinder", "Shotcaller", "Flex Player").forEach { style ->
                        val isSel = playstyle == style
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) CyberCyan.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.05f))
                                .border(1.dp, if (isSel) CyberCyan else Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                            .clickable { playstyle = style }
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                        ) {
                            Text(style, color = if (isSel) CyberCyan else TextSecondaryGamer, fontSize = 11.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Discord Tag & Mic
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = discordTag,
                        onValueChange = { discordTag = it },
                        label = { Text("Discord Username / Tag") },
                        modifier = Modifier.weight(1f).testTag("input_discord"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Voice Mic", color = TextPrimaryGamer, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Switch(
                            checked = hasMic,
                            onCheckedChange = { hasMic = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = CyberCyan)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bio
                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Bio & Team Goals") },
                    modifier = Modifier.fillMaxWidth().testTag("input_bio"),
                    maxLines = 3,
                    colors = outlinedFieldColors()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Card Aesthetic Theme Skin Selector
                Text("CARD HOLOGRAPHIC SKIN", color = TextMutedGamer, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    CardSkin.entries.forEach { skin ->
                        val isSel = selectedSkin == skin.name
                        val accent = Color(skin.accentColor)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(skin.startColor))
                                .border(1.5.dp, if (isSel) accent else Color.White.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                .clickable { selectedSkin = skin.name }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(skin.displayName, color = if (isSel) accent else Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Submit Button
                ElevatedButton(
                    onClick = {
                        val kd = kdText.toFloatOrNull() ?: 1.5f
                        val winRate = winRateText.toIntOrNull() ?: 50
                        val hs = headshotText.toIntOrNull() ?: 30
                        val matches = totalMatchesText.toIntOrNull() ?: 100

                        onSave(
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
                            discordTag,
                            hasMic,
                            selectedSkin
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("save_profile_button"),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = CyberCyan,
                        contentColor = DeepVoidBlack
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.FileUpload, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Publish Gamer Card", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun outlinedFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = CyberCyan,
    unfocusedBorderColor = Color(0xFF334155),
    focusedLabelColor = CyberCyan,
    unfocusedLabelColor = TextMutedGamer,
    focusedTextColor = TextPrimaryGamer,
    unfocusedTextColor = TextPrimaryGamer
)
