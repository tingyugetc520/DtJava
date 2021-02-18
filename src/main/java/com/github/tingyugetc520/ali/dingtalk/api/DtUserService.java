package com.github.tingyugetc520.ali.dingtalk.api;

import com.github.tingyugetc520.ali.dingtalk.bean.user.DtUser;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;

import java.util.List;

/**
 * 用户管理接口
 *
 * @author tingyugetc520
 */
public interface DtUserService {

  /**
   * 获取部门成员详情
   * 文档地址：https://ding-doc.dingtalk.com/document/app/obtain-department-members-details
   *
   * @param departId 必填。部门id
   * @param offset 必填
   * @param size 必填
   * @param order 选填
   * @return the list
   * @throws DtErrorException the error exception
   */
  List<DtUser> listByDepartment(Long departId, Integer offset, Integer size, String order) throws DtErrorException;

  /**
   * 获取部门成员
   * 文档地址：https://ding-doc.dingtalk.com/document/app/obtain-department-members
   *
   * @param departId 必填。部门id
   * @param offset 必填
   * @param size 必填
   * @param order 选填
   * @return the list
   * @throws DtErrorException the error exception
   */
  List<DtUser> listSimpleByDepartment(Long departId, Integer offset, Integer size, String order) throws DtErrorException;

  /**
   * 获取用户
   *
   * @param userId 用户id
   * @return the by id
   * @throws DtErrorException the error exception
   */
  DtUser getById(String userId) throws DtErrorException;

}
