package com.github.tingyugetc520.ali.dingtalk.api;

import com.github.tingyugetc520.ali.dingtalk.bean.user.DtUnionId2UserId;
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
   * 获取部门用户详情
   * https://ding-doc.dingtalk.com/document/app/obtain-department-members-details
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
   * 获取部门用户列表
   * https://ding-doc.dingtalk.com/document/app/obtain-department-members
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
   * @return user
   * @throws DtErrorException the error exception
   */
  DtUser getById(String userId) throws DtErrorException;

  /**
   * 根据手机号获取userid
   * https://developers.dingtalk.com/document/app/retrieve-userid-from-mobile-phone-number
   *
   * @param mobile mobile
   * @return userId
   * @throws DtErrorException error
   */
  String getUserIdByMobile(String mobile) throws DtErrorException;

  /**
   * 获取部门用户userid列表
   * https://developers.dingtalk.com/document/app/obtain-the-list-of-employee-ids-by-department-id
   *
   * @param departId departId
   * @return userIds
   * @throws DtErrorException error
   */
  List<String> userIdsByDepartment(Long departId) throws DtErrorException;

  /**
   * openid转userid
   * https://developers.dingtalk.com/document/app/you-can-call-this-operation-to-retrieve-the-userids-of
   *
   * @param unionId unionId
   * @return userId
   * @throws DtErrorException error
   */
  DtUnionId2UserId unionId2UserId(String unionId) throws DtErrorException;

}
