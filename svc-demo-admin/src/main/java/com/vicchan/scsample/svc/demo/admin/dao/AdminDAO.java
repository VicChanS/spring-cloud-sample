package com.vicchan.scsample.svc.demo.admin.dao;

import com.github.pagehelper.PageHelper;
import com.vicchan.scsample.svc.demo.admin.pojo.AdminDOExample;
import com.vicchan.scsample.svc.demo.admin.mapper.AdminDOMapper;
import com.vicchan.scsample.svc.demo.admin.pojo.AdminDO;
import com.vicchan.scsample.svc.demo.admin.pojo.AdminDO.Column;
import com.vicchan.scsample.svc.demo.admin.pojo.AdminDOExample;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class AdminDAO {

  private final Column[] result = new Column[]{Column.id, Column.username, Column.avatar, Column.roleIds};

  @Resource
  private AdminDOMapper adminMapper;

  public List<AdminDO> findAdmin(String username) {
    AdminDOExample example = new AdminDOExample();
    example.or().andUsernameEqualTo( username ).andDeletedEqualTo( false );
    return adminMapper.selectByExample( example );
  }

  public AdminDO findAdmin(Integer id) {
    return adminMapper.selectByPrimaryKey( id );
  }

  public List<AdminDO> querySelective(String username, Integer page, Integer limit, String sort, String order) {
    AdminDOExample example = new AdminDOExample();
    AdminDOExample.Criteria criteria = example.createCriteria();

    if (!StringUtils.isEmpty( username )) {
      criteria.andUsernameLike( "%" + username + "%" );
    }
    criteria.andDeletedEqualTo( false );

    if (!StringUtils.isEmpty( sort ) && !StringUtils.isEmpty( order )) {
      example.setOrderByClause( sort + " " + order );
    }

    PageHelper.startPage( page, limit );
    return adminMapper.selectByExampleSelective( example, result );
  }

  public int updateById(AdminDO admin) {
    admin.setUpdateTime( new Date() );
    return adminMapper.updateByPrimaryKeySelective( admin );
  }

  public void deleteById(Integer id) {
    adminMapper.logicalDeleteByPrimaryKey( id );
  }

  public void add(AdminDO admin) {
    admin.setAddTime( new Date() );
    admin.setUpdateTime( new Date() );
    adminMapper.insertSelective( admin );
  }

  public AdminDO findById(Integer id) {
    return adminMapper.selectByPrimaryKeySelective( id, result );
  }

  public List<AdminDO> all() {
    AdminDOExample example = new AdminDOExample();
    example.or().andDeletedEqualTo( false );
    return adminMapper.selectByExample( example );
  }
}
