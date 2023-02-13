package com.poly.service;

import java.util.List;
// duy
public interface ChartService {
	List<Object[]> getStatsByCategories();
    
    List<Object[]> getStatsByProducts();
    
    List<Object[]> getMonthStats();
    
    List<Object[]> getMonthStatsCheck(String fromDate, String toDate);
    
    List<Object[]> getStatsByQuntity();
    
    List<Object[]> getCilents();

    List<Object[]> getCilentByKeyWord(String key);
}
