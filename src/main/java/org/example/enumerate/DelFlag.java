package org.example.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 逻辑删除
 *
 * @author lyc
 * @since 1.0
 * @date 2023/8/25 17:14
 */
public enum DelFlag {
    /** 正常 */
    normal(0, "正常"),
    /** 文件已被物理删除 */
    physics(1, "文件已被物理删除"),
    /** 文件已被逻辑删除 */
    logic(2, "文件已被逻辑删除"),
    ;

    /** 数据库存储数值 */
    @EnumValue
    @JsonValue
    public final int code;

    /** 描述 */
    public final String descp;

    DelFlag(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }
}
