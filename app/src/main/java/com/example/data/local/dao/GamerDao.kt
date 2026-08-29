package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entities.CardEndorsementEntity
import com.example.data.local.entities.GamerProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GamerDao {
    @Query("SELECT * FROM gamer_profiles ORDER BY isUserUploaded DESC, createdAt DESC")
    fun getAllGamerProfiles(): Flow<List<GamerProfileEntity>>

    @Query("SELECT * FROM gamer_profiles WHERE mainGameId = :gameId OR secondaryGameId = :gameId ORDER BY isUserUploaded DESC, likesCount DESC")
    fun getProfilesByGame(gameId: String): Flow<List<GamerProfileEntity>>

    @Query("SELECT * FROM gamer_profiles WHERE id = :id LIMIT 1")
    fun getProfileById(id: String): Flow<GamerProfileEntity?>

    @Query("SELECT * FROM gamer_profiles WHERE id = 'user_me' LIMIT 1")
    fun getMyProfile(): Flow<GamerProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: GamerProfileEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfiles(profiles: List<GamerProfileEntity>)

    @Update
    suspend fun updateProfile(profile: GamerProfileEntity)

    @Query("UPDATE gamer_profiles SET likesCount = likesCount + 1 WHERE id = :id")
    suspend fun incrementLike(id: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEndorsement(endorsement: CardEndorsementEntity)

    @Query("SELECT * FROM card_endorsements WHERE targetGamerId = :gamerId")
    fun getEndorsementsForGamer(gamerId: String): Flow<List<CardEndorsementEntity>>

    @Query("SELECT COUNT(*) FROM gamer_profiles")
    suspend fun getProfileCount(): Int
}
