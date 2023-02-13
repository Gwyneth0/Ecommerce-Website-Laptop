package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.OrderDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.dao.ProductDAO;
import com.poly.service.ChartService;

import net.bytebuddy.asm.Advice.Return;

@Service
public class ChartServiceImpl implements ChartService{
	
	@Autowired
    ProductDAO pdao;
    
    @Autowired
    OrderDetailDAO oddao;
    
    @Autowired
    OrderDAO orderDAO;

    @Override
    public List<Object[]> getStatsByCategories() {
        return pdao.getStatsByCategories();
    }

    @Override
    public List<Object[]> getStatsByProducts() {
        return oddao.getStatsByProducts();
    }
    
    @Override
    public List<Object[]> getMonthStats() {
        return oddao.getMonthStats();
    }

    @Override
    public List<Object[]> getMonthStatsCheck(String fromDate, String toDate) {
        return oddao.getMonthStatsCheck(fromDate, toDate);
    }

	@Override
	public List<Object[]> getStatsByQuntity() {
		return pdao.getStatsByQuntity();
	}

	@Override
	public List<Object[]> getCilents() {
		return orderDAO.getCilents();
	}

	@Override
	public List<Object[]> getCilentByKeyWord(String key) {
		return orderDAO.getCilentByKeyWord(key);
	}
}
