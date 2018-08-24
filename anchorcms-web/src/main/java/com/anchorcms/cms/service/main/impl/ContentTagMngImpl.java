package com.anchorcms.cms.service.main.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.anchorcms.cms.dao.main.ContentTagDao;
import com.anchorcms.cms.service.main.ContentTagMng;
import com.anchorcms.cms.model.main.ContentTag;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class ContentTagMngImpl implements ContentTagMng {
	private static final Logger log = LoggerFactory
			.getLogger(ContentTagMngImpl.class);

	@Transactional(readOnly = true)
	public List<ContentTag> getListForTag(Integer count) {
		return dao.getList(count, true);
	}

	@Transactional(readOnly = true)
	public Pagination getPageForTag(int pageNo, int pageSize) {
		Pagination page = dao.getPage(null, pageNo, pageSize, true);
		return page;
	}

	@Transactional(readOnly = true)
	public Pagination getPage(String name, int pageNo, int pageSize) {
		Pagination page = dao.getPage(name, pageNo, pageSize, false);
		return page;
	}

	@Transactional(readOnly = true)
	public ContentTag findById(Integer id) {
		ContentTag entity = dao.findById(id);
		return entity;
	}

	@Transactional(readOnly = true)
	public ContentTag findByName(String name) {
		return dao.findByName(name, false);
	}

	@Transactional(readOnly = true)
	public ContentTag findByNameForTag(String name) {
		return dao.findByName(name, true);
	}

	/**
	 * @see ContentTagMng#saveTags(String[])
	 */
	public List<ContentTag> saveTags(String[] tagArr) {
		if (tagArr == null || tagArr.length <= 0) {
			return null;
		}
		List<ContentTag> list = new ArrayList<ContentTag>();
		// 用于检查重复
		Set<String> tagSet = new HashSet<String>();
		ContentTag tag;
		for (String name : tagArr) {
			// 检测重复
			for (String t : tagSet) {
				if (t.equalsIgnoreCase(name)) {
					continue;
				}
			}
			tagSet.add(name);
			tag = saveTag(name);
			list.add(tag);
		}
		return list;
	}

	/**
	 * @see ContentTagMng#saveTag(String)
	 */
	public ContentTag saveTag(String name) {
		ContentTag tag = findByName(name);
		if (tag != null) {
			tag.setRefCounter(tag.getRefCounter() + 1);
		} else {
			tag = new ContentTag();
			tag.setTagName(name);
			tag = save(tag);
		}
		return tag;
	}

	public List<ContentTag> updateTags(List<ContentTag> tags, String[] tagArr) {
		if (tagArr == null) {
			tagArr = new String[0];
		}
		List<ContentTag> list = new ArrayList<ContentTag>();
		ContentTag bean;
		for (String t : tagArr) {
			bean = null;
			for (ContentTag tag : tags) {
				if (t.equalsIgnoreCase(tag.getTagName())) {
					bean = tag;
					break;
				}
			}
			if (bean == null) {
				bean = saveTag(t);
			}
			list.add(bean);
		}
		Set<ContentTag> toBeRemove = new HashSet<ContentTag>();
		boolean contains;
		for (ContentTag tag : tags) {
			contains = false;
			for (String t : tagArr) {
				if (t.equalsIgnoreCase(tag.getTagName())) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				toBeRemove.add(tag);
			}
		}
		tags.clear();
		tags.addAll(list);
		removeTags(toBeRemove);
		return tags;
		// // 先save
		// List<ContentTag> newTags = saveTags(tagArr);
		// // 再remove
		// Set<ContentTag> toRemove = new HashSet<ContentTag>();
		// for (ContentTag tag : tags) {
		// tag.setCount(tag.getCount() - 1);
		// if (tag.getCount() <= 0) {
		// toRemove.add(tag);
		// }
		// }
		// tags.clear();
		// if (newTags != null) {
		// tags.addAll(newTags);
		// }
		// for (ContentTag tag : toRemove) {
		// deleteById(tag.getId());
		// }
		// return tags;
	}

	public void removeTags(Collection<ContentTag> tags) {
		Set<ContentTag> toRemove = new HashSet<ContentTag>();
		for (ContentTag tag : tags) {
			if(tag!=null){
				tag.setRefCounter(tag.getRefCounter() - 1);
				if (tag.getRefCounter() <= 0) {
					toRemove.add(tag);
				}
			}
		}
		for (ContentTag tag : toRemove) {
			//由于事务真正删除关联的sql语句还没有执行，这个时候jc_contenttag里至少还有一条数据。
			if (dao.countContentRef(tag.getTagId()) <= 1) {
				dao.deleteById(tag.getTagId());
			} else {
				// 还有引用，不应该出现的情况，此时无法删除。
				log.warn("ContentTag ref to Content > 1,"
						+ " while ContentTag.ref_counter <= 0");
			}
		}
	}

	public ContentTag save(ContentTag bean) {
		bean.init();
		dao.save(bean);
		return bean;
	}

	public ContentTag update(ContentTag bean) {
		Updater<ContentTag> updater = new Updater<ContentTag>(bean);
		ContentTag entity = dao.updateByUpdater(updater);
		return entity;
	}

	public ContentTag deleteById(Integer id) {
		dao.deleteContentRef(id);
		ContentTag bean = dao.deleteById(id);
		return bean;
	}

	public ContentTag[] deleteByIds(Integer[] ids) {
		ContentTag[] beans = new ContentTag[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Resource
	private ContentTagDao dao;
}