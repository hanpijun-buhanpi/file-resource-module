package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.config.FileResourceConfigurationProperties;
import org.example.constant.ExceptionTextConstant;
import org.example.entity.FileConf;
import org.example.entity.FileInfo;
import org.example.enumerate.DelFlag;
import org.example.enumerate.DelType;
import org.example.mapper.FileInfoMapper;
import org.example.service.FileConfService;
import org.example.service.FileInfoService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 文件信息表ServiceImpl
 *
 * @author lyc
 * @since 1.0
 * @date 2023/8/25 14:44
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo>
    implements FileInfoService {
    @Lazy
    @Autowired
    private FileConfService fileConfService;

    /**
     * 上传文件
     *
     * @param fileConf 文件配置
     * @param files    文件数组
     * @return 文件信息数组
     */
    @Override
    public List<FileInfo> upload(FileConf fileConf, List<MultipartFile> files) {
        List<FileInfo> fileInfos = new ArrayList<>();
        for (MultipartFile file : files) {
            // 构建参数
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String newName = uuid + getFileSuffix(file.getOriginalFilename());
            String path = joinPath(uuid.substring(0, 2), uuid.substring(2, 4), newName);
            String url = joinPath(FileResourceConfigurationProperties.RESOURCE_REALM,
                    FileResourceConfigurationProperties.DOWNLOAD,
                    fileConf.getResourceRealm(), newName);
            // 存储文件
            Path p = Paths.get(fileConf.getPath(), path);
            File parent = p.getParent().toFile();
            if (!parent.exists() || parent.isFile()) {
                parent.deleteOnExit();
                parent.mkdirs();
            }
            try (
                    InputStream in = file.getInputStream();
                    OutputStream out =Files.newOutputStream(p)
            ) {
                IOUtils.copy(in, out);
            } catch (IOException e) {
                // TODO 多文件上传时，存储失败的异常处理
            }
            // 存储数据
            LocalDateTime now = LocalDateTime.now();
            FileInfo fileInfo = FileInfo.builder()
                    .bizType(fileConf.getBizType())
                    .originalName(file.getOriginalFilename())
                    .newName(newName)
                    .fileType(file.getContentType() == null ? MediaType.MULTIPART_FORM_DATA_VALUE : file.getContentType())
                    .fileSize(file.getSize())
                    .filePath(path)
                    .fileUrl(url)
                    .createTime(now)
                    .updateTime(now)
                    .delFlag(DelFlag.normal)
                    .build();
            save(fileInfo);
            // 添加返回信息
            fileInfos.add(fileInfo);
        }
        return fileInfos;
    }

    /**
     * 获取文件后缀名（包含.）
     *
     * @param filename 文件名
     * @return 文件后缀名
     */
    private String getFileSuffix(String filename) {
        String s = ".";
        if (filename != null && filename.contains(s)) {
            return filename.substring(filename.lastIndexOf(s));
        }
        return "";
    }

    /**
     * 拼接文件路径
     * @param paths 文件路径数组
     * @return 拼接好的文件路径
     */
    private static String joinPath(String... paths) {
        List<String> list = new ArrayList<>();
        for (String path : paths) {
            if (path == null) {
                continue;
            }
            String[] split = path.split("[/\\\\]");
            for (String s : split) {
                if (s != null && !"".equals(s)) {
                    list.add(s);
                }
            }
        }
        return list.size() == 0 ? "" : "/" + String.join("/", list);
    }

    /**
     * 下载文件
     *
     * @param fileInfo 文件信息
     * @param response http返回
     */
    @Override
    public void download(FileInfo fileInfo, HttpServletResponse response) {
        FileConf fileConf = fileConfService.lambdaQuery()
                .eq(FileConf::getBizType, fileInfo.getBizType())
                .one();
        // 判空
        if (fileConf == null) {
            throw new RuntimeException(ExceptionTextConstant.FILE_CONF_LOSS);
        }
        Path path = Paths.get(fileConf.getPath(), fileInfo.getFilePath());
        if (!path.toFile().exists()) {
            throw new RuntimeException(ExceptionTextConstant.FILE_DOES_NOT_EXIST);
        }
        // 返回文件
        try (
                InputStream in = Files.newInputStream(path);
                OutputStream out = response.getOutputStream()
        ) {
            response.setHeader("Content-Type", fileInfo.getFileType());
            IOUtils.copy(in, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除文件
     *
     * @param fileInfo 文件信息
     */
    @Override
    public void delete(FileInfo fileInfo) {
        FileConf fileConf = fileConfService.lambdaQuery()
                .eq(FileConf::getBizType, fileInfo.getBizType())
                .one();
        // 判空
        if (fileConf == null) {
            throw new RuntimeException(ExceptionTextConstant.FILE_CONF_LOSS);
        }
        // 删除
        if (fileConf.getDelType() == DelType.physics) {
            File file = Paths.get(fileConf.getPath(), fileInfo.getFilePath()).toFile();
            if (file.exists()) {
                // file.deleteOnExit()会等到程序退出才进行删除
                file.delete();
            }
            fileInfo.setDelFlag(DelFlag.physics);
        } else {
            fileInfo.setDelFlag(DelFlag.logic);
        }
        fileInfo.setUpdateTime(LocalDateTime.now());
        updateById(fileInfo);
    }
}




