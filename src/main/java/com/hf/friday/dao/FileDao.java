package com.hf.friday.dao;

import com.hf.friday.model.SysFile;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Mapper
@Component
public interface FileDao {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_file(file_name, uuid_name, user_id,url,thumbnail_url,ratio,upload_user_id,upload_user_name, description, create_time, update_time,size) values(#{fileName},#{uuidName},#{userId},#{url},#{thumbnailUrl},#{ratio},#{uploadUserId},#{uploadUserName},#{description},now(), now(),#{size})")
    void save(SysFile sysFile);

    @Select("select * from sys_file t where user_id = #{userId} order by t.id limit #{startPosition} , #{limit}")
    List<SysFile> getAllFilesByPageAndUserId(Integer startPosition, Integer limit,Integer userId);

    @Select("select count(*) from sys_file t where user_id = #{userId}")
    Integer countFilesByUserId(Integer userId);

    @Select("select * from sys_file where id = #{id}")
    SysFile findFileById(Integer id);

    @Select("select * from sys_file where file_name = #{fileName}")
    SysFile findFileByName(String fileName);

    @Update("update sys_file set download_num = download_num + 1 where id = #{id}")
    void updateFileDownloadNum(Integer id);

    @Delete("delete from sys_file where id = #{id}")
    void deleteById(Integer id);

    @Select("select count(*) from sys_file t where t.user_id = #{userId} and t.file_name like '%${fileName}%'")
    int getFileCountByFuzzyFilename(String fileName,Integer userId);

    @Select("select * from sys_file t where t.user_id = #{userId} and t.file_name like '%${fileName}%' limit #{startPosition} , #{limit}")
    List<SysFile> findFileByFuzzyFileName(String fileName, Integer startPosition, Integer limit, Integer userId);
}
