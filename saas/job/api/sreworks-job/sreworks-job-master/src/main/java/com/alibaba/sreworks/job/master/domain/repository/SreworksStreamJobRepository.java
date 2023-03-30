package com.alibaba.sreworks.job.master.domain.repository;

import com.alibaba.sreworks.job.master.domain.DO.SreworksJob;
import com.alibaba.sreworks.job.master.domain.DO.SreworksStreamJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author jinghua.yjh
 */
public interface SreworksStreamJobRepository
    extends JpaRepository<SreworksStreamJob, Long>, JpaSpecificationExecutor<SreworksStreamJob> {

    SreworksStreamJob findFirstById(Long id);

    Page<SreworksStreamJob> findAll(Pageable pageable);

}
