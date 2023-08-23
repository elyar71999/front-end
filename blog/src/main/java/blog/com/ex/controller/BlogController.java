package blog.com.ex.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.ex.model.entity.BlogEntity;
import blog.com.ex.model.entity.UserEntity;
import blog.com.ex.service.BlogService;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user/blog")
@Controller
public class BlogController {
	@Autowired
	private BlogService blogService;

	@Autowired
	private HttpSession session;

	@GetMapping("/list")
	public String getBlogListPage(Model model) {
		UserEntity userList = (UserEntity) session.getAttribute("user");
		Long userId = userList.getUserId();

		String userName = userList.getUserName();

		List<BlogEntity> blogList = blogService.findAllBlogPost(userId);

		model.addAttribute("userName", userName);
		model.addAttribute("blogList", blogList);
		return "blog.html";

	}

	@GetMapping("/register")
	public String getBlogRegisterPage(Model model) {
		UserEntity userList = (UserEntity) session.getAttribute("user");
		String userName = userList.getUserName();
		model.addAttribute("userName", userName);
		model.addAttribute("registerMessage", "新規記事追加");
		return "blog_register.html";
	}

	@PostMapping("/register/process")

	public String blogRegister(@RequestParam String blogTitle, @RequestParam LocalDate registerDate,
			@RequestParam String category, @RequestParam MultipartFile blogImage, @RequestParam String blogDetail,
			Model model) {

		UserEntity userList = (UserEntity) session.getAttribute("user");
		Long userId = userList.getUserId();

		String FileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
				+ blogImage.getOriginalFilename();
		try {
			Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/image/" + FileName));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (blogService.createBlogPost(blogTitle, registerDate, FileName, blogDetail, category, userId)) {
			return "redirect:/user/blog/list";
		} else {
			model.addAttribute("registerMessage", "既に登録済みです");
			return "login.html";
		}
	}

	@GetMapping("/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId, Model model) {
		UserEntity userList = (UserEntity) session.getAttribute("user");
		String userName = userList.getUserName();
		model.addAttribute("userName", userName);
		BlogEntity blogList = blogService.getBlogPost(blogId);
		if (blogList == null) {
			return "redirecr:/user/blog/List";
		} else {
			model.addAttribute("blogList", blogList);
			model.addAttribute("editMessage", "記事編集");
			return "blog_edit.html";
		}
	}

	@PostMapping("/update")
	public String blogUpdate(@RequestParam String blogTitle, @RequestParam LocalDate registerDate,
			@RequestParam MultipartFile blogImage, @RequestParam String category, @RequestParam String blogDetail,
			@RequestParam Long blogId, Model model) {
		UserEntity userList = (UserEntity) session.getAttribute("user");
		Long userId = userList.getUserId();
		String FileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
				+ blogImage.getOriginalFilename();
		try {
			Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/image/" + FileName));

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (blogService.editBlogPost(blogTitle, registerDate, FileName, blogDetail, category, userId, blogId)) {
			return "redirect:/user/blog/list";
		} else {
			model.addAttribute("registerMessage", "更新失敗しました");
			return "blog_edit.html";
		}

	}

	@GetMapping("/image/edit/{blogId}")
	public String getBlogEditImagePage(@PathVariable Long blogId, Model model) {
		/**
		 * セッションから現在のユーザー情報を取得するため、sessionオブジェクトを使用しています。
		 */
		UserEntity userList = (UserEntity) session.getAttribute("user");

		/**
		 * userListから現在ログインしている人のユーザー名を取得
		 **/
		String userName = userList.getUserName();
		model.addAttribute("userName", userName);
		/**
		 * blogService.getBlogPost(blogId)を使用して、 指定されたblogIdに対応するブログを取得し、blogListに代入します。
		 **/
		BlogEntity blogList = blogService.getBlogPost(blogId);
		/** blogListがnullであれば、"/user/blog/list"にリダイレクトします。 **/
		if (blogList == null) {
			return "redirecr:/user/blog/list";
		} else {
			/** blogListと"画像編集"というメッセージをModelオブジェクトに追加し、"blog-edit.html"に遷移します。 **/
			model.addAttribute("blogList", blogList);
			model.addAttribute("editImageMessage", "画像編集");
			return "blog-img-edit.html";
		}

	}

	/**
	 * このメソッドはブログの投稿を処理するためのメソッドで以下のような処理を行っています。
	 * セッションから現在のユーザー情報を取得し、そのuserIdを取得する。 画像ファイル名を取得する。 画像ファイルをアップロードする。
	 * createBlogPost()メソッドを呼び出して、ブログをデータベースに登録する。
	 * 登録に成功した場合は、blog-register-fix.htmlにリダイレクトする。
	 * 登録に失敗した場合は、エラーメッセージをモデルに追加し、blog-register.htmlに戻る。
	 **/
	/**
	 * @param blogTitle    ブログタイトル
	 * @param registerDate 登録日
	 * @param category     カテゴリー
	 * @param blogImage    画像イメージ
	 * @param blogDetail   ブログ詳細
	 * @param model
	 * @return
	 */
	@PostMapping("/image/update")
	/**
	 * @RequestParamアノテーション： このアノテーションは、HTTPリクエストのパラメーターを引数にバインドするために使用されます。
	 * ブログの画像更新時に、ブログのタ、画像ファイル、ブログIdをパラメータとして受け取ります。
	 **/
	/**
	 * MultipartFile： MultipartFileは、Spring Frameworkが提供するインターフェースで、
	 * アップロードされたファイルの内容を扱うためのメソッドを提供しています。
	 **/
	public String blogImgUpdate(@RequestParam MultipartFile blogImage, @RequestParam Long blogId, Model model) {
		/**
		 * セッションから現在のユーザー情報を取得するため、sessionオブジェクトを使用しています。
		 * UserEntityのインスタンスが取得できた場合、そのuserIdを取得しています。
		 **/
		UserEntity userList = (UserEntity) session.getAttribute("user");
		Long userId = userList.getUserId();
		/**
		 * 現在の日時情報を元に、ファイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフォーマットを指定している。
		 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフォーマットされた文字列を取得している。
		 * その後、blogImageオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結して、fileName変数に代入
		 **/
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
				+ blogImage.getOriginalFilename();
		try {
			/**
			 * ファイルを実際にサーバー上に保存するための処理を行っています。Files.copy()メソッドを使用して、
			 * blogImageオブジェクトから入力ストリームを取得し、指定されたパスにファイルをコピー。
			 * Path.of()メソッドを使用して、保存先のパスを指定している。
			 * 保存先のパスは、"src/main/resources/static/blog-img/"というディレクトリの中に
			 * 、fileNameで指定されたファイル名で保存される。。
			 **/
			Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog-img/" + fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 最後に、blogService.editBlogImage()メソッドが呼び出され、入力されたデータをデータベースに保存します。
		 * fileName,、blogId、userIdの各引数を使用して、BlogEntityオブジェクトを生成し、
		 * blogDao.save()メソッドを使用して、データベースに保存します。
		 * editBlogImageメソッドはboolean型の戻り値を返します。trueが返された場合は、blog-edit-fix.htmlページが表示され、
		 * falseが返された場合は、blogListと更新失敗であることを示すメッセージが含まれたblog-img-edit.htmlページが表示されます。
		 **/
		if (blogService.editBlogImage(blogId, fileName, userId)) {
			return "blog-edit-fix.html";
		} else {
			BlogEntity blogList = blogService.getBlogPost(blogId);
			model.addAttribute("blogList", blogList);
			model.addAttribute("editImageMessage", "更新失敗です");
			return "blog-img-edit.html";
		}

	}

	/**
	 * @GetMappingアノテーションがgetBlogListPageメソッドに付与されています。 このアノテーションは、HTTP
	 * GETリクエストを受け取るメソッドであることを示します。
	 **/
	@GetMapping("/delete/list")
	/**
	 * getBlogListPageメソッドは、Modelクラスのインスタンスを引数に取ります。Modelは、
	 * コントローラーからビューに渡すためのデータを格納するために使用されます。
	 **/
	public String getBlogDeleteListPage(Model model) {
		/**
		 * セッションから現在のユーザー情報を取得するため、sessionオブジェクトを使用しています。
		 * UserEntityのインスタンスが取得できた場合、そのuserIdを取得しています。
		 **/
		UserEntity userList = (UserEntity) session.getAttribute("user");
		Long userId = userList.getUserId();
		/**
		 * userListから現在ログインしている人のユーザー名を取得
		 **/
		String userName = userList.getUserName();
		/**
		 * blogServiceのfindAllBlogPostメソッドを呼び出して、現在のユーザーに関連するブログ投稿を取得しています。
		 * 戻り値はBlogEntityのリストであり、このリストをmodelに追加しています。
		 **/
		List<BlogEntity> blogList = blogService.findAllBlogPost(userId);
		/**
		 * ModelクラスにuserNameとblogList,deleteMessageを追加して、blog-delete.htmlというビューを返しています。
		 * このビューは、userNameとblogList,deleteMessageを表示するためのHTMLテンプレート
		 **/
		model.addAttribute("userName", userName);
		model.addAttribute("blogList", blogList);
		model.addAttribute("deleteMessage", "削除一覧");
		return "blog-delete.html";
	}

	@GetMapping("/delete/detail/{blogId}")
	public String getBlogDeleteDetailPage(@PathVariable Long blogId, Model model) {
		/**
		 * セッションから現在のユーザー情報を取得するため、sessionオブジェクトを使用しています。
		 * UserEntityのインスタンスが取得できた場合、そのuserIdを取得しています。
		 **/
		UserEntity userList = (UserEntity) session.getAttribute("user");
		Long userId = userList.getUserId();
		/**
		 * userListから現在ログインしている人のユーザー名を取得
		 **/
		String userName = userList.getUserName();
		model.addAttribute("userName", userName);
		/**
		 * blogService.getBlogPost(blogId)を使用して、 指定されたblogIdに対応するブログを取得し、blogListに代入します。
		 **/
		BlogEntity blogList = blogService.getBlogPost(blogId);
		/** blogListがnullであれば、"/user/blog/list"にリダイレクトします。 **/
		if (blogList == null) {
			return "redirecr:/user/blog/list";
		} else {
			/**
			 * blogListと"削除記事詳細というメッセージをModelオブジェクトに追加し、 "blog-delete-detail.html"に遷移します。
			 **/
			model.addAttribute("blogList", blogList);
			model.addAttribute("DeleteDetailMessage", "削除記事詳細");
			return "blog-delete-detail.html";
		}

	}

	/**
	 * @param blogId ブログId
	 * @param model
	 * @return
	 */
	/**
	 * このソースコードは、Spring MVCのコントローラーにおいて、HTTP POSTリクエストが "/delete" に送信された場合に、
	 * 指定されたブログ記事を削除するblogDeleteメソッドを定義しています。
	 * メソッドの引数として、@RequestParamアノテーションを使用して、HTTPリクエストのパラメータとして送信されたblogIdを受け取ります。
	 * また、Modelクラスを引数にして、Viewに渡すためのデータを設定することができます。
	 * メソッド内では、blogService.deleteBlog(blogId)を呼び出して、指定されたブログ記事を削除します。
	 * このメソッドの戻り値がtrueであれば、"blog-delete-fix.html"にリダイレクトします。一方、戻り値がfalseであれば、
	 * Modelクラスに "DeleteDetailMessage"
	 * 属性を設定して、"blog-deletee.html"に遷移し、エラーメッセージを表示します。
	 **/
	@PostMapping("/delete")
	public String blogDelete(@RequestParam Long blogId, Model model) {
		if (blogService.deleteBlog(blogId)) {
			return "redirect:/user/blog/list";
		} else {
			model.addAttribute("DeleteDetailMessage", "記事削除に失敗しました");
			return "blog-delete.html";
		}

	}

	/**
	 * @GetMapping("/logout")は、"/logout"エンドポイントに対するHTTP GETリクエストを処理するためのアノテーションです。
	 * public String Logout()は、ログアウト処理を行うメソッドです。このメソッドは、セッションを無効にして
	 * 、"/user/login"にリダイレクトすることで、ログアウトを実行します。
	 * session.invalidate();は、現在のセッションを無効にするために使用されるコードです
	 * これにより、ユーザーがログアウトしたことが確認されます。 return
	 * "redirect:/user/login";は、"/user/login"にリダイレクトするために使用されるコードです。
	 * これにより、ログアウト後にユーザーがログインページにリダイレクトされます。 したがって、このコードは、セッションを無効にして、ユーザーをログアウトし、
	 * ログインページにリダイレクトすることにより、ユーザーの認証を管理します。
	 **/
	@GetMapping("/logout")
	public String Logout() {

		session.invalidate();
		return "redirect:/user/login";
	}
}
