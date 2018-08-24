package com.anchorcms.cms.service.assist;

import com.anchorcms.cms.model.assist.CmsConsult;
import com.anchorcms.cms.model.assist.CmsConsultExt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;

import java.util.List;


public interface CmsConsultMng {
	public Pagination getPage(Integer siteId, Integer contentId,
							  Integer greaterThen, Boolean checked, Boolean recommend,
							  boolean desc, int pageNo, int pageSize);

	public Pagination getPageForTag(Integer siteId, Integer contentId,
									Integer greaterThen, Boolean checked, Boolean recommend,
									boolean desc, int pageNo, int pageSize);
	
	/**
	 * 
	 * @param siteId
	 * @param contentId
	 * @param toUserId 写评论的用户
	 * @param fromUserId 投稿的信息接收到的相关评论
	 * @param greaterThen
	 * @param checked
	 * @param recommend
	 * @param desc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getPageForMember(Integer siteId, Integer contentId, Integer toUserId, Integer fromUserId,
									   Integer greaterThen, Boolean checked, Boolean recommend,
									   boolean desc, int pageNo, int pageSize);
	/**
	 * 
	 * @param siteId
	 * @param userId 发表信息用户id
	 * @param consultUserId 评论用户id
	 * @param ip  评论来访ip
	 * @return
	 */
	public List<CmsConsult> getListForDel(Integer siteId, Integer userId, Integer consultUserId, String ip);

	public List<CmsConsult> getListForTag(Integer siteId, Integer contentId,
										  Integer parentId, Integer greaterThen, Boolean checked, Boolean recommend,
										  boolean desc, int count);

	public CmsConsult findById(Integer id);

	public CmsConsult consult(Integer score, String text, String ip, Integer contentId,
							  Integer siteId, Integer userId, boolean checked, boolean recommend, Integer parentId);

	public CmsConsult update(CmsConsult bean, CmsConsultExt ext);

	public int deleteByContentId(Integer contentId);

	public CmsConsult deleteById(Integer id);

	public CmsConsult[] deleteByIds(Integer[] ids);

	public void ups(Integer id);

	public void downs(Integer id);

	public CmsConsult[] checkByIds(Integer[] ids, CmsUser user, boolean checked);
}