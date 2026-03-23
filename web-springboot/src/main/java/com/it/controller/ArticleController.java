package com.weibo.controller;

import com.weibo.dto.PageResult;
import com.weibo.dto.Result;
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

/**
 * 文章控制器
 * 处理文章发布、编辑、删除等功能
 * 
 * @author weibo Team
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<Article>> getArticleList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "tagId", required = false) Integer tagId,
                                                  @RequestParam(value = "keyword", required = false) String keyword) {
        return articleService.getArticleList(pageNum, pageSize, tagId, keyword);
    }

    /**
     * 发布文章
     */
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

        article.setAuthor(userId);

        // 处理标签
        List<Integer> tagIds = null;
        if (tagIdsStr != null && !tagIdsStr.isEmpty()) {
            String[] tagIdArray = tagIdsStr.split(",");
            tagIds = Arrays.stream(tagIdArray)
                           .map(Integer::parseInt)
                           .toList();
        }

        Result<Article> result = articleService.publishArticle(article, tagIds);

        if (result.isSuccess()) {
            logger.info("文章发布成功，文章ID：{}", result.getData().getArticleId());
            return "redirect:/page/home";
        } else {
            model.addAttribute("errorMsg", result.getMessage());
            return "article_edit";
        }
    }

    /**
     * 更新文章
     */
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

        // 处理标签
        List<Integer> tagIds = null;
        if (tagIdsStr != null && !tagIdsStr.isEmpty()) {
            String[] tagIdArray = tagIdsStr.split(",");
            tagIds = Arrays.stream(tagIdArray)
                           .map(Integer::parseInt)
                           .toList();
        }

        Result<Article> result = articleService.updateArticle(article, tagIds);

        if (result.isSuccess()) {
            logger.info("文章更新成功，文章ID：{}", article.getArticleId());
            return "redirect:/page/article/" + article.getArticleId();
        } else {
            model.addAttribute("errorMsg", result.getMessage());
            return "article_edit";
        }
    }

    /**
     * 删除文章
     */
    @PostMapping("/delete/{articleId}")
    @ResponseBody
    public Result<Boolean> deleteArticle(@PathVariable("articleId") Integer articleId,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail(401, "请先登录");
        }

        return articleService.deleteArticle(articleId, userId);
    }

    /**
     * 获取热门文章
     */
    @GetMapping("/hot")
    @ResponseBody
    public Result<List<Article>> getHotArticles(@RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return articleService.getHotArticles(limit);
    }
}
