package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.FileConf;
import org.example.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件信息表Service
 *
 * @author lyc
 * @since 1.0
 * @date 2023/8/25 14:44
 */
public interface FileInfoService extends IService<FileInfo> {
    /**
     * 上传文件
     *
     * @param fileConf 文件配置
     * @param files 文件数组
     * @return 文件信息数组
     */
    List<FileInfo> upload(FileConf fileConf, List<MultipartFile> files);

    /**
     * 下载文件
     *
     * @param fileInfo 文件信息
     * @param response http返回
     */
    void download(FileInfo fileInfo, HttpServletResponse response);

    /**
     * 删除文件
     *
     * @param fileInfo 文件信息
     */
    void delete(FileInfo fileInfo);
}
