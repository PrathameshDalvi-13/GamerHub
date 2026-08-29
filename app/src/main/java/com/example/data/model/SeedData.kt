package com.example.data.model

import com.example.data.local.entities.ChatMessageEntity
import com.example.data.local.entities.GamerProfileEntity
import com.example.data.local.entities.SquadEntity
import com.example.data.local.entities.SquadMemberEntity

object SeedData {
    fun getInitialProfiles(): List<GamerProfileEntity> {
        return listOf(
            GamerProfileEntity(
                id = "user_me",
                gamertag = "ShadowViper",
                tagline = "AIM#9941",
                mainGameId = "valorant",
                secondaryGameId = "apex",
                currentRank = "Ascendant 2",
                peakRank = "Immortal 1",
                preferredRole = "Duelist / IGL",
                signatureHero = "Jett / Reyna",
                playstyle = "Competitive Grinder",
                kdRatio = 1.82f,
                winRate = 64,
                headshotPercentage = 39,
                totalMatches = 482,
                region = "NA East",
                platform = "PC",
                bio = "Calm comms, aggressive entry fragger. Looking for a serious 5-stack for weekend premier tournaments.",
                discordTag = "shadow_viper#0001",
                hasMic = true,
                isLookingForTeam = true,
                skinName = "NEON_CYAN",
                likesCount = 142,
                isUserUploaded = true,
                avatarSeed = "1"
            ),
            GamerProfileEntity(
                id = "player_2",
                gamertag = "AuraKnight",
                tagline = "CHAMP#101",
                mainGameId = "lol",
                secondaryGameId = "cs2",
                currentRank = "Grandmaster",
                peakRank = "Challenger",
                preferredRole = "Mid Lane",
                signatureHero = "Azir / Syndra",
                playstyle = "Tournament Grinder",
                kdRatio = 2.45f,
                winRate = 68,
                headshotPercentage = 0,
                totalMatches = 920,
                region = "EU West",
                platform = "PC",
                bio = "Macro shotcaller with 4 years amateur league experience. Looking for serious Clash/Amateur team.",
                discordTag = "auraknight#7720",
                hasMic = true,
                isLookingForTeam = true,
                skinName = "INFERNO_EMBER",
                likesCount = 289,
                isUserUploaded = false,
                avatarSeed = "2"
            ),
            GamerProfileEntity(
                id = "player_3",
                gamertag = "GhostSniper",
                tagline = "AWP#7700",
                mainGameId = "cs2",
                secondaryGameId = "valorant",
                currentRank = "Premier 21,500",
                peakRank = "Global Elite",
                preferredRole = "AWPer / Sniper",
                signatureHero = "AWP / Desert Eagle",
                playstyle = "Competitive Grinder",
                kdRatio = 2.15f,
                winRate = 61,
                headshotPercentage = 54,
                totalMatches = 1140,
                region = "NA East",
                platform = "PC",
                bio = "Dedicated AWPer with utility knowledge for all active duty maps. Available evenings.",
                discordTag = "ghost_cs#4412",
                hasMic = true,
                isLookingForTeam = true,
                skinName = "VIOLET_PULSE",
                likesCount = 310,
                isUserUploaded = false,
                avatarSeed = "3"
            ),
            GamerProfileEntity(
                id = "player_4",
                gamertag = "VortexWing",
                tagline = "PRED#099",
                mainGameId = "apex",
                secondaryGameId = "ow2",
                currentRank = "Master",
                peakRank = "Apex Predator",
                preferredRole = "Skirmisher",
                signatureHero = "Horizon / Wraith",
                playstyle = "High Aggro",
                kdRatio = 3.20f,
                winRate = 26,
                headshotPercentage = 31,
                totalMatches = 2400,
                region = "NA West",
                platform = "PC",
                bio = "Predator movement player. Looking for a steady 3rd to grind Ranked and ALGS Challenger Circuit.",
                discordTag = "vortex_apex#1212",
                hasMic = true,
                isLookingForTeam = true,
                skinName = "CRIMSON_WARP",
                likesCount = 415,
                isUserUploaded = false,
                avatarSeed = "4"
            ),
            GamerProfileEntity(
                id = "player_5",
                gamertag = "NanoShield",
                tagline = "HEAL#4200",
                mainGameId = "ow2",
                secondaryGameId = "valorant",
                currentRank = "Grandmaster 3",
                peakRank = "Top 500",
                preferredRole = "Flex Support",
                signatureHero = "Ana / Kiriko / Baptiste",
                playstyle = "Strategic Team Player",
                kdRatio = 1.65f,
                winRate = 63,
                headshotPercentage = 24,
                totalMatches = 850,
                region = "EU West",
                platform = "PC",
                bio = "Top tier sleep darts and proactive ult tracking. Positive mental attitude only!",
                discordTag = "nanoshield#3309",
                hasMic = true,
                isLookingForTeam = true,
                skinName = "EMERALD_MATRIX",
                likesCount = 205,
                isUserUploaded = false,
                avatarSeed = "5"
            ),
            GamerProfileEntity(
                id = "player_6",
                gamertag = "NeonSpectre",
                tagline = "VAL#1337",
                mainGameId = "valorant",
                secondaryGameId = "cs2",
                currentRank = "Diamond 3",
                peakRank = "Ascendant 2",
                preferredRole = "Controller",
                signatureHero = "Omen / Viper / Astra",
                playstyle = "Support & Setup",
                kdRatio = 1.35f,
                winRate = 58,
                headshotPercentage = 32,
                totalMatches = 390,
                region = "NA East",
                platform = "PC",
                bio = "Smoke lineups for every bomb site. Never miss a round start smoke.",
                discordTag = "neonsmoke#8891",
                hasMic = true,
                isLookingForTeam = true,
                skinName = "NEON_CYAN",
                likesCount = 98,
                isUserUploaded = false,
                avatarSeed = "6"
            )
        )
    }

    fun getInitialSquads(): List<SquadEntity> {
        return listOf(
            SquadEntity(
                id = "squad_val_1",
                gameId = "valorant",
                name = "Phantom Syndicate",
                tag = "PSYN",
                leaderId = "user_me",
                leaderName = "ShadowViper",
                targetRank = "Ascendant / Immortal",
                region = "NA East",
                gameMode = "Competitive 5v5 / Premier",
                requiredMic = true,
                teamSize = 5,
                description = "Pushing for Premier playoffs this season. Need dedicated Controller and Sentinel with disciplined comms.",
                scheduledTime = "Tonight 8:30 PM EST",
                discordLink = "discord.gg/phantom-syn",
                isOpen = true
            ),
            SquadEntity(
                id = "squad_apex_1",
                gameId = "apex",
                name = "Predator Protocol",
                tag = "PRED",
                leaderId = "player_4",
                leaderName = "VortexWing",
                targetRank = "Diamond / Master",
                region = "NA West",
                gameMode = "Ranked Trios",
                requiredMic = true,
                teamSize = 3,
                description = "Grinding Master to Pred before split reset. Need a reliable Support / Controller legend player.",
                scheduledTime = "Daily 7:00 PM PST",
                discordLink = "discord.gg/pred-proto",
                isOpen = true
            ),
            SquadEntity(
                id = "squad_lol_1",
                gameId = "lol",
                name = "Nexus Dynasty",
                tag = "NDYN",
                leaderId = "player_2",
                leaderName = "AuraKnight",
                targetRank = "Emerald / Diamond+",
                region = "EU West",
                gameMode = "Clash Cup & Flex 5v5",
                requiredMic = true,
                teamSize = 5,
                description = "Weekly Tier 1 Clash tournament roster. Practicing 3 nights a week with VOD reviews.",
                scheduledTime = "Sat & Sun 6:00 PM CET",
                discordLink = "discord.gg/nexus-dynasty",
                isOpen = true
            ),
            SquadEntity(
                id = "squad_cs2_1",
                gameId = "cs2",
                name = "Vanguard Executioners",
                tag = "VGEX",
                leaderId = "player_3",
                leaderName = "GhostSniper",
                targetRank = "Premier 18k - 24k",
                region = "NA East",
                gameMode = "Premier & FaceIt Lvl 8-10",
                requiredMic = true,
                teamSize = 5,
                description = "Looking for an aggressive Entry Fragger and consistent Support to complete our 5-man stack.",
                scheduledTime = "Tue/Thu/Sat 9:00 PM EST",
                discordLink = "discord.gg/vanguard-cs",
                isOpen = true
            ),
            SquadEntity(
                id = "squad_ow2_1",
                gameId = "ow2",
                name = "Aegis Titans",
                tag = "AGIS",
                leaderId = "player_5",
                leaderName = "NanoShield",
                targetRank = "Master / GM",
                region = "EU West",
                gameMode = "Competitive Role Queue",
                requiredMic = true,
                teamSize = 5,
                description = "Dive and Brawl comps. Need an assertive Tank player who knows Winston/D.Va/Sigma.",
                scheduledTime = "Fri 8:00 PM CET",
                discordLink = "discord.gg/aegis-ow",
                isOpen = true
            )
        )
    }

    fun getInitialMembers(): List<SquadMemberEntity> {
        return listOf(
            // Valorant squad members
            SquadMemberEntity(squadId = "squad_val_1", gamerId = "user_me", gamerName = "ShadowViper", gamerRank = "Ascendant 2", assignedRole = "Duelist / IGL", isLeader = true, isReady = true),
            SquadMemberEntity(squadId = "squad_val_1", gamerId = "player_6", gamerName = "NeonSpectre", gamerRank = "Diamond 3", assignedRole = "Controller", isLeader = false, isReady = true),
            // Apex squad members
            SquadMemberEntity(squadId = "squad_apex_1", gamerId = "player_4", gamerName = "VortexWing", gamerRank = "Master", assignedRole = "Skirmisher", isLeader = true, isReady = true),
            // LoL squad members
            SquadMemberEntity(squadId = "squad_lol_1", gamerId = "player_2", gamerName = "AuraKnight", gamerRank = "Grandmaster", assignedRole = "Mid Lane", isLeader = true, isReady = true),
            // CS2 squad members
            SquadMemberEntity(squadId = "squad_cs2_1", gamerId = "player_3", gamerName = "GhostSniper", gamerRank = "Premier 21k", assignedRole = "AWPer", isLeader = true, isReady = true),
            // OW2 squad members
            SquadMemberEntity(squadId = "squad_ow2_1", gamerId = "player_5", gamerName = "NanoShield", gamerRank = "GM 3", assignedRole = "Flex Support", isLeader = true, isReady = true)
        )
    }

    fun getInitialChatMessages(): List<ChatMessageEntity> {
        val now = System.currentTimeMillis()
        return listOf(
            // Valorant general & LFG
            ChatMessageEntity(id = "msg_val_1", scope = "GAME", targetId = "valorant", channelName = "general", senderId = "player_6", senderName = "NeonSpectre", senderRank = "Diamond 3", senderGameRole = "Controller", text = "Anyone down for some Ascendant/Diamond 5-stack comp games tonight?", timestamp = now - 3600000),
            ChatMessageEntity(id = "msg_val_2", scope = "GAME", targetId = "valorant", channelName = "general", senderId = "user_me", senderName = "ShadowViper", senderRank = "Ascendant 2", senderGameRole = "Duelist", text = "Yeah I'm hosting a squad for Premier playoffs at 8:30 PM. Check our squad post!", timestamp = now - 3200000),
            ChatMessageEntity(id = "msg_val_3", scope = "GAME", targetId = "valorant", channelName = "lfg-ranked", senderId = "player_6", senderName = "NeonSpectre", senderRank = "Diamond 3", senderGameRole = "Controller", text = "LFG +2 Diamond/Ascendant. Have mic, playing Haven & Ascent.", timestamp = now - 2400000),
            ChatMessageEntity(id = "msg_val_4", scope = "GAME", targetId = "valorant", channelName = "tips-strats", senderId = "user_me", senderName = "ShadowViper", senderRank = "Ascendant 2", senderGameRole = "Duelist", text = "Pro tip: Double flash onto B site Sunset after Omen paranoia wins 80% of pistol rounds.", timestamp = now - 1800000),

            // Apex chat
            ChatMessageEntity(id = "msg_ap_1", scope = "GAME", targetId = "apex", channelName = "general", senderId = "player_4", senderName = "VortexWing", senderRank = "Master", senderGameRole = "Skirmisher", text = "Need +1 Master/Diamond for fast KP rotations on Olympus.", timestamp = now - 4000000),
            ChatMessageEntity(id = "msg_ap_2", scope = "GAME", targetId = "apex", channelName = "lfg-ranked", senderId = "player_4", senderName = "VortexWing", senderRank = "Master", senderGameRole = "Skirmisher", text = "Squad is 2/3. Need a Support legend (Conduit or Newcastle preferred).", timestamp = now - 2800000),

            // LoL chat
            ChatMessageEntity(id = "msg_lol_1", scope = "GAME", targetId = "lol", channelName = "general", senderId = "player_2", senderName = "AuraKnight", senderRank = "Grandmaster", senderGameRole = "Mid Lane", text = "Clash lock-in opens this Saturday! Recruiting Top and ADC players.", timestamp = now - 5000000),
            ChatMessageEntity(id = "msg_lol_2", scope = "GAME", targetId = "lol", channelName = "lfg-ranked", senderId = "player_2", senderName = "AuraKnight", senderRank = "Grandmaster", senderGameRole = "Mid Lane", text = "Looking for Duo partner Emerald+ to push Master before season split.", timestamp = now - 3100000),

            // CS2 chat
            ChatMessageEntity(id = "msg_cs_1", scope = "GAME", targetId = "cs2", channelName = "general", senderId = "player_3", senderName = "GhostSniper", senderRank = "Premier 21k", senderGameRole = "AWPer", text = "Mirage & Inferno executes practice in 30 mins. Drop tags if you want in.", timestamp = now - 3500000),
            ChatMessageEntity(id = "msg_cs_2", scope = "GAME", targetId = "cs2", channelName = "lfg-ranked", senderId = "player_3", senderName = "GhostSniper", senderRank = "Premier 21k", senderGameRole = "AWPer", text = "Need 5th for 20k+ Premier lobby right now! Good comms mandatory.", timestamp = now - 1500000),

            // Overwatch 2 chat
            ChatMessageEntity(id = "msg_ow_1", scope = "GAME", targetId = "ow2", channelName = "general", senderId = "player_5", senderName = "NanoShield", senderRank = "GM 3", senderGameRole = "Flex Support", text = "Looking for a high energy Winston/Doom player for Flashpoint scrims.", timestamp = now - 4200000),
            ChatMessageEntity(id = "msg_ow_2", scope = "GAME", targetId = "ow2", channelName = "lfg-ranked", senderId = "player_5", senderName = "NanoShield", senderRank = "GM 3", senderGameRole = "Flex Support", text = "GM lobby open. Need 1 Hitscan DPS and 1 Main Tank.", timestamp = now - 1900000),

            // Global Chat
            ChatMessageEntity(id = "msg_glob_1", scope = "GLOBAL", targetId = "global", channelName = "lounge", senderId = "user_me", senderName = "ShadowViper", senderRank = "Ascendant 2", senderGameRole = "Duelist", text = "Welcome everyone to SquadForge! Find your dream team and dominate.", timestamp = now - 8000000),
            ChatMessageEntity(id = "msg_glob_2", scope = "GLOBAL", targetId = "global", channelName = "lounge", senderId = "player_2", senderName = "AuraKnight", senderRank = "Grandmaster", senderGameRole = "Mid Lane", text = "Drop your gamer cards below! Let's build squads for weekend tourneys.", timestamp = now - 7200000)
        )
    }
}
