package org.encinet.kitebot.event;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class LoginInterceptor {

    public static void intercept(ServerLoginNetworkHandler handler, MinecraftServer server, ClientConnection connection, String userName) {
        if (!isUserNameValid(userName)) {
            handler.disconnect(new LiteralText("您的用户名不符合要求，请更改后再试！"));
        } else {
            // 允许玩家进入服务器
            handler.onHello(new ServerLoginNetworkHandler.LoginRequestHandler(connection));
        }
    }

    private static boolean isUserNameValid(String userName) {
        // 检查玩家用户名是否符合要求
        // 这里只是演示，实际应用中需要根据具体需求编写判断逻辑
        return userName.length() >= 3 && userName.length() <= 16;
    }

}