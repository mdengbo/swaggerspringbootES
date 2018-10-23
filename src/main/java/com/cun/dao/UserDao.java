package com.cun.dao;

import com.cun.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserDao extends ElasticsearchRepository<User,String> {
}
