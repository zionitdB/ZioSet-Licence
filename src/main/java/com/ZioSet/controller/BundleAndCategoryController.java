package com.ZioSet.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.security.sasl.AuthorizeCallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZioSet.Repo.AuthorizedApplicationRepo;
import com.ZioSet.Repo.InstallLicenceStockRepo;
import com.ZioSet.Repo.ProductRepo;
import com.ZioSet.Service.BundleAndCategoryService;
import com.ZioSet.dto.LicenceCountDto;
import com.ZioSet.dto.ResponceObj;
import com.ZioSet.model.AssetLicence;
import com.ZioSet.model.Associate;
import com.ZioSet.model.AuthorizedApplication;
import com.ZioSet.model.Bundle;
import com.ZioSet.model.BundleApplications;
import com.ZioSet.model.Category;
import com.ZioSet.model.CategoryApplications;
import com.ZioSet.model.InstallLicenceStock;
import com.ZioSet.model.Licence;
import com.ZioSet.model.Product;



@RestController
@CrossOrigin("*")
@RequestMapping("/bundle")
public class BundleAndCategoryController {
	
	@Autowired
	BundleAndCategoryService bundleAndCategoryService;
	@Autowired
	InstallLicenceStockRepo installLicenceStockRepo;
	@Autowired
	ProductRepo productRepo;
	@Autowired
	AuthorizedApplicationRepo authorizedApplicationRepo;
	
	
	
	
	@RequestMapping(value = "/getAuthorizedApplicationPagination/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<Product> getAuthorizedApplicationPagination(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<Product> list= new  ArrayList<Product>();
		try {	
			
				list=productRepo.getProductByPagination(page_no,item_per_page);
			
				for(Product product:list){
					Optional<AuthorizedApplication> optional= authorizedApplicationRepo.findByProductId(product.getId());
					if(optional.isPresent()){
						product.setCheck(true);

					}else{
						product.setCheck(false);
					}
					//list.add(product);
				}
				
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getAuthorizedApplicationSearchPagination", method = RequestMethod.GET)
	public @ResponseBody List<Product> getAuthorizedApplicationSearchPagination(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Product> list= new  ArrayList<Product>();
		try {	
			
			list=productRepo.getProductBySearchPagination(searchText,pageNo,perPage);
			for(Product product:list){
				Optional<AuthorizedApplication> optional= authorizedApplicationRepo.findByProductId(product.getId());
				if(optional.isPresent()){
					product.setCheck(true);

				}else{
					product.setCheck(false);
				}
				//list.add(product);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAuthorizedApplicationCount", method = RequestMethod.GET)
	public @ResponseBody int  getAuthorizedApplicationCount() {
		int  count= 0;
		try {
			count= productRepo.getProductCount();

			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getSearchCountProduct", method = RequestMethod.GET)
	public @ResponseBody int  getSearchCountProduct(@RequestParam("searchText") String searchText) {
		int  count= 0;
		try {
			count= productRepo.getSearchCountProduct(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@RequestMapping(value = "/getAuthorizedApplication", method = RequestMethod.GET)
	public @ResponseBody List<Product> getAuthorizedApplication() {
		List<Product> list= new  ArrayList<Product>();
		try {	
			List<Product> products=productRepo.findAll();
			
			for(Product product:products){
				Optional<AuthorizedApplication> optional= authorizedApplicationRepo.findByProductId(product.getId());
				if(optional.isPresent()){
					product.setCheck(true);

				}else{
					product.setCheck(false);
				}
				//list.add(product);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/addAuthorizedApplication", method = RequestMethod.POST)
	public @ResponseBody ResponceObj addAuthorizedApplication(@RequestBody Product product) {
		ResponceObj  responceObj= new  ResponceObj();
		try {	
			
			
			if(product.isCheck()){
				Optional<AuthorizedApplication> optional= authorizedApplicationRepo.findByProductId(product.getId());
				if(!optional.isPresent()){
					AuthorizedApplication application= new AuthorizedApplication();
					application.setProduct(product);
					authorizedApplicationRepo.save(application);
					responceObj.setCode(200);
					responceObj.setMessage("New Application is added");
				}
			}else{
				Optional<AuthorizedApplication> optional= authorizedApplicationRepo.findByProductId(product.getId());

			authorizedApplicationRepo.delete(optional.get());
			responceObj.setCode(200);
			responceObj.setMessage(" Application is removed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responceObj;
	}
	
	
	
	
	@RequestMapping(value = "/getBundleWiseCount", method = RequestMethod.GET)
	public @ResponseBody List<LicenceCountDto> getBundleWiseCount() {
		List<LicenceCountDto> list= new  ArrayList<LicenceCountDto>();

		try {	
			
			List<Bundle> bundles= bundleAndCategoryService.getAllBundles();
			
			for(Bundle bundle:bundles){
				List<BundleApplications> applications= bundleAndCategoryService.getApplicationByBundleId(bundle.getBundleId());
				int totalCount=0;
				for(BundleApplications bundleApplications:applications){
					int count = installLicenceStockRepo.getCountOfInstallLicenceStockByproduct(bundleApplications.getApplicationName());
					totalCount+=count;
				}
				LicenceCountDto countDto= new LicenceCountDto();
				countDto.setBundleName(bundle.getBundleName());
				countDto.setCount(totalCount);
				list.add(countDto);
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/getApplicationCountByBundle", method = RequestMethod.GET)
	public @ResponseBody List<LicenceCountDto> getApplicationCountByBundle(@RequestParam("bundleName") String bundleName) {
		List<LicenceCountDto> list= new  ArrayList<LicenceCountDto>();
		Set<String> associtesStr= new  HashSet<String>();

		try {	
			
			
				List<BundleApplications> applications= bundleAndCategoryService.getbundleApplicationByBundleName(bundleName);
				for(BundleApplications bundleApplications:applications){
					int count = installLicenceStockRepo.getCountOfInstallLicenceStockByproduct(bundleApplications.getApplicationName());
					LicenceCountDto countDto= new LicenceCountDto();
					countDto.setApplicationName(bundleApplications.getApplicationName());
					countDto.setCount(count);
					list.add(countDto);
				}
				
		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/getCategoriesWiseCount", method = RequestMethod.GET)
	public @ResponseBody List<LicenceCountDto> getCategoriesWiseCount() {
		List<LicenceCountDto> list= new  ArrayList<LicenceCountDto>();
		Set<String> associtesStr= new  HashSet<String>();

		try {	
			
			List<Category> categories= bundleAndCategoryService.getAllCategory();
			
			for(Category category:categories){
				List<CategoryApplications> applications= bundleAndCategoryService.getCategoryApplicationsByCategory(category.getCategorId());
				int totalCount=0;
				for(CategoryApplications categoryApplications:applications){
					int count = installLicenceStockRepo.getCountOfInstallLicenceStockByproduct(categoryApplications.getApplicationName());
					totalCount+=count;
				}
				LicenceCountDto countDto= new LicenceCountDto();
				countDto.setCategoryName(category.getCategoryName());
				countDto.setCount(totalCount);
				list.add(countDto);
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/getApplicationCountCategory", method = RequestMethod.GET)
	public @ResponseBody List<LicenceCountDto> getApplicationCountCategory(@RequestParam("categoryName") String categoryName) {
		List<LicenceCountDto> list= new  ArrayList<LicenceCountDto>();
		Set<String> associtesStr= new  HashSet<String>();

		try {	
			
			
				List<CategoryApplications> applications= bundleAndCategoryService.getCategoryApplicationsByCategoryName(categoryName);
				for(CategoryApplications categoryApplications:applications){
					int count = installLicenceStockRepo.getCountOfInstallLicenceStockByproduct(categoryApplications.getApplicationName());
					LicenceCountDto countDto= new LicenceCountDto();
					countDto.setApplicationName(categoryApplications.getApplicationName());
					countDto.setCount(count);
					list.add(countDto);
				}
				
		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/addBundle", method = RequestMethod.POST)
	public ResponceObj addBundle(@RequestBody Bundle bundle)  {
		ResponceObj responceDTO= new ResponceObj();
		try {		 
			System.out.println("adding ");
			//bundle.setCreatedDate(new Date());
			
			
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
			//category.setCreatedDate(new Date());
			
			
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
