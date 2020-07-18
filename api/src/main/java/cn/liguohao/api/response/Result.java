package cn.liguohao.api.response;

/**
 * @ClassName: Result
 * @Description: 响应结果
 * @author: li-guohao
 * @date: 2020-7-18 1:07:07
 */
public class Result {

    private Object data; //数据

    private Meta meta;  //信息和状态

    public Result() {
    }

    public Result(Meta meta) {
        this.meta = meta;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
