package com.anchorcms.cms.service.main.impl;

import java.util.List;

import com.anchorcms.cms.dao.assist.CmsKeywordDao;
import com.anchorcms.cms.model.assist.CmsKeyword;
import com.anchorcms.cms.service.main.CmsKeywordMng;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.ParserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class CmsKeywordMngImpl implements CmsKeywordMng {

	@Transactional(readOnly = true)
	public List<CmsKeyword> getListBySiteId(Integer siteId,
											boolean onlyEnabled, boolean cacheable) {
		List<CmsKeyword> list = dao.getListGlobal(onlyEnabled, cacheable);
		// TODO 关键字分站点？
		// list.addAll(dao.getList(siteId, onlyEnabled, cacheable));
		return list;
	}

	@Transactional(readOnly = true)
	public String attachKeyword(Integer siteId, String txt) {
		if (StringUtils.isBlank(txt)) {
			return txt;
		}
		List<CmsKeyword> list = getListBySiteId(siteId, true, true);
		int len = list.size();
		if (len <= 0) {
			return txt;
		}
		String[] searchArr = new String[len];
		String[] replacementArr = new String[len];
		int i = 0;
		for (CmsKeyword k : list) {
			searchArr[i] = k.getKeywordName();
			replacementArr[i] = k.getUrl();
			i++;
		}
		try {
			Lexer lexer = new Lexer(txt);
			Node node;
			StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
			while ((node = lexer.nextNode()) != null) {
				if (node instanceof TextNode) {
					sb.append(StringUtils.replaceEach(node.toHtml(), searchArr,
							replacementArr));
				} else {
					sb.append(node.toHtml());
				}
			}
			return sb.toString();
		} catch (ParserException e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional(readOnly = true)
	public CmsKeyword findById(Integer id) {
		CmsKeyword entity = dao.findById(id);
		return entity;
	}

	public CmsKeyword save(CmsKeyword bean) {
		bean.init();
		dao.save(bean);
		return bean;
	}

	public void updateKeywords(Integer[] ids, String[] names, String[] urls,
			Boolean[] disableds) {
		CmsKeyword keyword;
		for (int i = 0, len = ids.length; i < len; i++) {
			keyword = findById(ids[i]);
			keyword.setKeywordName(names[i]);
			keyword.setUrl(urls[i]);
			keyword.setIsDisabled(disableds[i]);
		}
	}

	public CmsKeyword deleteById(Integer id) {
		CmsKeyword bean = dao.deleteById(id);
		return bean;
	}

	public CmsKeyword[] deleteByIds(Integer[] ids) {
		CmsKeyword[] beans = new CmsKeyword[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	@Resource
	private CmsKeywordDao dao;
}