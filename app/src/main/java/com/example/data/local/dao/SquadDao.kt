package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.data.local.entities.SquadEntity
import com.example.data.local.entities.SquadInviteEntity
import com.example.data.local.entities.SquadMemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SquadDao {
    @Query("SELECT * FROM squads ORDER BY createdAt DESC")
    fun getAllSquads(): Flow<List<SquadEntity>>

    @Query("SELECT * FROM squads WHERE gameId = :gameId ORDER BY createdAt DESC")
    fun getSquadsByGame(gameId: String): Flow<List<SquadEntity>>

    @Query("SELECT * FROM squads WHERE id = :squadId LIMIT 1")
    fun getSquadById(squadId: String): Flow<SquadEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSquad(squad: SquadEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSquads(squads: List<SquadEntity>)

    @Update
    suspend fun updateSquad(squad: SquadEntity)

    @Query("DELETE FROM squads WHERE id = :squadId")
    suspend fun deleteSquad(squadId: String)

    // Squad members
    @Query("SELECT * FROM squad_members WHERE squadId = :squadId ORDER BY isLeader DESC, joinedAt ASC")
    fun getMembersForSquad(squadId: String): Flow<List<SquadMemberEntity>>

    @Query("SELECT * FROM squad_members WHERE gamerId = :gamerId")
    fun getSquadsForGamer(gamerId: String): Flow<List<SquadMemberEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: SquadMemberEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMembers(members: List<SquadMemberEntity>)

    @Query("DELETE FROM squad_members WHERE squadId = :squadId AND gamerId = :gamerId")
    suspend fun removeMember(squadId: String, gamerId: String)

    @Query("UPDATE squad_members SET isReady = :isReady WHERE squadId = :squadId AND gamerId = :gamerId")
    suspend fun updateMemberReadyStatus(squadId: String, gamerId: String, isReady: Boolean)

    // Invites
    @Query("SELECT * FROM squad_invites WHERE toGamerId = :gamerId ORDER BY createdAt DESC")
    fun getInvitesForGamer(gamerId: String): Flow<List<SquadInviteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvite(invite: SquadInviteEntity)

    @Query("UPDATE squad_invites SET status = :status WHERE id = :inviteId")
    suspend fun updateInviteStatus(inviteId: String, status: String)

    @Query("SELECT COUNT(*) FROM squads")
    suspend fun getSquadCount(): Int
}
