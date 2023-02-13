package com.poly.rest.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.poly.service.ChartService;
//duy
@RestController
public class ChartRestController {
	@Autowired
    ChartService service;
 
	@GetMapping("/rest/statsByCategories")
	public List<Object[]> getStatsByCategories(){
	    return service.getStatsByCategories();
	}
	
	@GetMapping("/rest/statsByProducts")
	public List<Object[]> getStatsByProducts(){
	    return service.getStatsByProducts();
	}
	
	@GetMapping("/rest/statsMonths")
	public List<Object[]> getMonthStats(){
	    return service.getMonthStats(); 
	}
	
	@GetMapping("/rest/statsMonthCheck/{fromDate}/{toDate}")
	public List<Object[]> getMonthStatsCheck(@PathVariable("fromDate") String fromDate, 
	        @PathVariable("toDate") String toDate) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       
	    String[] from = fromDate.split("-");
	    String[] to = toDate.split("-");
	    
	    String fDate = "", tDate = "";
	    
        for(int i = 2; i >= 0; i--) {
            fDate += from[i] + "-";
            tDate += to[i] + "-";
        }
        if(fromDate != null && toDate != null) {
            try {
                List<Object[]> list = service.getMonthStatsCheck(fDate.substring(0, fDate.length()-1), 
                        tDate.substring(0, tDate.length()-1));
                     return list;
            } catch (Exception e) {
                
            }
            return null;
        } else {
            return service.getMonthStats();
        }
        
	}
	
	@GetMapping("/rest/statsByQuantity")
	public List<Object[]> getQuantityStats(){
	    return service.getStatsByQuntity();
	}
	
	@GetMapping("/rest/statsByClient")
	public List<Object[]> getClient(){
	    return service.getCilents();
	}
	
	@GetMapping("/rest/statsClientByKeyword/{keyWord}")
	public List<Object[]> getCilentByKeyWord(@PathVariable("keyWord") String key){
	    if(service.getCilentByKeyWord(key).size() != 0) {
	        return service.getCilentByKeyWord(key);	        
	    } else return null;
	}
	
}
