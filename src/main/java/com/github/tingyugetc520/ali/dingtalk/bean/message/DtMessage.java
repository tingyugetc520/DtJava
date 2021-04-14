package com.github.tingyugetc520.ali.dingtalk.bean.message;

import com.github.tingyugetc520.ali.dingtalk.bean.message.builder.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

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
     * link oa
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
     * card
     */
    private String btnOrientation;

    /**
     * card
     * 0：竖直排列
     * 1：横向排列
     */
    private List<ActionCardBuilder.CardBtn> btnList;

    /**
     * oa
     */
    private OABuilder.OAHead oaHead;
    /**
     * oa
     */
    private OABuilder.OABody oaBody;


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
            case OA: {
                JsonObject oa = new JsonObject();
                oa.addProperty("messageUrl", this.getMessageUrl());

                if (this.getOaHead() != null) {
                    JsonObject headJson = new JsonObject();
                    headJson.addProperty("bgcolor", this.getOaHead().getBgColor());
                    headJson.addProperty("text", this.getOaHead().getText());
                    oa.add("head", headJson);
                }

                if (this.getOaBody() != null) {
                    JsonObject bodyJson = new JsonObject();
                    bodyJson.addProperty("title", this.getOaBody().getTitle());

                    if (CollectionUtils.isNotEmpty(this.oaBody.getForm())) {
                        JsonArray formJsonArray = new JsonArray();
                        for (OABuilder.OABodyForm form : this.getOaBody().getForm()) {
                            if (form == null) {
                                continue;
                            }

                            JsonObject formJson = new JsonObject();
                            formJson.addProperty("key", form.getKey());
                            formJson.addProperty("value", form.getValue());
                            formJsonArray.add(formJson);
                        }
                        bodyJson.add("form", formJsonArray);
                    }

                    if (this.oaBody.getRich() != null) {
                        JsonObject richJson = new JsonObject();
                        richJson.addProperty("num", this.getOaBody().getRich().getNum());
                        richJson.addProperty("unit", this.getOaBody().getRich().getUnit());
                        bodyJson.add("rich", richJson);
                    }

                    bodyJson.addProperty("content", this.getOaBody().getContent());
                    bodyJson.addProperty("image", this.getOaBody().getImage());
                    bodyJson.addProperty("file_count", this.getOaBody().getFileCount());
                    bodyJson.addProperty("author", this.getOaBody().getAuthor());
                    oa.add("body", bodyJson);
                }

                messageJson.add("oa", oa);
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

                if (StringUtils.isNotBlank(this.getSingleTitle())) {
                    card.addProperty("single_title", this.getSingleTitle());
                }
                if (StringUtils.isNotBlank(this.getSingleUrl())) {
                    card.addProperty("single_url", this.getSingleUrl());
                }

                if (StringUtils.isNotBlank(this.getBtnOrientation())) {
                    card.addProperty("btn_orientation", this.getBtnOrientation());
                }

                if (CollectionUtils.isNotEmpty(this.getBtnList())) {
                    JsonArray btnJsonArray = new JsonArray();
                    for (ActionCardBuilder.CardBtn btn : this.getBtnList()) {
                        if (btn == null) {
                            continue;
                        }

                        JsonObject btnJson = new JsonObject();
                        btnJson.addProperty("title", btn.getTitle());
                        btnJson.addProperty("action_url", btn.getActionUrl());

                        btnJsonArray.add(btnJson);
                    }
                    card.add("btn_json_list", btnJsonArray);
                }

                messageJson.add("action_card", card);
                break;
            }
            default: {
                // do nothing
            }
        }
    }

}
