package io.duckling.hexintrospection.casting.patterns.spells

import at.petrak.hexcasting.api.spell.ParticleSpray
import at.petrak.hexcasting.api.spell.RenderedSpell
import at.petrak.hexcasting.api.spell.SpellAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getVec3
import at.petrak.hexcasting.api.spell.iota.Iota
import io.duckling.hexintrospection.EyeInfusion
import io.duckling.hexintrospection.HexIntrospection
import io.duckling.hexintrospection.casting.mishaps.MishapInvalidInfusion
import io.duckling.hexintrospection.registry.HexIntrospectionBlockEntityRegistry
import io.duckling.hexintrospection.registry.HexIntrospectionBlockRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.client.network.BlockListChecker
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f
import org.apache.commons.codec.binary.Hex
import kotlin.math.abs

class OpCreateInfusion : SpellAction {
    override val argc = 2

    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>>? {
        val vec0 = args.getVec3(0, argc)
        val vec1 = args.getVec3(1, argc)




        return Triple(
            Spell(),
            100,
            listOf()
        )

    }

    private class Spell() : RenderedSpell{
        override fun cast(ctx: CastingContext) {

        }

    }

}