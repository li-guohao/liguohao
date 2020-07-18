package cn.liguohao.api.utils;

import org.apache.commons.lang3.StringUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/** 
 * @ClassName: MailUtils
 * @Description: 邮件工具类
 * @author: li-guohao
 * @date: 2020-7-18 1:09:35
 */
public class MailUtils {

    /**
     * 调用阿里云邮件推送API发送邮件
     * @param ACCESS_KEY 权限验证参数
     * @param ACCESS_SECRET 权限验证参数
     * @param SEND_MAIL_ADDRESS 发信地址
     * @param SEND_NAME 发信昵称
     * @param TAG 控制台标签
     * @param TARGET_MAIL_ADDRESS 目标地址
     * @param MailTheme 邮件主题
     * @param MailContent 邮件内容
     * @return
     */
    public static boolean sendMail(String ACCESS_KEY, String ACCESS_SECRET,
                                   String SEND_MAIL_ADDRESS, String SEND_NAME,
                                   String TAG, String TARGET_MAIL_ADDRESS,
                                   String MailTheme, String MailContent){
        // 非空处理
        if(StringUtils.isBlank(TAG)) {TAG = "code";}

        // 如果是除杭州region外的其它region（如新加坡、澳洲Region），需要将下面的"cn-hangzhou"替换为"ap-southeast-1"、或"ap-southeast-2"。
        IClientProfile profile;
        profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY, ACCESS_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            //request.setVersion("2017-06-22");// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
            request.setAccountName(SEND_MAIL_ADDRESS);
            request.setFromAlias(SEND_NAME);
            request.setAddressType(1);
            request.setTagName(TAG);//控制台创建的标签
            request.setReplyToAddress(true);
            request.setToAddress(TARGET_MAIL_ADDRESS);//目标地址
            //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
            //request.setToAddress("邮箱1,邮箱2");
            request.setSubject(MailTheme);//邮件主题
            request.setHtmlBody(MailContent);//邮件正文
            //开启需要备案，0关闭，1开启
            //request.setClickTrace("0");
            //如果调用成功，正常返回httpResponse；如果调用失败则抛出异常，需要在异常中捕获错误异常码；错误异常码请参考对应的API文档;
            //SingleSendMailResponse httpResponse = 
            		client.getAcsResponse(request);
            return true;
        } catch (ServerException e) {
            //捕获错误异常码
            System.out.println("ErrCode : " + e.getErrCode());
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            //捕获错误异常码
            System.out.println("ErrCode : " + e.getErrCode());
            e.printStackTrace();
            return false;
        }
    }
}
