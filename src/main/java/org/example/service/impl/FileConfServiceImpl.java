package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.FileConf;
import org.example.mapper.FileConfMapper;
import org.example.service.FileConfService;
import org.springframework.stereotype.Service;

/**
 * 文件配置表ServiceImpl
 *
 * @author lyc
 * @since 1.0
 * @date 2023/8/25 14:10
 */
@Service
public class FileConfServiceImpl extends ServiceImpl<FileConfMapper, FileConf>
    implements FileConfService {

}




