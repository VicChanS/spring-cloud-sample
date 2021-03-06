package com.vicchan.scsample.svc.demo.admin.mapper;

import com.vicchan.scsample.svc.demo.admin.pojo.AdminDO;
import com.vicchan.scsample.svc.demo.admin.pojo.AdminDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminDOMapper {
    long countByExample(AdminDOExample example);

    int deleteByExample(AdminDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdminDO record);

    int insertSelective(AdminDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table scs_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    AdminDO selectOneByExample(AdminDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table scs_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    AdminDO selectOneByExampleSelective(@Param("example") AdminDOExample example, @Param("selective") AdminDO.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table scs_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<AdminDO> selectByExampleSelective(@Param("example") AdminDOExample example, @Param("selective") AdminDO.Column ... selective);

    List<AdminDO> selectByExample(AdminDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table scs_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    AdminDO selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") AdminDO.Column ... selective);

    AdminDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table scs_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    AdminDO selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") AdminDO record, @Param("example") AdminDOExample example);

    int updateByExample(@Param("record") AdminDO record, @Param("example") AdminDOExample example);

    int updateByPrimaryKeySelective(AdminDO record);

    int updateByPrimaryKey(AdminDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table scs_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") AdminDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table scs_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}