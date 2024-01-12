package io.duckling.hexintrospection.casting.mishaps

import at.petrak.hexcasting.api.misc.FrozenColorizer
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.GarbageIota
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.Mishap
import net.minecraft.block.Block
import net.minecraft.text.Text
import net.minecraft.util.DyeColor

class MishapInvalidInfuseTarget(val wrongTarget: Block) : Mishap(){
    override fun accentColor(ctx: CastingContext, errorCtx: Context): FrozenColorizer =
        dyeColor(DyeColor.MAGENTA)

    override fun errorMessage(ctx: CastingContext, errorCtx: Context): Text =
        error("Incorrect infuse target: ${wrongTarget.name} instead of EyeInfuse")

    override fun execute(ctx: CastingContext, errorCtx: Context, stack: MutableList<Iota>) {
       stack.add(GarbageIota())
    }
}