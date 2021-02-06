package com.github.tingyugetc520.ali.dingtalk.bean.message;

import com.github.tingyugetc520.ali.dingtalk.bean.message.builder.*;
import com.google.gson.JsonObject;
import lombok.Data;

import java.io.Serializable;

import static com.github.tingyugetc520.ali.dingtalk.constant.DtConstant.AppMsgType.*;

/**
 * 消息.
 */
@Data
public class DtMessage implements Serializable {
    private static final long serialVersionUID = -2082278303476631708L;

    private String msgType;

    /**
     * text
     */
    private String content;
    /**
     * image file voice
     */
    private String mediaId;
    /**
     * voice
     */
    private Integer duration;
    /**
     * link
     */
    private String messageUrl;
    /**
     * link
     */
    private String picUrl;
    /**
     * link md card
     */
    private String title;
    /**
     * link md
     */
    private String text;
    /**
     * card
     */
    private String markdown;
    /**
     * card
     */
    private String singleTitle;
    /**
     * card
     */
    private String singleUrl;


    /**
     * 获得文本消息builder.
     */
    public static TextBuilder TEXT() {
        return new TextBuilder();
    }

    /**
     * 获得图片消息builder.
     */
    public static ImageBuilder IMAGE() {
        return new ImageBuilder();
    }

    /**
     * 获得语音消息builder.
     */
    public static VoiceBuilder VOICE() {
        return new VoiceBuilder();
    }

    /**
     * 获得文件消息builder.
     */
    public static FileBuilder FILE() {
        return new FileBuilder();
    }

    /**
     * 获得markdown消息builder.
     */
    public static MarkdownBuilder MARKDOWN() {
        return new MarkdownBuilder();
    }

    /**
     * 获得卡片消息builder.
     */
    public static ActionCardBuilder ACTIONCARD() {
        return new ActionCardBuilder();
    }

    /**
     * 请使用.
     * {@link com.github.tingyugetc520.ali.dingtalk.constant.DtConstant.AppMsgType}
     *
     * @param msgType 消息类型
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public JsonObject toJsonObject() {
        JsonObject messageJson = new JsonObject();
        messageJson.addProperty("msgtype", this.getMsgType());

        this.handleMsgType(messageJson);

        return messageJson;
    }

    public String toJson() {
        return toJsonObject().toString();
    }

    private void handleMsgType(JsonObject messageJson) {
        switch (this.getMsgType()) {
            case TEXT: {
                JsonObject text = new JsonObject();
                text.addProperty("content", this.getContent());
                messageJson.add("text", text);
                break;
            }
            case IMAGE: {
                JsonObject image = new JsonObject();
                image.addProperty("media_id", this.getMediaId());
                messageJson.add("image", image);
                break;
            }
            case VOICE: {
                JsonObject voice = new JsonObject();
                voice.addProperty("media_id", this.getMediaId());
                voice.addProperty("duration", this.duration);
                messageJson.add("voice", voice);
                break;
            }
            case FILE: {
                JsonObject file = new JsonObject();
                file.addProperty("media_id", this.getMediaId());
                messageJson.add("file", file);
                break;
            }
            case LINK: {
                JsonObject link = new JsonObject();
                link.addProperty("messageUrl", this.getMessageUrl());
                link.addProperty("picUrl", this.getPicUrl());
                link.addProperty("title", this.getTitle());
                link.addProperty("text", this.getText());
                messageJson.add("link", link);
                break;
            }
            case MARKDOWN: {
                JsonObject md = new JsonObject();
                md.addProperty("title", this.getTitle());
                md.addProperty("text", this.getText());
                messageJson.add("markdown", md);
                break;
            }
            case ACTIONCARD: {
                JsonObject card = new JsonObject();
                card.addProperty("title", this.getTitle());
                card.addProperty("markdown", this.getMarkdown());
                card.addProperty("single_title", this.getSingleTitle());
                card.addProperty("single_url", this.getSingleUrl());
                messageJson.add("action_card", card);
                break;
            }
            default: {
                // do nothing
            }
        }
    }

}
