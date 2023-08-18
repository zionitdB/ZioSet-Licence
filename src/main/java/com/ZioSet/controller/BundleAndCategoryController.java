package com.ZioSet.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZioSet.Service.BundleAndCategoryService;
import com.ZioSet.dto.ResponceObj;
import com.ZioSet.model.Bundle;
import com.ZioSet.model.BundleApplications;
import com.ZioSet.model.Category;
import com.ZioSet.model.CategoryApplications;



@RestController
@CrossOrigin("*")
@RequestMapping("/bundle")
public class BundleAndCategoryController {
	
	@Autowired
	BundleAndCategoryService bundleAndCategoryService;
	
	
	@RequestMapping(value = "/addBundle", method = RequestMethod.POST)
	public ResponceObj addBundle(@RequestBody Bundle bundle)  {
		ResponceObj responceDTO= new ResponceObj();
		try {		 
			System.out.println("adding ");
			bundle.setCreatedDate(new Date());
			
			
			Bundle bundle2=bundleAndCategoryService.addBundle(bundle);
			for(BundleApplications applications:bundle.getApplications()){
				applications.setBundle(bundle2);
				bundleAndCategoryService.addApplicationBundle(applications);
			}
			responceDTO.setCode(200);
			responceDTO.setMessage("New Bundle Added Successfully");
			
			return responceDTO	;
		} catch (Exception e) {
			e.printStackTrace();
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return responceDTO	;
		}
	}
	@RequestMapping(value = "/getAllBundles", method = RequestMethod.GET)
	public @ResponseBody List<Bundle> getAllBundles() {
		List<Bundle> list= new  ArrayList<Bundle>();
		try {	
			list=bundleAndCategoryService.getAllBundles();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getBundlePagination/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<Bundle> getBundlePagination(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<Bundle> list= new  ArrayList<Bundle>();
		try {	
			list=bundleAndCategoryService.getBundlePagination(page_no,item_per_page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	//get Machine By limit Search
	@RequestMapping(value = "/getBundleByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<Bundle> getBundleByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Bundle> list= new  ArrayList<Bundle>();
		try {	
			list=bundleAndCategoryService.getBundleByLimitAndSearch(searchText,pageNo,perPage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//Machine count 
	@RequestMapping(value = "/getBundleTotalCount", method = RequestMethod.GET)
	public @ResponseBody int  getBundleTotalCount() {
		int  count= 0;
		try {
			count= bundleAndCategoryService.getBundleTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	//Machine count and search 
	@RequestMapping(value = "/getBundleCountbySearch", method = RequestMethod.GET)
	public @ResponseBody int  getBundleCountbySearch(@RequestParam ("searchText") String searchText) {
		int  machineCount= 0;
		try {
			machineCount= bundleAndCategoryService.getBundleCountbySearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return machineCount;
	}
	
	
	
	@RequestMapping(value = "/addApplicationBundle", method = RequestMethod.POST)
	public ResponceObj addBundle(@RequestBody BundleApplications bundleApplications )  {
		ResponceObj responceDTO= new ResponceObj();
		try {		 
			//applicationBundle.set(new Date());
			
			
			bundleAndCategoryService.addApplicationBundle(bundleApplications);
			responceDTO.setCode(200);
			responceDTO.setMessage("New ApplicationBundle Added Successfully");
			
			return responceDTO	;
		} catch (Exception e) {
			e.printStackTrace();
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return responceDTO	;
		}
	}
	@RequestMapping(value = "/getApplicationBundlePagination/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<BundleApplications> getApplicationBundlePagination(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<BundleApplications> list= new  ArrayList<BundleApplications>();
		try {	
			list=bundleAndCategoryService.getApplicationBundlePagination(page_no,item_per_page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	//get Machine By limit Search
	@RequestMapping(value = "/getApplicationBundleByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<BundleApplications> getApplicationBundleByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<BundleApplications> list= new  ArrayList<BundleApplications>();
		try {	
			list=bundleAndCategoryService.getApplicationBundleByLimitAndSearch(searchText,pageNo,perPage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//Machine count 
	@RequestMapping(value = "/getApplicationBundleTotalCount", method = RequestMethod.GET)
	public @ResponseBody int  getApplicationBundleTotalCount() {
		int  count= 0;
		try {
			count= bundleAndCategoryService.getApplicationBundleTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	//Machine count and search 
	@RequestMapping(value = "/getApplicationBundleCountbySearch", method = RequestMethod.GET)
	public @ResponseBody int  getApplicationBundleCountbySearch(@RequestParam ("searchText") String searchText) {
		int  machineCount= 0;
		try {
			machineCount= bundleAndCategoryService.getApplicationBundleCountbySearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return machineCount;
	}
	
	
	@RequestMapping(value = "/getApplicationByBundleId", method = RequestMethod.GET)
	public @ResponseBody List<BundleApplications> getApplicationByBundleId(@RequestParam("bundleId") int bundleId) {
		List<BundleApplications> list= new  ArrayList<BundleApplications>();
		try {	
			list=bundleAndCategoryService.getApplicationByBundleId(bundleId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public ResponceObj addCategory(@RequestBody Category category)  {
		ResponceObj responceDTO= new ResponceObj();
		try {		 
			System.out.println("adding ");
			category.setCreatedDate(new Date());
			
			
			Category category2=bundleAndCategoryService.addCategory(category);
			for(CategoryApplications applications:category.getApplications()){
				applications.setCategory(category2);
				bundleAndCategoryService.addCategoryApplications(applications);
			}
			responceDTO.setCode(200);
			responceDTO.setMessage("New Category Added Successfully");
			
			return responceDTO	;
		} catch (Exception e) {
			e.printStackTrace();
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return responceDTO	;
		}
	}
	@RequestMapping(value = "/getAllCategory", method = RequestMethod.GET)
	public @ResponseBody List<Category> getAllCategory() {
		List<Category> list= new  ArrayList<Category>();
		try {	
			list=bundleAndCategoryService.getAllCategory();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getCategoryPagination/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<Category> getCategoryPagination(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<Category> list= new  ArrayList<Category>();
		try {	
			list=bundleAndCategoryService.getCategoryPagination(page_no,item_per_page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	//get Machine By limit Search
	@RequestMapping(value = "/getCategoryByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<Category> getCategoryByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Category> list= new  ArrayList<Category>();
		try {	
			list=bundleAndCategoryService.getCategoryByLimitAndSearch(searchText,pageNo,perPage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//Machine count 
	@RequestMapping(value = "/getCategoryTotalCount", method = RequestMethod.GET)
	public @ResponseBody int  getCategoryTotalCount() {
		int  count= 0;
		try {
			count= bundleAndCategoryService.getCategoryTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	//Machine count and search 
	@RequestMapping(value = "/getCategoryCountbySearch", method = RequestMethod.GET)
	public @ResponseBody int  getCategoryCountbySearch(@RequestParam ("searchText") String searchText) {
		int  machineCount= 0;
		try {
			machineCount= bundleAndCategoryService.getCategoryCountbySearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return machineCount;
	}
	@RequestMapping(value = "/getCategoryApplicationsByCategory", method = RequestMethod.GET)
	public @ResponseBody List<CategoryApplications> getCategoryApplicationsByCategory(@RequestParam("categoryId") int categoryId) {
		List<CategoryApplications> list= new  ArrayList<CategoryApplications>();
		try {	
			list=bundleAndCategoryService.getCategoryApplicationsByCategory(categoryId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
