package fish.spiknyckel.beautfulmod.mixin;

// https://github.com/BismuthServer/ResourceLoaderFix/blob/main/src/main/java/si/bismuth/ResourceLoaderFix/mixin/CraftingManagerMixin.java
import net.minecraft.crafting.CraftingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

@Mixin(CraftingManager.class)
public class CraftingManagerMixin {
	@Redirect(
		method = "load",
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/stream/Stream;iterator()Ljava/util/Iterator;",
			remap = false
		)
	)
	private static Iterator<Path> fixRecipesIdDependenceOnFileSystemWalkOrder(final Stream<Path> instance) {
		return instance.sorted((a, b) -> b.toString().compareTo(a.toString())).iterator();
	}
}
