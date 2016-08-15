package com.fctx.ex;

/**
 * Created by liuJian on 2016/8/15.
 */
public class SunException extends RuntimeException {
    private static final long serialVersionUID = 5481772751624388319L;
    private int code;
    private String module;

    public SunException(int code, String module) {
        super();
        this.code = code;
        this.module = module;
    }

    public SunException(int code, String module, String msg) {
        super(msg);
        this.code = code;
        this.module = module;
    }

    public SunException(int code, String module, Throwable e) {
        super(e);
        this.code = code;
        this.module = module;
    }

    public SunException(int code, String module, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
        this.module = module;
    }

    /**
     * @return the code
     */
    public int code() {
        return code;
    }

    /**
     * @return the module
     */
    public String module() {
        return module;
    }
}
