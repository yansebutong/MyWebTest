package com.yzp.fruit.service.impl;

import com.yzp.fruit.dao.FruitDAO;
import com.yzp.fruit.dao.base.ConnUtil;
import com.yzp.fruit.pojo.Fruit;
import com.yzp.fruit.service.FruitService;

import java.util.List;

public class FruitServiceImpl implements FruitService {

    private FruitDAO fruitDAO = null;
    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNo) {
        System.out.println("ConnUtil.getConn() = " + ConnUtil.getConn());
        return fruitDAO.getFruitList(keyword, pageNo);
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitDAO.addFruit(fruit);
    }

    @Override
    public void delFruit(Integer fid) {
        fruitDAO.delFruit(fid);
    }

    @Override
    public void updateFruit(Fruit fruit) {
        fruitDAO.updateFruit(fruit);
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        return fruitDAO.getFruitByFid(fid);
    }

    @Override
    public Integer getPageCount(String keyword) {
        System.out.println("ConnUtil.getConn() = " + ConnUtil.getConn());
        Integer fruitCount = fruitDAO.getFruitCount(keyword);
        return (fruitCount+5-1)/5;
    }
}
