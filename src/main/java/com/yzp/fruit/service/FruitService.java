package com.yzp.fruit.service;

import com.yzp.fruit.pojo.Fruit;

import java.util.List;

public interface FruitService {
    // 获取指定页面的库存列表信息
    List<Fruit> getFruitList(String keyword, Integer pageNo);
    // 添加库存记录信息
    void addFruit(Fruit fruit);
    // 删除库存信息
    void delFruit(Integer fid);
    // 修改特定库存记录
    void updateFruit(Fruit fruit);
    // 查看特定库存记录
    Fruit getFruitByFid(Integer fid);
    // 获取总页数
    Integer getPageCount(String keyword);
}
