package com.hw.service;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hw.dao.ImageDao;
import com.hw.exception.ErrorCode;
import com.hw.exception.HwException;
import com.hw.model.Image;
import com.hw.tools.FileUtils;

@Service
public class ImageSvc {
	
	public static final short ORIGINAL = 1,PROCESSED = 2;
	
	@Value("${file.upload.path}")
	public String fileUploadPath;

	@Autowired
	private ImageDao imageDao;
	
	public void saveOriginalImg(Image image) throws IOException {
		String filename = new Date().getTime() + ".png";
		File path = new File(fileUploadPath + "originalImage");
		if (!path.exists()) {
			path.mkdirs();
		}
		path = new File(path, filename);
    	FileUtils.saveImage(image.getPath(), path);
    	image.setPath(fileUploadPath + "originalImage/" + filename);
    	image.setType(ORIGINAL);
    	imageDao.save(image);
	}
	
	public void saveProcessedImg(Image image) throws IOException {
		String filename = new Date().getTime() + ".png";
		File path = new File(fileUploadPath + "processedImage");
		if (!path.exists()) {
			path.mkdirs();
		}
		path = new File(path, filename);
    	FileUtils.saveImage(image.getPath(), path);
    	image.setPath(fileUploadPath + "processedImage/" + filename);
    	image.setType(PROCESSED);
    	imageDao.save(image);
	}
	
	/**
	 * 查询视频截图原图
	 */
	public List<Image> getOriginalImage(Integer patientDataId){
		return imageDao.findByType(ORIGINAL);
	}
	
	/**
	 * 查询视频截图编辑后图片
	 */
	public List<Image> getProcessedImage(Integer patientDataId){
		return imageDao.findByType(PROCESSED);
	}
	
	/**
	 * 查询原图和编辑后的图片
	 */
	public List<Image> getOriginalAndProcessedImage(Integer patientDataId){
		return imageDao.findAll(new Specification<Image>() {

			private static final long serialVersionUID = -4710613362535875930L;

			@Override
			public Predicate toPredicate(Root<Image> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> list = new ArrayList<>();
				list.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("type").as(short.class),1), 
						criteriaBuilder.equal(root.get("type").as(short.class),2)));
				list.add(criteriaBuilder.equal(root.get("patientDataId").as(short.class),patientDataId));
				Predicate[] predicates = new Predicate[list.size()];
				list.toArray(predicates);
				return criteriaBuilder.and(predicates);
			}
		});
	}
	
	public void addReport(Long id) throws HwException{
		Image image = getImage(id);
        image.setReport(true);
        imageDao.save(image);
	}
	
	public void rmReport(Long id) throws HwException {
		Image image = getImage(id);
        image.setReport(false);
        imageDao.save(image);
	}
	
	/**
	 * 删除图片byID
	 * @throws HwException 
	 */
	public void deleteImage(Long id) throws HwException{
		List<Image> list = imageDao.findByImageId(id);
		if (list == null || list.size() == 0) {
			throw new HwException(ErrorCode.非法参数, "没有对应id的图片");
		}
		File image = new File(list.get(0).getPath());
		image.delete();
		imageDao.deleteById(id);
	}

	public Image getImage(Long id) throws HwException {
		// TODO Auto-generated method stub
		List<Image> list = imageDao.findByImageId(id);
		if (list == null || list.size() == 0) {
			throw new HwException(ErrorCode.非法参数, "没有对应id的图片");
		}
		return list.get(0);
	}
}
