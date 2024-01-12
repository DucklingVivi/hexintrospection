package io.duckling.hexintrospection.casting.patterns.spells

import at.petrak.hexcasting.api.spell.*
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapInvalidIota
import io.duckling.hexintrospection.HexIntrospectionSaveManager
import io.duckling.hexintrospection.MindSphereEyeData
import io.duckling.hexintrospection.blocks.BlockEyeInfuse
import io.duckling.hexintrospection.casting.iota.EyeIota
import io.duckling.hexintrospection.casting.mishaps.MishapEyeWrongTier
import io.duckling.hexintrospection.casting.mishaps.MishapInvalidInfuseTarget
import io.duckling.hexintrospection.client.MindSphereClientEyeData
import io.duckling.hexintrospection.registry.HexIntrospectionBlockEntityRegistry
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import java.util.UUID

class OpInfuseEye : SpellAction {
    override val argc = 2;

    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>> {

        val vec3 = args.getVec3(0, argc)
        val eye = args.get(1)
        val pos =  BlockPos(vec3);

        ctx.assertVecInRange(vec3)


        if(eye !is EyeIota) {
            throw MishapInvalidIota(eye,1, Text.of("EyeIota"));
        }

        val state = ctx.world.getBlockState(pos)

        if(state.block !is BlockEyeInfuse){
            throw MishapInvalidInfuseTarget(state.block);
        }


        val mindeye = HexIntrospectionSaveManager.get(ctx.world.server).getMindSphereData(eye.dimension).eyeData.get(eye.index)
        if (mindeye != null) {


        }else{
            throw MishapInvalidIota(eye,1, Text.of("EyeIota"));
        }
        //TODO CHANGE MISHAP
        throw MishapInvalidIota(eye,1, Text.of("EyeIota"));
    }

    private data class Spell(val pos: BlockPos, val dimension: UUID, val index: Int) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            val mindeye = HexIntrospectionSaveManager.get(ctx.world.server).getMindSphereData(dimension).eyeData.get(index)
            val state = ctx.world.getBlockState(pos)
            val be = ctx.world.getBlockEntity(pos, HexIntrospectionBlockEntityRegistry.EYE_INFUSE.get())
            if (mindeye != null && be != null) {

            }
        }

    }

}