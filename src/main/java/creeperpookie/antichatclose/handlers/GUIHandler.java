package creeperpookie.antichatclose.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class GUIHandler
{
	private static String savedText = "";
	private static int delay = -1;
	private static boolean isChatEscapeClosed = false;

	@SubscribeEvent
	public static void onGUIOpen(GuiOpenEvent event)
	{
		if (!isChatEscapeClosed && Minecraft.getMinecraft().currentScreen instanceof GuiChat && !(event.getGui() instanceof GuiChat))
		{
			GuiChat guiChat = (GuiChat) Minecraft.getMinecraft().currentScreen;
			savedText = ((GuiTextField) ObfuscationReflectionHelper.getPrivateValue(GuiChat.class, guiChat, 4)).getText();
		}
		if (isChatEscapeClosed) isChatEscapeClosed = false;
	}

	@SubscribeEvent
	public static void onGUIKeyPress(GuiScreenEvent.KeyboardInputEvent.Pre event)
	{
		if (event.getGui() instanceof GuiChat && (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_RETURN) || Keyboard.isKeyDown(Keyboard.KEY_NUMPADENTER)))
		{
			delay = -1;
			savedText = "";
			isChatEscapeClosed = true;
		}
	}

	@SubscribeEvent
	public static void onTick(TickEvent.ClientTickEvent event)
	{
		if (!savedText.isEmpty() && !isChatEscapeClosed && event.phase == TickEvent.Phase.START && Minecraft.getMinecraft().currentScreen == null)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiChat());
			delay = 5;
		}
		else if (!savedText.isEmpty() && delay == 0 && event.phase == TickEvent.Phase.START && Minecraft.getMinecraft().currentScreen instanceof GuiChat)
		{
			GuiChat guiChat = (GuiChat) Minecraft.getMinecraft().currentScreen;
			GuiTextField textField = ObfuscationReflectionHelper.getPrivateValue(GuiChat.class, guiChat, 4);
			textField.setText(savedText);
			delay = -1;
			savedText = "";
		}
		if (delay > 0)
		{
			delay--;
		}
	}
}
