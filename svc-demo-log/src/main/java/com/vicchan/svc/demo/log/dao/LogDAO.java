package com.vicchan.svc.demo.log.dao;

import com.github.pagehelper.PageHelper;
import com.vicchan.svc.demo.log.mapper.LogDOMapper;
import com.vicchan.svc.demo.log.pojo.LogDO;
import com.vicchan.svc.demo.log.pojo.LogDOExample;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class LogDAO {
    @Resource
    private LogDOMapper logMapper;

    public void deleteById(Integer id) {
        logMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LogDO log) {
        log.setAddTime(new Date());
        log.setUpdateTime(new Date());
        logMapper.insertSelective(log);
    }

    public List<LogDO> querySelective(String name, Integer page, Integer size, String sort, String order) {
        LogDOExample example = new LogDOExample();
        LogDOExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andAdminLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return logMapper.selectByExample(example);
    }
}
