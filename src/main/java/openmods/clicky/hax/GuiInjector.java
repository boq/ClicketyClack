package openmods.clicky.hax;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class GuiInjector implements IClassTransformer {

    private static byte[] inject(String rawName, byte[] bytes) {
        ClassReader cr = new ClassReader(bytes);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor mod = new GuiScreenInjector(cw, rawName);
        cr.accept(mod, 0);
        return cw.toByteArray();
    }

    @Override
    public byte[] transform(String rawName, String transformedName, byte[] bytes) {
        if (bytes == null)
            return null;

        if (transformedName.equals("net.minecraft.client.gui.GuiScreen")) {
            return inject(rawName, bytes);
        }

        return bytes;
    }

}
