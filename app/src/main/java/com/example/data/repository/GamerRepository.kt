package com.example.data.repository

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.local.entities.CardEndorsementEntity
import com.example.data.local.entities.ChatMessageEntity
import com.example.data.local.entities.GamerProfileEntity
import com.example.data.local.entities.SquadEntity
import com.example.data.local.entities.SquadInviteEntity
import com.example.data.local.entities.SquadMemberEntity
import com.example.data.model.SeedData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

class GamerRepository(context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val gamerDao = database.gamerDao()
    private val squadDao = database.squadDao()
    private val chatDao = database.chatDao()

    init {
        // Seed initial data asynchronously on first run
        CoroutineScope(Dispatchers.IO).launch {
            if (gamerDao.getProfileCount() == 0) {
                gamerDao.insertProfiles(SeedData.getInitialProfiles())
            }
            if (squadDao.getSquadCount() == 0) {
                squadDao.insertSquads(SeedData.getInitialSquads())
                squadDao.insertMembers(SeedData.getInitialMembers())
            }
            if (chatDao.getMessageCount() == 0) {
                chatDao.insertMessages(SeedData.getInitialChatMessages())
            }
        }
    }

    // Profiles
    val allProfiles: Flow<List<GamerProfileEntity>> = gamerDao.getAllGamerProfiles()
    val myProfile: Flow<GamerProfileEntity?> = gamerDao.getMyProfile()

    fun getProfilesForGame(gameId: String): Flow<List<GamerProfileEntity>> =
        gamerDao.getProfilesByGame(gameId)

    fun getProfileById(id: String): Flow<GamerProfileEntity?> =
        gamerDao.getProfileById(id)

    suspend fun saveMyProfile(profile: GamerProfileEntity) {
        gamerDao.insertProfile(profile.copy(id = "user_me", isUserUploaded = true))
    }

    suspend fun uploadGamerProfileCard(profile: GamerProfileEntity) {
        gamerDao.insertProfile(profile)
    }

    suspend fun likeProfile(id: String) {
        gamerDao.incrementLike(id)
    }

    suspend fun endorseProfile(targetId: String, endorsementTag: String, fromId: String = "user_me") {
        gamerDao.insertEndorsement(
            CardEndorsementEntity(
                targetGamerId = targetId,
                endorsementTag = endorsementTag,
                fromGamerId = fromId
            )
        )
        gamerDao.incrementLike(targetId)
    }

    fun getEndorsements(gamerId: String): Flow<List<CardEndorsementEntity>> =
        gamerDao.getEndorsementsForGamer(gamerId)

    // Squads
    val allSquads: Flow<List<SquadEntity>> = squadDao.getAllSquads()

    fun getSquadsForGame(gameId: String): Flow<List<SquadEntity>> =
        squadDao.getSquadsByGame(gameId)

    fun getSquadById(squadId: String): Flow<SquadEntity?> =
        squadDao.getSquadById(squadId)

    fun getSquadMembers(squadId: String): Flow<List<SquadMemberEntity>> =
        squadDao.getMembersForSquad(squadId)

    suspend fun createSquad(squad: SquadEntity, leaderRole: String, leaderRank: String) {
        squadDao.insertSquad(squad)
        // Add leader as first squad member
        squadDao.insertMember(
            SquadMemberEntity(
                squadId = squad.id,
                gamerId = squad.leaderId,
                gamerName = squad.leaderName,
                gamerRank = leaderRank,
                assignedRole = leaderRole,
                isLeader = true,
                isReady = true
            )
        )
    }

    suspend fun joinSquad(squadId: String, gamerId: String, gamerName: String, gamerRank: String, role: String) {
        squadDao.insertMember(
            SquadMemberEntity(
                squadId = squadId,
                gamerId = gamerId,
                gamerName = gamerName,
                gamerRank = gamerRank,
                assignedRole = role,
                isLeader = false,
                isReady = true
            )
        )
    }

    suspend fun leaveSquad(squadId: String, gamerId: String) {
        squadDao.removeMember(squadId, gamerId)
    }

    suspend fun toggleReadyStatus(squadId: String, gamerId: String, currentReady: Boolean) {
        squadDao.updateMemberReadyStatus(squadId, gamerId, !currentReady)
    }

    // Invites
    fun getMyInvites(gamerId: String = "user_me"): Flow<List<SquadInviteEntity>> =
        squadDao.getInvitesForGamer(gamerId)

    suspend fun sendSquadInvite(squad: SquadEntity, targetGamerId: String, role: String) {
        squadDao.insertInvite(
            SquadInviteEntity(
                id = UUID.randomUUID().toString(),
                squadId = squad.id,
                squadName = squad.name,
                gameId = squad.gameId,
                fromGamerId = squad.leaderId,
                fromGamerName = squad.leaderName,
                toGamerId = targetGamerId,
                proposedRole = role
            )
        )
    }

    suspend fun acceptInvite(invite: SquadInviteEntity, gamerName: String, gamerRank: String) {
        squadDao.updateInviteStatus(invite.id, "ACCEPTED")
        joinSquad(
            squadId = invite.squadId,
            gamerId = invite.toGamerId,
            gamerName = gamerName,
            gamerRank = gamerRank,
            role = invite.proposedRole
        )
    }

    suspend fun declineInvite(inviteId: String) {
        squadDao.updateInviteStatus(inviteId, "DECLINED")
    }

    // Chat
    fun getGameChannelMessages(gameId: String, channel: String): Flow<List<ChatMessageEntity>> =
        chatDao.getGameChannelMessages(gameId, channel)

    fun getSquadChat(squadId: String): Flow<List<ChatMessageEntity>> =
        chatDao.getSquadMessages(squadId)

    fun getDirectMessages(otherId: String, myId: String = "user_me"): Flow<List<ChatMessageEntity>> =
        chatDao.getDirectMessages(myId, otherId)

    val globalMessages: Flow<List<ChatMessageEntity>> = chatDao.getGlobalMessages()

    suspend fun sendGameChatMessage(
        gameId: String,
        channel: String,
        senderId: String,
        senderName: String,
        senderRank: String,
        senderRole: String,
        text: String
    ) {
        chatDao.insertMessage(
            ChatMessageEntity(
                id = UUID.randomUUID().toString(),
                scope = "GAME",
                targetId = gameId,
                channelName = channel,
                senderId = senderId,
                senderName = senderName,
                senderRank = senderRank,
                senderGameRole = senderRole,
                text = text
            )
        )
    }

    suspend fun sendSquadChatMessage(
        squadId: String,
        senderId: String,
        senderName: String,
        senderRank: String,
        senderRole: String,
        text: String
    ) {
        chatDao.insertMessage(
            ChatMessageEntity(
                id = UUID.randomUUID().toString(),
                scope = "SQUAD",
                targetId = squadId,
                channelName = "squad",
                senderId = senderId,
                senderName = senderName,
                senderRank = senderRank,
                senderGameRole = senderRole,
                text = text
            )
        )
    }

    suspend fun sendDirectMessage(
        receiverId: String,
        senderId: String,
        senderName: String,
        senderRank: String,
        senderRole: String,
        text: String
    ) {
        chatDao.insertMessage(
            ChatMessageEntity(
                id = UUID.randomUUID().toString(),
                scope = "DM",
                targetId = receiverId,
                channelName = "dm",
                senderId = senderId,
                senderName = senderName,
                senderRank = senderRank,
                senderGameRole = senderRole,
                text = text
            )
        )
    }

    suspend fun sendGlobalChatMessage(
        senderId: String,
        senderName: String,
        senderRank: String,
        senderRole: String,
        text: String
    ) {
        chatDao.insertMessage(
            ChatMessageEntity(
                id = UUID.randomUUID().toString(),
                scope = "GLOBAL",
                targetId = "global",
                channelName = "lounge",
                senderId = senderId,
                senderName = senderName,
                senderRank = senderRank,
                senderGameRole = senderRole,
                text = text
            )
        )
    }
}
