package com.github.tingyugetc520.ali.dingtalk.api;

import com.github.tingyugetc520.ali.dingtalk.bean.department.DtDepart;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;

import java.util.List;

/**
 * 部门管理接口
 */
public interface DtDepartmentService {

    /**
     * 获取部门列表
     * @see #list(Long, Boolean) 默认递归获取部门列表
     */
    List<DtDepart> list(Long id) throws DtErrorException;

    /**
     * 部门管理接口 - 获取部门列表.
     * 详情请见: https://ding-doc.dingtalk.com/document/app/obtain-the-department-list
     *
     * @param id 部门id。非必需，可为null
     * @param fetchChild 是否递归部门的全部子部门。非必填，可为null
     * @return 获取的部门列表
     * @throws DtErrorException 异常
     */
    List<DtDepart> list(Long id, Boolean fetchChild) throws DtErrorException;

}
