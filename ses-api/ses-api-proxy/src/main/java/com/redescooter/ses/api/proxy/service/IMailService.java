package com.redescooter.ses.api.proxy.service;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 5/9/2019 5:07 下午
 * @ClassName: IMailService
 * @Function: 邮件发送基础服务
 */
public interface IMailService {
    /**
     * 发送文本邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to, String subject, String content);

    public void sendSimpleMail(String to, String subject, String content, String... cc);

    /**
     * 发送HTML邮件
     *
     * @param to
     * @param subject
     * @param parameterJson
     * @param templateNo
     */
    public void sendHtmlMail(String to, String subject, String parameterJson, int templateNo);

    public void sendHtmlMail(String to, String subject, String content);

    public void sendHtmlMail(String to, String subject, String parameterJson, int templateNo, String... cc);

    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath);

    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc);

    /**
     * 发送正文中有静态资源的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId);

    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc);


}
