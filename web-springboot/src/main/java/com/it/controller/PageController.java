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

@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                      @RequestParam(value = "tagId", required = false) Integer tagId,
                      @RequestParam(value = "keyword", required = false) String keyword,
                      Model model,
                      HttpServletRequest request) {
        Result<PageResult<Article>> articleResult = articleService.getArticleList(pageNum, pageSize, tagId, keyword);
        if (articleResult.isSuccess()) {
            model.addAttribute("articlePage", articleResult.getData());
        }

        Result<List<Article>> hotResult = articleService.getHotArticles(10);
        if (hotResult.isSuccess()) {
            model.addAttribute("hotArticles", hotResult.getData());
        }

        model.addAttribute("tagId", tagId);
        model.addAttribute("keyword", keyword);

        return "article_list";
    }

    @GetMapping("/article/{articleId}")
    public String articleDetail(@PathVariable("articleId") Integer articleId,
                            HttpServletRequest request,
                            Model model) {
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

    @GetMapping("/article/edit")
    public String editArticlePage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }
        return "article_edit";
    }

    @GetMapping("/article/edit/{articleId}")
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

    @GetMapping("/everyone")
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

    @GetMapping("/following")
    public String followingPage() {
        return "attention";
    }

    @GetMapping("/follower")
    public String followerPage() {
        return "friend";
    }

    @GetMapping("/blacklist")
    public String blacklistPage() {
        return "blacklist";
    }

    @GetMapping("/doumail")
    public String doumailPage() {
        return "doumail";
    }

    @GetMapping("/doumail/{userId}")
    public String doumailDetailPage(@PathVariable("userId") Integer userId, Model model) {
        model.addAttribute("targetUserId", userId);
        return "doumail_show";
    }

    @GetMapping("/sendmail/{userId}")
    public String sendMailPage(@PathVariable("userId") Integer userId, Model model) {
        User user = userService.getUserByUserId(userId);
        model.addAttribute("targetUser", user);
        return "send_mail";
    }
}
