package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.example.enumerate.DelType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件配置表
 *
 * @author lyc
 * @since 1.0
 * @date 2023/08/25 13:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "文件配置")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FileConf implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @NotNull(message = "[主键]不能为空")
    @ApiModelProperty(value = "主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 业务类型
     */
    @NotBlank(message = "[业务类型]不能为空")
    @Length(max = 32, message = "[业务类型]编码长度不能超过32")
    @ApiModelProperty(value = "业务类型")
    private String bizType;
    /**
     * 文件类型限制(mime-type标准)，以 , 分隔
     */
    @Length(max = 255, message = "[文件类型限制]编码长度不能超过255")
    @ApiModelProperty(value = "文件类型限制(mime-type标准)，以 , 分隔")
    private String fileTypeLimit;
    /**
     * 单个文件大小限制（允许B、KB、MB）
     */
    @Length(max = 8, message = "[单个文件大小限制]编码长度不能超过8")
    @ApiModelProperty(value = "单个文件大小限制（允许B、KB、MB）")
    private String fileSizeLimit;
    /**
     * 多个文件总大小限制（允许B、KB、MB）
     */
    @Length(max = 8, message = "[多个文件总大小限制]编码长度不能超过8")
    @ApiModelProperty(value = "多个文件总大小限制（允许B、KB、MB）")
    private String multiFileSizeLimit;
    /**
     * 文件存储根路径
     */
    @NotBlank(message = "[文件存储根路径]不能为空")
    @Length(max = 64, message = "[文件存储根路径]编码长度不能超过64")
    @ApiModelProperty(value = "文件存储根路径")
    private String path;
    /**
     * 文件访问相对根url
     */
    @NotBlank(message = "[文件访问相对根url]不能为空")
    @Length(max = 128, message = "[文件访问相对根url]编码长度不能超过128")
    @ApiModelProperty(value = "文件访问相对根url")
    private String resourceRealm;
    /**
     * 删除方式（默认1物理删除，2逻辑删除）
     */
    @NotNull(message = "[删除方式]不能为空")
    @ApiModelProperty(value = "删除方式（默认1物理删除，2逻辑删除）")
    private DelType delType;
    /**
     * 描述
     */
    @Length(max = 128, message = "[描述]编码长度不能超过128")
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 是否可用（默认1可用，0禁用）
     */
    @NotNull(message = "[是否可用]不能为空")
    @ApiModelProperty(value = "是否可用（默认1可用，0禁用）")
    private Boolean enabled;
    public boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    /**
     * 创建时间
     */
    @NotNull(message = "[创建时间]不能为空")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @NotNull(message = "[更新时间]不能为空")
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;
}
