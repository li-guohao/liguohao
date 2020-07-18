package cn.liguohao.api.response;

/**
 * @ClassName: Meta
 * @Description: 响应描述
 * @author: li-guohao
 * @date: 2020-7-18 1:06:52
 */
public class Meta {

    private Integer status; //响应状态，详情请参考接口文档

    private String msg;     //响应信息

    private String detail; //响应信息详情

    public Meta(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Meta(Integer status, String msg, String detail) {
        this.status = status;
        this.msg = msg;
        this.detail = detail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
