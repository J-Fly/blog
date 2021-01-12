package com.wjh.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_blog")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Blog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	private String firstPicture;//首图
	private String flag;//是否原创
	private Integer views;//浏览次数
	private boolean appreciation;//赞赏是否开启
	private boolean shareStatement;//转载声明是否开启
	private boolean commentabled;//评论是否开启
	private boolean published;//是否发布
	private boolean recommend;//是否推荐
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;//创建时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;//修改时间
	@Transient//代表不传入数据库
	private String tagIds;

	//博客描述
	private String description;


	//关联关系多的一端作为关系维护端(根据type)
	@ManyToOne
	private Type type;
	//由Blog来维护多对多的关系(根据tags)
	@ManyToMany(cascade = {CascadeType.PERSIST})//设置级联新增
	private List<Tag> tags = new ArrayList<>();
	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "blog")
	private List<Comment> comments = new ArrayList<>();

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Blog() {

	}

	//处理tagIds
	public void init() {
		this.tagIds = tagsToIds(this.getTags());
	}

	//1,2,3
	private String tagsToIds(List<Tag> tags) {
		if (!tags.isEmpty()) {
			StringBuffer ids = new StringBuffer();
			boolean flag = false;
			for (Tag tag : tags) {
				if (flag) {
					ids.append(",");
				} else {
					flag = true;
				}
				ids.append(tag.getId());
			}
			return ids.toString();
		} else {
			return tagIds;
		}
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFirstPicture() {
		return firstPicture;
	}

	public void setFirstPicture(String firstPicture) {
		this.firstPicture = firstPicture;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public boolean isAppreciation() {
		return appreciation;
	}

	public void setAppreciation(boolean appreciation) {
		this.appreciation = appreciation;
	}

	public boolean isShareStatement() {
		return shareStatement;
	}

	public void setShareStatement(boolean shareStatement) {
		this.shareStatement = shareStatement;
	}

	public boolean isCommentabled() {
		return commentabled;
	}

	public void setCommentabled(boolean commentabled) {
		this.commentabled = commentabled;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
