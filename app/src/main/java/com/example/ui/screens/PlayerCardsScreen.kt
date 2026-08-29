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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.data.model.GameType
import com.example.ui.components.GamerCardItem
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlayerCardsScreen(
    viewModel: GamerViewModel,
    modifier: Modifier = Modifier
) {
    val myProfile by viewModel.myProfile.collectAsState()
    val profiles by viewModel.filteredProfiles.collectAsState()
    val selectedGameFilter by viewModel.cardFilterGame.collectAsState()
    val selectedRoleFilter by viewModel.cardFilterRole.collectAsState()
    val searchQuery by viewModel.cardSearchQuery.collectAsState()

    var showFilters by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BentoBg)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Title & Bento upload showcase
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Gamer Profile Cards",
                                color = BentoTextPrimary,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(BentoPrimaryContainer)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("SHOWCASE", color = BentoOnPrimaryContainer, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Text(
                            text = "Upload your profile card & connect with teams",
                            color = BentoTextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    ElevatedButton(
                        onClick = { viewModel.isEditProfileDialogOpen.value = true },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = BentoPrimary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("upload_profile_card_header_btn")
                    ) {
                        Icon(Icons.Default.FileUpload, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (myProfile != null) "Edit My Card" else "Upload Card",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // User's own card showcase banner (if exists)
            myProfile?.let { me ->
                item {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "YOUR UPLOADED PROFILE CARD",
                                color = BentoPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { viewModel.isEditProfileDialogOpen.value = true }
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null, tint = BentoPrimary, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text("Customize", color = BentoPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        GamerCardItem(
                            profile = me,
                            onLike = { viewModel.likeCard(me.id) },
                            onEndorse = { tag -> viewModel.endorseCard(me.id, tag) },
                            onInviteToSquad = {},
                            onDirectMessage = {}
                        )
                    }
                }
            }

            // Search Bar & Filter Toggle
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.cardSearchQuery.value = it },
                        placeholder = { Text("Search by IGN, Rank, Agent, or Bio...", color = BentoTextMuted, fontSize = 12.sp) },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = BentoTextMuted, modifier = Modifier.size(18.dp))
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("search_cards_input"),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BentoPrimary,
                            unfocusedBorderColor = BentoBorderSubtle,
                            focusedContainerColor = BentoSurfaceCard,
                            unfocusedContainerColor = BentoSurfaceCard,
                            focusedTextColor = BentoTextPrimary,
                            unfocusedTextColor = BentoTextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (showFilters) BentoPrimaryContainer else BentoSurfaceCard)
                            .border(1.dp, if (showFilters) BentoPrimary else BentoBorderSubtle, RoundedCornerShape(14.dp))
                            .clickable { showFilters = !showFilters },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter",
                            tint = if (showFilters) BentoOnPrimaryContainer else BentoTextSecondary
                        )
                    }
                }
            }

            // Game Filter Chips (5 Games + ALL)
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        val isSel = selectedGameFilter == "ALL"
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSel) BentoPrimary else BentoSurfaceCard)
                                .border(1.dp, if (isSel) BentoPrimary else BentoBorderSubtle, RoundedCornerShape(16.dp))
                                .clickable { viewModel.cardFilterGame.value = "ALL" }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("All Games", color = if (isSel) Color.White else BentoTextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    items(GameType.entries.toTypedArray()) { game ->
                        val isSel = selectedGameFilter.equals(game.id, ignoreCase = true)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSel) BentoPrimary else BentoSurfaceCard)
                                .border(1.dp, if (isSel) BentoPrimary else BentoBorderSubtle, RoundedCornerShape(16.dp))
                                .clickable { viewModel.cardFilterGame.value = game.id }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .testTag("filter_game_${game.id}")
                        ) {
                            Text(game.title, color = if (isSel) Color.White else BentoTextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Expanded Role Filters
            if (showFilters) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(BentoSurfaceCard)
                            .border(1.dp, BentoBorderSubtle, RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        Text("FILTER BY ROLE", color = BentoTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf("ALL", "Duelist", "IGL", "Support", "Controller", "Tank", "Mid", "Jungle", "AWP", "Skirmisher").forEach { role ->
                                val isSel = selectedRoleFilter.equals(role, ignoreCase = true)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (isSel) BentoPrimaryContainer else BentoSurface)
                                        .border(1.dp, if (isSel) BentoPrimary else BentoBorderSubtle, RoundedCornerShape(10.dp))
                                        .clickable { viewModel.cardFilterRole.value = role }
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        role,
                                        color = if (isSel) BentoOnPrimaryContainer else BentoTextSecondary,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Cards Feed Count Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "COMMUNITY PLAYER CARDS (${profiles.size})",
                        color = BentoTextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tap Card for Full Stats",
                        color = BentoPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Gamer Profile Cards List
            if (profiles.isEmpty()) {
                item {
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
                            Icon(Icons.Default.Search, contentDescription = null, tint = BentoTextMuted, modifier = Modifier.size(36.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No gamer cards matched your filter", color = BentoTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text("Try searching with different keywords or clear filters", color = BentoTextSecondary, fontSize = 11.sp)
                        }
                    }
                }
            } else {
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
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

