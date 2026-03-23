package com.weibo.controller;

import com.weibo.common.Result;
import com.weibo.dto.PageResult;
import com.weibo.model.Article;
import com.weibo.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    @ResponseBody
    public Result<com.weibo.model.ArticleList> getArticleList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                              @RequestParam(value = "tagId", required = false) Integer tagId,
                                                              @RequestParam(value = "keyword", required = false) String keyword) {
        return articleService.getArticleList(pageNum, pageSize, tagId, keyword);
    }

    @PostMapping("/publish")
    public String publishArticle(@ModelAttribute Article article,
                                 @RequestParam(value = "tagIds", required = false) String tagIdsStr,
                                 HttpServletRequest request,
                                 Model model) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }
        article.setAuthorId(userId);
        List<Integer> tagIds = null;
        if (tagIdsStr != null && !tagIdsStr.isEmpty()) {
            String[] tagIdArray = tagIdsStr.split(",");
            tagIds = Arrays.stream(tagIdArray).map(Integer::parseInt).collect(Collectors.toList());
        }
        Result<Article> result = articleService.publishArticle(article, tagIds);
        if (result.isSuccess()) {
            logger.info("文章发布成功，文章ID：{}", result.getData().getArticleId());
            return "redirect:/page/home";
        } else {
            model.addAttribute("errorMsg", result.getMsg());
            return "article_edit";
        }
    }

    @PostMapping("/update")
    public String updateArticle(@ModelAttribute Article article,
                                @RequestParam(value = "tagIds", required = false) String tagIdsStr,
                                HttpServletRequest request,
                                Model model) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }
        List<Integer> tagIds = null;
        if (tagIdsStr != null && !tagIdsStr.isEmpty()) {
            String[] tagIdArray = tagIdsStr.split(",");
            tagIds = Arrays.stream(tagIdArray).map(Integer::parseInt).collect(Collectors.toList());
        }
        Result<Article> result = articleService.updateArticle(article, tagIds);
        if (result.isSuccess()) {
            logger.info("文章更新成功，文章ID：{}", article.getArticleId());
            return "redirect:/page/article/" + article.getArticleId();
        } else {
            model.addAttribute("errorMsg", result.getMsg());
            return "article_edit";
        }
    }

    @PostMapping("/delete/{articleId}")
    @ResponseBody
    public Result<Boolean> deleteArticle(@PathVariable("articleId") Integer articleId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }
        return articleService.deleteArticle(articleId, userId);
    }

    @GetMapping("/hot")
    @ResponseBody
    public Result<List<Article>> getHotArticles(@RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return articleService.getHotArticles(limit);
    }
}
