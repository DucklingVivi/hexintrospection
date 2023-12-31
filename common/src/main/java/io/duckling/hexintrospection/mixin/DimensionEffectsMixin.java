package io.duckling.hexintrospection.mixin;


import io.duckling.hexintrospection.DimensionEffectsAccess;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DimensionEffects.class)
public interface DimensionEffectsMixin{
    @Accessor("BY_IDENTIFIER")
    public static Object2ObjectMap<Identifier, DimensionEffects> getBY_IDENTITIER(){
        throw new AssertionError();
    }


}
