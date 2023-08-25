package org.example.constant;

/**
 * 异常文本常量
 *
 * @author lyc
 * @since 1.0
 * @date 2023/8/25 14:59
 */
public class ExceptionTextConstant {
    // 上传时异常文本
    /** 未选择文件 */
    public static final String NO_FILE_SELECTED = "未选择文件";
    /** 找不到文件配置 */
    public static final String NOT_FOUND_FILE_CONF = "找不到文件配置";
    /** 业务已关闭 */
    public static final String DISABLE_BUSINESS = "业务已关闭";
    /** 单个文件不得大于 */
    public static final String FILE_SIZE_CANNOT_GREATER_THEN = "单个文件不得大于";
    /** 多个文件不得大于 */
    public static final String MULTI_FILE_SIZE_CANNOT_GREATER_THEN = "多个文件不得大于";
    /** 无法识别的文件类型 */
    public static final String UNRECOGNIZED_FILE_TYPE = "无法识别的文件类型";
    /** 不支持的文件类型 */
    public static final String UNSUPPORTED_FILE_TYPE = "不支持的文件类型";

    // 下载时异常文本
    /** 文件不存在 */
    public static final String FILE_DOES_NOT_EXIST = "文件不存在";
    /** 文件配置丢失 */
    public static final String FILE_CONF_LOSS = "文件配置丢失";
}
