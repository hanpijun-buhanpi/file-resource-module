package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.example.enumerate.DelFlag;
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
 * 文件信息表
 *
 * @author lyc
 * @date 2023/8/25 14:40
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "文件信息")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FileInfo implements Serializable {
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
     * 文件原名称
     */
    @NotBlank(message = "[文件原名称]不能为空")
    @Length(max = 255, message = "[文件原名称]编码长度不能超过255")
    @ApiModelProperty(value = "文件原名称")
    private String originalName;
    /**
     * 文件新名称
     */
    @NotBlank(message = "[文件新名称]不能为空")
    @Length(max = 64, message = "[文件新名称]编码长度不能超过64")
    @ApiModelProperty(value = "文件新名称")
    private String newName;
    /**
     * 文件类型（mime-type标准）
     */
    @NotBlank(message = "[文件类型]不能为空")
    @Length(max = 32, message = "[文件类型]编码长度不能超过32")
    @ApiModelProperty(value = "文件类型（mime-type标准）")
    private String fileType;
    /**
     * 文件存储大小，单位B
     */
    @NotNull(message = "[文件存储大小]不能为空")
    @ApiModelProperty(value = "文件存储大小，单位B")
    private Long fileSize;
    /**
     * 文件存储相对路径
     */
    @NotBlank(message = "[文件存储相对路径]不能为空")
    @Length(max = 255, message = "[文件存储相对路径]编码长度不能超过255")
    @ApiModelProperty(value = "文件存储相对路径")
    private String filePath;
    /**
     * 文件访问相对url
     */
    @NotBlank(message = "[文件访问相对url]不能为空")
    @Length(max = 255, message = "[文件访问相对url]编码长度不能超过255")
    @ApiModelProperty(value = "文件访问相对url")
    private String fileUrl;
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
    /**
     * 逻辑删除（默认0正常，1文件已被物理删除，2文件已被逻辑删除）
     */
    @NotNull(message = "[逻辑删除]不能为空")
    @ApiModelProperty(value = "逻辑删除（默认0正常，1文件已被物理删除，2文件已被逻辑删除）")
    private DelFlag delFlag;
}
