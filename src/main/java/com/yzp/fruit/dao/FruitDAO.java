package com.yzp.fruit.dao;

import com.yzp.fruit.pojo.Fruit;

import java.util.List;

public interface FruitDAO {
    // 获取所有的库存列表信息，每页显示五条
    List<Fruit> getFruitList(String keyword, Integer pageNo);

    // 根据fid获取特定的水果库存信息
    Fruit getFruitByFid(int fid);

    // 修改指定的库存记录
    void updateFruit(Fruit fruit);

    // 根据fid删除指定的水果库存信息
    void delFruit(Integer fid);

    // 添加新的水果库存记录
    void addFruit(Fruit fruit);

    // 查询库存总记录条数
    int getFruitCount(String keyword);
}
