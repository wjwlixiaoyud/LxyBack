package org.example.lxyback.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.example.lxyback.common.Result;
import org.example.lxyback.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.example.lxyback.entity.Files;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalfilename=file.getOriginalFilename();
        String type = FileUtil.extName(originalfilename);
        long filesize=file.getSize();
        //定义一个文件唯一的标识码
        String uuid= IdUtil.fastSimpleUUID();
        String fileUUID=uuid+ StrUtil.DOT+type;
        File uploadFile=new File(fileUploadPath+fileUUID);
        //把获取到的文件存入磁盘路径
        File parentFile=uploadFile.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        String md5;
        String url;
        //上传文件到磁盘
        file.transferTo(uploadFile);
        //获取文件Md5
        md5=SecureUtil.md5(uploadFile);
        //从数据库查询是否存在相同数据
        Files dbFiles=getFileByMd5(md5);
        if(dbFiles !=null){
           url=dbFiles.getUrl();
           uploadFile.delete();
        }else {
            url = "http://localhost:9090/file/"+fileUUID;
        }

        //存储数据库
        Files saveFile=new Files();
        saveFile.setName(originalfilename);
        saveFile.setType(type);
        saveFile.setSize(filesize/1024);
        saveFile.setIsDelete(false);
        saveFile.setEnable(true);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);
        return url;
    }

    @GetMapping("/{fileUUID}")
    public void  download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException{
        //根据文件唯一标识码获取文件
        File uploadFile =new File(fileUploadPath+fileUUID);
        ServletOutputStream os=response.getOutputStream();
        response.addHeader("content-disposition","attachment;filename="+ URLEncoder.encode(fileUUID, StandardCharsets.UTF_8));
        response.setContentType("application/octet-stream");
        //读取上传字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    private Files getFileByMd5(String md5){
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList=fileMapper.selectList(queryWrapper);
        return filesList.size() ==0 ? null:filesList.get(0);
    }

    /**
     * 分页查询接口
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/pages")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        //查询未删除的记录
        queryWrapper.eq("is_delete",false);
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @PostMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files=fileMapper.selectById(id);
        files.setIsDelete(true);
        return Result.success(fileMapper.updateById(files));
    }

    @PostMapping("/del/batch")
    public Result deletebatch(@RequestBody List<Integer> ids)
    {
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<Files> files=fileMapper.selectList(queryWrapper);
        for(Files file:files){
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }
        return Result.success();

    }

    @PostMapping("/update")
    public Result update(@RequestBody Files files){
        return Result.success(fileMapper.updateById(files));
    }

}
