package org.encinet.oceanbot.mirai.consciousness;

import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import org.encinet.oceanbot.mirai.consciousness.response.*;

public class CS {
  public static MessageChain enter(String msg) {
    // builder是否有内容
    boolean have = false;
    MessageChainBuilder builder = new MessageChainBuilder();
    //            File consciousnessAudio = Audio.get(miraiMessage);
    //            if (consciousnessAudio != null) {
    //                MiraiBot.getBot(BotID).getGroup(groupID)
    //                        .sendAudio(consciousnessAudio);
    //                Audio audio;
    //                try (ExternalResource resource = ExternalResource.create(consciousnessAudio))
    // {
    //
    //                }
    //                group.sendMessage(audio); // 发送语音消息
    //                contact.sendMessage(ExternalResource.uploadAsAudio(/*...*/));
    //            }
    String consciousnessText = Text.get(msg);
    if (consciousnessText != null) {
      have = true;
      builder.append(consciousnessText);
    }
    MessageChain mChain = builder.build();
    return mChain.toString().trim() == "" ? null : mChain;
  }
}
