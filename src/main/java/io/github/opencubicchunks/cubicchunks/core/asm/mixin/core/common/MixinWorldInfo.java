/*
 *  This file is part of Cubic Chunks Mod, licensed under the MIT License (MIT).
 *
 *  Copyright (c) 2015-2019 OpenCubicChunks
 *  Copyright (c) 2015-2019 contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package io.github.opencubicchunks.cubicchunks.core.asm.mixin.core.common;

import io.github.opencubicchunks.cubicchunks.core.asm.mixin.ICubicWorldSettings;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mixin(WorldInfo.class)
public class MixinWorldInfo implements ICubicWorldSettings {

    private boolean isCubic;

    @Inject(method = "populateFromWorldSettings", at = @At("RETURN"))
    private void onConstructWithSettings(WorldSettings settings, CallbackInfo cbi) {
        this.isCubic = ((ICubicWorldSettings) (Object) settings).isCubic();
    }

    @Inject(method = "<init>(Lnet/minecraft/world/storage/WorldInfo;)V", at = @At("RETURN"))
    private void onConstructWithSettings(WorldInfo other, CallbackInfo cbi) {
        this.isCubic = ((ICubicWorldSettings) other).isCubic();
    }

    @Inject(method = "<init>(Lnet/minecraft/nbt/NBTTagCompound;)V", at = @At("RETURN"))
    private void onConstructWithSettings(NBTTagCompound tag, CallbackInfo cbi) {
        this.isCubic = tag.getBoolean("isCubicWorld");
    }

    @Inject(method = "updateTagCompound", at = @At("RETURN"))
    private void onConstructWithSettings(NBTTagCompound nbt, NBTTagCompound playerNbt, CallbackInfo cbi) {
        nbt.setBoolean("isCubicWorld", isCubic);
    }

    @Override public boolean isCubic() {
        return isCubic;
    }

    @Override public void setCubic(boolean cubic) {
        this.isCubic = cubic;
    }
}
