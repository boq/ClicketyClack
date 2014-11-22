package openmods.clicky.hax;

import openmods.clicky.GuiInputEvent.GuiKeyInputEvent;
import openmods.clicky.GuiInputEvent.GuiMouseInputEvent;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.Method;

import cpw.mods.fml.common.FMLCommonHandler;

public class GuiScreenInjector extends ClassVisitor {

    private static class CallInjector extends MethodVisitor {
        private final Type injectedCls;
        private final Method injectedMethod;

        public CallInjector(MethodVisitor mv, Type injectedCls, Method injectedMethod) {
            super(Opcodes.ASM5, mv);
            this.injectedCls = injectedCls;
            this.injectedMethod = injectedMethod;
        }

        @Override
        public void visitCode() {
            super.visitMethodInsn(Opcodes.INVOKESTATIC, injectedCls.getInternalName(), injectedMethod.getName(), injectedMethod.getDescriptor(), false);
        }
    }

    private static final String VOID_DESC = "()V";

    public static void signalKeyboardEvent() {
        FMLCommonHandler.instance().bus().post(new GuiKeyInputEvent());
    }

    public static void signalMouseEvent() {
        FMLCommonHandler.instance().bus().post(new GuiMouseInputEvent());
    }

    private final Type cls = Type.getType(getClass());

    private final MethodMatcher handleKeyboardMatcher;

    private final MethodMatcher handleMouseMatcher;

    private final Method signalKeyboardInput = new Method("signalKeyboardEvent", VOID_DESC);

    private final Method signalMouseInput = new Method("signalMouseEvent", VOID_DESC);

    public GuiScreenInjector(ClassVisitor parent, String rawCls) {
        super(Opcodes.ASM5, parent);

        handleMouseMatcher = new MethodMatcher(rawCls, VOID_DESC, "handleMouseInput", "func_146274_d");

        handleKeyboardMatcher = new MethodMatcher(rawCls, VOID_DESC, "handleKeyboardInput", "func_146282_l");
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        if (handleMouseMatcher.match(name, desc)) {
            return new CallInjector(mv, cls, signalMouseInput);
        } else if (handleKeyboardMatcher.match(name, desc)) {
            return new CallInjector(mv, cls, signalKeyboardInput);
        }

        return mv;
    }
}
