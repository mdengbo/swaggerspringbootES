package com.cun.controller;

import com.cun.dao.UserDao;
import com.cun.entity.User;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Optional;

/**
 * @author madengbo
 * @create 2018-10-22 15:12
 * @desc User 查询
 * @Version 1.0
 **/
@Controller
@RequestMapping("user")
@EnableSwagger2
public class UserController {
    @Autowired
    private UserDao userDao;

    /**
     * 1、查  id
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    public Optional<User> getBookById(@PathVariable String id) {
        return userDao.findById(id);
    }

    /**
     * 4、增
     * @param
     * @return
     */
    @RequestMapping("/addUser")
    public String add(){
       return "addUser";
    }

    @PostMapping("/insert")
    @ResponseBody
    public User insertBook(User user) {
        User us = userDao.save(user);
        return us;
    }

    /**
     * 3、查   +++：分页、分数、分域（结果一个也不少）
     * @param page
     * @param size
     * @param q
     * @return
     * @return
     */
/*	@GetMapping("/{page}/{size}/{q}/{username}")
	public List<User> searchCity(@PathVariable Integer page, @PathVariable Integer size, @PathVariable String q) {

		// 分页参数
		Pageable pageable = new PageRequest(page, size);


		// 分数，并自动按分排序
		FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
				.add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("name", q)),
						ScoreFunctionBuilders.weightFactorFunction(1000)) // 权重：name 1000分
				.add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("message", q)),
						ScoreFunctionBuilders.weightFactorFunction(100)); // 权重：message 100分

		// 分数、分页
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable)
				.withQuery(functionScoreQueryBuilder).build();

		Page<User> searchPageResults = userDao.search(searchQuery);
		return searchPageResults.getContent();

	}*/

    @GetMapping("/select/{page}/{size}/{q}")
    @ResponseBody
    public List<User> testSearch(@PathVariable Integer page, @PathVariable Integer size,@PathVariable String q) {
        // 分页参数
        Pageable pageable = new PageRequest(page, size);

        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(q);
        // 分数、分页
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable)
                .withQuery(builder).build();

        Page<User> searchPageResults = userDao.search(searchQuery);
        /*Iterable<User> searchResult = userDao.search(builder);
        Iterator<User> iterator = searchResult.iterator();
        List<User> list = new ArrayList<User>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }*/

        return searchPageResults.getContent();
        //return list;
    }
}
