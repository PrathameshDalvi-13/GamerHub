package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.entities.ChatMessageEntity
import com.example.data.local.entities.GamerProfileEntity
import com.example.data.local.entities.SquadEntity
import com.example.data.local.entities.SquadInviteEntity
import com.example.data.local.entities.SquadMemberEntity
import com.example.data.model.GameType
import com.example.data.repository.GamerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class GamerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GamerRepository(application)

    // Current navigation state
    val selectedGame = MutableStateFlow<GameType>(GameType.VALORANT)
    val currentMainTab = MutableStateFlow(0) // 0: Game Hubs (5 Games), 1: Player Cards Feed, 2: Squads Hub, 3: Live Chat Lounge
    val selectedGameSubTab = MutableStateFlow(0) // 0: Squads / LFG, 1: Live Chatbox, 2: Roster / Top Cards

    // Search and filter states
    val cardFilterGame = MutableStateFlow<String>("ALL")
    val cardFilterRole = MutableStateFlow<String>("ALL")
    val cardSearchQuery = MutableStateFlow<String>("")
    val activeChatChannel = MutableStateFlow<String>("general")

    // Direct Message selected recipient
    val activeDmRecipient = MutableStateFlow<GamerProfileEntity?>(null)

    // Dialog & Interaction states
    val isEditProfileDialogOpen = MutableStateFlow(false)
    val isCreateSquadDialogOpen = MutableStateFlow(false)
    val isJoinSquadDialogOpen = MutableStateFlow(false)
    val isInvitePlayerDialogOpen = MutableStateFlow(false)
    val selectedCardForDetail = MutableStateFlow<GamerProfileEntity?>(null)
    val selectedSquadForJoin = MutableStateFlow<SquadEntity?>(null)
    val selectedPlayerForInvite = MutableStateFlow<GamerProfileEntity?>(null)

    // Reactive streams
    val myProfile: StateFlow<GamerProfileEntity?> = repository.myProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val allProfiles: StateFlow<List<GamerProfileEntity>> = repository.allProfiles
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredProfiles: StateFlow<List<GamerProfileEntity>> = combine(
        repository.allProfiles,
        cardFilterGame,
        cardFilterRole,
        cardSearchQuery
    ) { profiles, game, role, query ->
        profiles.filter { profile ->
            val matchGame = (game == "ALL" || profile.mainGameId.equals(game, ignoreCase = true) || profile.secondaryGameId.equals(game, ignoreCase = true))
            val matchRole = (role == "ALL" || profile.preferredRole.contains(role, ignoreCase = true))
            val matchQuery = (query.isBlank() ||
                    profile.gamertag.contains(query, ignoreCase = true) ||
                    profile.currentRank.contains(query, ignoreCase = true) ||
                    profile.signatureHero.contains(query, ignoreCase = true) ||
                    profile.bio.contains(query, ignoreCase = true))
            matchGame && matchRole && matchQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSquads: StateFlow<List<SquadEntity>> = repository.allSquads
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentSelectedGameSquads: StateFlow<List<SquadEntity>> = selectedGame
        .flatMapLatest { game -> repository.getSquadsForGame(game.id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentSelectedGameProfiles: StateFlow<List<GamerProfileEntity>> = selectedGame
        .flatMapLatest { game -> repository.getProfilesForGame(game.id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentChannelMessages: StateFlow<List<ChatMessageEntity>> = combine(
        selectedGame,
        activeChatChannel
    ) { game, channel -> Pair(game.id, channel) }
        .flatMapLatest { (gameId, channel) -> repository.getGameChannelMessages(gameId, channel) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val globalMessages: StateFlow<List<ChatMessageEntity>> = repository.globalMessages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val myInvites: StateFlow<List<SquadInviteEntity>> = repository.getMyInvites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getMembersForSquad(squadId: String) = repository.getSquadMembers(squadId)
    fun getDirectMessages(otherId: String) = repository.getDirectMessages(otherId)

    // User Profile Actions
    fun saveMyProfile(
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
    ) {
        viewModelScope.launch {
            val existing = myProfile.value
            val profile = GamerProfileEntity(
                id = "user_me",
                gamertag = gamertag.ifBlank { "PlayerOne" },
                tagline = tagline.ifBlank { "IGN#0001" },
                mainGameId = mainGameId,
                secondaryGameId = secondaryGameId,
                currentRank = currentRank.ifBlank { "Unranked" },
                peakRank = peakRank.ifBlank { currentRank },
                preferredRole = preferredRole.ifBlank { "Flex" },
                signatureHero = signatureHero.ifBlank { "Main Hero" },
                playstyle = playstyle,
                kdRatio = kdRatio,
                winRate = winRate,
                headshotPercentage = headshotPercentage,
                totalMatches = totalMatches,
                region = region,
                platform = platform,
                bio = bio.ifBlank { "Ready to team up and climb the ranks!" },
                discordTag = discordTag.ifBlank { "${gamertag.lowercase()}#0001" },
                hasMic = hasMic,
                isLookingForTeam = true,
                skinName = skinName,
                likesCount = existing?.likesCount ?: 50,
                isUserUploaded = true,
                avatarSeed = "1"
            )
            repository.saveMyProfile(profile)
            isEditProfileDialogOpen.value = false
        }
    }

    fun likeCard(profileId: String) {
        viewModelScope.launch {
            repository.likeProfile(profileId)
        }
    }

    fun endorseCard(targetId: String, endorsementTag: String) {
        viewModelScope.launch {
            repository.endorseProfile(targetId, endorsementTag)
        }
    }

    // Squad Actions
    fun createSquad(
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
    ) {
        viewModelScope.launch {
            val user = myProfile.value
            val squadId = "squad_${UUID.randomUUID().toString().take(8)}"
            val squad = SquadEntity(
                id = squadId,
                gameId = gameId,
                name = name.ifBlank { "Elite Squad" },
                tag = tag.ifBlank { "SQD" },
                leaderId = "user_me",
                leaderName = user?.gamertag ?: "ShadowViper",
                targetRank = targetRank.ifBlank { "Any Rank" },
                region = region.ifBlank { "NA East" },
                gameMode = gameMode.ifBlank { "Competitive 5v5" },
                requiredMic = requiredMic,
                teamSize = teamSize,
                description = description.ifBlank { "Looking for solid teammates with good comms." },
                scheduledTime = scheduledTime.ifBlank { "Tonight 8:00 PM" },
                discordLink = discordLink,
                isOpen = true
            )
            repository.createSquad(
                squad = squad,
                leaderRole = myRole.ifBlank { "Captain / IGL" },
                leaderRank = user?.currentRank ?: "Ascendant"
            )
            isCreateSquadDialogOpen.value = false
        }
    }

    fun joinSquad(squad: SquadEntity, role: String) {
        viewModelScope.launch {
            val user = myProfile.value
            repository.joinSquad(
                squadId = squad.id,
                gamerId = "user_me",
                gamerName = user?.gamertag ?: "ShadowViper",
                gamerRank = user?.currentRank ?: "Ascendant 2",
                role = role
            )
            isJoinSquadDialogOpen.value = false
            selectedSquadForJoin.value = null
        }
    }

    fun leaveSquad(squadId: String) {
        viewModelScope.launch {
            repository.leaveSquad(squadId, "user_me")
        }
    }

    fun toggleReady(squadId: String, currentReady: Boolean) {
        viewModelScope.launch {
            repository.toggleReadyStatus(squadId, "user_me", currentReady)
        }
    }

    fun sendSquadInvite(squad: SquadEntity, targetPlayerId: String, role: String) {
        viewModelScope.launch {
            repository.sendSquadInvite(squad, targetPlayerId, role)
            isInvitePlayerDialogOpen.value = false
            selectedPlayerForInvite.value = null
        }
    }

    fun acceptInvite(invite: SquadInviteEntity) {
        viewModelScope.launch {
            val user = myProfile.value
            repository.acceptInvite(
                invite = invite,
                gamerName = user?.gamertag ?: "ShadowViper",
                gamerRank = user?.currentRank ?: "Ascendant 2"
            )
        }
    }

    fun declineInvite(inviteId: String) {
        viewModelScope.launch {
            repository.declineInvite(inviteId)
        }
    }

    // Chat Actions
    fun sendGameChatMessage(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            val user = myProfile.value
            val game = selectedGame.value
            val channel = activeChatChannel.value
            repository.sendGameChatMessage(
                gameId = game.id,
                channel = channel,
                senderId = "user_me",
                senderName = user?.gamertag ?: "ShadowViper",
                senderRank = user?.currentRank ?: "Ascendant 2",
                senderRole = user?.preferredRole ?: "Duelist",
                text = text.trim()
            )
        }
    }

    fun sendGlobalChatMessage(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            val user = myProfile.value
            repository.sendGlobalChatMessage(
                senderId = "user_me",
                senderName = user?.gamertag ?: "ShadowViper",
                senderRank = user?.currentRank ?: "Ascendant 2",
                senderRole = user?.preferredRole ?: "Duelist",
                text = text.trim()
            )
        }
    }

    fun sendSquadChatMessage(squadId: String, text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            val user = myProfile.value
            repository.sendSquadChatMessage(
                squadId = squadId,
                senderId = "user_me",
                senderName = user?.gamertag ?: "ShadowViper",
                senderRank = user?.currentRank ?: "Ascendant 2",
                senderRole = user?.preferredRole ?: "Duelist",
                text = text.trim()
            )
        }
    }

    fun sendDirectMessage(recipientId: String, text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            val user = myProfile.value
            repository.sendDirectMessage(
                receiverId = recipientId,
                senderId = "user_me",
                senderName = user?.gamertag ?: "ShadowViper",
                senderRank = user?.currentRank ?: "Ascendant 2",
                senderRole = user?.preferredRole ?: "Duelist",
                text = text.trim()
            )
        }
    }
}
