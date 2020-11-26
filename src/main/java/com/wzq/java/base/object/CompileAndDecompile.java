package com.wzq.java.base.object;

public class CompileAndDecompile {

    /**
     * 通过idea工具 jclasslib 反编译查看
     */
    public Object pubObj;
    protected Object proObj;
    private Object priObj;
    public static Object pubStaticObj;

    static {
        Object pubStaticBlockObj;
    }

    public static final Object pubStaticFinalObj = new Object();

    public volatile Object volatileObj;

}
