package creeperpookie.antichatclose;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = AntiChatCloseMod.MODID, name = AntiChatCloseMod.NAME, version = AntiChatCloseMod.VERSION, clientSideOnly = true)
public class AntiChatCloseMod
{
    public static final String MODID = "antichatclose";
    public static final String NAME = "Anti Chat Close";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // This is where you can register your event handlers, commands, etc.
        logger.info("Initializing Anti Chat Close");
    }
}
