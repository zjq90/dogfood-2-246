package com.it.controller;

import com.it.common.PageResult;
import com.it.common.Result;
import com.it.model.Article;
import com.it.model.User;
import com.it.service.ArticleService;
import com.it.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 页面跳转控制器
 * 处理通用页面跳转
 * 
 * @author weibo Team
 */
@Controller
public class PageController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    /**
     * 首页
     */
    @GetMapping({"/", "/page/home"})
    public String home(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                      @RequestParam(value = "tagId", required = false) Integer tagId,
                      @RequestParam(value = "keyword", required = false) String keyword,
                      Model model,
                      HttpServletRequest request) {
        // 获取热门文章
        Result<PageResult<Article>> articleResult = articleService.getArticleList(pageNum, pageSize, tagId, keyword);
        if (articleResult.isSuccess()) {
            model.addAttribute("articlePage", articleResult.getData());
        }

        // 获取热门文章TOP10
        Result<List<Article>> hotResult = articleService.getHotArticles(10);
        if (hotResult.isSuccess()) {
            model.addAttribute("hotArticles", hotResult.getData());
        }

        model.addAttribute("tagId", tagId);
        model.addAttribute("keyword", keyword);

        return "article_list";
    }

    /**
     * 文章详情页
     */
    @GetMapping("/page/article/{articleId}")
    public String articleDetail(@PathVariable("articleId") Integer articleId,
                            HttpServletRequest request,
                            Model model) {
        // 增加浏览量
        articleService.incrementPageView(articleId);

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        Result<Article> result = articleService.getArticleDetail(articleId, userId);
        if (result.isSuccess()) {
            model.addAttribute("article", result.getData());
            return "article_show";
        }
        return "redirect:/page/home";
    }

    /**
     * 文章编辑页
     */
    @GetMapping("/page/article/edit")
    public String editArticlePage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }
        return "article_edit";
    }

    /**
     * 编辑现有文章
     */
    @GetMapping("/page/article/edit/{articleId}")
    public String editExistArticlePage(@PathVariable("articleId") Integer articleId,
                                   HttpServletRequest request,
                                   Model model) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }

        Result<Article> result = articleService.getArticleDetail(articleId, userId);
        if (result.isSuccess()) {
            model.addAttribute("article", result.getData());
            return "article_edit";
        }
        return "redirect:/page/home";
    }

    /**
     * 所有人页面
     */
    @GetMapping("/page/everyone")
    public String everyonePage(@RequestParam(value = "keyword", required = false) String keyword,
                            Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            Result<List<User>> result = userService.searchUsers(keyword);
            if (result.isSuccess()) {
                model.addAttribute("users", result.getData());
            }
        }
        return "everyone";
    }

    /**
     * 关注列表页面
     */
    @GetMapping("/page/following")
    public String followingPage() {
        return "attention";
    }

    /**
     * 粉丝列表页面
     */
    @GetMapping("/page/follower")
    public String followerPage() {
        return "friend";
    }

    /**
     * 黑名单页面
     */
    @GetMapping("/page/blacklist")
    public String blacklistPage() {
        return "blacklist";
    }

    /**
     * 豆邮页面
     */
    @GetMapping("/page/doumail")
    public String doumailPage() {
        return "doumail";
    }

    /**
     * 豆邮详情页面
     */
    @GetMapping("/doumail/{userId}")
    public String doumailDetailPage(@PathVariable("userId") Integer userId, Model model) {
        model.addAttribute("targetUserId", userId);
        return "doumail_show";
    }

    /**
     * 发送豆邮页面
     */
    @GetMapping("/page/sendmail/{userId}")
    public String sendMailPage(@PathVariable("userId") Integer userId, Model model) {
        User user = userService.getUserByUserId(userId);
        model.addAttribute("targetUser", user);
        return "send_mail";
    }
}
