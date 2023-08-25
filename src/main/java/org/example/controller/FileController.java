package org.example.controller;

import org.example.config.FileResourceConfigurationProperties;
import org.example.constant.ExceptionTextConstant;
import org.example.entity.FileConf;
import org.example.entity.FileInfo;
import org.example.enumerate.DelFlag;
import org.example.service.FileConfService;
import org.example.service.FileInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件Controller
 *
 * @author lyc
 * @since 1.0
 * @date 2023/8/25 14:14
 */
@RestController
@RequestMapping(FileResourceConfigurationProperties.RESOURCE_REALM)
@Api(tags = "文件Controller")
public class FileController {
    @Autowired
    private FileConfService fileConfService;
    @Autowired
    private FileInfoService fileInfoService;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public List<FileInfo> upload(
            @RequestParam("biz_type") @ApiParam(value = "业务类型", required = true) String bizType,
            @RequestParam @ApiParam(value = "文件数组", required = true) List<MultipartFile> files
    ) {
        // 判空
        if (files == null || files.size() == 0) {
            throw new RuntimeException(ExceptionTextConstant.NO_FILE_SELECTED);
        }
        FileConf fileConf = fileConfService.lambdaQuery().eq(FileConf::getBizType, bizType).one();
        if (fileConf == null) {
            throw new RuntimeException(ExceptionTextConstant.NOT_FOUND_FILE_CONF);
        }
        // 是否可用
        if (!fileConf.getEnabled()) {
            throw new RuntimeException(ExceptionTextConstant.DISABLE_BUSINESS);
        }
        // 大小限制
        long fileSizeLimit = DataSize.parse(fileConf.getFileSizeLimit()).toBytes();
        long multiFileSizeLimit = DataSize.parse(fileConf.getMultiFileSizeLimit()).toBytes();
        long filesLength = 0;
        for (MultipartFile file : files) {
            long fileSize = file.getSize();
            if (fileSize > fileSizeLimit) {
                throw new RuntimeException(ExceptionTextConstant.FILE_SIZE_CANNOT_GREATER_THEN + fileConf.getFileSizeLimit());
            }
            filesLength += fileSize;
        }
        if (filesLength > multiFileSizeLimit) {
            throw new RuntimeException(ExceptionTextConstant.MULTI_FILE_SIZE_CANNOT_GREATER_THEN + fileConf.getMultiFileSizeLimit());
        }
        // 类型限制
        String[] split = fileConf.getFileTypeLimit().split(",");
        for (MultipartFile file : files) {
            String contentType = file.getContentType();
            if (contentType == null) {
                throw new RuntimeException(ExceptionTextConstant.UNRECOGNIZED_FILE_TYPE);
            }
            boolean b = false;
            for (String s : split) {
                if (contentType.equals(s)) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                throw new RuntimeException(file.getOriginalFilename() + ExceptionTextConstant.UNSUPPORTED_FILE_TYPE);
            }
        }
        // 存储文件
        return fileInfoService.upload(fileConf, files);
    }

    @GetMapping(FileResourceConfigurationProperties.DOWNLOAD + "/**")
    @ApiOperation("下载文件")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        String servletPath = request.getServletPath();
        FileInfo fileInfo = fileInfoService.lambdaQuery()
                .eq(FileInfo::getFileUrl, servletPath)
                .eq(FileInfo::getDelFlag, DelFlag.normal)
                .one();
        // 判空
        if (fileInfo == null) {
            throw new RuntimeException(ExceptionTextConstant.FILE_DOES_NOT_EXIST);
        }
        // 返回文件
        fileInfoService.download(fileInfo, response);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除文件")
    public void delete(@RequestParam @ApiParam(value = "文件id", required = true) Long id) {
        FileInfo fileInfo = fileInfoService.lambdaQuery()
                .eq(FileInfo::getId, id)
                .eq(FileInfo::getDelFlag, DelFlag.normal)
                .one();
        if (fileInfo == null) {
            throw new RuntimeException(ExceptionTextConstant.FILE_DOES_NOT_EXIST);
        }
        fileInfoService.delete(fileInfo);
    }
}
