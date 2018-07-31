package com.hw.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hw.bean.PageBean;
import com.hw.dao.PatientDao;
import com.hw.exception.ErrorCode;
import com.hw.exception.HwException;
import com.hw.model.Patient;

@Service
public class PatientSvc {

	@Autowired
	private PatientDao patientDao;
	
	private Specification<Patient> specification = null;
	private int lastPageNum = 1;
	private static final int PAGESIZE = 2;
	
	Pageable currPage = PageRequest.of(0, PAGESIZE);
	
	public PageBean<Patient> getCurrPage() {
		Page<Patient> page = patientDao.findAll(specification, currPage);
		return getPageBean(page);
	}
	
	public PageBean<Patient> getFristPage() {
		currPage = currPage.first();
		Page<Patient> page = patientDao.findAll(specification, currPage);
		return getPageBean(page);
	}
	
	public PageBean<Patient> getLastPage() {
		currPage = PageRequest.of(lastPageNum, PAGESIZE);
		Page<Patient> page = patientDao.findAll(specification, currPage);
		return getPageBean(page);
	}
	
	public PageBean<Patient> getNextPage() throws HwException {
		if (currPage.getPageNumber() >= lastPageNum) {
			throw new HwException(ErrorCode.流程出错, "已经到最后一页了");
		}
		currPage = currPage.next();
		System.out.println(currPage.getPageNumber() + "," + lastPageNum);
		Page<Patient> page = patientDao.findAll(specification, currPage);
		return getPageBean(page);
	}
	
	public PageBean<Patient> getPreviousPage() {
		currPage = currPage.previousOrFirst();
		Page<Patient> page = patientDao.findAll(specification, currPage);
		return getPageBean(page);
	}
	
	public List<Patient> getAllPatient(){
		return patientDao.findAll();
	}
	
	public PageBean<Patient> getPageBean(Page<Patient> page){
		PageBean<Patient> pageBean = new PageBean<>();
		pageBean.setPageIndex(page.getNumber() + 1);
		pageBean.setPageSize(page.getNumberOfElements());
		pageBean.setPageCount(page.getTotalPages());
		pageBean.setTotalCount(page.getTotalElements());
		pageBean.setList(page.getContent());
		lastPageNum = pageBean.getPageCount() - 1;
		return pageBean;
	}
	
	public List<Patient> getPatientById(Integer id) {
        return patientDao.findByPatientId(id);
    }
	
	/**
	 * 条件查询
	 * @param name
	 * @param sex
	 * @param id
	 * @return
	 */
	public PageBean<Patient> getPatientByCondition(String name, String sex, Integer id) {
		final String finalSex = sex.equals("1") ? "男" : (sex.equals("2") ? "女" : null); 
		specification = new Specification<Patient>() {
			
			private static final long serialVersionUID = -4059012909212817626L;

			@Override
			public Predicate toPredicate(Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if (name != null) 
					list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%"));
				if (finalSex != null)
					list.add(criteriaBuilder.equal(root.get("sex").as(String.class), finalSex));
				if (id != null) 
					list.add(criteriaBuilder.like(root.get("patientId").as(String.class), "%" + id + "%"));
				
				Predicate[] predicates = new Predicate[list.size()];
				list.toArray(predicates);
				return criteriaBuilder.and(predicates);
			}
		};
		return getFristPage();
    }
}
