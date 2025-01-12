package io.duckling.hexintrospection.registry;

import at.petrak.hexcasting.api.PatternRegistry;
import at.petrak.hexcasting.api.spell.Action;
import at.petrak.hexcasting.api.spell.math.HexDir;
import at.petrak.hexcasting.api.spell.math.HexPattern;
import io.duckling.hexintrospection.HexIntrospection;
import io.duckling.hexintrospection.casting.patterns.actions.OpEyeRaycast;
import io.duckling.hexintrospection.casting.patterns.math.OpSignum;
import io.duckling.hexintrospection.casting.patterns.spells.OpCongrats;
import io.duckling.hexintrospection.casting.patterns.spells.OpCreateInfusion;
import kotlin.Triple;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class HexIntrospectionPatternRegistry {
    public static List<Triple<HexPattern, Identifier, Action>> PATTERNS = new ArrayList<>();
    public static List<Triple<HexPattern, Identifier, Action>> PER_WORLD_PATTERNS = new ArrayList<>();
    // IMPORTANT: be careful to keep the registration calls looking like this or be prepared to edit the regex pattern on line 199 of the docgen script (doc/collate_data.py)
    public static HexPattern CONGRATS = registerPerWorld(HexPattern.fromAngles("eed", HexDir.WEST), "congrats", new OpCongrats());
    public static HexPattern SIGNUM = register(HexPattern.fromAngles("edd", HexDir.NORTH_WEST), "signum", new OpSignum());
    public static HexPattern EYEFIND = register(HexPattern.fromAngles("eaa", HexDir.NORTH_WEST), "eyefind", new OpEyeRaycast());
    public static HexPattern CREATEINFUSION = register(HexPattern.fromAngles("eaqa",HexDir.NORTH_WEST),"createinfusion", new OpCreateInfusion());


    public static void init() {
        try {
            for (Triple<HexPattern, Identifier, Action> patternTriple : PATTERNS) {
                PatternRegistry.mapPattern(patternTriple.getFirst(), patternTriple.getSecond(), patternTriple.getThird());
            }
            for (Triple<HexPattern, Identifier, Action> patternTriple : PER_WORLD_PATTERNS) {
                PatternRegistry.mapPattern(patternTriple.getFirst(), patternTriple.getSecond(), patternTriple.getThird(), true);
            }
        } catch (PatternRegistry.RegisterPatternException e) {
            e.printStackTrace();
        }
    }

    private static HexPattern register(HexPattern pattern, String name, Action action) {
        Triple<HexPattern, Identifier, Action> triple = new Triple<>(pattern, HexIntrospection.id(name), action);
        PATTERNS.add(triple);
        return pattern;
    }

    private static HexPattern registerPerWorld(HexPattern pattern, String name, Action action) {
        Triple<HexPattern, Identifier, Action> triple = new Triple<>(pattern, HexIntrospection.id(name), action);
        PER_WORLD_PATTERNS.add(triple);
        return pattern;
    }
}
