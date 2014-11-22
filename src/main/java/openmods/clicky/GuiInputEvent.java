package openmods.clicky;

import cpw.mods.fml.common.eventhandler.Event;

public class GuiInputEvent extends Event {

    public static class GuiMouseInputEvent extends GuiInputEvent {}

    public static class GuiKeyInputEvent extends GuiInputEvent {}

}
