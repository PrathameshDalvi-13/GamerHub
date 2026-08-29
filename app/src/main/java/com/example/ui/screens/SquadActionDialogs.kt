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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.entities.GamerProfileEntity
import com.example.data.local.entities.SquadEntity
import com.example.data.model.GameType
import com.example.ui.components.RankBadge
import com.example.ui.components.RolePill
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.DarkNavySurface
import com.example.ui.theme.DeepVoidBlack
import com.example.ui.theme.NeonViolet
import com.example.ui.theme.RadiantPink
import com.example.ui.theme.TextMutedGamer
import com.example.ui.theme.TextPrimaryGamer
import com.example.ui.theme.TextSecondaryGamer

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateSquadDialog(
    initialGame: GameType,
    onDismiss: () -> Unit,
    onCreate: (
        name: String,
        tag: String,
        gameId: String,
        targetRank: String,
        region: String,
        gameMode: String,
        teamSize: Int,
        description: String,
        scheduledTime: String,
        discordLink: String,
        myRole: String,
        requiredMic: Boolean
    ) -> Unit
) {
    var selectedGameId by remember { mutableStateOf(initialGame.id) }
    var name by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }
    var targetRank by remember { mutableStateOf("Diamond / Ascendant") }
    var region by remember { mutableStateOf("NA East") }
    var gameMode by remember { mutableStateOf("Competitive 5v5") }
    val game = GameType.fromId(selectedGameId)
    var teamSize by remember { mutableIntStateOf(game.teamSize) }
    var description by remember { mutableStateOf("") }
    var scheduledTime by remember { mutableStateOf("Tonight 8:00 PM EST") }
    var discordLink by remember { mutableStateOf("discord.gg/team-lobby") }
    var myRole by remember { mutableStateOf(game.availableRoles.firstOrNull() ?: "Captain") }
    var requiredMic by remember { mutableStateOf(true) }

    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(20.dp))
                .border(1.5.dp, Color(game.primaryColor), RoundedCornerShape(20.dp))
                .testTag("create_squad_dialog"),
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
                                .background(Color(game.primaryColor).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.GroupAdd, contentDescription = null, tint = Color(game.primaryColor), modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Form a New Squad", color = TextPrimaryGamer, fontSize = 16.sp, fontWeight = FontWeight.Black)
                            Text("Recruit players & schedule matches", color = TextSecondaryGamer, fontSize = 11.sp)
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMutedGamer)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Game Selector
                Text("GAME", color = TextMutedGamer, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    GameType.entries.forEach { g ->
                        val isSel = g.id == selectedGameId
                        val color = Color(g.primaryColor)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) color.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.05f))
                                .border(1.dp, if (isSel) color else Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                .clickable {
                                    selectedGameId = g.id
                                    teamSize = g.teamSize
                                    if (g.availableRoles.isNotEmpty()) myRole = g.availableRoles.first()
                                }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(g.title, color = if (isSel) Color.White else TextSecondaryGamer, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Squad Name & Tag
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Squad / Team Name") },
                        placeholder = { Text("e.g. Apex Predators") },
                        modifier = Modifier.weight(1.3f).testTag("input_squad_name"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                    OutlinedTextField(
                        value = tag,
                        onValueChange = { tag = it },
                        label = { Text("Tag (2-5 Chars)") },
                        placeholder = { Text("e.g. PRED") },
                        modifier = Modifier.weight(1f).testTag("input_squad_tag"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Game Mode & Target Rank
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = gameMode,
                        onValueChange = { gameMode = it },
                        label = { Text("Mode (Ranked, Scrim, Clash)") },
                        modifier = Modifier.weight(1f).testTag("input_game_mode"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                    OutlinedTextField(
                        value = targetRank,
                        onValueChange = { targetRank = it },
                        label = { Text("Target Rank") },
                        modifier = Modifier.weight(1f).testTag("input_target_rank"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Region & Schedule
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = region,
                        onValueChange = { region = it },
                        label = { Text("Server Region") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                    OutlinedTextField(
                        value = scheduledTime,
                        onValueChange = { scheduledTime = it },
                        label = { Text("Schedule / Match Time") },
                        modifier = Modifier.weight(1.2f).testTag("input_schedule"),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Captain's Role in Squad
                Text("YOUR ROLE IN THIS SQUAD", color = TextMutedGamer, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    game.availableRoles.forEach { role ->
                        val isSel = myRole == role
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) CyberCyan.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.05f))
                                .border(1.dp, if (isSel) CyberCyan else Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                .clickable { myRole = role }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(role, color = if (isSel) CyberCyan else TextSecondaryGamer, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Discord / Voice Comms Link & Mic Req
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = discordLink,
                        onValueChange = { discordLink = it },
                        label = { Text("Discord / Voice Server") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = outlinedFieldColors()
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Mic Required", color = TextPrimaryGamer, fontSize = 11.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Switch(
                            checked = requiredMic,
                            onCheckedChange = { requiredMic = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = CyberCyan)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Recruiting Note / Strategy") },
                    placeholder = { Text("Describe team vibe, needed roles, strategy...") },
                    modifier = Modifier.fillMaxWidth().testTag("input_squad_desc"),
                    maxLines = 3,
                    colors = outlinedFieldColors()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Create Squad Button
                ElevatedButton(
                    onClick = {
                        onCreate(
                            name.ifBlank { "Squad ${tag.ifBlank { "Alpha" }}" },
                            tag.ifBlank { "SQD" },
                            selectedGameId,
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
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("submit_create_squad_button"),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(game.primaryColor),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.GroupAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Post Squad Recruitment", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JoinSquadDialog(
    squad: SquadEntity,
    onDismiss: () -> Unit,
    onConfirmJoin: (selectedRole: String) -> Unit
) {
    val game = GameType.fromId(squad.gameId)
    val gameColor = Color(game.primaryColor)
    var selectedRole by remember { mutableStateOf(game.availableRoles.firstOrNull() ?: "Flex") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.5.dp, gameColor, RoundedCornerShape(16.dp))
                .testTag("join_squad_dialog"),
            colors = CardDefaults.cardColors(containerColor = DarkNavySurface)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Join ${squad.name}", color = TextPrimaryGamer, fontSize = 16.sp, fontWeight = FontWeight.Black)
                        Text("${game.title} • [${squad.tag}]", color = gameColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMutedGamer)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text("SELECT YOUR ROLE IN THIS SQUAD", color = TextMutedGamer, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))

                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    game.availableRoles.forEach { role ->
                        val isSel = selectedRole == role
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) gameColor.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.05f))
                                .border(1.dp, if (isSel) gameColor else Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                .clickable { selectedRole = role }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(role, color = if (isSel) Color.White else TextSecondaryGamer, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text("Schedule: ${squad.scheduledTime}", color = CyberCyan, fontSize = 11.sp)
                Text("Target Rank: ${squad.targetRank} (${squad.region})", color = TextSecondaryGamer, fontSize = 11.sp)

                Spacer(modifier = Modifier.height(16.dp))

                ElevatedButton(
                    onClick = { onConfirmJoin(selectedRole) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .testTag("confirm_join_squad_button"),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = gameColor,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Confirm Join as $selectedRole", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InvitePlayerDialog(
    player: GamerProfileEntity,
    mySquads: List<SquadEntity>,
    onDismiss: () -> Unit,
    onSendInvite: (squad: SquadEntity, proposedRole: String) -> Unit
) {

    var selectedSquad by remember { mutableStateOf(mySquads.firstOrNull()) }
    val availableRoles = selectedSquad?.let { GameType.fromId(it.gameId).availableRoles } ?: listOf("Flex")
    var selectedRole by remember { mutableStateOf(availableRoles.firstOrNull() ?: player.preferredRole) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.5.dp, CyberCyan, RoundedCornerShape(16.dp))
                .testTag("invite_player_dialog"),
            colors = CardDefaults.cardColors(containerColor = DarkNavySurface)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Invite ${player.gamertag}", color = TextPrimaryGamer, fontSize = 16.sp, fontWeight = FontWeight.Black)
                        Text("Send a squad roster invitation", color = CyberCyan, fontSize = 11.sp)
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMutedGamer)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (mySquads.isEmpty()) {
                    Text("You don't have any active squads. Create a squad first to invite teammates!", color = TextSecondaryGamer, fontSize = 12.sp)
                } else {
                    Text("SELECT SQUAD", color = TextMutedGamer, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyColumn(modifier = Modifier.height(120.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(mySquads) { sq ->
                            val isSel = selectedSquad?.id == sq.id
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSel) CyberCyan.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.05f))
                                    .border(1.dp, if (isSel) CyberCyan else Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                                    .clickable {
                                        selectedSquad = sq
                                        val newRoles = GameType.fromId(sq.gameId).availableRoles
                                        selectedRole = newRoles.firstOrNull() ?: player.preferredRole
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(sq.name, color = if (isSel) CyberCyan else TextPrimaryGamer, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    Text("[${sq.tag}] • ${sq.gameMode}", color = TextMutedGamer, fontSize = 10.sp)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("PROPOSED ROLE", color = TextMutedGamer, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        availableRoles.forEach { r ->
                            val isSel = selectedRole == r
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSel) NeonViolet.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.05f))
                                    .border(1.dp, if (isSel) NeonViolet else Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                    .clickable { selectedRole = r }
                                    .padding(horizontal = 8.dp, vertical = 5.dp)
                            ) {
                                Text(r, color = if (isSel) Color.White else TextSecondaryGamer, fontSize = 11.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ElevatedButton(
                        onClick = {
                            selectedSquad?.let { sq ->
                                onSendInvite(sq, selectedRole)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .testTag("send_invite_button"),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = CyberCyan,
                            contentColor = DeepVoidBlack
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Send Squad Invite", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
