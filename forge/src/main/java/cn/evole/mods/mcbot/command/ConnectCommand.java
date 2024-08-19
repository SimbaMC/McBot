package cn.evole.mods.mcbot.command;


import cn.evole.mods.mcbot.Const;
import cn.evole.mods.mcbot.IMcBot;
import cn.evole.mods.mcbot.config.ModConfig;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import java.util.regex.Pattern;
import net.minecraft.network.chat.Component;
//#if MC <11900
import net.minecraft.network.chat.TextComponent;
//#endif

public class ConnectCommand {

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        val parameter = context.getArgument("parameter", String.class);

        val pattern = Pattern.compile(":");
        val matcher = pattern.matcher(parameter);
        if (matcher.find()) {
            ModConfig.INSTANCE.getBotConfig().setUrl(parameter);
            ModConfig.INSTANCE.save();
            doConnect(context);
            return 1;
        } else {
            //#if MC >= 12000
            //$$ context.getSource().sendSuccess(()->Component.literal("▌ " +ChatFormatting.RED + "参数错误❌"), true);
            //#elseif MC < 11900
            context.getSource().sendSuccess(new TextComponent("▌ " +ChatFormatting.RED + "参数错误❌"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("▌ " +ChatFormatting.RED + "参数错误❌"), true);
            //#endif
            return 0;
        }
    }


    public static int commonExecute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        doConnect(context);
        return 1;
    }

    public static void doConnect(CommandContext<CommandSourceStack> context) {
        if (!IMcBot.onebot.getWs().isOpen()){
            //#if MC >= 12000
            //$$ context.getSource().sendSuccess(()->Component.literal("▌ " +ChatFormatting.LIGHT_PURPLE + "尝试链接框架"), true);
            //#elseif MC < 11900
            context.getSource().sendSuccess(new TextComponent("▌ " +ChatFormatting.LIGHT_PURPLE + "尝试链接框架"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("▌ " +ChatFormatting.LIGHT_PURPLE + "尝试链接框架"), true);
            //#endif
            Const.wsConnect();
        } else {
            //#if MC >= 12000
            //$$ context.getSource().sendSuccess(()->Component.literal("▌ " +ChatFormatting.LIGHT_PURPLE + "已存在WS连接"), true);
            //#elseif MC < 11900
            context.getSource().sendSuccess(new TextComponent("▌ " + ChatFormatting.LIGHT_PURPLE + "已存在WS连接"), true);
            //#else
            //$$ context.getSource().sendSuccess(Component.literal("▌ " +ChatFormatting.LIGHT_PURPLE + "已存在WS连接"), true);
            //#endif
        }
    }


}
