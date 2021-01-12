package com.wjh.po;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nickname;//昵称
	private String email;//邮箱
	private String content;//评论内容
	private String avatar;//头像
	private boolean adminComment;//标记是否是管理员的评论

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;//创建时间
	@ManyToOne()
	private Blog blog;

	@OneToMany(mappedBy = "parentComment")
	private List<Comment> replyComments = new ArrayList<>();//子评论(回复)

	@ManyToOne
	private Comment parentComment;//父评论，一个子评论只能由一个父评论

	public List<Comment> getReplyComments() {
		return replyComments;
	}

	public void setReplyComments(List<Comment> replyComments) {
		this.replyComments = replyComments;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	@JsonBackReference
	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public Comment() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isAdminComment() {
		return adminComment;
	}

	public void setAdminComment(boolean adminComment) {
		this.adminComment = adminComment;
	}

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", nickname='" + nickname + '\'' +
				", email='" + email + '\'' +
				", content='" + content + '\'' +
				", avatar='" + avatar + '\'' +
				", adminComment=" + adminComment +
				", createTime=" + createTime +
				", blog=" + blog +
				", replyComments=" + replyComments +
				", parentComment=" + parentComment +
				'}';
	}
}
