package org.example.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 删除方式
 *
 * @author lyc
 * @since 1.0
 * @date 2023/8/25 17:07
 */
public enum DelType {
    /** 物理删除 */
    physics(1, "物理删除"),
    /** 逻辑删除 */
    logic(2, "逻辑删除"),
    ;

    /** 数据库存储数值 */
    @EnumValue
    @JsonValue
    public final int code;

    /** 描述 */
    public final String descp;

    DelType(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }
}
