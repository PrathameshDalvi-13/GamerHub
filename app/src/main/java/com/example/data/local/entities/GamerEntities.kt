package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gamer_profiles")
data class GamerProfileEntity(
    @PrimaryKey val id: String, // e.g. user_1 or me
    val gamertag: String,
    val tagline: String,
    val mainGameId: String,
    val secondaryGameId: String = "",
    val currentRank: String,
    val peakRank: String,
    val preferredRole: String,
    val signatureHero: String,
    val playstyle: String, // "Competitive", "Casual Chill", "Tournament Grinder", "Content Creator"
    val kdRatio: Float,
    val winRate: Int, // e.g. 62%
    val headshotPercentage: Int, // e.g. 38%
    val totalMatches: Int,
    val region: String, // "NA East", "EU West", "Asia East", "SA", etc.
    val platform: String, // "PC", "PlayStation 5", "Xbox Series X"
    val bio: String,
    val discordTag: String,
    val hasMic: Boolean = true,
    val isLookingForTeam: Boolean = true,
    val skinName: String = "NEON_CYAN",
    val likesCount: Int = 0,
    val isUserUploaded: Boolean = false,
    val avatarSeed: String = "1",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "squads")
data class SquadEntity(
    @PrimaryKey val id: String,
    val gameId: String,
    val name: String,
    val tag: String,
    val leaderId: String,
    val leaderName: String,
    val targetRank: String,
    val region: String,
    val gameMode: String, // e.g. "Ranked 5v5", "Premier Scrims", "Casual Trios"
    val requiredMic: Boolean = true,
    val teamSize: Int = 5,
    val description: String,
    val scheduledTime: String = "Tonight 8:00 PM EST",
    val discordLink: String = "",
    val isOpen: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "squad_members")
data class SquadMemberEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val squadId: String,
    val gamerId: String,
    val gamerName: String,
    val gamerRank: String,
    val assignedRole: String,
    val isLeader: Boolean = false,
    val isReady: Boolean = false,
    val joinedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey val id: String,
    val scope: String, // "GAME", "SQUAD", "DM"
    val targetId: String, // gameId like "valorant", or squadId, or receiverId
    val channelName: String = "general", // "general", "lfg-ranked", "scrims", "tips"
    val senderId: String,
    val senderName: String,
    val senderRank: String,
    val senderGameRole: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "card_endorsements")
data class CardEndorsementEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val targetGamerId: String,
    val endorsementTag: String, // "CLUTCH_KING", "TOP_COMMS", "GOD_AIM", "TEAM_PLAYER", "SMART_IGL"
    val fromGamerId: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "squad_invites")
data class SquadInviteEntity(
    @PrimaryKey val id: String,
    val squadId: String,
    val squadName: String,
    val gameId: String,
    val fromGamerId: String,
    val fromGamerName: String,
    val toGamerId: String,
    val proposedRole: String,
    val status: String = "PENDING", // "PENDING", "ACCEPTED", "DECLINED"
    val createdAt: Long = System.currentTimeMillis()
)
