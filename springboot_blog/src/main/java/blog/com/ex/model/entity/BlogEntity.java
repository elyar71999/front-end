package blog.com.ex.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "blogs")
public class BlogEntity {
@Id
@Column(name = "blog_id")
@GeneratedValue(strategy = GenerationType.AUTO)
private String blogId;

@NonNull
@Column(name = "blog_title")
private String blogTitle;

@NonNull
@DateTimeFormat(pattern = "yyyy-MM-dd")
@Column(name = "register_date")
private LocalDate registerDate;

@NonNull
@Column(name = "blog_image")
private String blogImage;

@NonNull
@Column(name = "blog_datail")
private String blogDetail;

@NonNull
@Column(name = "category")
private String category;

@Column(name = "user_id")
private Long userId;

public BlogEntity(@NonNull String blogTitle,@NonNull LocalDate registerDate,@NonNull String blogImage,@NonNull String blogDetail,@NonNull String category,Long userId) {
	this.blogTitle = blogTitle;
	this.registerDate = registerDate;
	this.blogImage = blogImage;
	this.blogDetail = blogDetail;
	this.category = category;
	this.userId = userId;
}
}
