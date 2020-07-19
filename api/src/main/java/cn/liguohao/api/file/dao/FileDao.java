package cn.liguohao.api.file.dao;

import cn.liguohao.api.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName: FileDao
 * @Description: 文件持久层接口
 * @author: li-guohao
 * @date: 2020-7-18 1:05:42
 */
public interface FileDao extends JpaRepository<File,Long>,JpaSpecificationExecutor<File> {

    @Query(value = "select * from file  limit ?1,?2",nativeQuery = true)
    List<File> fingAllByPageingAndType( Integer currentPage, Integer pageSize);

    File findFileByFid(Long fid);
}
