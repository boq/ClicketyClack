package openmods.clicky.hax;

import net.minecraft.launchwrapper.Launch;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class MethodMatcher {
    private final String clsName;
    private final String description;
    private final String srgName;
    private final String mcpName;

    public MethodMatcher(String clsName, String description, String mcpName, String srgName) {
        this.clsName = clsName;
        this.description = description;
        this.srgName = srgName;
        this.mcpName = mcpName;
    }

    public static boolean useSrgNames() {
        Boolean deobfuscated = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
        return deobfuscated == null || !deobfuscated;
    }

    public boolean match(String methodName, String methodDesc) {
        if (!methodDesc.equals(description))
            return false;
        if (methodName.equals(mcpName))
            return true;
        if (!useSrgNames())
            return false;
        String mapped = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(clsName, methodName, methodDesc);
        return mapped.equals(srgName);
    }
}
