package com.example.data.model

import androidx.compose.ui.graphics.Color

enum class GameType(
    val id: String,
    val title: String,
    val shortName: String,
    val genre: String,
    val teamSize: Int,
    val description: String,
    val primaryColor: Long,
    val secondaryColor: Long,
    val availableRoles: List<String>,
    val ranks: List<String>
) {
    VALORANT(
        id = "valorant",
        title = "VALORANT",
        shortName = "VAL",
        genre = "Tactical FPS",
        teamSize = 5,
        description = "5v5 character-based tactical FPS where precise gunplay meets unique agent abilities.",
        primaryColor = 0xFFFF4655,
        secondaryColor = 0xFF0F1923,
        availableRoles = listOf("Duelist", "Initiator", "Controller", "Sentinel", "IGL"),
        ranks = listOf("Iron", "Bronze", "Silver", "Gold", "Platinum", "Diamond", "Ascendant", "Immortal", "Radiant")
    ),
    APEX_LEGENDS(
        id = "apex",
        title = "Apex Legends",
        shortName = "APEX",
        genre = "Battle Royale",
        teamSize = 3,
        description = "Hero shooter Battle Royale with legendary characters with powerful abilities.",
        primaryColor = 0xFFDA292A,
        secondaryColor = 0xFF2E0909,
        availableRoles = listOf("Assault", "Skirmisher", "Recon", "Controller", "Support", "IGL"),
        ranks = listOf("Rookie", "Bronze", "Silver", "Gold", "Platinum", "Diamond", "Master", "Apex Predator")
    ),
    LEAGUE_OF_LEGENDS(
        id = "lol",
        title = "League of Legends",
        shortName = "LoL",
        genre = "MOBA",
        teamSize = 5,
        description = "Team-based strategy game where two teams of five champions face off.",
        primaryColor = 0xFFC89B3C,
        secondaryColor = 0xFF0A1428,
        availableRoles = listOf("Top Lane", "Jungle", "Mid Lane", "ADC / Bot", "Support", "Shotcaller"),
        ranks = listOf("Iron", "Bronze", "Silver", "Gold", "Platinum", "Emerald", "Diamond", "Master", "Grandmaster", "Challenger")
    ),
    COUNTER_STRIKE_2(
        id = "cs2",
        title = "Counter-Strike 2",
        shortName = "CS2",
        genre = "Tactical Shooter",
        teamSize = 5,
        description = "The premier tactical shooter experience with realistic physics and precision shooting.",
        primaryColor = 0xFFF59E0B,
        secondaryColor = 0xFF1E293B,
        availableRoles = listOf("Entry Fragger", "AWPer / Sniper", "IGL / Leader", "Support / Utility", "Lurker"),
        ranks = listOf("Silver I-Elite", "Gold Nova", "Master Guardian", "Distinguished MG", "Eagle / Supreme", "Global Elite", "Premier 15k+", "Premier 25k+")
    ),
    OVERWATCH_2(
        id = "ow2",
        title = "Overwatch 2",
        shortName = "OW2",
        genre = "Hero Shooter",
        teamSize = 5,
        description = "Hero shooter set in an optimistic future with 5v5 competitive team-based action.",
        primaryColor = 0xFFFA9C1E,
        secondaryColor = 0xFF282C34,
        availableRoles = listOf("Tank", "Damage / Hitscan", "Damage / Flex", "Main Support", "Flex Support", "Shotcaller"),
        ranks = listOf("Bronze", "Silver", "Gold", "Platinum", "Diamond", "Master", "Grandmaster", "Champion", "Top 500")
    );

    companion object {
        fun fromId(id: String): GameType = entries.find { it.id.equals(id, ignoreCase = true) } ?: VALORANT
    }
}

enum class CardSkin(val displayName: String, val startColor: Long, val endColor: Long, val accentColor: Long) {
    NEON_CYAN("Neon Cyber", 0xFF0B1E2E, 0xFF0A3C53, 0xFF00F0FF),
    VIOLET_PULSE("Void Violet", 0xFF1C0B2E, 0xFF38145C, 0xFFB026FF),
    INFERNO_EMBER("Inferno Gold", 0xFF2A1504, 0xFF542407, 0xFFFF7B00),
    EMERALD_MATRIX("Matrix Green", 0xFF062413, 0xFF0A4422, 0xFF10B981),
    CRIMSON_WARP("Crimson Blade", 0xFF28070F, 0xFF500F1E, 0xFFFF0055)
}
