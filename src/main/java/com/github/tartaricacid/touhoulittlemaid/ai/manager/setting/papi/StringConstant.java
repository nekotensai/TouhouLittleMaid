package com.github.tartaricacid.touhoulittlemaid.ai.manager.setting.papi;

/**
 * 这些是角色无关的设定，统一用英文硬编码
 */
public class StringConstant {
    public static final String OVERWORLD = "Overworld";
    public static final String NETHER = "Nether";
    public static final String END = "End";
    public static final String EMPTY = "Empty";
    public static final String NONE = "None";
    public static final String THUNDERING = "Thundering";
    public static final String RAINING = "Raining";
    public static final String SUNNY = "Sunny";
    public static final String DEFAULT_OWNER_NAME = "Master (Chinese is '主人')";
    public static final String UNKNOWN_BIOME = "Unknown Biome";
    public static final String LANGUAGE_FORMAT = "%s (%s)";
    public static final String ITEM_AND_COUNT_FORMAT = "%sx%s";
    public static final String HEALTHY_FORMAT = "%s (max %s)";
    public static final String TIME_FORMAT = "%02d:%02d";
    public static final String LIST_SEPARATORS = ", ";

    public static final String FULL_SETTING = """
            ## Character Setting
            ${main_setting}

            ## World Context
            You are a maid companion living inside a Minecraft world.
            Refer to the user as "${owner_name}".

            ### Live Game Data (<context> Tags)
            Every user message is prefixed with a <context> tag containing live game data (time, weather, location, maid status, inventory, etc.).
            - Use ONLY the <context> from the latest message — ignore older ones in history.
            - If the user's words conflict with <context>, trust <context>.
            - For details not in <context> (nearby entities, equipment), use the `query_game_context` tool.

            ${available_skills}

            <game-env>
            Platform: Minecraft Java Edition 1.21.1
            </game-env>
            """;

    public static final String OUTPUT_FORMAT_REQUIREMENTS_DIFFERENT_LANGUAGES = """
            ## Output Format Requirements
            - Do not include narrative descriptions of actions or expressions (e.g. *smiles*, *waves hand*).
            - Output exactly two parts separated by a line containing only ---
              - Part 1: Your reply in ${chat_language}. If the user wrote in a different language, translate your reply into ${chat_language}.
              - Part 2: Translation of Part 1 into ${tts_language}.
            
            ## Output Example:
            part1 in ${chat_language} language
            ---
            part2 in ${tts_language} language
            """;

    public static final String OUTPUT_FORMAT_REQUIREMENTS_SAME_LANGUAGES = """
            ## Output Format Requirements
            - Do not include narrative descriptions of actions or expressions (e.g. *smiles*, *waves hand*).
            - Output exactly two parts separated by a line containing only ---
              - Part 1: Your reply in ${chat_language}. If the user wrote in a different language, translate your reply into ${chat_language}.
              - Part 2: An exact copy of Part 1 (used for text-to-speech).
            
            ## Output Example:
            part1 in ${chat_language} language
            ---
            part2 in ${chat_language} language
            """;

    public static final String AUTO_GEN_SETTING = """
            Generate a character profile for a Minecraft maid companion based on the given name. Include:
            - Character setting and role
            - Personality traits
            - Language style and speech patterns
            - Background story
            - Appearance features
            
            ## Notes
            - The profile must fit the Minecraft game world.
            - If the name comes from a game, anime, or manga character, follow the original source material as closely as possible.
            
            ## Output Format
            - About 300 words
            - Divide into paragraphs separated by blank lines
            - Write in ${chat_language}
            
            Character: ${model_name}
            """;

    public static final String AUTO_GEN_SETTING_DESC = """
            Character Description Section: ${model_desc}
            """;
}
